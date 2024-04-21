package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;

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
		public int[] getFormatPostitionsCustom() {
			int[] iaReturn = {
				ILogStringZZZ.iTHREAD,
				ILogStringZZZ.iARGNEXT,
				ILogStringZZZ.iCLASSNAME,
				ILogStringZZZ.iARGNEXT,
			};
			return iaReturn;
		}
}
