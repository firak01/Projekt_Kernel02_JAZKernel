package basic.zBasic.util.abstractList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;

/** Funktionalität:
 *  Das Hinzugefuegte Object wird mit den angegebenen XML-Tags umgeben.
 *  Damit wird quasi eine HashMap simuliert, bei der ein Tag als Key abgelegt werden wuerde.  
 * 
 * @author Fritz Lindhauer, 09.06.2024, 09:19:40
 * 
 */
public class VectorExtended4XmlTagSimpleZZZ<ITagSimpleZZZ> extends VectorExtendedZZZ<ITagSimpleZZZ> implements IVectorExtended4XmlZZZ {
	private static final long serialVersionUID = -3251609755562628308L;

	
	/**Mache aus dem Key einen Tag, der dann den Wert umschliesst und lege diesen neuen String ab.
	 * @param key
	 * @param value
	 * @return
	 * @author Fritz Lindhauer, 09.06.2024, 09:30:26
	 * @throws ExceptionZZZ 
	 */
	public ITagSimpleZZZ put(Object key, Object value) throws ExceptionZZZ {
		ITagSimpleZZZ objReturn=null;
		main:{
			if(key==null)break main;
			
			objReturn = (ITagSimpleZZZ) XmlUtilZZZ.computeAsTagSimple(key.toString(), value.toString());		
			this.add(objReturn);
		}//end main:
		return objReturn;
	}
}
