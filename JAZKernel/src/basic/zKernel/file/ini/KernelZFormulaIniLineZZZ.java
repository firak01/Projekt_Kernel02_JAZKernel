package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zKernel.KernelUseObjectZZZ;

/**Klasse, in der die Zeile mit einer Z-Formula behandelt wird.
 * Z.B.: Erstelle eine Zeile aus einem übergebenen Objekt heraus (ICryptZZZ)
 *   * 
 * @author Fritz Lindhauer, 20.03.2023, 12:21:15
 * 
 */
public class KernelZFormulaIniLineZZZ  extends ObjectZZZ{
	public static String createLineFrom(ICryptZZZ objCrypt) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Crypt Algorithm Object'",iERROR_PARAMETER_MISSING, KernelZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			TODOGOON20230320;
			
		}
		return sReturn;
	}
}
