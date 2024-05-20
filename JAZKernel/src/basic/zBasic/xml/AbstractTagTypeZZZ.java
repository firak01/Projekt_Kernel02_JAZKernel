package basic.zBasic.xml;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;

public abstract class AbstractTagTypeZZZ extends AbstractObjectZZZ implements ITagTypeZZZ{
	protected String sTagName="";
	
	public AbstractTagTypeZZZ() throws ExceptionZZZ{
		super();
	}
	
	public AbstractTagTypeZZZ(String sName) throws ExceptionZZZ{
		super();
		AbstractTagTypeNew_(sName);
	}
	
	private boolean AbstractTagTypeNew_(String sName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			this.setTagName(sName);
			bReturn =true;
		}
		return bReturn;
	}
	
	@Override
	public String getTagName() {
		return this.sTagName;
	}
	
	@Override
	public void setTagName(String sTagName) {
		this.sTagName = sTagName;
	}
}
