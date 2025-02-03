package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

/** Merke: Einfache Tags sind nicht verschachtelt.
 *         => 1. Bei einer compute() Berechnung wird nur der Inhalt des Tags zurückgegeben. 
 *               Sie haben also keine Formel, wohl aber eine "Expression"
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 20.07.2024, 08:03:01
 * 
 */
public abstract class AbstractIniTagSimpleZZZ<T>  extends AbstractIniTagBasicZZZ<T> implements IConvertEnabledZZZ{
	private static final long serialVersionUID = -5785934791199206030L;
	
	public AbstractIniTagSimpleZZZ() throws ExceptionZZZ{
		super();
		AbstractKernelIniTagNew_();
	}
		
	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
		
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	//### Aus IResettableValuesZZZ
		@Override
		public boolean reset() throws ExceptionZZZ{
			super.reset();			
			return true;
		}
		
		@Override
		public boolean resetValues() throws ExceptionZZZ{
			super.resetValues();			
			return true;
		}
	
	//### aus IConvertableZZZ
	@Override
	public String convert(String sLine) throws ExceptionZZZ {
		return sLine;
	}	
	
	/* IDEE: convertable != parseable.
    convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
    parsable würde dann lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
	
	* 
	* (non-Javadoc)
	* @see basic.zKernel.file.ini.AbstractIniTagSimpleZZZ#isConvertRelevant(java.lang.String)
	*/
	@Override
	public boolean isConvertRelevant(String sLineWithExpression) throws ExceptionZZZ {
	//Nein return this.isParseRelevant(sStringToProof);
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
		
		bReturn = this.getEmpty().equalsIgnoreCase(sLineWithExpression);
		if(bReturn) break main;
	
		
		bReturn = XmlUtilZZZ.isSurroundedByTag(sLineWithExpression, this.getTagStarting(), this.getTagClosing());		
	}//end main
	return bReturn;
	}
	
	
	//### aus IExpressionUserZZZ
	@Override
	public String makeAsExpression(String sString) throws ExceptionZZZ{
		return ExpressionIniUtilZZZ.makeAsExpression(sString, this.getName());
	}
	
	
	
	//### aus IIniTagWithExpressionZZZ (siehe auch: IExpressionUserZZZ)	
	
	//### Aus IParseEnabledZZZ
	@Override
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		//Merke: Mit Klassen auf dem Flag-Weg koennen hier das Flag abfragen.
		return true;
	}
		
	

//	@Override
//	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
//		return vecExpression;
//	}
//
//
//	@Override
//	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
//		return vecExpression;
//	}


	
}//End class