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
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zBasic.xml.tagtype.TagByTypeZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractLogStringZZZ extends AbstractObjectWithFlagZZZ implements ILogStringZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	//muss als Singleton static sein.
	protected static ILogStringZZZ objLogStringSingleton;
	
	//Merke: Wenn das aber als Singleton gebraucht wird, dann gilt wg. proteceted:
	//in der entsprechenden Klasse ein eigenes Objekt definieren. Alsod folgendes nutzen und verwenden:
	//protected static ILogStringZZZ objLogStringSingletonHERE; //muss als Singleton static sein, und HERE weil das Objekt in AbstractLogString vom Typ LogStringZZZ ist, gibt es dann eine TypeCastException.
	
	//MERKE: Alles volatile, damit es über mehrere Threads gleich bleibt.
	protected volatile HashMap<Integer,String>hmFormatPositionString=null;
	
	//Das Fomat
	protected volatile IEnumSetMappedLogStringFormatZZZ[]ienumaMappedFormat=null;
	
	//Der LogString-Index - also von den reinen String ( nicht ggfs. hinzugefuegte XML Strings wie von ReflectCodeZZZ.getPositionCurrent() )
	//Hier als Array aller schon benutzter "einfacher" Strings. Das wird gemacht, damit die XML Strings auch weiterhin bei jeder Operation beruecksichtigt werden können.
	//protected volatile int iStringIndexToReadLbound=0;
	protected volatile ArrayListUniqueZZZ<Integer>listaintStringIndexRead=null;
	
	//Zum Buendig machen
	protected volatile StringJustifierZZZ objStringJustifier = null;
	
	
	//### GETTER / SETTER
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp1 = this.resetStringIndexRead();			
			boolean btemp2 = this.getStringJustifier().reset();
			
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
	
//	@Override
//	public int getStringIndexStart() throws ExceptionZZZ {
//		return this.iStringIndexToReadLbound;
//	}
//	
//	@Override
//	public void setStringIndexStart(int iStringIndex) throws ExceptionZZZ {
//		this.iStringIndexToReadLbound = iStringIndex;
//	}
	
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
	public StringJustifierZZZ getStringJustifier() throws ExceptionZZZ {
		if(this.objStringJustifier==null) {
			this.objStringJustifier = new StringJustifierZZZ();
		}
		return this.objStringJustifier;
	}
	
	@Override
	public void setStringJustifier(StringJustifierZZZ objStringJustifier) {
		this.objStringJustifier = objStringJustifier;
	}
	
	//############################################################
	public static boolean isFormatUsingString(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringZZZ.iARG_STRING:
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
			case ILogStringZZZ.iARG_STRINGXML:
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
			case ILogStringZZZ.iARG_OBJECT:
				bReturn = true;
				break;
			case ILogStringZZZ.iARG_SYSTEM:
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
		this.resetStringIndexRead();
		return computeByObject_(classObj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.compute(null, sLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		
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
		this.resetStringIndexRead();
		
		Class classObj=null;
		if(obj == null) {
			classObj = this.getClass();
		}else {
			classObj = obj.getClass();
		}
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		
		return computeUsingFormat_(classObj,saLog,ienumFormatLogString);		
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
		this.resetStringIndexRead();
		
		String[] saLog = new String[1];
		saLog[0] = sLog; 
		return this.computeUsingFormat_(classObj, saLog, ienumFormatLogString);
	}
		
	private String computeUsingFormat_(Class classObjIn, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
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
							sReturn = this.computeByString_(classObj, saLog[iStringIndexToRead], ienumFormatLogString);
							this.getStringIndexReadList().add(intIndex);
							break; //nach der ersten Verarbeitung aus der Schleife raus!!!
						}
					}
				}							
										
			}else if(bFormatUsingStringXml) {			
				sReturn = this.computeByStringXml_(classObj, saLog, ienumFormatLogString);
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
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
	            case ILogStringZZZ.iFACTOR_CLASSNAMESIMPLE:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringZZZ.iFACTOR_CLASSNAMESIMPLE));
	                        sReturn = String.format(sFormat, classObj.getSimpleName());
	                    }
	                }
	                break;

	            case ILogStringZZZ.iFACTOR_CLASSNAME:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringZZZ.iFACTOR_CLASSNAME));
	                        sReturn = String.format(sFormat, classObj.getName());
	                    }
	                }
	                break;

	            case ILogStringZZZ.iFACTOR_CLASSFILENAME:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(ILogStringZZZ.iFACTOR_CLASSNAME));
	                        sReturn = String.format(sFormat, classObj.getName());
	                    }
	                }
	                break;

	            case ILogStringZZZ.iFACTOR_DATE:
	                GregorianCalendar d = new GregorianCalendar();
	                Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	                Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	                Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	                Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	                Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE));

	                String sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	                        + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();

	                sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_DATE));
	                sReturn = String.format(sFormat, sDate);
	                break;

	            case ILogStringZZZ.iFACTOR_THREADID:
	                if (this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(ILogStringZZZ.iFACTOR_THREADID));
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
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!isFormatUsingString(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			
			//+++ Pruefe darauf, ob es ein XML-String ist. Wenn ja... Abbruch. Ansonsten wird ggfs. <filepositioncurrent> als normaler Logeintrag behandelt.
			//    Dieser String wird naemlich über das Array saLog gerettet und uebergeben ( aus der entsprechenden ermittelnden ReflectionZZZ Methode ). 
			boolean bXml = XmlUtilZZZ.isXmlContained(sLogIn);
			if(bXml) break main; //hier werden nur einfach Strings verarbeitet und keine XML Strings...
			
			//+++++++++++++++++++++++++++					
//		    Class classObj = null;		
//			if(classObjIn==null) {
//				classObj = this.getClass();			
//			}else {
//				classObj = classObjIn;
//			}

			String sLog=sLogIn;
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
						
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			
			switch(ienumFormatLogString.getFactor()) {		
			case ILogStringZZZ.iFACTOR_STRING_TYPE01:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_STRING_TYPE01));
								
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLogIn, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLogIn, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLog = sLeft + sMid + sRight;
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case ILogStringZZZ.iFACTOR_STRING_TYPE02:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_STRING_TYPE02));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLogIn, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLogIn, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				
				sLog = sLeft + sMid + sRight;
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
			case ILogStringZZZ.iFACTOR_STRING_TYPE03:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_STRING_TYPE03));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sMid = StringZZZ.midLeftRightback(sLogIn, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLogIn, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				sLog = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
			case ILogStringZZZ.iFACTOR_LINENEXT:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_LINENEXT));
				
				//MERKE: DAS IST EIN FORMAT ZUM EINFACHEN WEITERSCHIEBEN DES ARGNEXT - WERTS.
				//       Z.B. wenn vorher die FilePostion ausgegeben wurde. Diese ist immer Bestandteil eines Argument-Strings
				//       Diese ist aber nicht ARGNEXT...
				//       Damit also ein nachfolgender Argument-String danach verarbeitet wird, muss das Argument mit dieser "Schiebe" Anweisung extra weitergeschoben werden.
				//Die Postionsangabe weglassen
				sLog = "";
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
				
//			case ILogStringZZZ.iFACTOR_CLASSMETHOD:
//				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSMETHOD));
//				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLogIn+ReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER, ReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLogIn, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLogIn, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Nur die Methodenangabe
//				sLog = StringZZZ.stripRight(sLeft, ReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER);
//				sLog = String.format(sFormat, sLog);
//				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
//				break;
//			case ILogStringZZZ.iFACTOR_CLASSFILEPOSITION:
//				if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
//					System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens (also auch der Dateiposition) per gesetztem Flag unterbunden.");
//				}else {
//					sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSFILEPOSITION));
//					//sReturn = String.format(sFormat, sLog);//Merke: Da wir hier nicht die Postion erraten können, gehen wir davon aus, dass sie im naechsten Argument steckt.
//					
//					//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//					//Merke: Der Position steht im Logstring immer am Anfang
//					//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//					//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//					
//					//Auseinanderbauen
//					sLeft = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//					sMid = StringZZZ.midLeftRightback(sLogIn, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//					sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLogIn, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//					
//					//Die Postionsangabe weglassen
//					sLog = StringZZZ.stripRight(sLeft, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//					sLog = String.format(sFormat, sLog);
//					sReturn = sPrefixSeparator + sLog + sPostfixSeparator;
//					
//					ITagByTypeZZZ objTagFilePosition = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE, sReturn);
//					sReturn = objTagFilePosition.getElementString();
//					break;
//				}
//			 break;
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
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
			case ILogStringZZZ.iFACTOR_CLASSFILEPOSITION_REFLECTED:			
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
			case ILogStringZZZ.iFACTOR_CLASSMETHOD_REFLECTED:
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
			case ILogStringZZZ.iFACTOR_POSITIONCURRENT_REFLECTED:
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
	

	@Override
	public String compute(String sLog, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		return this.compute(null, sLog, ienumaFormatLogString);
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
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
						
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
		this.resetStringIndexRead();
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
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
			List<IEnumSetMappedLogStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogString, ILogStringZZZ.LOGSTRING.LINENEXT, IEnumSetMappedLogStringFormatZZZ.class);
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
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
	@Override
	public String compute(String sMessage) throws ExceptionZZZ {
		String[] saMessage = new String[1];
		saMessage[0] = sMessage;
		return this.compute(null, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(String sMessage01, String sMessage02) throws ExceptionZZZ {
		this.resetStringIndexRead();
		
		String[] saMessage = new String[2];
		saMessage[0] = sMessage01;
		saMessage[1] = sMessage02;
		return this.compute(null, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(String[] saMessage) throws ExceptionZZZ {		
		this.resetStringIndexRead();
		
		return this.compute(null, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
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
		this.resetStringIndexRead();
		
		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		
		return this.compute(this.getClass(), hm);				
	}
	
	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		
		if(obj==null) {
			return this.compute(this.getClass(), hm);			
		}else {
			return this.compute(obj.getClass(), hm);
		}	
	}
	
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

			this.resetStringIndexRead();
			
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
					sReturn = sReturn + ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + sLogUsed;									
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
		IEnumSetMappedLogStringFormatZZZ[] ienumaReturn = EnumAvailableHelperZZZ.searchEnumMapped(this, ILogStringZZZ.sENUMNAME);
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
	
	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringDefault() throws ExceptionZZZ {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		main:{
		//HashMap automatisch aus dem Enum errechnen.
		IEnumSetMappedZZZ[] ienuma = EnumAvailableHelperZZZ.searchEnumMapped(this, ILogStringZZZ.sENUMNAME);
		for(IEnumSetMappedZZZ ienum : ienuma) {
			IEnumSetMappedLogStringFormatZZZ ienumLogString = (IEnumSetMappedLogStringFormatZZZ) ienum;
			hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString.getFormat());
		}
		
		//für Custom Eintraege ist dann so ein Ueberschreiben moeglich
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT01), ILogStringZZZ.sARGNEXT01);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT02), ILogStringZZZ.sARGNEXT02);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME),ILogStringZZZ.sCLASSNAME);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSMETHOD),ILogStringZZZ.sCLASSMETHOD);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSFILENAME),ILogStringZZZ.sCLASSFILENAME);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSPOSITION),ILogStringZZZ.sCLASSPOSITION);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSFILEPOSITION),ILogStringZZZ.sCLASSFILEPOSITION);
//
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_THREADID), ILogStringZZZ.sTHREAD);
//		hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_DATE), ILogStringZZZ.sDATE);
		
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
	//### FLAG: ILogStringZZZ
	//###################################################
	@Override
	public boolean getFlag(ILogStringZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(ILogStringZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(ILogStringZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ILogStringZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(ILogStringZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(ILogStringZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
