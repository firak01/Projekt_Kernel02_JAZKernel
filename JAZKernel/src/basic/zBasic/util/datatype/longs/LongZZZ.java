package basic.zBasic.util.datatype.longs;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import base.io.IoUtil;
import base.numeric.Hex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;

/** /** Merke: Der Packagename endet mit einem "s", da "long" selbst als java-Schluesselwort nicht darin verwendet werden darf.
 * @author fl86kyvo
 *
 */
public class LongZZZ implements IConstantZZZ, Serializable{
	
	public static String longToString(long lValue) throws ExceptionZZZ, IllegalArgumentException {
		return longToString_(lValue);
	}
	
	private static String longToString_(long lValue) throws ExceptionZZZ, IllegalArgumentException {
		String sReturn = null;
		main:{
			//Es gibt nun verschieden Möglichkeiten
			//Variante 1:
			sReturn = Hex.longToString(lValue);
			
			//Variante 2:
			//sReturn = IoUtil.longToString(lValue);

			//Variante 3:
			//Long objLong = new Long(lValue);
			//sReturn = objLong.toString();
		}//end main:
		return sReturn;
	}
}
