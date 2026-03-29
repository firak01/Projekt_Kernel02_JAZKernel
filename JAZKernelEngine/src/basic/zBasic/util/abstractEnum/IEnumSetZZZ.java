package basic.zBasic.util.abstractEnum;

import java.util.EnumSet;

public interface IEnumSetZZZ{
	//Das bring ENUM von sich auch mit
	public String name();
	
	public String getName();  //Das ist name() von Enum
	public int getIndex();    //Das ist ordinal() von Enum
	public int getPosition(); //das ist normalerweies order()+1
	public String toString();
		
	public EnumSet<?> getEnumSetUsed();
	
}
