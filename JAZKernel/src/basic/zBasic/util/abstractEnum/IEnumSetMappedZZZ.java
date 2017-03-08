package basic.zBasic.util.abstractEnum;

import java.util.EnumSet;

public interface IEnumSetMappedZZZ {
	
	//Ds bring ENUM von sich auch mit
	public String name();
	public int getIndex();      //Das ist ordinal() von Enum
	public String toString();
	
	//Das sind meine Erweiterungen
	public String getAbbreviation();
	public String getDescription();
	public int getPosition(); //das ist normalerweies order()+1
	public EnumSet<?> getEnumSetUsed();
	
}
