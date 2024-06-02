package basic.zBasic.reflection.position;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.tagtype.AbstractTagTypeZZZ;

public class TagTypeMethodZZZ extends AbstractTagTypeZZZ {
	private static final long serialVersionUID = 1162924189948537555L;
	public final static String sTAGNAME = "method";//Merke: Das geht nicht, da in dem Enum genau diese Konstente verwendet wird. TagFactoryZZZ.TAGTYPE.METHOD.getTag();
	
	public TagTypeMethodZZZ() throws ExceptionZZZ {
		super(sTAGNAME);		
	}	
}
