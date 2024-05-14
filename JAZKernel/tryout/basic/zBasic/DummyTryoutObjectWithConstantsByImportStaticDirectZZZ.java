package basic.zBasic;

import static basic.zBasic.DummyTryoutObjectWithConstantsZZZ.*;

public class DummyTryoutObjectWithConstantsByImportStaticDirectZZZ {

	
	public DummyTryoutObjectWithConstantsByImportStaticDirectZZZ() {
	}
	
	public String usingConstants() {
		String sConstant = sTEST; //BEMERKENSWERT: Diese Konstante ist in der Klasse definiert, die mit "import static" eingebunden wurde.
		return sConstant;
	}
}
