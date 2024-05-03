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
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public abstract class AbstractLogStringZZZ extends AbstractObjectWithFlagZZZ implements ILogStringZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	protected static ILogStringZZZ objLogStringSingleton; //muss als Singleton static sein
	protected HashMap<Integer,String>hmFormatPositionString=null;
	
	//Das Fomat
	protected IEnumSetMappedLogStringZZZ[]ienumaMappedFormat=null;
	
	public static boolean isFormatUsingLogString(IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ {
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
	
	
	public static boolean isFormatUsingObject(IEnumSetMappedLogStringZZZ ienumFormatLogString) {
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
		public String compute(Object obj, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ{
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
	public String compute(IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.compute(null, ienumFormatLogString);
	}
		
	@Override
	public String compute(String sLog, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.compute(null, sLog, ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringZZZ[] ienumFormatLogString) throws ExceptionZZZ {
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
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringZZZ ienumFormatLogString) throws ExceptionZZZ {
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
			
		}//end main:
		return sReturn;		
	}
	
	@Override
	public String compute(String sLog, IEnumSetMappedLogStringZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		return this.compute(null, sLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog, IEnumSetMappedLogStringZZZ[] ienumaFormatLogString) throws ExceptionZZZ {
		String[]saLog = new String[1];
		saLog[0] = sLog;
		return this.compute(obj, saLog, ienumaFormatLogString);
	}

	@Override
	public String compute(Object obj, String sLog01, String sLog02, IEnumSetMappedLogStringZZZ ienumaFormatLogString) throws ExceptionZZZ {
		ArrayList<String> listas = new ArrayList<String>();
		if(sLog01!=null) {
			listas.add(sLog01);
		}
		
		if(sLog02!=null) {
			listas.add(sLog02);
		}
				
		String[]saLog = ArrayListZZZ.toStringArray(listas);
		
		IEnumSetMappedLogStringZZZ[]iaFormat=new IEnumSetMappedLogStringZZZ[1];
		iaFormat[0] = ienumaFormatLogString;
		return this.compute(obj, saLog, iaFormat);
	}
	
	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringZZZ ienumFormatLogString)	throws ExceptionZZZ {
		IEnumSetMappedLogStringZZZ[] ienumaFormatLogString = new IEnumSetMappedLogStringZZZ[1];
		if(ienumFormatLogString!=null) {
			ienumaFormatLogString[0] = ienumFormatLogString;
		}
		return this.compute(obj, saLog, ienumaFormatLogString);
	}
	
	@Override
	public String compute(Object obj, String[] saLog, IEnumSetMappedLogStringZZZ[]ienumaFormatLogString) throws ExceptionZZZ {
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
			for(IEnumSetMappedLogStringZZZ ienumMappedFormat : ienumaFormatLogString){
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
						sReturn = sReturn + " " + sLogUsed;
					}					
				}
			}
			
		}//end main:
		return sReturn;

	}


	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//ohne explizite Formatangabe
	@Override
	public String compute(String sLog) throws ExceptionZZZ {
				String[] saLog = new String[1];
				saLog[0] = sLog;
				return this.compute(null, saLog, (IEnumSetMappedLogStringZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String sLog) throws ExceptionZZZ {
				String[] saLog = new String[1];
				saLog[0] = sLog;
				return this.compute(obj, saLog, (IEnumSetMappedLogStringZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ {
				String[] saLog = new String[2];
				saLog[0] = sLog01;
				saLog[1] = sLog02;
				return this.compute(obj, saLog, (IEnumSetMappedLogStringZZZ[])null);
	}
	
	@Override
	public String compute(Object obj, String[] saLog) throws ExceptionZZZ {
		return this.compute(obj, saLog, (IEnumSetMappedLogStringZZZ[])null);
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setFormatPositionsMapped(IEnumSetMappedLogStringZZZ[] ienumaMappedFormat) {
		this.ienumaMappedFormat=ienumaMappedFormat;
	}

	@Override
	public IEnumSetMappedLogStringZZZ[] getFormatPositionsMapped() throws ExceptionZZZ {
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
	public IEnumSetMappedLogStringZZZ[] getFormatPositionsMappedDefault() throws ExceptionZZZ {
		
		//Merke: Verwendet wird z.B. ein LogString in dieser Form, den es abzubilden gilt:
		//       In getPositionCurrent() wird schon die ThreadID zum ersten Mal gesetzt. Damit das Log lesbarer wird soll vor dem Status noch der Thread gesetzt werden.
		//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
		
		
		
		//Also Classname und Thread z.B. raus. Das 1. iARGNext ist für getPositionCurrent(), das 2. ARGNext für den Text ab "Status...", das 3. ARGNext als Reserve.

		//Array automatisch aus dem Enum errechnen.
		IEnumSetMappedLogStringZZZ[] ienumaReturn = EnumAvailableHelperZZZ.searchEnumMapped(this, ILogStringZZZ.sENUMNAME);
		return ienumaReturn;
	}
	
	@Override
	public abstract IEnumSetMappedLogStringZZZ[] getFormatPositionsMappedCustom();
	
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
			IEnumSetMappedLogStringZZZ ienumLogString = (IEnumSetMappedLogStringZZZ) ienum;
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
			IEnumSetMappedLogStringZZZ[]ienumaMappedFormat = this.getFormatPositionsMapped();
			if(ArrayUtilZZZ.isEmpty(ienumaMappedFormat))break main;
			
			int iPosition=0;
			for(IEnumSetMappedLogStringZZZ ienumMappedFormat : ienumaMappedFormat) {
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
