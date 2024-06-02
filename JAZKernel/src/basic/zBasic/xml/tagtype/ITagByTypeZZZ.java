package basic.zBasic.xml.tagtype;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicsZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;

/**This interface provides methods for reading every type of attribute, which is allowed (or is necessary to be read) in a html-tag.
 * @author 0823
 *
 */
public interface ITagByTypeZZZ extends ITagBasicsZZZ{
	
	public Vector<ITagByTypeZZZ> getChildTags(); //Also: Damit soll ohne eine Document-Klasse einen Struktur von Tags möglich werden.
	public void setChildTags(Vector<ITagByTypeZZZ> vecTags);
	
	//Kein Setter, da ein Tag nicht im Laufenden Program seinen Typ aendern kann.
	public ITagTypeZZZ getTagType();		
}
