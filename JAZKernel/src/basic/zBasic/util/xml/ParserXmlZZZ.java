package basic.zBasic.util.xml;

import java.util.Vector;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.ITagZZZ;

/** Einfacher Parser um einen String in seine XML Bestandteile zu zerlegen.
 *  Einfach deshalb, weil kein (JDOM) XML Dokument benoetigt wird.
 *  Es wird nur Stringverarbeitung gemacht.
 *  Allerdings werden ITagZZZ und ITagTypeZZZ verwendet.
 *  Die Liste der Tags kommt aus einem Enum, besser noch aus DER TagFactoryZZZ - Klasse.
 *  
 *  
 * Merke: Zum Parsen eines JDOM XML Dokument gibt es "ParserXMLDOMZZZ" 
 *        Aus dieser Klasse stammen dann auch einige Methodennamen
 * 
 * @author fl86kyvo
 *
 */
public class ParserXmlZZZ extends AbstractObjectZZZ implements IParserXmlZZZ{
	
	public ParserXmlZZZ() {
		super();
	}
		
	/* (non-Javadoc)
	 * @see basic.zBasic.util.xml.IParserXmlZZZ#parse(java.lang.String)
	 */
	@Override
	public Vector<ITagZZZ> parse(String sText) throws ExceptionZZZ{
		Vector<ITagZZZ> vecReturn = new Vector<ITagZZZ>();
		main:{
			TODOGOON20240521; 
		}
		return vecReturn;
	}
}
