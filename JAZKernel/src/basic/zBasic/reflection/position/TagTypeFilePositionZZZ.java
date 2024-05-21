package basic.zBasic.reflection.position;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.AbstractTagTypeZZZ;
import basic.zBasic.xml.TagFactoryZZZ;

public class TagTypeFilePositionZZZ extends AbstractTagTypeZZZ {
	private static final long serialVersionUID = 1162924189948537555L;
	public final static String sTAGNAME = "fileposition";//Merke: Das geht nicht, da in dem Enum genau diese Konstente verwendet wird. TagFactoryZZZ.TAGTYPE.METHOD.getTag();
	
	public TagTypeFilePositionZZZ() throws ExceptionZZZ {
		super(sTAGNAME);		
	}	

	
	
	
	 
}
