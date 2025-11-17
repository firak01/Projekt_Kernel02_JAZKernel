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
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
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
//	@Override 
//	public StringJustifierZZZ getStringJustifier() throws ExceptionZZZ {
//		if(this.objStringJustifier==null) {
//			this.objStringJustifier = new StringJustifierZZZ();
//		}
//		return this.objStringJustifier;
//	}
//	
//	@Override
//	public void setStringJustifier(StringJustifierZZZ objStringJustifier) {
//		this.objStringJustifier = objStringJustifier;
//	}
	
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
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return compute(this.getClass(), ienumFormatLogString);
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
	    if (obj == null) {
	    	return compute(this.getClass(), ienumFormatLogString);
	    }else {
	    	return compute(obj.getClass(), ienumFormatLogString);
	    }
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
		
		return computeByObject_(classObj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String compute(String sLog) throws ExceptionZZZ {
		String[]saLog=new String[1];
		saLog[0]=sLog;
		return this.compute(null, saLog, (IEnumSetMappedLogStringFormatZZZ[]) null);
	}
	
	@Override
	public String compute(String[] saLog) throws ExceptionZZZ {
		return this.compute(null, saLog, (IEnumSetMappedLogStringFormatZZZ[]) null);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.compute(null, sLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ {
		ArrayList<String>listas = new ArrayList<String>();
		if(sLog01!=null) {
			listas.add(sLog01);
		}		
		if(sLog02!=null) {
			listas.add(sLog02);
		}		
		String[]saLog = ArrayListUtilZZZ.toStringArray(listas);
		
		return this.compute(obj, saLog, ienumFormatLogString);
	}
	
	@Override
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-verschiedenen Stellen aufgerufenen private Methode.
		
		Class classObj=null;
		if(obj == null) {
			classObj = this.getClass();
		}else {
			classObj = obj.getClass();
		}
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		
		return computeUsingFormat_(classObj,saLog, null, ienumFormatLogString);		
	}
	
	/**Den Code fuer compute in dieser zentralen private-Methode konzentriert.
	 * Dann findet man sie auch sofort wieder, z.B. in der Outline.
	 * @param obj
	 * @param sLogIn
	 * @param ienumFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 19.05.2024, 09:14:10
	 */	
	@Override
	public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier in der aufrufenden Methode und nicht in der von x-Stellen aus aufgerufenen private Methode.
		
		String[] saLog = new String[1];
		saLog[0] = sLog; 
		return this.computeUsingFormat_(classObj, saLog, null, ienumFormatLogString);
	}

	private String computeUsingFormat_(Class classObjIn, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeUsingFormat_(classObjIn, saLog, null, ienumFormatLogString);	
	}
	private String computeUsingFormat_(Class classObjIn, String[] saLog, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString,  IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj=null;
			if(classObjIn == null) {
				classObj = this.getClass();
			}else {
				classObj = classObjIn;
			}
			
			boolean bFormatUsingObject = isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingObject) {
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);
				
			}else if(bFormatUsingString) {				
				if(!StringArrayZZZ.isEmpty(saLog)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead < saLog.length; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead);
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByString_(classObj, saLog[iStringIndexToRead], ienumFormatLogString);
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
				sReturn = this.computeByStringXml_(classObj, saLog, ienumFormatLogString);
			}else if(bFormatUsingStringHashMap) {
				sReturn = this.computeByStringHashMap_(classObj, hmLogString, ienumFormatLogString);
			}else {
				//mache nix				
			}
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //ABER: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//sReturn = this.getStringJustifier().justifyInfoPart(sReturn);						
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
	            case ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE));
	                        sReturn = String.format(sFormat, classObj.getSimpleName());
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSNAME:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAME));
	                        sReturn = String.format(sFormat, classObj.getName());
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILENAME));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
	                        String sFileName = classObj.getSimpleName() + ".java";
	                        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
	                        sReturn = String.format(sFormat, sFilePathTotal);
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_DATE:
	                GregorianCalendar d = new GregorianCalendar();
	                Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	                Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	                Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	                Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	                Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE));

	                String sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	                        + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();

	                sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_DATE));
	                sReturn = String.format(sFormat, sDate);
	                break;

	            case ILogStringFormatZZZ.iFACTOR_THREADID:
	                if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringFormatZZZ.iFACTOR_THREADID));
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
			case ILogStringFormatZZZ.iFACTOR_STRING_TYPE01:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_STRING_TYPE01));
								
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
				
			case ILogStringFormatZZZ.iFACTOR_STRING_TYPE02:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_STRING_TYPE02));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				
				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);				
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
			case ILogStringFormatZZZ.iFACTOR_STRING_TYPE03:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_STRING_TYPE03));
				
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
				
			case ILogStringFormatZZZ.iFACTOR_LINENEXT:
				//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
				break;
			default:
				System.out.println("AbstractLogStringZZZ.computeByString_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
				break;					
			}				
						
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //ABER: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//sReturn = this.justifyInfoPart(sReturn);
						
		}//end main:
		return sReturn;		
	}
	
	private String computeByStringXml_(Class classObjIn, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
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
			if(ArrayUtilZZZ.isNull(saLog)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				if(sReturn==null) {
					sReturn = this.compute(classObj, ienumMappedFormat);
				}else {
					sReturn = sReturn + this.compute(classObj, ienumMappedFormat);
				}
				break main;
			}
			
			//###### Mit Strings, alle durchsuchen.			
			for(String sLog:saLog) {
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
						
			String sTagTemp=null;
			switch(ienumMappedFormat.getFactor()) {
			case ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML:			
				ITagTypeZZZ objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFilePosition = new TagByTypeZZZ(objTagTypeFilePosition);
					objTagFilePosition.setValue(sReturn);
					sReturn = objTagFilePosition.getElementString();		            
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_XML:
				ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagMethod = new TagByTypeZZZ(objTagTypeMethod);
					objTagMethod.setValue(sReturn);
					sReturn = objTagMethod.getElementString();
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_XML:
				ITagTypeZZZ objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagLineNumber = new TagByTypeZZZ(objTagTypeLineNummer);
					objTagLineNumber.setValue(sReturn);
					sReturn = objTagLineNumber.getElementString();
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_XML:
				ITagTypeZZZ objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFileName = new TagByTypeZZZ(objTagTypeFileName);
					objTagFileName.setValue(sReturn);
					sReturn = objTagFileName.getElementString();
				}			
				break;
			case ILogStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML:
				ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagPositionCurrent = new TagByTypeZZZ(objTagTypePositionCurrent);
					objTagPositionCurrent.setValue(sReturn);
					sReturn = objTagPositionCurrent.getElementString();
				}			
				break;
			default:
				System.out.println("AbstractLogStringZZZ.computeByStringXml_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumMappedFormat.getFactor());
				break;					
			}		
						
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //ABER: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//sReturn = this.justifyInfoPart(sReturn);
						
		}//end main:
		return sReturn;		
	}
	
	
	private String computeByStringHashMap_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingHashMap(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
			
		    		    
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;						
	        switch (ienumFormatLogString.getFactor()) {
	            case ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_HASHMAP:
	            	//TODOGOON20251117;//Anpassen
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILELINE_HASHMAP));
	                        sReturn = String.format(sFormat, classObj.getSimpleName());
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSFILENAME_HASHMAP:
	            	//TODOGOON20251117;//Anpassen
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSNAME));
	                        sReturn = String.format(sFormat, classObj.getName());
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_HASHMAP:
	            	//TODOGOON20251117;//Anpassen
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                    	
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringFormatZZZ.iFACTOR_CLASSFILENAME));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
	                        String sFileName = classObj.getSimpleName() + ".java";
	                        String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
	                        sReturn = String.format(sFormat, sFilePathTotal);
	                    }
	                }
	                break;

	            case ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_HASHMAP:	            
	               // sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringFormatZZZ.iFACTOR_CLASSMETHOD_HASHMAP));
	               // sReturn = String.format(sFormat, sLog);
	            	
	            	sLog = hmLogString.get(ienumFormatLogString);
	            	if(sReturn==null) {
	            		sReturn = sLog;
	            	}else {
	            		sReturn = sReturn + sLog;
	            	}
	                break;

	            default:
	                System.out.println("AbstractLogStringZZZ.computeByHashMap_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	

	@Override
	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		return this.compute(this.getClass(), sLog, ienumaFormatLogString);
	}
	
	@Override
	public String compute(String[] saLog, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString) throws ExceptionZZZ {		
		return this.compute(this.getClass(), saLog, ienumaFormatLogString);					
	}

	@Override
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {			
		if(obj==null) {
			return this.compute(this.getClass(), sLog, ienumaFormatLogString);
		}else {
			return this.compute(obj.getClass(), sLog, ienumaFormatLogString);
		}
		
	}
	
	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ArrayList<String> listas = new ArrayList<String>();
		if(sLog01!=null) {
			listas.add(sLog01);
		}
		
		if(sLog02!=null) {
			listas.add(sLog02);
		}		
		String[]saLog = ArrayListUtilZZZ.toStringArray(listas);
		
		if(obj==null) {
			return this.compute(this.getClass(), saLog, ienumFormatLogString);			
		}else {
			return this.compute(obj.getClass(), saLog, ienumFormatLogString);
		}		
	}
	
	@Override
	public String compute(Class classObj, String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String[]saLog = new String[1];
		saLog[0]=sLog;
		return this.compute(classObj, saLog, ienumaFormatLogString);
	}
	
	
	
	
	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {			
		if(obj==null) {
			return this.compute(this.getClass(), saLog, ienumFormatLogString);
		}else {
			return this.compute(obj.getClass(), saLog, ienumFormatLogString);
		}			
	}
	
	
	@Override
	public String compute(Class classObj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = "";
		main:{			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
						
			if(StringArrayZZZ.isEmpty(saLog)) {							
				sReturn = this.computeByObject_(classObj, ienumFormatLogString);			
			}else {
				IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedLogStringFormatZZZ[1];
				ienumaFormatLogString[0] = ienumFormatLogString;
				
				sReturn = this.computeLinesInLog_(classObj, saLog, ienumaFormatLogString);
			}
		}//end main:
		return sReturn;
	}
	
	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString) throws ExceptionZZZ {		
		if(obj==null) {
			return this.compute(this.getClass(), saLog, ienumaFormatLogString);			
		}else {
			return this.compute(obj.getClass(), saLog, ienumaFormatLogString);
		}		
	}
	
	@Override
	public String compute(Class classObjIn, String[] saLog, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		return this.computeLinesInLog_(classObjIn, saLog, ienumaFormatLogString);
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
	private String computeLinesInLog_(Class<?> classObjIn, String[] saLog, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogString)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}
			
			
			Class<?> classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
			//###### Splitte das Array der Formatanweisungen auf an der "LINENEXT" STEUERANWEISUNG
			List<IEnumSetMappedLogStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogString, ILogStringFormatZZZ.LOGSTRINGFORMAT.LINENEXT, IEnumSetMappedLogStringFormatZZZ.class);
			ArrayListUniqueZZZ<String>listasLine = new ArrayListUniqueZZZ<String>();
			for(IEnumSetMappedLogStringFormatZZZ[] ienumaLine: listaEnumLine){
				String sLine = computeLineInLog_(classObj, saLog, ienumaLine);
				if(sLine!=null) {
					listasLine.add(sLine);
				}
			}
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//sReturn = this.getStringJustifier().justifyInfoPart(sReturn);
			
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
			for(String sLine : listasLineReversedJustified2) {
				if(sReturn.equals("")){
					sReturn = sLine;
				}else {					
					sReturn = sReturn + StringZZZ.crlf() + sLine;
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
	private String computeLineInLog_(Class classObjIn, String[] saLog, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogString)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}
		
			Class classObj = null;		
			if(classObjIn==null) {
				classObj = this.getClass();			
			}else {
				classObj = classObjIn;
			}
			
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(saLog)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				for(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
					String sValue = this.computeByObject_(classObj, ienumFormatLogString); 
					if(sReturn==null) {
						sReturn = sValue;
					}else {
						sReturn = sReturn + sValue; 
					}
				}
				break main;
			}
			
			
			//##### Mit zu verarbeitenden Strings			
			for(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
				String sValue = this.computeUsingFormat_(classObj, saLog, ienumFormatLogString);
				if(sValue!=null) {
					if(sReturn==null) {					
						sReturn = sValue;
					}else {					
						sReturn = sReturn + sValue;
					}
				}
			}
			

			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//sReturn = this.getStringJustifier().justifyInfoPart(sReturn);			
		}//end main:
		return sReturn;

	}


	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//ohne explizite Formatangabe 
	
	//OHNE KLASSEN ODER OBJEKTANGABE MACHT DAS KEINEN SINN	
	/* (non-Javadoc)
	 * @see basic.zBasic.util.log.ILogStringZZZ#compute(java.lang.Object, java.lang.String)
	 */
	@Override
	public String compute(Object obj, String sMessage) throws ExceptionZZZ {
		String[] saMessage = new String[1];
		saMessage[0] = sMessage;
		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		String[] saMessage = new String[2];
		saMessage[0] = sMessage01;
		saMessage[1] = sMessage02;
		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String[] saMessage) throws ExceptionZZZ {
		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.compute(this.getClass(), hm);				
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
	@Override
	public String compute(Class classObj, String sMessage) throws ExceptionZZZ {
		String[] saMessage = new String[1];
		saMessage[0] = sMessage;
		return this.compute(classObj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Class classObj, String sMessage01, String sMessage02) throws ExceptionZZZ {
		String[] saMessage = new String[2];
		saMessage[0] = sMessage01;
		saMessage[1] = sMessage02;
		return this.compute(classObj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Class classObj, String[] saMessage) throws ExceptionZZZ {		
		return this.compute(classObj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe	
	/* (non-Javadoc)
	 * @see basic.zBasic.util.log.ILogStringZZZ#compute(java.util.LinkedHashMap)
	 */
	@Override
	public String compute(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
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
	            String sLog = entry.getValue();	           
	            sLogUsed = this.compute(classObj, sLog, enumAsKey);
	           
	            if(sLogUsed!=null) { 
					//Die einzelnen Bestandteile ggfs. noch mit einem Trennzeichen voneinander trennen.
					sReturn = sReturn + ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + sLogUsed;									
				}
	        }
	        			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			sReturn = this.getStringJustifier().justifyInfoPart(sReturn);
			
		}//end main:
		return sReturn;

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
