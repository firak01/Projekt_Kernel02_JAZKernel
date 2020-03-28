package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;

/**Wenn eine "User"-Klasse Expansion-Behandlung direkt anbieten soll. (Z.B. KernelFileZZZ)
 * @author Fritz Lindhauer
 *
 */
public interface IFileExpansionProxyZZZ extends IFileExpansionConstZZZ{
	public String getExpansionCurrent() throws ExceptionZZZ;
	public String getExpansionFirst() throws ExceptionZZZ;
	public String getExpansionNext() throws ExceptionZZZ;	
}
