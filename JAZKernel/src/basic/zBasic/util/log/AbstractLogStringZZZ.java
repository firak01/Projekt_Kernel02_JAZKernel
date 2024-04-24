package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public abstract class AbstractLogStringZZZ extends AbstractObjectWithFlagZZZ implements ILogStringZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	protected static ILogStringZZZ objLogStringSingleton; //muss als Singleton static sein
	protected HashMap<Integer,String>hmFormatPositionString=null;
	
	//Das Fomat
	protected int[]iaFormat=null;
	
	@Override
	public String compute(Object obj, String sLog) throws ExceptionZZZ {
				String[] saLog = new String[1];
				saLog[0] = sLog;
				return this.compute(obj, saLog);
	}
	
	@Override
	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ {
				String[] saLog = new String[2];
				saLog[0] = sLog01;
				saLog[1] = sLog02;
				return this.compute(obj, saLog);
	}
	
	@Override
	public String compute(Object obj, String[] saLog) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(obj==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING,   AbstractLogStringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(ArrayUtilZZZ.isEmpty(saLog)) {
				//mach nix
				break main;
			}
			
			
			//Der zu verwendende Logteil
			String sLogUsed;
			
			//Anzahl der geschriebenen sLogs aus saLog
			int iLogIndexNext=0;
			
			
			//Ermittle in einer Schleife den auszugebenden Teil
			int[] iaFormat = this.getFormatPositions();
			for(int iFormat : iaFormat){
				sLogUsed=null; //wieder zuruecksetzen. Ordnung muss sein.
				
				
				switch(iFormat) {
				case ILogStringZZZ.iCLASSNAME:
					if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
					}else {
						String sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iCLASSNAME));
						sLogUsed = String.format(sFormat, obj.getClass().getName());						
					}
					break;
					
				case ILogStringZZZ.iTHREAD:
					if(this.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD)) {
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"In diesem Format ist die Ausgabe der ThreaId per gesetztem Flag unterbunden.");
					}else {
						String sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iTHREAD));
						
						long lngThreadID = Thread.currentThread().getId();
						sLogUsed = String.format(sFormat, lngThreadID);
					}				
					break;
					
				case ILogStringZZZ.iARGNEXT01:					
					if(saLog.length>iLogIndexNext) {
						String sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iARGNEXT01));
						
						sLogUsed = String.format(sFormat, saLog[iLogIndexNext]);
						iLogIndexNext++;
					}					
					break;
					
				case ILogStringZZZ.iARGNEXT02:					
					if(saLog.length>iLogIndexNext) {
						String sFormat = this.getHashMapFormatPositionString().get(new Integer(ILogStringZZZ.iARGNEXT02));
						
						sLogUsed = String.format(sFormat, saLog[iLogIndexNext]);
						iLogIndexNext++;
					}					
					break;
					
				default:
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+"Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFormat="+iFormat);
					break;					
				}				
				
				
				if(sLogUsed!=null) { 
					if(sReturn==null) {
						sReturn = sLogUsed;
					}else {
						//Die einzelnen Bestandteile noch mit Leerstring voneinander trennen.
						sReturn = sReturn + " " + sLogUsed;
					}					
				}
				
			}//end for
					
			
		}//end main:
		return sReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setFormatPositions(int[] iaFormat) {
		this.iaFormat=iaFormat;
	}

	@Override
	public int[] getFormatPositions() {
		if(ArrayUtilZZZ.isEmpty(this.iaFormat)) {
			this.iaFormat = this.getFormatPositionsCustom();
			
			//Wenn im custom nix drin ist, default nehmen
			if(ArrayUtilZZZ.isEmpty(iaFormat)) {
				this.iaFormat = this.getFormatPositionsDefault();
			}
		}
		return this.iaFormat;
	}
	
	
	@Override
	public int[] getFormatPositionsDefault() {
		//Merke: Verwendet wird z.B. ein LogString in dieser Form, den es abzubilden gilt:
		//       In getPositionCurrent() wird schon die ThreadID zum ersten Mal gesetzt. Damit das Log lesbarer wird soll vor dem Status noch der Thread gesetzt werden.
		//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
		
		//Also Classname und Thread z.B. raus. Das 1. iARGNext ist für getPositionCurrent(), das 2. ARGNext für den Text ab "Status...", das 3. ARGNext als Reserve.
		int[] iaReturn = {

			//ILogStringZZZ.iCLASSNAME,
			//ILogStringZZZ.iTHREAD,
			ILogStringZZZ.iARGNEXT01,  //ggfs. getPostionCurrent()
			ILogStringZZZ.iTHREAD,	 			
			ILogStringZZZ.iARGNEXT02,  //Der LogString
			ILogStringZZZ.iARGNEXT01,  //... zur Reserve
			
		};
		return iaReturn;
	}
	
	@Override
	public abstract int[] getFormatPositionsCustom();
	
	//+++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setHashMapFormatPositionString(HashMap<Integer, String> hmFormatPostionString) {
		this.hmFormatPositionString = hmFormatPostionString;
	}
	
	@Override
	public HashMap<Integer,String>getHashMapFormatPositionString(){
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
	public HashMap<Integer, String> getHashMapFormatPositionStringDefault() {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT01), ILogStringZZZ.sARGNEXT01);
		hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT02), ILogStringZZZ.sARGNEXT02);
		hmReturn.put(new Integer(ILogStringZZZ.iCLASSNAME),ILogStringZZZ.sCLASSNAME);
		hmReturn.put(new Integer(ILogStringZZZ.iTHREAD), ILogStringZZZ.sTHREAD);
		return hmReturn;
	}
	
	@Override 
	public abstract HashMap<Integer,String>getHashMapFormatPositionStringCustom();

	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	@Override
	public int computeFormatPositionsNumber() {
		//FGL 20240421 - experimentell und nicht notwendig, solange ich den Weg der Rueckumwandlung noch nicht kann.
		int iReturn = 0;
		main:{
			int[]ia = this.getFormatPositions();
			if(ArrayUtilZZZ.isEmpty(ia))break main;
			
			int iPosition=0;
			for(int i : ia) {
				iPosition++;
				iReturn = iReturn + PrimeNumberZZZ.primePosition(i, iPosition);
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
