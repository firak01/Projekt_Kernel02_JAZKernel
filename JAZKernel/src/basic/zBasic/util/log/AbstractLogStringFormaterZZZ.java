package basic.zBasic.util.log;

import static basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ.sENUMNAME;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.reflection.position.TagTypeFileNameZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.enums.EnumMappedLogStringFormatAvailableHelperZZZ;
import basic.zBasic.util.datatype.longs.LongZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zBasic.xml.tagtype.TagByTypeZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractLogStringFormaterZZZ extends AbstractObjectWithFlagZZZ implements ILogStringFormaterZZZ, ILogStringFormatZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Globale Objekte ---
	//MERKE: Alles volatile, damit es über mehrere Threads gleich bleibt.
	protected volatile HashMap<Integer,String>hmFormatPositionString=null;
	
	//Das Fomat
	protected volatile IEnumSetMappedLogStringFormatZZZ[]ienumaMappedFormat=null;
	
	//Der LogString-Index - also von den reinen String ( nicht ggfs. hinzugefuegte XML Strings wie von ReflectCodeZZZ.getPositionCurrent() )
	//Hier als Array aller schon benutzter "einfacher" Strings. Das wird gemacht, damit die XML Strings auch weiterhin bei jeder Operation beruecksichtigt werden können.
	//protected volatile int iStringIndexToReadLbound=0;
	protected volatile ArrayListUniqueZZZ<Integer>listaintStringIndexRead=null;
	
	//Zum Buendig machen
	//Idee: Bei mehreren Instanzen per Default auf das Singleton zugreifen
	//      Aber ggfs. ueberschreibend auf einen hinterlegten zugreifen.
	protected volatile IStringJustifierZZZ objStringJustifier = null;
		
	
	//######################
	//### KONSTRUKTOR
	public AbstractLogStringFormaterZZZ() throws ExceptionZZZ{		
		super();
		AbstractLogStringFormaterNew_(null);
	}
	
	public AbstractLogStringFormaterZZZ(IStringJustifierZZZ objJustifier) throws ExceptionZZZ{
		super();
		AbstractLogStringFormaterNew_(objJustifier);
	}
	
	private boolean AbstractLogStringFormaterNew_(IStringJustifierZZZ objJustifier) throws ExceptionZZZ{
		this.setStringJustifier(objJustifier);
		
		return true;
	}
	
	
	//### GETTER / SETTER
	@Override
	public boolean hasStringJustifierPrivate() throws ExceptionZZZ{
		if(this.objStringJustifier==null) {
			return false;
		}else {
			return true;
		}		
	}
	
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp1 = this.resetStringIndexRead();

			//!!! nur resetten, wenn es ein eigener String Justifier ist
			boolean btemp2 = false;
			if(this.hasStringJustifierPrivate()) {
				btemp2 = this.getStringJustifier().reset();
			}
			
			bReturn = btemp1 | btemp2;
		}//end main:
		return bReturn;
	}
	
	@Override 
	public boolean resetStringIndexRead() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//if(this.iStringIndexToReadLbound==0) break main;			
			//this.iStringIndexToReadLbound=0;
			if(this.getStringIndexReadList().size()>=1) {
				bReturn = true;
			}
			this.getStringIndexReadList().clear();	
		}//end main:
		return bReturn;
	}
	
	@Override 
	public ArrayListUniqueZZZ<Integer> getStringIndexReadList() throws ExceptionZZZ{
		if(this.listaintStringIndexRead==null) {
			this.listaintStringIndexRead = new ArrayListUniqueZZZ<Integer>();
		}
		return this.listaintStringIndexRead;
	}
	
	@Override
	public void setStringIndexRead(ArrayListUniqueZZZ<Integer> listaintStringIndexRead) throws ExceptionZZZ{
		this.listaintStringIndexRead = listaintStringIndexRead;
	}
	
	//+++ Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################	
	@Override
	public IStringJustifierZZZ getStringJustifier() throws ExceptionZZZ {
		if(!this.hasStringJustifierPrivate()) {
			//Verwende als default das Singleton
			return SeparatorMessageStringJustifierZZZ.getInstance();
		}else {
			//Verwende als "manual override" den einmal hinterlegten StringJustifier.
			return this.objStringJustifier;
		}
	}

	@Override
	public void setStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ {
		this.objStringJustifier = objStringJustifier;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeJagged(ienumFormatLogString);
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeJagged(obj, ienumFormatLogString);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeJagged(classObj, ienumFormatLogString);
	}
	
	
	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {
		return this.computeJagged(sLogs);
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJagged(obj, ienumaFormatLogString, sLogs);		
	}
	
	
	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJagged(classObj, ienumFormatLogString, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		return this.computeJagged(ienumaFormatLogString, sLogs);
	}

	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {			
		return this.computeJagged(obj, ienumFormatLogString, sLogs);
	}
	
	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}


	//################################################
	private String computeUsingFormat_(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeUsingFormat_(classObj, null, ienumFormatLogString, sLogs);	
	}
	
	private String computeUsingFormat_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString,  IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj=null;
			if(classObjIn == null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
			
			boolean bFormatUsingControl = LogStringFormaterUtilZZZ.isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = LogStringFormaterUtilZZZ.isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = LogStringFormaterUtilZZZ.isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = LogStringFormaterUtilZZZ.isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = LogStringFormaterUtilZZZ.isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl & bFormatUsingString) {
				//Hier werden Strings mit dem Steuerungszeichen mitverarbeitet
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead);
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByControl_(classObj, ienumFormatLogString, sLogs[iStringIndexToRead]);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}else {
					sReturn = this.computeByControl_(classObj, ienumFormatLogString, null);
				}	
				
			}else if(bFormatUsingControl & !bFormatUsingString) {
				//Hier wird nur das Steuerungszeichen ohne String verarbeitet
				sReturn = this.computeByControl_(classObj, ienumFormatLogString);					
			}else if(bFormatUsingObject) {
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);				
			}else if(bFormatUsingString & !bFormatUsingControl) {				
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead);
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByString_(classObj, sLogs[iStringIndexToRead], ienumFormatLogString);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}							
										
			}else if(bFormatUsingStringXml) {			
				sReturn = this.computeByStringXml_(classObj, ienumFormatLogString, sLogs);
			}else if(bFormatUsingStringHashMap) {
				sReturn = this.computeByStringHashMap_(classObj, hmLogString, ienumFormatLogString);
			}else {
				//mache nix				
			}									
		}//end main:
		return sReturn;
	}
	
	private String computeUsingFormat_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog,  IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj=null;
			if(classObjIn == null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
									
			boolean bFormatUsingControl = LogStringFormaterUtilZZZ.isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = LogStringFormaterUtilZZZ.isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = LogStringFormaterUtilZZZ.isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = LogStringFormaterUtilZZZ.isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = LogStringFormaterUtilZZZ.isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl) {		
				String sLog = hmLog.get(ienumFormatLogString);
				sReturn = this.computeByControl_(classObj, ienumFormatLogString, sLog);
			}else if(bFormatUsingObject) {
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);			
			}else if(bFormatUsingString) {	
				String sLog = hmLog.get(ienumFormatLogString);
				sReturn = this.computeByString_(classObj, sLog, ienumFormatLogString);						
			}else if(bFormatUsingStringXml) {
				String sLog = hmLog.get(ienumFormatLogString); //Könnte ja auch ein etwas umfangreicherer Tag sein und man fischt daraus den passenden raus.
				sReturn = this.computeByStringXml_(classObj, sLog, ienumFormatLogString);
			}else if(bFormatUsingStringHashMap) {
				sReturn = this.computeByStringHashMap_(classObj, hmLog, ienumFormatLogString);
			}else {
				//mache nix				
			}									
		}//end main:
		return sReturn;
	}
	
	private String computeByControl_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!LogStringFormaterUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		    if (LogStringFormaterUtilZZZ.isFormatUsingString(ienumFormatLogString)) break main;
		    					   
			String sFormat=null; 
			String sMessageSeparator=null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
		
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	ITagByTypeZZZ objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
		        	String sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            
		        case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
		        	//ByControl?
		            sFormat = this.getHashMapFormatPositionString().get(
		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));	                    
		            sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
		            sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		              
		            sReturn = sMessageSeparator;
		            break;
		        case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
		        	//ByControl?
		            sFormat = this.getHashMapFormatPositionString().get(
		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));	                    
		            sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
		            sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		              
		
		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
		            sReturn = sMessageSeparatorTag;
		            break;
		        case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
		        	//ByControl?
		              sFormat = this.getHashMapFormatPositionString().get(
		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));	                    
		              sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
		              sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		              
		            sReturn = sMessageSeparator;
		            break;
		        case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
		        	//ByControl?
		            sFormat = this.getHashMapFormatPositionString().get(
		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));	                    
		            sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
		            sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		              
		
		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
		            sReturn = sMessageSeparatorTag;
		            break;
		        default:
		            System.out.println("AbstractLogStringFormaterZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
		                    + ienumFormatLogString.getFactor());
		            break;
		    }	
		}//end main:
		return sReturn;
	}

	
	
	private String computeByControl_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String sLogIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
            
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!LogStringFormaterUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    					   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sMessageSeparator=null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			String sLog= null;
			if(sLogIn!=null) {
				String sOuter = XmlUtilZZZ.findTextOuterXml(sLogIn);
				if(!StringZZZ.isEmpty(sOuter)) {				
					//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
					//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
					//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
					//Darum entfernen wir dies ggfs.															
					sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
					if(StringZZZ.isEmpty(sOuter)) break main;
					sLog = StringZZZ.joinAll(sLog, sOuter);
				}else {
					//Also: sOuter ist Leerstring oder Null UND es nicht explizit ein XML, nur dann den String übernehme
					boolean bContainsXml = XmlUtilZZZ.isXmlContained(sLogIn);	
					if(bContainsXml) {
						//mache nix
					}else {
						sLog = StringZZZ.joinAll(sLog, sOuter);
					}
				}				
			}
			//Ziel ist es eine unnoetigerweise erzeugte Leerzeile mit KommentarSeparator zu verhindern.
			if(sLog==null)break main; //Ein explizit uebergebener Leerstring gilt aber.
			
			
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            default:
	                System.out.println("AbstractLogStringFormaterZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	
	private String computeByObject_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//!!! Verwende hier nur einfache Methoden und keine Methoden, die wiederum Logging verwenden, sonst Endlosschleifengefahr !!!
		
		String sReturn = null;
		main:{
		 	Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;						
			}else {
				classObj = classObjIn;
			}
				
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!LogStringFormaterUtilZZZ.isFormatUsingObject(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
	    
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sDate = null;
			GregorianCalendar d=null; Integer iDateYear = null; Integer iDateMonth = null; Integer iDateDay = null; Integer iTimeHour = null; Integer iTimeMinute = null;

			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAME_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAME_STRING));
	                        sReturn = String.format(sFormat, classObj.getName());
	                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAME_XML:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAME_XML));
	                        String sClassname = String.format(sFormat, classObj.getName());
	                        sClassname = sPrefixSeparator + sClassname + sPostfixSeparator;
	                        
	                        ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME, sClassname);
	            	 		String sClassnameTag = objTagClassname.getElementString();
	                        sReturn = sClassnameTag;
	    	                break;
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING));
	                        sReturn = String.format(sFormat, classObj.getSimpleName());
	                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING));
	                        String sClassnameSimple = String.format(sFormat, classObj.getSimpleName());
	                        sClassnameSimple = sPrefixSeparator + sClassnameSimple + sPostfixSeparator;
	                        
	                        ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME, sClassnameSimple);
	            	 		String sClassnameTag = objTagClassname.getElementString();
	                        sReturn = sClassnameTag;
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
	                        String sFileName = classObj.getSimpleName() + ".java";
	                        //NEIN: ENDLOSSCHLEIFE weil darin ebenfalls geloggt wird.
	                        //String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
	                        //ALSO: Einfacher halten.
	                        String sFilePathTotal = sDirectory + StringZZZ.char2String(IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR) + sFileName;
	                        sReturn = String.format(sFormat, sFilePathTotal);
	                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
	                        String sFileName = classObj.getSimpleName() + ".java";
	                        //NEIN: ENDLOSSCHLEIFE weil darin ebenfalls geloggt wird.
	                        //String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
	                        //ALSO: Einfacher halten.
	                        String sFilePathTotal = sDirectory + StringZZZ.char2String(IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR) + sFileName;
	                        String sClassFileName = String.format(sFormat, sFilePathTotal);
	                        sClassFileName = sPrefixSeparator + sClassFileName + sPostfixSeparator;
	                        
	                        ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.FILENAME, sClassFileName);
	            	 		String sClassnameTag = objTagClassname.getElementString();
	                        sReturn = sClassnameTag;
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_DATE_STRING:
	                d = new GregorianCalendar();
	                iDateYear = new Integer(d.get(Calendar.YEAR));
	                iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	                iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	                iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	                iTimeMinute = new Integer(d.get(Calendar.MINUTE));

	                sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	                        + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();

	                sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_DATE_STRING));
	                sReturn = String.format(sFormat, sDate);
                    sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                break;

	            case ILogStringFormatZZZ.iFACTOR_DATE_XML:
	                d = new GregorianCalendar();
	                iDateYear = new Integer(d.get(Calendar.YEAR));
	                iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	                iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	                iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	                iTimeMinute = new Integer(d.get(Calendar.MINUTE));

	                sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	                        + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();

	                sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_DATE_STRING));
	                sDate = String.format(sFormat, sDate);
                    sDate = sPrefixSeparator + sDate + sPostfixSeparator;
                    
                    ITagByTypeZZZ objTagDate = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.DATE, sDate);
        	 		String sDateTag = objTagDate.getElementString();
                    sReturn = sDateTag;
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_THREADID_STRING:
	                if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_THREADID_STRING));
	                    long lngThreadID = Thread.currentThread().getId();
	                    sReturn = String.format(sFormat, lngThreadID);
                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_THREADID_XML:
	                if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_THREADID_XML));
	                    long lngThreadId = Thread.currentThread().getId();     
	        			String sThreadId = LongZZZ.longToString(lngThreadId);
	        			sThreadId = String.format(sFormat, sThreadId);
	        			sThreadId = sPrefixSeparator + sThreadId + sPostfixSeparator;
	        			ITagByTypeZZZ objTagThreadId = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID, sThreadId);
	        			String sThreadIdTag = objTagThreadId.getElementString();
	                    
	                    sReturn = sThreadIdTag;                        
	                }
	                break;
	                
	            default:
	                System.out.println("AbstractLogStringFormaterZZZ.computeByObject_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	private String computeByObject_Justified_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}else {
				classObj = classObjIn;
			}
			
			sReturn = this.computeByObject_(classObj, ienumFormatLogString);

			//20251128: Der MessageSeparator ist nun eine eigene Formatanweisung
			//Damit hiervon ggfs. folgende Kommentare abgegrenzt werden koennen
			//sReturn = sReturn  + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //ABER: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			sReturn = this.getStringJustifier().justifyInfoPart(sReturn);
		}//end main:
		return sReturn;
	}
	
	
	private String computeByString_(Class classObjIn, String sLogIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}else {
				classObj = classObjIn;
			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!LogStringFormaterUtilZZZ.isFormatUsingString(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			
			
			//+++ Pruefe darauf, ob es ein XML-String ist. Wenn ja... Abbruch. Ansonsten wird ggfs. <filepositioncurrent> als normaler Logeintrag behandelt.
			//    Dieser String wird naemlich über das Array saLog gerettet und uebergeben ( aus der entsprechenden ermittelnden ReflectionZZZ Methode ).
			
			//Nein, dadurch wird ggfs. Text vor oder nach dem XML unterschlagen.
			//boolean bXml = XmlUtilZZZ.isXmlContained(sLogIn);
			//if(bXml) break main; //hier werden nur einfach Strings verarbeitet und keine XML Strings...
			
			//Statt dessen
			//+++ Pruefe darauf, ob Text vor oder hinter XML steht (oder alles, wenn kein XML).
			String sOuter = XmlUtilZZZ.findTextOuterXml(sLogIn);
			if(StringZZZ.isEmpty(sOuter)) break main;
			
			//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
			//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
			//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
			//Darum entfernen wir dies ggfs.
			sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
			if(StringZZZ.isEmpty(sOuter)) break main;
			
			
			
			//+++++++++++++++++++++++++++					
			String sLog = sOuter;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
						
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sLogTag=null;
			
			switch(ienumFormatLogString.getFactor()) {		
			case ILogStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING));
								
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				
//				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
			
			case ILogStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING:												
			 	sFormat = this.getHashMapFormatPositionString().get(
                     new Integer(ILogStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING));
			 	
			 	sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType01 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE01, sLog);
     			sLogTag = objTagStringType01.getElementString();
                 
                sReturn = sLogTag; 				
				break;
			case ILogStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING));
				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);				
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
			case ILogStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING:
				sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType02 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE02, sLog);
     			sLogTag = objTagStringType02.getElementString();
                 
                sReturn = sLogTag; 				
				break;
				
			case ILogStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING));
				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
			
			case ILogStringFormatZZZ.iFACTOR_STRINGTYPE03_XML_BY_STRING:
				sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType03 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE03, sLog);
     			sLogTag = objTagStringType03.getElementString();
                 
                sReturn = sLogTag; 				
				break;
			
			case iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;
				break;
			case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
				//SOLLTE HIER NICHT ERSCHEINEN SONDERN EINE EIGENE FORMATKLASSE SEIN computeByControl_(...)
//				//By HashMap?
//				 sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	             sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);	               
	              break;
			case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
				//SOLLTE HIER NICHT ERSCHEINEN SONDERN EINE EIGENE FORMATKLASSE SEIN computeByControl_(...)
//				//By HashMap?
//				 sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	             sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);	               
	              break;
			case ILogStringFormatZZZ.iFACTOR_LINENEXT_STRING:
				//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
				break;
			default:
				System.out.println("AbstractLogStringFormaterZZZ.computeByString_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
				break;					
			}				
						
		}//end main:
		return sReturn;		
	}
	
	private String computeByStringXml_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumMappedFormat, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!LogStringFormaterUtilZZZ.isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				if(sReturn==null) {
					sReturn = this.compute(classObj, ienumMappedFormat);
				}else {
					sReturn = sReturn + this.compute(classObj, ienumMappedFormat);
				}
				break main;
			}
			
			//###### Mit Strings, alle durchsuchen.			
			for(String sLog:sLogs) {
				if(sReturn==null) {
					sReturn = this.computeByStringXml_(classObj, sLog, ienumMappedFormat);
				}else {
					sReturn = sReturn + this.computeByStringXml_(classObj, sLog, ienumMappedFormat);
				}
			}
		}//end main:
		return sReturn;
	}
	
	private String computeByStringXml_(Class classObjIn, String sLogIn, IEnumSetMappedLogStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!LogStringFormaterUtilZZZ.isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();					    

			String sLog=sLogIn;
			ITagTypeZZZ objTagTypePositionCurrent = null; ITagTypeZZZ objTagTypeLineNummer = null; ITagTypeZZZ objTagTypeFileName = null; ITagTypeZZZ objTagTypeFilePosition = null; ITagTypeZZZ objTagTypeMethod = null;
						
			String sTagTemp=null;
			switch(ienumMappedFormat.getFactor()) {
			//#######################################################################
			//### XML AUS DEM XML-STRING WERT ZURUECKGEBEN
			//#######################################################################
			case ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_XML:			
				objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFilePosition = new TagByTypeZZZ(objTagTypeFilePosition);
					objTagFilePosition.setValue(sTagTemp);
					sReturn = objTagFilePosition.getElementString();	
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_XML:
				objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagMethod = new TagByTypeZZZ(objTagTypeMethod);
					objTagMethod.setValue(sTagTemp);
					sReturn = objTagMethod.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;					
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_XML:
				objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagLineNumber = new TagByTypeZZZ(objTagTypeLineNummer);
					objTagLineNumber.setValue(sTagTemp);
					sReturn = objTagLineNumber.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_XML:
				objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFileName = new TagByTypeZZZ(objTagTypeFileName);
					objTagFileName.setValue(sTagTemp);
					sReturn = objTagFileName.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML_BY_XML:
				objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagPositionCurrent = new TagByTypeZZZ(objTagTypePositionCurrent);
					objTagPositionCurrent.setValue(sTagTemp);
					sReturn = objTagPositionCurrent.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
				
			//#######################################################################
			//### EINFACHEN STRING AUS DEM XML-STRINGWERT ZURUECKGEBEN
			//#######################################################################
			case ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_XML:			
				objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_XML:
				objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}		
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_XML:
				objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}		
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_XML:
				objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_POSITIONCURRENT_STRING_BY_XML:
				objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			default:
				System.out.println("AbstractLogStringFormaterZZZ.computeByStringXml_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumMappedFormat.getFactor());
				break;					
			}								
		}//end main:
		return sReturn;		
	}
	
	//######################################
	private String computeLinesInLog_Justified_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			ArrayListUniqueZZZ<String> listasLine = this.computeLinesInLog_(classObj, hmLog);
			
			if(listasLine.size()>=2) {
				//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
				ArrayListUniqueZZZ<String>listasLineReversed1 = (ArrayListUniqueZZZ<String>) ArrayListUtilZZZ.reverse(listasLine);
				ArrayListUniqueZZZ<String>listasLineReversedJustified1 = new ArrayListUniqueZZZ<String>();
				for(String sLine : listasLineReversed1) {
					String sLineJustified = this.getStringJustifier().justifyInfoPart(sLine);
					listasLineReversedJustified1.add(sLineJustified);
				}
				
				ArrayListUniqueZZZ<String>listasLineReversed2 = (ArrayListUniqueZZZ<String>) ArrayListUtilZZZ.reverse(listasLineReversedJustified1);
				ArrayListUniqueZZZ<String>listasLineReversedJustified2 = new ArrayListUniqueZZZ<String>();
				for(String sLine : listasLineReversed2) {
					String sLineJustified = this.getStringJustifier().justifyInfoPart(sLine);
					listasLineReversedJustified2.add(sLineJustified);
				}
				
				//Die Zeilen so verbinden, das sie mit einem "System.println" ausgegeben werden können.
				for(String sLineJustified : listasLineReversedJustified2) {
					if(sReturn.equals("")){
						sReturn = sLineJustified;
					}else {					
						sReturn = sReturn + StringZZZ.crlf() + sLineJustified;
					}
				}							
			}else {
				String sLine = listasLine.get(0);
				if(sLine!=null) {
					sReturn = this.getStringJustifier().justifyInfoPart(sLine);					
				}
			}		
		}//end main:
		return sReturn;

	}
	
	private String computeLineInLog_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
				
			//Iteration über die Einträge, die ja in einer Zeile sein sollen, findet darin statt	                  	                  
            sReturn = this.computeByStringHashMap_(classObj, hmLog);	                              		
		}//end main:
		return sReturn;

	}
	
	
	
	private ArrayListUniqueZZZ<String> computeLinesInLog_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		ArrayListUniqueZZZ<String>  alsReturn = new ArrayListUniqueZZZ<String>();
		main:{	
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			String sLog = null;
			
			//TODOGOON20251117;//Eigentlich müssten hier mit dem .LINENEXT Argument versehen die HashMap gesplittet werden.
			//
			
			//Ermittle in einer Schleife jede Zeile
			//Iteration über die mit LINENEXT gesteuerten Einträge
	        //for (Entry<IEnumSetMappedLogStringFormatZZZ, String> entry : hmLog.entrySet()) {
	        //    IEnumSetMappedLogStringFormatZZZ enumAsKey = entry.getKey();	                   
	            sLog = this.computeLineInLog_(classObj, hmLog);	           
	            if(sLog!=null) {
	            	alsReturn.add(sLog);
				}
	        //}
		}//end main:
		return alsReturn;

	}
	
	private String computeByStringHashMap_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = "";
		main:{		
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(hm == null) 	break main;
						
			//Der zu verwendende Logteil
			String sLogUsed=null;
						
			//Ermittle in einer Schleife den auszugebenden Teil
			// Iteration über die Einträge
	        for (Entry<IEnumSetMappedLogStringFormatZZZ, String> entry : hm.entrySet()) {
	            IEnumSetMappedLogStringFormatZZZ enumAsKey = entry.getKey();	                   
	            sLogUsed = this.computeUsingFormat_(classObj, hm, enumAsKey);	           
	            if(sLogUsed!=null) {
	            	if(StringZZZ.isEmpty(sReturn)) {
	            		sReturn = sLogUsed;
	            	}else {	            		
	            		sReturn = sReturn + sLogUsed;	
	            	}
				}
	        }
	        
	        
		}//end main:
		return sReturn;

	}
	
	private String computeByStringHashMap_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString, IEnumSetMappedLogStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(hmLogString == null) break main;
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if (!LogStringFormaterUtilZZZ.isFormatUsingHashMap(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();
			
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;	
			
			//TODO: Weil es immer das gleiche ist, scheint es die SWITCH Anweisung eigentlich nicht zu benoetigen.
	        switch (ienumMappedFormat.getFactor()) {
	        	case ILogStringFormatZZZ.iFACTOR_DATE_XML_BY_HASHMAP:
	        		sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	        		break;
	        	case ILogStringFormatZZZ.iFACTOR_THREADID_XML_BY_HASHMAP:	        		
	        		sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	        		break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;					
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;
	                
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP:	            	
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}	            	
	            	break;
	            	
	            case ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_HASHMAP:	            	               
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            default:
	                System.out.println("AbstractLogStringStringZZZ.computeByStringHashMap_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumMappedFormat.getFactor());
	                break;
	        }		
		}//end main:
		return sReturn;
	}
	
	
		
	
	
	
	/** Beruecksichtigt .LINENEXT als Steuerkennzeichen und teilt das Array entsprechend auf.
	 * @param classObjIn
	 * @param saLog
	 * @param iStringIndexToReadFromStart
	 * @param ienumaFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.11.2025, 08:08:19
	 */
	private ArrayListUniqueZZZ<String> computeLinesInLog_(Class<?> classObjIn, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		ArrayListUniqueZZZ<String> alsReturn = new ArrayListUniqueZZZ<String>();;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
						
			IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString=null;
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogStringIn)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}else {
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}
						
			
			//###### Splitte das Array der Formatanweisungen auf an der "LINENEXT" STEUERANWEISUNG
			List<IEnumSetMappedLogStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogString, (IEnumSetMappedLogStringFormatZZZ)ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedLogStringFormatZZZ.class);			
			for(IEnumSetMappedLogStringFormatZZZ[] ienumaLine: listaEnumLine){
				String sLine = computeLineInLog_(classObj, ienumaLine, sLogs);
				alsReturn.add(sLine);				
			}							
		}//end main:
		return alsReturn;
	}
	
	
	private String computeLinesInLog_Jagged_(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaFormatLogString[0] = ienumFormatLogString;
			
			sReturn = computeLinesInLog_Jagged_(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	
	private String computeLinesInLog_Jagged_(Object obj, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			sReturn = computeLinesInLog_Jagged_(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	
	private String computeLinesInLog_Jagged_(Class<?> classObjIn, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			ArrayListUniqueZZZ<String> listasLine = this.computeLinesInLog_(classObj, ienumaFormatLogString, sLogs);
			sReturn = ArrayListUtilZZZ.implode(listasLine, StringZZZ.crlf());				
		}//end main:
		return sReturn;
	}
	
	private String computeLinesInLog_Jagged_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			ArrayListUniqueZZZ<String> listasLine = this.computeLinesInLog_(classObj, hmLog);
			sReturn = ArrayListUtilZZZ.implode(listasLine, StringZZZ.crlf()); 			
		}//end main:
		return sReturn;

	}
	
	
	
	private String computeLinesInLog_Justified_(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaFormatLogString[0] = ienumFormatLogString;
			
			sReturn = computeLinesInLog_Justified_(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	
	private String computeLinesInLog_Justified_(Object obj, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			sReturn = computeLinesInLog_Justified_(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	
	private String computeLinesInLog_Justified_(Class<?> classObjIn, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;		
		  	if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			ArrayListUniqueZZZ<String> listasLine = this.computeLinesInLog_(classObj, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//sReturn = this.getStringJustifier().justifyInfoPart(sReturn);
			
			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, listasLine);				
		}//end main:
		return sReturn;
	}
	
	
	/** .LINENEXT als Steuerkennzeichen wird hier nicht mehr beruecksichtig
	 * @param classObjIn
	 * @param saLog
	 * @param iStringIndexToRead
	 * @param ienumaFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.11.2025, 08:08:19
	 */
	private String computeLineInLog_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
				
			IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString=null;
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogStringIn)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}else {
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}
		
		
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				for(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
					String sValue = this.computeLinePartInLog_(classObj, ienumFormatLogString, sLogs);//this.computeByObject_(classObj, ienumFormatLogString);
					if(sValue!=null) {
						if(sReturn==null) {
							sReturn = sValue;
						}else {
							sReturn = sReturn + sValue; 
						}
					}
				}
				break main;
			}
			
						
			//####### Mit Strings
			for(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
				String sValue = this.computeLinePartInLog_(classObj, ienumFormatLogString, sLogs);
				if(sValue!=null) {
					if(sReturn==null) {
						sReturn = sValue;
					}else {
						sReturn = sReturn + sValue; 
					}
				}
			}
			
			//Also eine Zeile, die nur den Kommentartrenner enthaelt ist keine Zeile.
			String sCommentSeparatorFormated = LogStringFormaterUtilZZZ.computeLinePartInLog_ControlCommentSeparator();
			if(sReturn!=null && sReturn.equalsIgnoreCase(sCommentSeparatorFormated)){
				sReturn = null;
			}
		}//end main:
		return sReturn;
	}
			
		/** .LINENEXT als Steuerkennzeichen wird hier nicht mehr beruecksichtig
		 * @param classObjIn
		 * @param saLog
		 * @param iStringIndexToRead
		 * @param ienumaFormatLogString
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 09.11.2025, 08:08:19
		 */
		private String computeLinePartInLog_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
			String sReturn = null;
			main:{								
				Class classObj = null;		
				if(classObjIn==null) {
					//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
					ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;			
				}else {
					classObj = classObjIn;
				}
				
				//###### Ohne irgendeinen String
				if(ArrayUtilZZZ.isNull(sLogs)) {
					//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.						
					sReturn = this.computeByObject_(classObj, ienumFormatLogString); 					
					break main;
				}
		
			
				//##### Mit zu verarbeitenden Strings			
				sReturn = this.computeUsingFormat_(classObj, ienumFormatLogString, sLogs);				
		}//end main:
		return sReturn;
	}


	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//ohne explizite Formatangabe 
	
	//OHNE KLASSEN ODER OBJEKTANGABE MACHT DAS KEINEN SINN		
	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		return this.computeJagged(obj, sLogs);
	}
		
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJagged(hm);
	}
	
	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJagged(obj, hm);
	}
	
	//##########################################################
	
	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		return this.computeJagged(classObj, sLogs);
	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe	
	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		return this.computeJagged(classObj, hmLog);
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setFormatPositionsMapped(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedFormat) {
		this.ienumaMappedFormat=ienumaMappedFormat;
	}

	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMapped() throws ExceptionZZZ {
		if(ArrayUtilZZZ.isNull(this.ienumaMappedFormat)) {
			this.ienumaMappedFormat = this.getFormatPositionsMappedCustom();
			
			//Wenn im custom nix drin ist, default nehmen
			if(ArrayUtilZZZ.isNull(this.ienumaMappedFormat)) {
				this.ienumaMappedFormat = this.getFormatPositionsMappedDefault();
			}
		}
		return this.ienumaMappedFormat;
	}
	
	
	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMappedDefault() throws ExceptionZZZ {
		
		//Merke: Verwendet wird z.B. ein LogString in dieser Form, den es abzubilden gilt:
		//       In getPositionCurrent() wird schon die ThreadID zum ersten Mal gesetzt. Damit das Log lesbarer wird soll vor dem Status noch der Thread gesetzt werden.
		//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
		
		
		
		//Also Classname und Thread z.B. raus. Das 1. iARGNext ist für getPositionCurrent(), das 2. ARGNext für den Text ab "Status...", das 3. ARGNext als Reserve.
		
		//Kein Array, s. ChatGPT 20260110
		List<?> listaReturn = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(this.getClass(), sENUMNAME);
		
		//Array automatisch aus dem Enum errechnen.
		IEnumSetMappedLogStringFormatZZZ[] ienumaReturn = (IEnumSetMappedLogStringFormatZZZ[]) ArrayListUtilZZZ.toArray((ArrayList<?>) listaReturn);
		return ienumaReturn;
	}
	
	@Override
	public abstract IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMappedCustom();
	
	//+++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setHashMapFormatPositionString(HashMap<Integer, String> hmFormatPostionString) {
		this.hmFormatPositionString = hmFormatPostionString;
	}
	
	@Override
	public HashMap<Integer,String>getHashMapFormatPositionString() throws ExceptionZZZ{
		if(this.hmFormatPositionString==null) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringCustom();
		}else if(this.hmFormatPositionString.isEmpty()) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringCustom();			
		}
		
		//Wenn im custom nix drin ist, default nehmen
		if(this.hmFormatPositionString==null) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringDefault();
		}else if(this.hmFormatPositionString.isEmpty()) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringDefault();
		}
		return this.hmFormatPositionString;
	}
	
	/* (non-Javadoc)
	 * @see basic.zBasic.util.log.ILogStringFormaterZZZ#getHashMapFormatPositionStringDefault()
	 * 
	 * Macht eine HashMap mit dem Enum des Formats als Key 
	 * und dem "Abarbeitungstypen" als Wert
	 */
	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringDefault() throws ExceptionZZZ {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		main:{
			//HashMap automatisch aus dem Enum errechnen.			
			ArrayList<IEnumSetMappedLogStringFormatZZZ>  listaEnumMappedLogStringFormat = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(this, sENUMNAME);
			for(IEnumSetMappedLogStringFormatZZZ ienum : listaEnumMappedLogStringFormat) {
				IEnumSetMappedLogStringFormatZZZ ienumLogString = (IEnumSetMappedLogStringFormatZZZ) ienum;
				hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString.getFormat());
			}				
		}//end main:
		return hmReturn;
	}
	
	@Override 
	public abstract HashMap<Integer,String>getHashMapFormatPositionStringCustom() throws ExceptionZZZ;

	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	@Override
	public int computeFormatPositionsNumber() throws ExceptionZZZ {
		//FGL 20240421 - experimentell und nicht notwendig, solange ich den Weg der Rueckumwandlung noch nicht kann.
		int iReturn = 0;
		main:{
			IEnumSetMappedLogStringFormatZZZ[]ienumaMappedFormat = this.getFormatPositionsMapped();
			if(ArrayUtilZZZ.isNull(ienumaMappedFormat))break main;
			
			int iPosition=0;
			for(IEnumSetMappedLogStringFormatZZZ ienumMappedFormat : ienumaMappedFormat) {
				iPosition++;
				iReturn = iReturn + PrimeNumberZZZ.primePosition(ienumMappedFormat.getFactor(), iPosition);
			}
		}//end main:
		return iReturn;
	}
	
	//###################################################
	//### aus ILogStringFormatComputerJaggedZZZ
	//###################################################
	

	@Override
	public String computeJagged(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
				
		Class classObj = this.getClass();
		return computeByObject_(classObj, ienumFormatLogString);

	}
	
	
	@Override
	public String computeJagged(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode.
				
		Class classObj = this.getClass();		    		
		return this.computeLinesInLog_Jagged_(classObj, ienumaFormatLogString, sLogs);
		//return this.computeLinesInLog_Jagged_(ienumaFormatLogString, sLogs);
	}
	

	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
				
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
		
		Class classObj = null;
	    if (obj == null) {
	    	classObj = this.getClass();	
	    }else {
	    	classObj = obj.getClass();
	    }
	    
	    return computeByObject_(classObj, ienumFormatLogString);
	}
		
	@Override
	public String computeJagged(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
		
		Class classObj = null;
	    if (classObjIn == null) {
	    	classObj = this.getClass();	
	    }else {
	    	classObj = classObjIn;
	    }
		return computeByObject_(classObj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
				
		Class classObj = null;
	    if (classObjIn == null) {
	    	classObj = this.getClass();	
	    }else {
	    	classObj = classObjIn;
	    }
		return this.computeLinesInLog_Justified_(classObj, hmLog);
	}
	
	@Override
	public String computeJagged(String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		
		Class classObj = this.getClass();
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(classObj, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
		//ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_((IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;	
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 
		return this.computeLinesInLog_Jagged_(obj, ienumaFormatLogString, sLogs);
	}
	
	@Override
	public String computeJagged(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs)	throws ExceptionZZZ {
		String sReturn = "";
		main:{								
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			Class classObj = null;
			if(classObjIn==null) {
				classObj = this.getClass();
			}else {
				classObj = classObjIn;
			}
			
			if(StringArrayZZZ.isEmpty(sLogs)) {							
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);										
			}else {
				sReturn = this.computeLinePartInLog_(classObj, ienumFormatLogString, sLogs);
			}
			
		}//end main:
		return sReturn;
	}
	
	
	@Override
	public String computeJagged(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			Class classObj = null;
			if(obj==null) {
				classObj = this.getClass();
			}else {
				classObj = obj.getClass();
			}
			ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(classObj, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
			sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
	
	@Override
	public String computeJagged(Class classObjIn, String... sLogs) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
	
		Class classObj = null;
		if(classObjIn==null) {
			classObj = this.getClass();
		}else {
			classObj = classObjIn;
		}
		
		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(classObj, (IEnumSetMappedLogStringFormatZZZ[])null, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;
	}
	
	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;
			if(obj==null) {
				classObj = this.getClass();
			}else {
				classObj = obj.getClass();
			}
			
			sReturn = this.computeJagged(classObj, ienumFormatLogString, sLogs);				
		}//end main:
		return sReturn;
	}

	
	@Override
	public String computeJagged(Class classObjIn, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		Class classObj = null;
		if(classObjIn==null) {
			classObj = this.getClass();
		}else {
			classObj = classObjIn;
		}
		
		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(classObj, ienumaFormatLogString, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;
	}
	
	

	@Override
	public String computeJagged(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		Class classObj = null;
		if(obj==null) {
			classObj = this.getClass();
		}else {
			classObj = obj.getClass();
		}
		
		return this.computeLinesInLog_Jagged_(classObj, hm);
	}
	
	@Override
	public String computeJagged(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		return this.computeLinesInLog_Jagged_(this.getClass(), hm);
	}
	
	//###################################################
	//### aus ILogStringFormatComputerJustifiedZZZ
	//### Normalerweise sorgt der FormatManager für das Justified, 
	//### aber ggfs. soll der Formater selbst auch buendige Zeilen produzieren
	//###################################################
	
	@Override
	public String computeJustified(String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
		
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			sReturn = this.computeJagged(sLogs);
			if(StringZZZ.isEmpty(sReturn)) break main;
		
		
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
		}//end main:
		return sReturn;
	}
	
	@Override
	public String computeJustified(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		return this.computeLinesInLog_Justified_(this.getClass(), hm);
	}
	
	
	@Override
	public String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			return this.computeLinesInLog_Justified_(obj.getClass(), hm);
	}
	
//	@Override
//	public String computeJustified(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(ienumFormatLogString);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}

//	@Override
//	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(obj, ienumFormatLogString);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}

	@Override
	public String computeJustified(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			sReturn = this.computeJagged(obj, sLogs);
			if(StringZZZ.isEmpty(sReturn)) break main;
		
		
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
		}//end main:
	return sReturn;
	}

	@Override
	public String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			sReturn = this.computeJagged(classObj, sLogs);
			if(StringZZZ.isEmpty(sReturn)) break main;
		
		
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
		}//end main:
	return sReturn;
	}

//	@Override
//	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(classObj, ienumFormatLogString);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}

//	@Override
//	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(obj, ienumFormatLogString, sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}

//	@Override
//	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(classObj, ienumFormatLogString, sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}

	@Override
	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			sReturn = this.computeJagged(obj, ienumaFormatLogString, sLogs);
			if(StringZZZ.isEmpty(sReturn)) break main;
		
		
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
		}//end main:
	return sReturn;
	}

	@Override
	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString,	String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			sReturn = this.computeJagged(classObj, ienumFormatLogString, sLogs);
			if(StringZZZ.isEmpty(sReturn)) break main;
		
		
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
		}//end main:
	return sReturn;
	}

	//###################################################
	//### FLAG: ILogStringFormaterZZZ
	//###################################################
	@Override
	public boolean getFlag(ILogStringFormaterZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(ILogStringFormaterZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(ILogStringFormaterZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ILogStringFormaterZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(ILogStringFormaterZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(ILogStringFormaterZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
