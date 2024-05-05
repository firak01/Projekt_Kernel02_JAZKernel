package basic.zBasic.util.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public abstract class AbstractLogStringZZZ extends AbstractObjectWithFlagZZZ implements ILogStringZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	protected static ILogStringZZZ objLogStringSingleton; //muss als Singleton static sein
	
	//MERKE: Alles volatile, damit es über mehrere Threads gleich bleibt.
	protected volatile HashMap<Integer,String>hmFormatPositionString=null;
	
	//Das Fomat
	protected volatile IEnumSetMappedLogStringFormatZZZ[]ienumaMappedFormat=null;
	
	//Zum Buendig machen
	protected volatile String sPositionSeparator = null;	  //Ggfs. individuelle Positions-Trenner Zeichen.
	protected volatile int iInfoPartBoundLeft=-1; //Die aktuelle linke Grenze, an der der InfoPart beginnt.
	
	//### Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################
	@Override
	public String getPositionSeparatorDefault() {
		return ReflectCodeZZZ.sPOSITION_SEPARATOR;
	}
	
	@Override
	public String getPositionSeparator() {
		if(this.sPositionSeparator==null) {
			this.sPositionSeparator = this.getPositionSeparatorDefault();
		}
		return this.sPositionSeparator;
	}
	
	@Override
	public void setPositionSeparator(String sPositionSeparator) {
		this.sPositionSeparator = sPositionSeparator;
	}
	
	@Override
	public int getInfoPartBoundLeftBehindCurrent() {
		return this.iInfoPartBoundLeft;
	}
	
	@Override
	public void setInfoPartBoundLeftBehindCurrent(int iIndex) {
		this.iInfoPartBoundLeft = iIndex;
	}
	
	public static int indexOfInfoPartBoundLeftStatic(String sLog, String sPositionSeparator) {
		return StringZZZ.indexOfFirst(sLog, sPositionSeparator);				
	}
	
	public static int indexOfInfoPartBoundLeftBehindStatic(String sLog, String sPositionSeparator) {
		return StringZZZ.indexOfFirstBehind(sLog, sPositionSeparator);				
	}
	
	@Override 
	public int indexOfInfoPartBoundLeftBehind(String sLog) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionSeparator();
			iReturn = AbstractLogStringZZZ.indexOfInfoPartBoundLeftBehindStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override 
	public int indexOfInfoPartBoundLeft(String sLog) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionSeparator();
			iReturn = AbstractLogStringZZZ.indexOfInfoPartBoundLeftStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override
	public int getInfoPartBoundLeftBehind2use(String sLog) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			int itemp = this.indexOfInfoPartBoundLeftBehind(sLog);
			iReturn = this.getInfoPartBoundLeftBehindCurrent(); 
			if(itemp>iReturn) {
				//nein, nicht setzen. Das justify überlassen this.setInfoPartBoundLeftCurrent(itemp);
				iReturn = itemp;
			}			
		}//end main:
		return iReturn;
	}
	
	@Override
	public synchronized String justifyInfoPart(String sLog) throws ExceptionZZZ {
		String sReturn = sLog;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			//hole die bisherige, aktuelle Grenze
			int iBoundLeftBehindCurrent = this.getInfoPartBoundLeftBehindCurrent();
						
			//ermittle die Grenze aus dem Logstring
			//int iBoundLeft2use = this.getInfoPartBoundLeft2use(sLog);
			int iBoundLeftBehind = this.indexOfInfoPartBoundLeftBehind(sLog);
			
			//Differenz vorhanden
			if(iBoundLeftBehind>iBoundLeftBehindCurrent) {
				this.setInfoPartBoundLeftBehindCurrent(iBoundLeftBehind);
				sReturn = sReturn  + "-DEBUG01: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
			}
			if(iBoundLeftBehind==iBoundLeftBehindCurrent) {				
				sReturn = sReturn  + "-DEBUG02: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
			}
			if(iBoundLeftBehind<iBoundLeftBehindCurrent) {
				//aufsplitten und auffuellen
				//... aber vor dem \t aufsplitten
				int iBoundLeft = this.indexOfInfoPartBoundLeft(sLog);
	
				//falls ja: Berechne die Anzahl er Tabs aus der Differenz				
				int iDifference = iBoundLeftBehindCurrent - iBoundLeftBehind;
				int iTabs = iDifference / 7;//7 Leerzeichen entsprechen 1 Tab???
				
				if(iTabs>=1) {
					String sLeft = StringZZZ.left(sLog, iBoundLeft);
					String sRight = StringZZZ.rightback(sLog, iBoundLeft);
								
					//String sMid = StringZZZ.repeat("\t", iTabs); //Die Anzahl der Tabs selbst auszugeben, macht es schwierig.
					//Daher nur die Anzahl der Zeichen als Leerzeichen.
					String sMid = StringZZZ.repeat(" ", iDifference);
								
					//wieder zusamensetzen
					sReturn = sLeft + sMid + sRight + "-DEBUG03: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
				}
			}
		
			//falls nein. einfach weiter
		}//end main:
		return sReturn;
	}
		
	
	
	
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
				case ILogStringZZZ.iFACTOR_CLASSSIMPLENAME:
					if(obj==null) {
							
					}else {
						if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
							System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
						}else {
							sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSSIMPLENAME));
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
		String sReturn = null;
		main:{
			if(!isFormatUsingLogString(ienumFormatLogString)) break main;
		    if(sLog==null) break main;
		   
			String sFormat=null;
		
			switch(ienumFormatLogString.getFactor()) {
			case ILogStringZZZ.iFACTOR_CLASSPOSITION:
				if(obj==null) {
						
				}else {
					if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
					}else {						
						sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSPOSITION));
						sReturn = String.format(sFormat, sLog);//Merke: Da wir hier nicht die Postion erraten können, gehen wir davon aus, dass sie im naechsten Argument steckt.						
					}
				}
			 break;
			
			case ILogStringZZZ.iFACTOR_CLASSFILEPOSITION:
				if(obj==null) {
					
				}else {
					if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens (also auch der Dateiposition) per gesetztem Flag unterbunden.");
					}else {
						sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSPOSITION));
						sReturn = String.format(sFormat, sLog);//Merke: Da wir hier nicht die Postion erraten können, gehen wir davon aus, dass sie im naechsten Argument steckt.
					}
				}
			 break;
			
			case ILogStringZZZ.iFACTOR_ARGNEXT01:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT01));					
				sReturn = String.format(sFormat, sLog);						
				break;
				
			case ILogStringZZZ.iFACTOR_ARGNEXT02:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_ARGNEXT02));
				sReturn = String.format(sFormat, sLog);
				break;
				
			case ILogStringZZZ.iFACTOR_CLASSMETHOD:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iFACTOR_CLASSMETHOD));
				sReturn = String.format(sFormat, sLog);
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
		
			if(ArrayUtilZZZ.isEmpty(saLog)) {
				break main;
			}
		
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
			}
			
			
			//Der zu verwendende Logteil
			String sLogUsed;
			
			//Anzahl der geschriebenen sLogs aus saLog
			int iLogIndexNext=0;
			
			//Ermittle in einer Schleife den auszugebenden Teil
			for(IEnumSetMappedLogStringFormatZZZ ienumMappedFormat : ienumaFormatLogString){
				sLogUsed=null;
			
				if(isFormatUsingLogString(ienumMappedFormat)) {
					if(saLog.length>iLogIndexNext) {
						String sLog = saLog[iLogIndexNext];
						sLogUsed = this.compute(obj, sLog, ienumMappedFormat);
						iLogIndexNext++;
					};
				}else {
					sLogUsed = this.compute(obj, ienumMappedFormat);
				}
			
				
				
				if(sLogUsed!=null) { 
					if(sReturn==null) {
						sReturn = sLogUsed;
					}else {
						//Die einzelnen Bestandteile ggfs. noch mit einem Trennzeichen voneinander trennen.
						sReturn = sReturn + ienumMappedFormat.getPrefixSeparator() + sLogUsed + ienumMappedFormat.getPostfixSeparator();
					}					
				}
			}//end for
				
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Wenn jetzt noch Text vorhanden ist, diesen mit dem Standardformat ausgeben.
			while(saLog.length<iLogIndexNext) {
				String sLog = saLog[iLogIndexNext];
				sLogUsed = this.compute(obj, sLog, ILogStringZZZ.LOGSTRING.ARGNEXT01);
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
			sReturn = this.justifyInfoPart(sReturn);
			
		}//end main:
		return sReturn;

	}


	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//ohne explizite Formatangabe
	@Override
	public String compute(String sLog) throws ExceptionZZZ {
				String[] saLog = new String[1];
				saLog[0] = sLog;
				return this.compute(null, saLog, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String sLog) throws ExceptionZZZ {
				String[] saLog = new String[1];
				saLog[0] = sLog;
				return this.compute(obj, saLog, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ {
				String[] saLog = new String[2];
				saLog[0] = sLog01;
				saLog[1] = sLog02;
				return this.compute(obj, saLog, (IEnumSetMappedLogStringFormatZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String[] saLog) throws ExceptionZZZ {
		return this.compute(obj, saLog, (IEnumSetMappedLogStringFormatZZZ[])null);
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setFormatPositionsMapped(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedFormat) {
		this.ienumaMappedFormat=ienumaMappedFormat;
	}

	@Override
	public IEnumSetMappedLogStringFormatZZZ[] getFormatPositionsMapped() throws ExceptionZZZ {
		if(ArrayUtilZZZ.isEmpty(this.ienumaMappedFormat)) {
			this.ienumaMappedFormat = this.getFormatPositionsMappedCustom();
			
			//Wenn im custom nix drin ist, default nehmen
			if(ArrayUtilZZZ.isEmpty(this.ienumaMappedFormat)) {
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
			if(ArrayUtilZZZ.isEmpty(ienumaMappedFormat))break main;
			
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
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
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
