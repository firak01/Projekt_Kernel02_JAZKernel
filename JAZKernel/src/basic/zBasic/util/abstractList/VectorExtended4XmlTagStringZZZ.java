package basic.zBasic.util.abstractList;

import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.IVectorExtended4XmlZZZ;

/** Funktionalität:
 *  Das Hinzugefuegte Object wird mit den angegebenen XML-Tags umgeben.
 *  Damit wird quasi eine HashMap simuliert, bei der ein Tag als Key abgelegt werden wuerde.  
 * 
 * @author Fritz Lindhauer, 09.06.2024, 09:19:40
 * 
 */
public class VectorExtended4XmlTagStringZZZ<T> extends VectorExtendedZZZ<T> implements IVectorExtended4XmlZZZ {
	private static final long serialVersionUID = -3251609755562628308L;

	
	/**Mache aus dem Key einen Tag, der dann den Wert umschliesst und lege diesen neuen String ab.
	 * @param key
	 * @param value
	 * @return
	 * @author Fritz Lindhauer, 09.06.2024, 09:30:26
	 * @param <K>
	 */
	@Override
	public Object put(Object key, Object value) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(key==null)break main;
			
			sReturn = XmlUtilZZZ.computeTag(key.toString(), value.toString());		
			this.add(sReturn);
		}//end main:
		return sReturn;
	}
}
