package basic.zBasic.xml.tagtype;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicZZZ;

//Merke: Der ITagType ist keine Erweiterung von ITagBasics 
public interface ITagTypeZZZ{	
	
	//Kein Setter, da über Konstante in der TagType-Klasse definiert
	public String getTagName() throws ExceptionZZZ;
	
	String getTagPartOpening() throws ExceptionZZZ;
	String getTagPartClosing() throws ExceptionZZZ;
	String getTagPartEmpty() throws ExceptionZZZ;	
}
