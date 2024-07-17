package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;

public class TagSimpleZZZ extends AbstractTagSimpleZZZ{
	private static final long serialVersionUID = 4140817328884049290L;

	public TagSimpleZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public TagSimpleZZZ(String sTagName, String sTagValue) throws ExceptionZZZ{
		super(sTagName, sTagValue);
	}

	@Override
	public String getNameDefault() throws ExceptionZZZ {		
		return null;
	}
}
