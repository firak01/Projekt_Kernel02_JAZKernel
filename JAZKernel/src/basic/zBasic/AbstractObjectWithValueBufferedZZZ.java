package basic.zBasic;

import java.util.Vector;

import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;

public abstract class AbstractObjectWithValueBufferedZZZ<T> extends AbstractObjectWithValueZZZ<T> implements IValueBufferedUserZZZ{
	private static final long serialVersionUID = -2430016737090603862L;
	
	protected VectorDifferenceZZZ<String> vecValue = null;
	
	public AbstractObjectWithValueBufferedZZZ() throws ExceptionZZZ{	
		super();
	}
	
	public AbstractObjectWithValueBufferedZZZ(String sValue) throws ExceptionZZZ{	
		super(sValue);
	}
	
	//### aus IResettableValuesZZZ
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		if(this.vecValue!=null)this.vecValue.clear();
		return true;
	}
	
	//### Aus IValueBufferedUserZZZ
	@Override 
	public VectorDifferenceZZZ<String> getValueVector(){
		if(this.vecValue==null) {
			this.vecValue = new VectorDifferenceZZZ<String>();
		}
		return this.vecValue;
	}
	
	//Merke: Muss wg. dem Vector als Buffer ueberschrieben werden
	@Override
	public String getValue() {
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return null; //wenn die Section nicht existiert, dann auch kein Wert.			
		}else {
			return this.getValueVector().getEntryHigh();
		}
	}

	//Merke: Muss wg. dem Vector als Buffer ueberschrieben werden
	@Override
	public void setValue(String sValue) {
		
		this.hasAnyValue(true);
		this.getValueVector().add(sValue);	
		if(sValue!=null){		
			this.hasNullValue(false);
		}else{
			this.hasNullValue(true);
		}		
	}
	
	
	
	//####################################################
	
}
