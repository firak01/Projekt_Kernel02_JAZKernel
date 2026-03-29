package basic.zBasic;

import basic.zBasic.AbstractObjectWithFlagZZZ;

/**Damit man hier Generics sinnvoll Testen kann, beim Hinzuf�gen von Objekten, die �ber String etc. hinausgehen,
 *  diese Dummy-Klasse verwenden, die von ObjectZZZ erbt, also durchaus etwas mehr kann, z.B. Flags nutzen.
 * @author lindhaueradmin
 *
 */
public class DummyTestObjecWithDefaultValuesZZZ extends AbstractObjectWithFlagZZZ<Object> {
	private static final long serialVersionUID = 5419180949291043750L;
		private String sValue=null;
	
		public DummyTestObjecWithDefaultValuesZZZ() {
			super();
		}
		
		/** Damit kann man ReflectionTesten. Z.B. beim Aufruf von Objektvergleichen mit bestimmten Methoden.
		 *   Die folgenden Methode soll es nur in Objekten dieser DummyObjectZZZ - Klasse geben.
		* @return
		* 
		* lindhaueradmin; 23.12.2013 13:45:49
		 */
		public String gibtsNurImDummy(){
			return new String("Testwert");
		}
		
		public String getValue() {
			return this.sValue;
		}
		public void setValue(String sValue) {
			this.sValue = sValue;
		}
}
