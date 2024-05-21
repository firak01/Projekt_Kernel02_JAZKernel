package basic.zBasic.reflection.position;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.AbstractTagTypeZZZ;
import basic.zBasic.xml.TagFactoryZZZ;

public class TagTypeFileNameZZZ extends AbstractTagTypeZZZ {
	private static final long serialVersionUID = 1162924189948537555L;
	public final static String sTAGNAME = "filename"; //Das geht nicht, das diese Konstante genau in der Enum verwendet wird. TagFactoryZZZ.TAGTYPE.FILENAME.getTag();
	
	public TagTypeFileNameZZZ() throws ExceptionZZZ {
		super(sTAGNAME);		
	}	

	
	
	
	 
}
