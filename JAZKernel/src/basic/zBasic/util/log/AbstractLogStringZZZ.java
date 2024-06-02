package basic.zBasic.util.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.checkerframework.checker.units.qual.s;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

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
	
	//Zum Buendig machen
	protected volatile StringJustifierZZZ objStringJustifier = null;
	//protected volatile String sPositionSeparator = null;	  //Ggfs. individuelle Positions-Trenner Zeichen.
	//protected volatile int iInfoPartBoundLeft=-1; //Die aktuelle linke Grenze, an der der InfoPart beginnt.
	
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
	
	//### Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################
	
	
	
	//############################################################
	public static boolean isFormatUsingLogString(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
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
	
	
	public static boolean isFormatUsingObject(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) {
		boolean bReturn = false;
		main:{
			if(ienumFormatLogString==null) break main;
			
			int iArgumentType = ienumFormatLogString.getArgumentType();
			
			switch(iArgumentType) {
			case ILogStringZZZ.iARG_OBJECT:
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
		public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(isFormatUsingLogString(ienumFormatLogString)) break main;
				
				String sFormat=null;
				switch(ienumFormatLogString.getFactor()) {
				case ILogStringZZZ.iFACTOR_CLASSNAMESIMPLE:
					if(obj==null) {
							
					}else {
						if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
							System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
						}else {
							sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSNAMESIMPLE));
							sReturn= String.format(sFormat, obj.getClass().getSimpleName());
						}
					}
					break;
				case ILogStringZZZ.iFACTOR_CLASSNAME:
					if(obj==null) {
							
					}else {
						if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
							System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
						}else {
							sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME));
							sReturn= String.format(sFormat, obj.getClass().getName());
						}
					}
					break;
				case ILogStringZZZ.iFACTOR_CLASSFILENAME:
					if(obj==null) {
							
					}else {
						if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
							System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
						}else {
							sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME));
							sReturn = String.format(sFormat, obj.getClass().getName());
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
					 
					 String sDate =new String( iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
					 + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString());
					
					sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_DATE));
					sReturn = String.format(sFormat, sDate);
					break;

				case ILogStringZZZ.iFACTOR_THREADID:
					if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
					}else {
						sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_THREADID));
						
						long lngThreadID = Thread.currentThread().getId();
						sReturn = String.format(sFormat, lngThreadID);
					}				
					break;
				default:
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+"Dieses Format ist nicht in den gueltigen Formaten für einen objektbasierten nLogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
					break;					
				}
				
			}//end main:
			return sReturn;
		}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.compute(null, ienumFormatLogString);
	}
		
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
		
		String[]saLog = ArrayListZZZ.toStringArray(listas);
		
		return this.compute(obj, saLog, ienumFormatLogString);
	}
	
	@Override
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return compute_(obj,sLog,ienumFormatLogString);		
	}
	
	/**Den Code fuer compute in dieser zentralen private-Methode konzentriert.
	 * Dann findet man sie auch sofort wieder, z.B. in der Outline.
	 * @param obj
	 * @param sLog
	 * @param ienumFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 19.05.2024, 09:14:10
	 */
	private String compute_(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(!isFormatUsingLogString(ienumFormatLogString)) break main;
		    if(sLog==null) break main;
		   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sLogUsed = null;
			
			String sPrefixSeparator; String sPostfixSeparator;
			if(ienumFormatLogString!=null) {
				sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
				sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			}else {
				sPrefixSeparator = "";
				sPostfixSeparator= "";
			}
			
			switch(ienumFormatLogString.getFactor()) {
			case ILogStringZZZ.iFACTOR_CLASSPOSITION:
				if(obj==null) {
						
				}else {
					if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
					}else {						
						sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSPOSITION));
						sLogUsed = sPrefixSeparator + sRight + sPostfixSeparator;
						sReturn = String.format(sFormat, sLog);//Merke: Da wir hier nicht die Postion erraten können, gehen wir davon aus, dass sie im naechsten Argument steckt.
						
					}
				}
			 break;
			
			case ILogStringZZZ.iFACTOR_CLASSFILEPOSITION:
				if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens (also auch der Dateiposition) per gesetztem Flag unterbunden.");
				}else {
					sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSFILEPOSITION));
					//sReturn = String.format(sFormat, sLog);//Merke: Da wir hier nicht die Postion erraten können, gehen wir davon aus, dass sie im naechsten Argument steckt.
					
					//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
					//Merke: Der Position steht im Logstring immer am Anfang
					//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
					//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
					
					//Auseinanderbauen
					sLeft = StringZZZ.left(sLog + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
					sMid = StringZZZ.midLeftRight(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
					sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
					
					//Die Postionsangabe weglassen
					sLogUsed = StringZZZ.stripRight(sLeft, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
					sLogUsed = String.format(sFormat, sLogUsed);
					sReturn = sPrefixSeparator + sLogUsed + sPostfixSeparator;
					
					ITagByTypeZZZ objTagFilePosition = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE, sReturn);
					sReturn = objTagFilePosition.getElementString();
					break;
				}
			 break;			
				
			case ILogStringZZZ.iFACTOR_ARGNEXT01:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT01));
				
				/*
				TODOGOON: An dieser Stelle nur in einer HashMap nach den Platzhaltern schauen
				          und diese ggfs. einfügen..als XML
				*/
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sMid = StringZZZ.midLeftRight(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLogUsed = sRight;
				sLogUsed = String.format(sFormat, sLogUsed);
				sReturn = sPrefixSeparator + sLogUsed + sPostfixSeparator;				
				break;
				
			case ILogStringZZZ.iFACTOR_ARGNEXT02:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT02));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sMid = StringZZZ.midLeftRight(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLogUsed = String.format(sFormat, sLogUsed);
				sReturn = sPrefixSeparator + sLogUsed + sPostfixSeparator;					
				break;
				
			case ILogStringZZZ.iFACTOR_ARGNEXT03:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT03));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sMid = StringZZZ.midLeftRight(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Die Postionsangabe weglassen
				sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				sLogUsed = String.format(sFormat, sLogUsed);
				sReturn = sPrefixSeparator + sLogUsed + sPostfixSeparator;					
				break;
				
			case ILogStringZZZ.iFACTOR_ARGNEXT05:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT05));
				
				//MERKE: DAS IST EIN FORMAT ZUM EINFACHEN WEITERSCHIEBEN DES ARGNEXT - WERTS.
				//       Z.B. wenn vorher die FilePostion ausgegeben wurde. Diese ist immer Bestandteil eines Argument-Strings
				//       Diese ist aber nicht ARGNEXT...
				//       Damit also ein nachfolgender Argument-String danach verarbeitet wird, muss das Argument mit dieser "Schiebe" Anweisung extra weitergeschoben werden.
				//Die Postionsangabe weglassen
				sLogUsed = "";
				sLogUsed = String.format(sFormat, sLogUsed);
				sReturn = sPrefixSeparator + sLogUsed + sPostfixSeparator;					
				break;
				
				
			case ILogStringZZZ.iFACTOR_CLASSMETHOD:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSMETHOD));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
				sLeft = StringZZZ.left(sLog+ReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER, ReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER);
				sMid = StringZZZ.midLeftRight(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				//Nur die Methodenangabe
				sLogUsed = StringZZZ.stripRight(sLeft, ReflectCodeZZZ.sPOSITION_METHOD_IDENTIFIER);
				sLogUsed = String.format(sFormat, sLogUsed);
				sReturn = sPrefixSeparator + sLogUsed + sPostfixSeparator;				
				break;
				
			default:
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+"Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
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
		String[]saLog = new String[1];
		saLog[0] = sLog;
		return this.compute(obj, saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringFormatZZZ ienumaFormatLogString) throws ExceptionZZZ {
		ArrayList<String> listas = new ArrayList<String>();
		if(sLog01!=null) {
			listas.add(sLog01);
		}
		
		if(sLog02!=null) {
			listas.add(sLog02);
		}
				
		String[]saLog = ArrayListZZZ.toStringArray(listas);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat=new IEnumSetMappedLogStringFormatZZZ[1];
		iaFormat[0] = ienumaFormatLogString;
		return this.compute(obj, saLog, iaFormat);
	}
	
	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString)	throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedLogStringFormatZZZ[1];
		if(ienumFormatLogString!=null) {
			ienumaFormatLogString[0] = ienumFormatLogString;
		}
		return this.compute(obj, saLog, ienumaFormatLogString);
	}
	
	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[]ienumaFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//Man soll auch ohne Object einen String berechnen dürfen
//			if(obj==null) {
//				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING,   AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}
		
			if(ArrayUtilZZZ.isNull(saLog)) {
				break main;
			}
		
			if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
			}
			
			/*
			TODO: Die übergebenen LogStrings nach XML Elementen Parsen.
			      Diese sind vorgegeben, s. TagFactoryZZZ
			      Dann eine HashMap mit den Platzhaltern füllen
			      hm(TAGNAME, TAGWERT)
			      
			      DIES Mehrstufig tun, bis alle Platzhalter der HashMap keinen Tag mehr besitzten.
			      - A) Tagwert mit einem "freien" XML Element
			      - B) Tagwert mit einem TagFactoryZZZ XML Element
*/
			      
			      
			
			
			//Der zu verwendende Logteil
			String sLogUsed=null; String sLog=null;
			
			//Anzahl der geschriebenen sLogs aus saLog
			int iLogIndexNext=0;//wird weitergeschoben durch iARG_NEXT
			int iLogIndexCurrent=0;
			
			//Ermittle in einer Schleife den auszugebenden Teil
			for(IEnumSetMappedLogStringFormatZZZ ienumMappedFormat : ienumaFormatLogString){
				sLogUsed=null;
			
				if(isFormatUsingLogString(ienumMappedFormat)) {
					if(saLog.length>iLogIndexCurrent) {					
						if(ienumMappedFormat.getName().startsWith("ARGNEXT")) {
							if(saLog.length>iLogIndexNext) {
								sLog = saLog[iLogIndexNext];						
								sLogUsed = this.compute(obj, sLog, ienumMappedFormat);
								iLogIndexNext++; //Also nur wenn das naechste Argument verarbeitet wurde, den Index weiterschieben.
								if(saLog.length>iLogIndexNext) {
									iLogIndexCurrent = iLogIndexNext; //Also den Current Index nur weiterschieben, wenn noch nicht das ende erreicht ist.
									//Hintergrund: Nur so kann z.B. eine FILEPOSITION aus einem String herausgerechnet werden, falls die FILEPOSITION ans Ende kommen soll, nachdem das ARGNEXT verarbeitet worden ist.
								}
							}
						}else {
							sLog = saLog[iLogIndexCurrent];
							sLogUsed = this.compute(obj, sLog, ienumMappedFormat);							
						}
					};
				}else {
					sLogUsed = this.compute(obj, ienumMappedFormat);
				}
			
				
				
				if(sLogUsed!=null) { 
					if(sReturn==null) {
						sReturn = sLogUsed;
					}else {
						//Die einzelnen Bestandteile ggfs. noch mit einem Trennzeichen voneinander trennen.
						sReturn = sReturn + ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + sLogUsed;
					}					
				}
			}//end for
				
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Wenn jetzt noch Text vorhanden ist, diesen mit dem Standardformat ausgeben.
			while(saLog.length>iLogIndexNext) {
				sLog = saLog[iLogIndexNext];
				if(iLogIndexNext==0) {
					sLogUsed = this.compute(obj, sLog, ILogStringZZZ.LOGSTRING.ARGNEXT01);
				}else if(iLogIndexNext>=1) {
					sLogUsed = this.compute(obj, sLog, ILogStringZZZ.LOGSTRING.ARGNEXT02);
				}
				iLogIndexNext++;
				
				if(sLogUsed!=null) { 
					if(sReturn==null) {
						sReturn = sLogUsed;
					}else {
						//Die einzelnen Bestandteile noch mit Leerstring voneinander trennen.
						sReturn = sReturn + ILogStringZZZ.sSEPARATOR_PREFIX_DEFAULT + sLogUsed;
					}					
				}
			}
			
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			sReturn = this.getStringJustifier().justifyInfoPart(sReturn);
			
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
				String[] saMessage = new String[2];
				saMessage[0] = sMessage01;
				saMessage[1] = sMessage02;
				return this.compute(null, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(String[] saMessage) throws ExceptionZZZ {				
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
		return this.compute(obj, saMessage, (IEnumSetMappedLogStringFormatZZZ[])null);
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
				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
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
