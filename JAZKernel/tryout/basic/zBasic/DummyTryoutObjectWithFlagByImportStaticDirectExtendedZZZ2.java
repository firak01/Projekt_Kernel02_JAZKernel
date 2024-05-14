package basic.zBasic;

import static basic.zBasic.DummyTryoutObjectWithFlagByDirectZZZ.*;

public class DummyTryoutObjectWithFlagByImportStaticDirectExtendedZZZ2 extends AbstractObjectWithFlagZZZ<Object> implements IDummyTryoutObjectWithFlagByDirectZZZ{
	
	public DummyTryoutObjectWithFlagByImportStaticDirectExtendedZZZ2() {
		
	}
	
	public String usingConstants() {
		//Merke: Sobald diese Klasse mit AbstractObjectWithFlagZZZ erweitert wird, stehen die ueber "import static" eingebundenen enums nicht mehr zur Verfuegung
		//extends AbstractObjectWithFlagZZZ<Object> implements IDummyTryoutObjectWithFlagByDirectZZZ{		
		//JETZT FEHLER   String sConstant = FLAGZ.DUMMY02DIRECT.name();
		
		
		String sConstant = FLAGZ.DEBUG.name(); //Dann stehen nur noch diese Enums zur Verfuegung.
		return sConstant;
	}

}
