package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class LogStringZZZ extends AbstractLogStringZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

		//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
		private LogStringZZZ() throws ExceptionZZZ{		
		}
		
		public static LogStringZZZ getInstance() throws ExceptionZZZ{
			if(objLogStringSingleton==null){
				objLogStringSingleton = new LogStringZZZ();
			}
			return (LogStringZZZ) objLogStringSingleton;	
		}

		@Override
		public int[] getFormatPositionsCustom() {
			//Merke: Verwendet wird z.B. ein LogString in dieser Form, den es abzubilden gilt:
			//       In getPositionCurrent() wird schon die ThreadID zum ersten Mal gesetzt. Damit das Log lesbarer wird soll vor dem Status noch der Thread gesetzt werden.
			//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
			
			//Also Classname und Thread z.B. raus. Das 1. iARGNext ist für getPositionCurrent(), das 2. ARGNext für den Text ab "Status...", das 3. ARGNext als Reserve.
			int[] iaReturn = {

				//ILogStringZZZ.iCLASSNAME,
				//ILogStringZZZ.iTHREAD,
				ILogStringZZZ.iARGNEXT01,
				ILogStringZZZ.iTHREAD,				
				ILogStringZZZ.iARGNEXT02,
				ILogStringZZZ.iARGNEXT01,
				
			};
			return iaReturn;
		}

		@Override
		public HashMap<Integer, String> getHashMapFormatPositionStringCustom() {
			HashMap<Integer,String>hmReturn=super.getHashMapFormatPositionStringDefault();
			
			//Hier mögliche Abweichende Strings angeben, z.B. in einfache Hochkommata packen			
			hmReturn.put(new Integer(ILogStringZZZ.iCLASSNAME),"'" + ILogStringZZZ.sCLASSNAME + "'");
			//und das ist nicht notwendig, dar argnext02 schon einfache hochkommata enthaelt: hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT01), "'" + ILogStringZZZ.sARGNEXT01 + "'");
			//und das bleibt unveraendert hmReturn.put(new Integer(ILogStringZZZ.iTHREAD), ILogStringZZZ.sTHREAD);
			
			
			return hmReturn;
		}

		

}
