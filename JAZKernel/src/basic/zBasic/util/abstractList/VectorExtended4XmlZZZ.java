package basic.zBasic.util.abstractList;

import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

/** Funktionalität:
 *  Das Hinzugefuegte Object wird mit den angegebenen XML-Tags umgeben.
 *  Damit wird quasi eine HashMap simuliert, bei der ein Tag als Key abgelegt werden wuerde.  
 * 
 * @author Fritz Lindhauer, 09.06.2024, 09:19:40
 * 
 */
public class VectorExtended4XmlZZZ<K,V> extends VectorExtendedZZZ<V> {
	private static final long serialVersionUID = -3251609755562628308L;

	
	/**Mache aus dem Key einen Tag, der dann den Wert umschliesst und lege diesen neuen String ab.
	 * @param key
	 * @param value
	 * @return
	 * @author Fritz Lindhauer, 09.06.2024, 09:30:26
	 */
	public V put(K key, V value) {
		main:{
			if(key==null)break main;
			
			String sElement = XmlUtilZZZ.computeTag(key.toString(), value.toString());		
			this.add(sElement);
		}//end main:
		return value;
	}
}
