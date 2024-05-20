package basic.zBasic.xml;

import basic.zBasic.ExceptionZZZ;

/**This interface provides methods for reading every type of attribute, which is allowed (or is necessary to be read) in a html-tag.
 * @author 0823
 *
 */
public interface ITagZZZ {	
	public ITagTypeZZZ getTagType();
	
	public String getValue() throws ExceptionZZZ;
	public void setValue(String sValuel) throws ExceptionZZZ;
	
	public String getName() throws ExceptionZZZ;
	//Kein Setter, da über Konstante in der Tag-Klasse definiert

	//Berechne wie ein einfaches Tag (also ohne Attribute) aussieht, fuer diesen Namen
	String getStarting() throws ExceptionZZZ;
	String getClosing() throws ExceptionZZZ;
	String getEmpty() throws ExceptionZZZ;

	//Berechne den Tag mit den enthaltenden Wert
	//20240520: Im Hinterkopf behalten, dass es ja org.w3c.dom.Element gibt. 
	//          Darum ElementString()... Aber ohne ein XML-Dokument verzichten wird auf org.w3c.dom.Element.
	String getElementString() throws ExceptionZZZ;
}
