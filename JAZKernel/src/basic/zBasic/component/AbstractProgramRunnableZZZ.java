package basic.zBasic.component;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractProgramRunnableZZZ extends AbstractProgramZZZ implements IProgramRunnableZZZ {	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractProgramRunnableZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public AbstractProgramRunnableZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);		
	}
	
	//###
	public void run() {
		try {
			this.start();
		} catch (ExceptionZZZ ez) {
			try {
				this.logLineDate(ez.getDetailAllLast());
			} catch (ExceptionZZZ e1) {
				System.out.println(e1.getDetailAllLast());
				e1.printStackTrace();
			}
			
			try {
				String sLog = ez.getDetailAllLast();
				this.logLineDate("An error happend: '" + sLog + "'");
			} catch (ExceptionZZZ e1) {				
				System.out.println(ez.getDetailAllLast());
				e1.printStackTrace();
			}			
		} catch (InterruptedException e) {					
			try {
				String sLog = e.getMessage();
				this.logLineDate("An error happend: '" + sLog + "'");
			} catch (ExceptionZZZ e1) {
				System.out.println(e1.getDetailAllLast());
				e1.printStackTrace();
			}
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public abstract boolean start() throws ExceptionZZZ, InterruptedException;
		
	//##########################################
	//### FLAG HANDLING
	@Override
	public boolean getFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IProgramRunnableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IProgramRunnableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
	
	@Override
	public boolean proofFlagSetBefore(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	//##########################		
}
