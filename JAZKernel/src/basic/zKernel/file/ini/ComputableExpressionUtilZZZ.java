package basic.zKernel.file.ini;

import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

public class ComputableExpressionUtilZZZ {
	public static boolean isExpression4TagXml(String sLineWithExpression, String sTagName){
		boolean bReturn = false;
		main:{			
			bReturn = XmlUtilZZZ.containsTag(sLineWithExpression, sTagName, false);
			if(bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	/** Merke: Path-Anweisungen in einer ini-Datei haben eine andere Struktur als XML-Tags.
	 * @param sLineWitchExpression
	 * @param sTagName
	 * @return
	 * @author Fritz Lindhauer, 20.07.2024, 07:48:25
	 */
	public static boolean isExpression4Tag(String sLineWithExpression, String sTagStarting, String sTagClosing){
		boolean bReturn = false;
		main:{			
			bReturn=StringZZZ.containsAsTag(sLineWithExpression, sTagStarting, sTagClosing, false);
			if(bReturn) break main;
			
		}//end main
		return bReturn;
	}
}
