package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;

public interface ITagSimpleZZZ extends ITagBasicsZZZ{
	
	
	public abstract String getName() throws ExceptionZZZ; //Unterscheidet sich ggfs. in verschiedenen Klassen. Z.B. wg Vewendung TagByTypeZZZ
	public abstract void setName(String sTagName) throws ExceptionZZZ; 


}
