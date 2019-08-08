package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyZZZ implements IConstantZZZ, ICounterStrategyZZZ{
	private boolean bRightAligned = false;
	private boolean bIncreasableInOtherMethod = true;
	private String sClassMethodInitialisedName=null;
	
	public AbstractCounterStrategyZZZ() throws ExceptionZZZ{
		this.setClassMethodInitialisedName();
	}
	
	//++++ Aus Interface
	@Override
	public boolean isRightAligned() {
		return this.bRightAligned;
	}
	@Override
	public void isRightAligned(boolean bValue) {
		this.bRightAligned = bValue;
	}
	
	
	@Override
	public boolean isIncreasableInOtherMethod(){
		return this.bIncreasableInOtherMethod;
	}
	@Override
	public void isIncreasableInOtherMethod(boolean bValue){
		this.bIncreasableInOtherMethod = bValue;
	}
	
	@Override
	public String getClassMethodInitialisedName(){
		return this.sClassMethodInitialisedName;
	}
	@Override
	public void setClassMethodInitialisedName() throws ExceptionZZZ{
		String sClassMethod = ReflectCodeZZZ.getCallingStackName();
		// TODO GOON 20190807 Bier aus dem Stack-Trace diejenige herausholen, die 
		//a) Vom Kernel Sind 
		//b) Kein Kontruktor ist (NEU)
		//c) die eheste ist
		String[] saClassMethod = ReflectCodeKernelZZZ.getCallingStack();
		sClassMethod = saClassMethod[0];
		
		//FRAGE: Wo startet der Stacktrace eigentlich immer? Wirklich in der main()-Methode.
		
		
		this.setClassMethodInitialisedName(sClassMethod);
	}
	@Override
	public void setClassMethodInitialisedName(String sClassWithMethod) throws ExceptionZZZ{
		if(StringZZZ.isEmpty(sClassWithMethod)){
			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Classenname.Methodenname erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(!StringZZZ.contains(sClassWithMethod, ".")){
			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Classenname.Methodenname erwartet. Es fehlt der Punkt!", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.sClassMethodInitialisedName = sClassWithMethod;
	}
	@Override
	public void setClassMethodInitialisedName(Class<?>objClass, String sMethod) throws ExceptionZZZ{
		if(objClass==null){
			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: Classenobject erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(StringZZZ.isEmpty(sMethod)){
			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Methodenname erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		String sClassname = objClass.getName();
		this.sClassMethodInitialisedName = sClassname + "." + sMethod;		
	}
	
	
	
}
