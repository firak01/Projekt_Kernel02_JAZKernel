package basic.zBasic;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**Manchmal notweniger "Lueckenfueller".
 * Beispielsweise kann man einem Vektor kein "NULL" zuweisen.
 *  
 * 
 * @author Fritz Lindhauer, 25.07.2025, 08:24:05
 * 
 */
public class NullObjectZZZ extends AbstractObjectZZZ{
	private static final long serialVersionUID = 5904573388001179481L;

	public NullObjectZZZ() {
	}
	
	@Override
	public String toString(){
		return new String("");
	}

	public String valueOf() {
		return null;
	}
	
}
