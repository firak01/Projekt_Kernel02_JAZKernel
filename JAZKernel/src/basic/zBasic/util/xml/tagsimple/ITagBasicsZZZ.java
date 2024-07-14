package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueUserZZZ;

public interface ITagBasicsZZZ extends IValueUserZZZ {	
	public abstract String getName() throws ExceptionZZZ;	
	//Hier nicht definiert, da ggfs. in TagByType und TagSimple unterschiedlich
	//public abstract void setName(String sTagName);
	
	//Berechne wie ein einfaches Tag (also ohne Attribute) aussieht, fuer diesen Namen
	String getTagPartStarting() throws ExceptionZZZ;
	String getTagPartClosing() throws ExceptionZZZ;
	String getTagPartEmpty() throws ExceptionZZZ;
	String getEmpty() throws ExceptionZZZ;

	//Berechne den Tag mit den enthaltenden Wert
	//20240520: Im Hinterkopf behalten, dass es ja org.w3c.dom.Element gibt. 
	//          Darum ElementString()... Aber ohne ein XML-Dokument verzichten wird auf org.w3c.dom.Element.
	String getElementString() throws ExceptionZZZ;	
}
