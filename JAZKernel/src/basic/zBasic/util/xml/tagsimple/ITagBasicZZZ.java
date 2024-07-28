package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueUserZZZ;

public interface ITagBasicZZZ extends IValueUserZZZ {	
	public abstract String getNameDefault() throws ExceptionZZZ;
	public abstract String getName() throws ExceptionZZZ;	//Unterscheidet sich ggfs. in verschiedenen Klassen. Z.B. wg Vewendung TagByTypeZZZ
	public abstract void setName(String sTagName) throws ExceptionZZZ;
	
	//Berechne wie ein einfaches Tag (also ohne Attribute) aussieht, fuer diesen Namen
	String getTagStarting() throws ExceptionZZZ;
	String getTagClosing() throws ExceptionZZZ;
	String getTagEmpty() throws ExceptionZZZ;
	String getEmpty() throws ExceptionZZZ;

	//Berechne den Tag mit den enthaltenden Wert
	//20240520: Im Hinterkopf behalten, dass es ja org.w3c.dom.Element gibt. 
	//          Darum ElementString()... Aber ohne ein XML-Dokument verzichten wird auf org.w3c.dom.Element.
	String getElementString() throws ExceptionZZZ;	
}
