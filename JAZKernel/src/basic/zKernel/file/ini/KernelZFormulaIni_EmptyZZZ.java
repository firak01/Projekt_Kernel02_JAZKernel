package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zBasic.util.xml.tagsimple.AbstractTagParseEnabledZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

//Klasse verwendet keinen Kernel, keine Expression Flags also nur ein einfacher Tag 
//Sie wird einfach nur vom KernelZFormulaIniConvertZZZ aus aufgerufen, wenn dieser die passenden Flags hat.
public class KernelZFormulaIni_EmptyZZZ<T> extends AbstractIniTagSimpleZZZ<T> implements IConvertEnabledZZZ{	
	private static final long serialVersionUID = 7203160369729097L;
	public static String sTAG_NAME = "z:Empty";
	
	//Hier die Variante ohne ini-File
	public KernelZFormulaIni_EmptyZZZ() throws ExceptionZZZ{		
		super();		
		KernelExpressionIniEmptyNew_();
	}
			
	private boolean KernelExpressionIniEmptyNew_() throws ExceptionZZZ {
//	 boolean bReturn = false; 
//	 main:{			 														
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}				
//			
//				
//	 	}//end main:
		return true;
	 }//end function KernelExpressionMathSolverNew_
			
	//### Aus IParseEnabledZZZ	
		
	//### Aus IIniTagBasicZZZ
	@Override
	public Vector<String> parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		return this.parseFirstVector(sLineWithExpression, true);
	}
	
	@Override
	public Vector<String> parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			//Nein, der Konverter nutzt das Flag auch nicht if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
			
			//nun den mittleren Teil weiter verarbeiten, sprich leersetzen
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			vecReturn.add(1, "");
			
			this.setValue(vecReturn.get(1));
		}//end main:
		return vecReturn;
	}
	
//	@Override
//	public Vector<String> parseFirstVector(String sLineWithExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
//		Vector<String> vecReturn = new Vector<String>();
//		main:{
//			//Nein, der Konverter nutzt das Flag auch nicht if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
//			
//			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
//			Vector<String> vecSection = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagStarting(), this.getTagClosing(), false, false);
//								
//			String sSection = (String) vecSection.get(1);
//			String sProperty = (String) vecSection.get(2);
//			String sBefore = "";
//			String sRest = "";
//						
//			if(!(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty))){
//
//					//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
//					String sMathValueTag = XmlUtilZZZ.computeTagPartClosing(this.getName());
//					if(StringZZZ.contains(sProperty, sMathValueTag)){
//						sBefore = (String) vecSection.get(0);
//						sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
//						sProperty = StringZZZ.left(sProperty, sMathValueTag);												
//					}
//										
//					FileIniZZZ objFileIni = this.getFileConfigKernelIni();
//					if(objFileIni==null){
//						ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//						throw ez;
//					}
//					
//					//20080109 FGL: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
//					String sSystemNr = this.getKernelObject().getSystemNumber();
//					String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
//					
//					//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
//					if(sValue!=null){
//						
//						//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
//						if(!StringZZZ.isEmpty(sBefore)){
//							if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
//							vecReturn.add(0, sBefore);
//						}else{
//							vecReturn.add(0,"");
//						}
//																
//						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
//						vecReturn.add(1, sValue);
//						
//						if(vecReturn.size()>=3) vecReturn.removeElementAt(2); //Immer den Namen der Property löschen....
//						if(!StringZZZ.isEmpty(sRest)){							
//							vecReturn.add(2, sRest); //Falls vorhanden einen Restwert eintragen.
//						}else{
//							vecReturn.add(2,"");
//						}		
//				}//end if sValue!=null
//									
//			}//end if isempty(sSection)
//			
//		}//end main:
//		return vecReturn;
//	}
	
	//### Aus Interface IKernelExpressionIniZZZ
//	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
//	* @param sLineWithExpression
//	* @return
//	* 
//	* lindhaueradmin; 06.03.2007 11:20:34
//	 * @throws ExceptionZZZ 
//	 */
//	@Override
//	public Vector<String> computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector<String> vecReturn = new Vector<String>();		
//		main:{
//			vecReturn = StringZZZ.vecMid(sLineWithExpression, XmlUtilZZZ.computeTagPartStarting(this.getName()), XmlUtilZZZ.computeTagPartClosing(this.getName()), false,false);
//		}
//		return vecReturn;
//	}
	
//	/**
//	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
//	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
//	 * ist der Ausdruck NACH der ersten Expression.
//	 * 
//	 * @param sLineWithExpression
//	 * @throws ExceptionZZZ
//	 */
//	@Override
//	public Vector<String>parseFirstVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector<String>vecReturn = new Vector<String>();		
//		main:{
//			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
//			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagEmpty(), false, false);
//		}
//		return vecReturn;
//	}
	
//	@Override
//	public String parse(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{
//			if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			//... also bei Empty Tag genauso verfahren wie bei anderen Tags???
//			//Das halte ich fuer nicht korrekt, mal debuggen. Falls das klappt diese Methode hier löschen
//			//und nur Elternklassenmethode nutzen
//			
//			//Bei einfachen Tags den Ersten Vektor holen
//			Vector<String> vecAll = this.parseFirstVector(sLineWithExpression);
//			
//			//Bei einfachen Tags, den Wert zurückgeben
//			this.setValue(vecAll.get(1));
//			
//			//implode NUR bei CASCADED Tags, NEIN: Es koennen ja einfache String vor- bzw. nachstehend sein.
//			sReturn = VectorZZZ.implode(vecAll);  //Merke: Der eigentliche Wert des Tags unterscheidet sich also vom Gesamt-compute
//		}//end main:
//		return sReturn;
//	}	
	

//	@Override
//	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{
//			if(!this.isParseRelevant(sLineWithExpression)) break main;
//			
//			//Hier einfach eine leere Expression zurückgeben
//			//TODOGOON20240905 DAS IST FALSCH....
//			//Richtig wäre es zu parsen und dann an der Leerstelle diesen Wert zu setzen...
//			sReturn = "<Z/>";
//			
//		}//end main:
//		return sReturn;
//	}
	
	
	//### aus IConvertableZZZ
	@Override
	public String convert(String sLineWithoutExpression) throws ExceptionZZZ{
		String sReturn = sLineWithoutExpression;
		main:{
			if(!this.isConvertRelevant(sLineWithoutExpression)) break main;
			
			//Hier einfach den Leeren-Tag zurückgeben. Merke: Convert != Parse, convert bezieht sich auf den ganzen String. 
			sReturn = "";
						
		}//end main
		return sReturn;
	}

	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelZFormulaIni_EmptyZZZ.sTAG_NAME;
	}
}//End class