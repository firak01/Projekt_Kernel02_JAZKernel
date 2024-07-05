package basic.zBasic;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

public abstract class AbstractObjectWithFormulaZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IObjectWithFormulaZZZ{
	private static final long serialVersionUID = 4049221887081114236L;
	protected IKernelConfigSectionEntryZZZ objEntry = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.
	
	
	public AbstractObjectWithFormulaZZZ() throws ExceptionZZZ{
		super();
		AbstractObjectWithFormulaNew_();
	}
	
	public AbstractObjectWithFormulaZZZ(String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);
		AbstractObjectWithFormulaNew_();
	}
	
	public AbstractObjectWithFormulaZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		AbstractObjectWithFormulaNew_();
	}
	
	private boolean AbstractObjectWithFormulaNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
//			 String stemp; boolean btemp;
		 	//try{	 		
		 			//setzen der uebergebenen Flags	
//					if(saFlagControlIn != null){
//						for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//							stemp = saFlagControlIn[iCount];
//							btemp = setFlag(stemp, true);
//							if(btemp==false){
//								ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//								throw ez;		 
//							}
//						}
			 
						if(this.getFlag("init")==true){
							bReturn = true;
							break main;
						}
//					}			
		 	}//end main:
			return bReturn;
		 }//end function KernelExpressionMathSolverNew_

	
	//### aus IKernelConfigSectionEntryZZZ 
	public IKernelConfigSectionEntryZZZ getEntry() {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ();			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	
	//### aus IValueSolvedUserZZZ

	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() {
		return this.getEntry().getValueVector();
	}

	@Override
	public String getValue() {
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() {
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() {
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) {
		this.getEntry().setValue(sRaw);
	}
	//##############################
	
}
