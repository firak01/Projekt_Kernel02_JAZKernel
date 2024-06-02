package basic.zBasic.util.xml;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.tagtype.ITagZZZ;

public interface IParserXmlZZZ {

	//Merke: also entweder nebeneinander liegende Tags, oder auch Tags, die Kindtags haben (d.h. sie enthalten auch einen Vector von Tags)
	public Vector<ITagZZZ> parse(String sText) throws ExceptionZZZ;
	

	//Mit den Mapped EnumWerten der Tag-Factory hat man dann die Möglichkeit alle Tags - die es zu finden gibt - im String abzuprüfen.
	TODOGOON20240521: Es Fehlt noch der Zugriff auf die TagFactory, in der dann in einem Enum alle Tags liegen...
	
}
