package basic.zBasic.util.log;

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
import basic.zBasic.util.abstractList.HashMapIndexedObjektZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
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
			return StringJustifierZZZ.getInstance();
		}else {
			//Verwende als "manual override" den einmal hinterlegten StringJustifier.
			return this.objStringJustifier;
		}
	}

	@Override
	public void setStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ {
		this.objStringJustifier = objStringJustifier;
	}
	
	//############################################################
	public static boolean isFormatUsingString(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_STRING:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}
	
	public static boolean isFormatUsingStringXml(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_STRINGXML:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}
	
	public static boolean isFormatUsingHashMap(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_STRINGHASHMAP:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}
	
	
	public static boolean isFormatUsingObject(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_OBJECT:
				bReturn = true;
				break;
			case ILogStringFormatZZZ.iARG_SYSTEM:
				bReturn = true;
				break;
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}
	
	public static boolean isFormatUsingControl(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringFormatZZZ.iARG_CONTROL:
				bReturn = true;
				break;			
			default:
				bReturn = false;
			};
		}//end main:
		return bReturn;
		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
				
		return computeByObject_(this.getClass(), ienumFormatLogString);
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
						
	    if (obj == null) {
	    	return computeByObject_(this.getClass(), ienumFormatLogString);
	    }else {
	    	return computeByObject_(obj.getClass(), ienumFormatLogString);
	    }
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
		
		return computeByObject_(classObj, ienumFormatLogString);
	}
	
	
	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(null, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;		
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 
		return this.computeLinesInLog_Justified_(obj, ienumaFormatLogString, sLogs);	
	}
	
	private String computeUsingFormat_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeUsingFormat_(classObjIn, null, ienumFormatLogString, sLogs);	
	}
	
	private String computeUsingFormat_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString,  IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj=null;
			if(classObjIn == null) {
				classObj = this.getClass();
			}else {
				classObj = classObjIn;
			}
			
			boolean bFormatUsingControl = isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl) {
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
				
			}else if(bFormatUsingObject) {
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);				
			}else if(bFormatUsingString) {				
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
				classObj = this.getClass();
			}else {
				classObj = classObjIn;
			}
									
			boolean bFormatUsingControl = isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = isFormatUsingHashMap(ienumFormatLogString);
									
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
	
	
	private String computeByControl_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String sLogIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
					    
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
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
	                  sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                  sReturn = sPrefixSeparator + sReturn + sLog + sPostfixSeparator;
	                break;
	            default:
	                System.out.println("AbstractLogStringZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	
	private String computeByObject_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingObject(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
		    		    
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;						
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAME_STRING_BY_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAME_STRING_BY_STRING));
	                        sReturn = String.format(sFormat, classObj.getName());
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING_BY_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING_BY_STRING));
	                        sReturn = String.format(sFormat, classObj.getSimpleName());
	                    }
	                }
	                break;
	            case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_STRING));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
	                        String sFileName = classObj.getSimpleName() + ".java";
	                        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
	                        sReturn = String.format(sFormat, sFilePathTotal);
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_DATE_STRING:
	                GregorianCalendar d = new GregorianCalendar();
	                Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	                Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	                Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	                Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	                Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE));

	                String sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	                        + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();

	                sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_DATE_STRING));
	                sReturn = String.format(sFormat, sDate);
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
	                }
	                break;
	              
	            default:
	                System.out.println("AbstractLogStringZZZ.computeByObject_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	private String computeByObject_Justified_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = this.computeByObject_(classObjIn, ienumFormatLogString);

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
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingString(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			
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
//		    Class classObj = null;		
//			if(classObjIn==null) {
//				classObj = this.getClass();			
//			}else {
//				classObj = classObjIn;
//			}

//			String sLog=sLogIn;
			String sLog = sOuter;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
						
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			
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
			case ILogStringFormatZZZ.iFACTOR_LINENEXT_STRING:
				//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
				break;
			default:
				System.out.println("AbstractLogStringZZZ.computeByString_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
				break;					
			}				
						
		}//end main:
		return sReturn;		
	}
	
	private String computeByStringXml_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumMappedFormat, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
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
			
			//Umgib diese XML-Tags noch mit einem weiteren, kuenstlichen Tag, der das Ergebnis von ReflectCodeZZZ.getMethod... und .getPositionInFile... zusammenfasst.
//			if(sReturn!=null) {
//				ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
//				sReturn = objTagPositionCurrent.getElementString();
//			}
		}//end main:
		return sReturn;
	}
	
	private String computeByStringXml_(Class classObjIn, String sLogIn, IEnumSetMappedLogStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();
			
//		    Class classObj = null;		
//			if(classObjIn==null) {
//				classObj = this.getClass();			
//			}else {
//				classObj = classObjIn;
//			}

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
			default:
				System.out.println("AbstractLogStringZZZ.computeByStringXml_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumMappedFormat.getFactor());
				break;					
			}								
		}//end main:
		return sReturn;		
	}
	
	//######################################
	private String computeLinesInLog_Justified_(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
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
	
	private String computeLineInLog_(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
						
			//Iteration über die Einträge, die ja in einer Zeile sein sollen, findet darin statt	                  	                  
            sReturn = this.computeByStringHashMap_(classObj, hmLog);	                              		
		}//end main:
		return sReturn;

	}
	
	
	
	private ArrayListUniqueZZZ<String> computeLinesInLog_(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		ArrayListUniqueZZZ<String>  alsReturn = new ArrayListUniqueZZZ<String>();
		main:{	
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
			if(hm == null) 	break main;
						
			Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
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
			if(hmLogString == null) 	break main;
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if (!isFormatUsingHashMap(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();
			
		    Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
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
	

//	@Override
//	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
//		return this.compute(this.getClass(), sLog, ienumaFormatLogString);
//	}
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(this.getClass(), ienumaFormatLogString, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;
	}

	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {			
		if(obj==null) {
			return this.compute(this.getClass(), ienumFormatLogString, sLogs);
		}else {
			return this.compute(obj.getClass(), ienumFormatLogString, sLogs);
		}			
	}
	
	
	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{			
						
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
						
			if(StringArrayZZZ.isEmpty(sLogs)) {							
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);
											
			}else {
				sReturn = this.computeLineInLog_(classObj, ienumFormatLogString, sLogs);
			}
			
		}//end main:
		return sReturn;
	}
	
	
	@Override
	public String compute(Class classObjIn, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(classObjIn, ienumaFormatLogString, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
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
						
			Class<?> classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
			//###### Splitte das Array der Formatanweisungen auf an der "LINENEXT" STEUERANWEISUNG
			List<IEnumSetMappedLogStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogString, (IEnumSetMappedLogStringFormatZZZ)ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedLogStringFormatZZZ.class);			
			for(IEnumSetMappedLogStringFormatZZZ[] ienumaLine: listaEnumLine){
				String sLine = computeLineInLog_(classObj, ienumaLine, sLogs);
				alsReturn.add(sLine);
				
//				if(sLine!=null) {
//					//!!!Nimm keine Zeilen auf, die nur diesen ggfs. durch die Konfiguration hinzugekommenen "MessageSeparator" haben.
//					if(!sLine.equalsIgnoreCase(ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
//					   alsReturn.add(sLine);
//					}
//				}
			}							
		}//end main:
		return alsReturn;
	}
	
	
	private String computeLinesInLog_Justified_(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				classObj = this.getClass();
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
				classObj = this.getClass();
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
			ArrayListUniqueZZZ<String> listasLine = this.computeLinesInLog_(classObjIn, ienumaFormatLogString, sLogs);
			
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
		
			Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				for(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
					String sValue = this.computeLineInLog_(classObj, ienumFormatLogString, sLogs);//this.computeByObject_(classObj, ienumFormatLogString);
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
				String sValue = this.computeLineInLog_(classObj, ienumFormatLogString, sLogs);//this.computeByObject_(classObj, ienumFormatLogString);
				if(sValue!=null) {
					if(sReturn==null) {
						sReturn = sValue;
					}else {
						sReturn = sReturn + sValue; 
					}
				}
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
		private String computeLineInLog_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
			String sReturn = null;
			main:{								
				Class classObj = null;		
				if(classObjIn==null) {
					classObj = this.getClass();			
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
	/* (non-Javadoc)
	 * @see basic.zBasic.util.log.ILogStringZZZ#compute(java.lang.Object, java.lang.String)
	 */
//	@Override
//	public String compute(Object obj, String sMessage) throws ExceptionZZZ {
//		String[] saMessage = new String[1];
//		saMessage[0] = sMessage;
//		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
//	}
	
	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
			
			Class classObj = null;
			if(obj==null) {
				classObj = this.getClass();
			}else {
				classObj = obj.getClass();
			}
			
			ArrayListUniqueZZZ<String> listaline = this.computeLinesInLog_(classObj, (IEnumSetMappedLogStringFormatZZZ[])null, sLogs);
			for(String sLine : listaline) {
				if(sReturn==null) {
					sReturn = sLine;
				}else {
					sReturn = sReturn + StringZZZ.crlf() + sLine;
				}
			}
		}//end main
		return sReturn;
	}
	
//	@Override
//	public String compute(Object obj, String[] saMessage) throws ExceptionZZZ {
//		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
//	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		return this.computeLinesInLog_Justified_(this.getClass(), hm);
	}
	
	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		if(obj==null) {
			return this.compute(this.getClass(), hm);			
		}else {
			return this.compute(obj.getClass(), hm);
		}	
	}
	
	//##########################################################
//	@Override
//	public String compute(Class classObj, String sLog) throws ExceptionZZZ {
//		return this.compute(classObj, (IEnumSetMappedLogStringFormatZZZ[])null, sLog);
//	}
	
	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
	
		//Nein, hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		//return this.computeLinesInLog_Justified_(classObjIn, ienumaFormatLogString, sLogs);
		ArrayListUniqueZZZ<String> lista =  this.computeLinesInLog_(classObj, (IEnumSetMappedLogStringFormatZZZ[])null, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;
	}
	
//	@Override
//	public String compute(Class classObj, String[] saMessage) throws ExceptionZZZ {		
//		return this.compute(classObj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
//	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe	
	/* (non-Javadoc)
	 * @see basic.zBasic.util.log.ILogStringZZZ#compute(java.util.LinkedHashMap)
	 */
	@Override
	public String compute(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		return this.computeLinesInLog_Justified_(classObjIn, hmLog);
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
			
			//TODOGOON20240503: Eine Sortierung des Arrays ist wünschenswert.
			//                  Zuerst die ohne Argument, dann die mit Objekt als Argument, dann erst die Stringbasierten Argumente.
		}
		return this.ienumaMappedFormat;
	}
	
	
	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMappedDefault() throws ExceptionZZZ {
		
		//Merke: Verwendet wird z.B. ein LogString in dieser Form, den es abzubilden gilt:
		//       In getPositionCurrent() wird schon die ThreadID zum ersten Mal gesetzt. Damit das Log lesbarer wird soll vor dem Status noch der Thread gesetzt werden.
		//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
		
		
		
		//Also Classname und Thread z.B. raus. Das 1. iARGNext ist für getPositionCurrent(), das 2. ARGNext für den Text ab "Status...", das 3. ARGNext als Reserve.

		//Array automatisch aus dem Enum errechnen.
		IEnumSetMappedLogStringFormatZZZ[] ienumaReturn = EnumAvailableHelperZZZ.searchEnumMapped(this, ILogStringFormatZZZ.sENUMNAME);
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
			IEnumSetMappedZZZ[] ienuma = EnumAvailableHelperZZZ.searchEnumMapped(this, ILogStringFormatZZZ.sENUMNAME);
			for(IEnumSetMappedZZZ ienum : ienuma) {
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
	//### FLAG: ILogStringFormaterZZZ
	//###################################################
	@Override
	public boolean getFlag(ILogStringFormaterZZZ.FLAGZ objEnumFlag) {
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
