package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;

public interface ITagBasicChildZZZ extends ITagBasicZZZ {
	public abstract String getParentNameDefault() throws ExceptionZZZ;
	public abstract String getParentName() throws ExceptionZZZ;
	public abstract void setParentName(String sTagName) throws ExceptionZZZ;	
}
