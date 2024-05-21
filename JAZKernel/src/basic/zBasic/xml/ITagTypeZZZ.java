package basic.zBasic.xml;

import basic.zBasic.ExceptionZZZ;

public interface ITagTypeZZZ {	
	public abstract String getTagName();
	public abstract void setTagName(String sTagName);
	
	
	public abstract String getTagPartStarting() throws ExceptionZZZ;
	public abstract String getTagPartClosing() throws ExceptionZZZ;
	public abstract String getTagPartEmpty() throws ExceptionZZZ;
}
