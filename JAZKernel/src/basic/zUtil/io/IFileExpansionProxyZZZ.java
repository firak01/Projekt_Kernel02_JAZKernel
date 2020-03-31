package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;

/**Wenn eine "User"-Klasse Expansion-Behandlung direkt anbieten soll. (Z.B. KernelFileZZZ)
 * @author Fritz Lindhauer
 *
 */
public interface IFileExpansionProxyZZZ extends IFileExpansionConstZZZ{
	public String searchExpansionCurrent() throws ExceptionZZZ;
	public String searchExpansionFreeLowest() throws ExceptionZZZ;
	public String searchExpansionFreeNext() throws ExceptionZZZ;	
}
