package basic.zBasic;

import static basic.zBasic.DummyTryoutObjectWithFlagByDirectZZZ.*;

public class DummyTryoutObjectWithFlagByImportStaticDirectZZZ{ 
	//Merke: Sobald diese Klasse mit AbstractObjectWithFlagZZZ erweitert wird, stehen die ueber "import static" eingebundenen enums nicht mehr zur Verfuegung
	//extends AbstractObjectWithFlagZZZ<Object> implements IDummyTryoutObjectWithFlagByDirectZZZ{
	
	public DummyTryoutObjectWithFlagByImportStaticDirectZZZ() {
		
	}
	
	public String usingConstants() {
		String sConstant = FLAGZ.DUMMY02DIRECT.name();
		
		return sConstant;
	}

}
