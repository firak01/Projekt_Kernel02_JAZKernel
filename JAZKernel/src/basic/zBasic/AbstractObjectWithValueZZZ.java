package basic.zBasic;

import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractObjectWithValueZZZ<T> extends AbstractObjectZZZ<T> implements IValueUserZZZ{
	private static final long serialVersionUID = -2430016737090603862L;

	protected String sValue = null;
	protected boolean bAnyValue = false;
	protected boolean bNullValue = false;
	
	public AbstractObjectWithValueZZZ() throws ExceptionZZZ{	
		super();
	}
	
	public AbstractObjectWithValueZZZ(String sValue) throws ExceptionZZZ {	
		super();
		AbstractObjectWithValueNew_(sValue);
	}
	
	private void AbstractObjectWithValueNew_(String sValue) throws ExceptionZZZ{
		main:{
			if(sValue==null){
				ExceptionZZZ ez = new ExceptionZZZ("Element-Value", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					
			
			this.setValue(sValue);			
		}//End main
	}
	
	
	//### Aus IValueUserZZZ
	@Override
	public String getValue() throws ExceptionZZZ {
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return null; //wenn die Section nicht existiert, dann auch kein Wert.			
		}else {
			return this.sValue;
		}
	}

	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		
		this.hasAnyValue(true);
		this.sValue=sValue;	
		if(sValue!=null){		
			this.hasNullValue(false);
		}else{
			this.hasNullValue(true);
		}		
	}
	
	@Override
	public boolean hasAnyValue() {
		return this.bAnyValue;
	}	
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar
	//daher protected. Was nicht im Intface definierbar ist.
	public void hasAnyValue(boolean bAnyValue) {
		this.bAnyValue=bAnyValue;
	}
	
	@Override
	public boolean hasNullValue() {
		return this.bNullValue;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar, 
	//daher protected. Was nicht im Intface definierbar ist.
	public void hasNullValue(boolean bNullValue) {
		this.bNullValue=bNullValue;
	}
	//####################################################
	
}
