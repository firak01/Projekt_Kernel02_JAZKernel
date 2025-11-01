package basic.zBasic.reflection.position;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.xml.tagtype.AbstractTagTypeZZZ;

public class TagTypePositionCurrentZZZ extends AbstractTagTypeZZZ {
	private static final long serialVersionUID = 1162924189948537555L;
	public final static String sTAGNAME = "positioncurrent";//Merke: Das geht nicht, da in dem Enum genau diese Konstente verwendet wird. TagFactoryZZZ.TAGTYPE.POSITIONCURRENT.getTag();
	
	public TagTypePositionCurrentZZZ() throws ExceptionZZZ {
		super(sTAGNAME);		
	}		 
}
