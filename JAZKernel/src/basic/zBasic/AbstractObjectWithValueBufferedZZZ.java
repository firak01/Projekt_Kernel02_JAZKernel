package basic.zBasic;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

public abstract class AbstractObjectWithValueBufferedZZZ<T> extends AbstractObjectWithValueZZZ<T> implements IValueBufferedUserZZZ{
	private static final long serialVersionUID = -2430016737090603862L;
	
	protected VectorExtendedDifferenceZZZ<String> vecValue = new VectorExtendedDifferenceZZZ<String>();
	
	public AbstractObjectWithValueBufferedZZZ() {	
		super();
	}
	
	//### Aus IValueBufferedUserZZZ
	@Override 
	public VectorExtendedDifferenceZZZ<String> getValueVector(){
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
