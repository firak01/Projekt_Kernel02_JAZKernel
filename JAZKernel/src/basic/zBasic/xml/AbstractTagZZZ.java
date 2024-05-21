package basic.zBasic.xml;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractTagZZZ extends AbstractObjectZZZ implements ITagZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;
	protected ITagTypeZZZ objTagType = null;
	protected String sValue = null;
	
	public AbstractTagZZZ() throws ExceptionZZZ{
		super();
	}
	
	public AbstractTagZZZ(ITagTypeZZZ objType) throws ExceptionZZZ{
		super();
		AbstractTagNew_(objType, "");
	}
	
	public AbstractTagZZZ(ITagTypeZZZ objType, String sValue) throws ExceptionZZZ{
		super();
		AbstractTagNew_(objType, sValue);
	}
		
	private void AbstractTagNew_(ITagTypeZZZ objType, String sValue) throws ExceptionZZZ{
		main:{
			if(objType==null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelTagType", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sValue==null){
				ExceptionZZZ ez = new ExceptionZZZ("Element-Value", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					

			this.setTagType(objType);
			this.setValue(sValue);			
		}//End main
	}
		
	//######## Getter / Setter ##################
	@Override	
	public String getValue() throws ExceptionZZZ{
		return this.sValue;
	}

	@Override
	public void setValue(String sValue) throws ExceptionZZZ{
		this.sValue = sValue;
	}
	
	@Override 
	public String getElementString() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sValue = this.getValue();
			if(sValue==null) {
				sReturn = this.getEmpty();
				break main;
			}
			
			sReturn = this.getStarting() + sValue + this.getClosing();
		}
		return sReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Details aus TagType +++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//#### Aus Interfacace
	//s. Analog zu z.B. KernelZFormulaIni_NullZZZ, dort aber als Static Methoden.
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface

	@Override
	public ITagTypeZZZ getTagType(){
		return this.objTagType;
	}
	protected void  setTagType(ITagTypeZZZ objTagType){
		this.objTagType = objTagType;
	}
	
	
	@Override
	public String getName() throws ExceptionZZZ{
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagName();
		}else {
			return "";
		}
	}	
	
	@Override
	public String getStarting() throws ExceptionZZZ{
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagPartStarting();
		}else {
			return "";
		}
	}
	
	@Override
	public String getClosing() throws ExceptionZZZ{				
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagPartClosing();
		}else {
			return "";
		}
	}	
	
	@Override
	public String getEmpty() throws ExceptionZZZ{
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagPartEmpty();
		}else {
			return "";
		}
	}
		
}
