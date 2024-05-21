package basic.zBasic.util.datatype.xml;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.ITagTypeZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;

/**Einfache Klasse, in der ohne ein XML-Dokument zu haben
 * Statt dessen mit den Interfaces ITagZZZ, bzw. ITagTypeZZZ arbeiten. 
 * Tags mit den Mitteln der Stringverarbeitung verarbeitet, gesucht, Werte herausgeloest, etc. werden.
 * @author fl86kyvo
 *
 * Merke: Im Gegensatz zu XMLUtil oder XMLDocumentUtilZZZ Klasse
 * wird hier nicht JDom oder ein XML - Dokument gebraucht.
 *
 */
public class XmlUtilZZZ {

	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sLineWithExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public static Vector computeExpressionFirstVector(String sLineWithExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		Vector vecReturn = new Vector();		
		main:{
			if(objTagType==null) break main;
			
			if(StringZZZ.contains(sLineWithExpression, objTagType.getTagPartEmpty())) {
				vecReturn = StringZZZ.vecSplitFirst(sLineWithExpression, objTagType.getTagPartEmpty(), false,true);				
				
			}else {
				vecReturn = StringZZZ.vecMid(sLineWithExpression, objTagType.getTagPartStarting(), objTagType.getTagPartClosing(), false,true);
				
			}
		}
		return vecReturn;
	}
	
	public static boolean isExpression(String sLine, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, objTagType.getTagPartStarting(), false);
			if(btemp) {
				btemp = StringZZZ.contains(sLine, objTagType.getTagPartClosing(), false);
				if(btemp==false) break main;
			}else {
				btemp = StringZZZ.contains(sLine, objTagType.getTagPartEmpty(), false);
				if(btemp==false) break main;
			}
												
			bReturn = true;
		}//end main
		return bReturn;
	}
}
