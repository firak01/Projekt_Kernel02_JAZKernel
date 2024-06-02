package basic.zBasic.xml.tagtype;

import basic.zBasic.ExceptionZZZ;

public class TagByTypeZZZ extends AbstractTagByTypeZZZ implements ITagByTypeZZZ{
	private static final long serialVersionUID = 8817015233202583461L;

	public TagByTypeZZZ() throws ExceptionZZZ{
		super();
	}
	
	public TagByTypeZZZ(ITagTypeZZZ objType) throws ExceptionZZZ {
		super(objType);
	}
}
