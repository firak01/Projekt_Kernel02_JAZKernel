package basic.zBasic.reflection.position;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.tagtype.AbstractTagTypeZZZ;

public class TagTypeLineNumberZZZ extends AbstractTagTypeZZZ {
	private static final long serialVersionUID = 1162924189948537555L;
	public static final String sTAGNAME = "linenr"; //Das geht nicht, das diese Konstante genau in der Enum verwendet wird.  TagFactoryZZZ.TAGTYPE.LINENUMBER.getTag();
		
	public TagTypeLineNumberZZZ() throws ExceptionZZZ {
		super(sTAGNAME);		
	}	
}
