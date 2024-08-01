package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaIni_EmptyZZZ<T> extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelZFormulaIniZZZ{
//public class KernelZFormulaIni_EmptyZZZ  extends AbstractKernelUseObjectZZZ implements IKernelZFormulaIniZZZ{	
	private static final long serialVersionUID = 7203160369729097L;
	public static String sTAG_NAME = "z:Empty";
	//private FileIniZZZ objFileIni=null;
			
	//Braucht man hier die ini und den Kernel überhaupt???? oder kann man ein IniTagSimpleZZZ sein???
//	public KernelZFormulaIni_EmptyZZZ() throws ExceptionZZZ{
//	super("init");
//	KernelExpressionIniEmptyNew_(null);
//}

//	public KernelZFormulaIni_EmptyZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
//		super(objFileIni.getKernelObject());
//		KernelExpressionIniEmptyNew_(objFileIni);
//	}
//	
//	public KernelZFormulaIni_EmptyZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
//		super(objFileIni.getKernelObject(),saFlag);
//		KernelExpressionIniEmptyNew_(objFileIni);
//	}
//	
//	public KernelZFormulaIni_EmptyZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
//		super(objKernel);
//		KernelExpressionIniEmptyNew_(objFileIni);
//	}
//	
//	public KernelZFormulaIni_EmptyZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
//		super(objKernel, saFlag);
//		KernelExpressionIniEmptyNew_(objFileIni);
//	}
	
//	private boolean KernelExpressionIniEmptyNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
//		 boolean bReturn = false; 
//		 main:{		
//		 															
//				if(this.getFlag("init")==true){
//					bReturn = true;
//					break main;
//				}										
//					
//				this.setFileIni(objFileIni);
//		 	}//end main:
//			return bReturn;
//		 }//end function KernelExpressionMathSolverNew_
		
	
	
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
			
	@Override
	public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String> vecReturn = new Vector<String>();
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			//Nun die Section suchen
			Vector<String> vecSection = this.computeExpressionFirstVector(sLineWithExpression);	
								
			String sSection = (String) vecSection.get(1);
			String sProperty = (String) vecSection.get(2);
			String sBefore = "";
			String sRest = "";
						
			if(!(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty))){

					//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
					String sMathValueTag = XmlUtilZZZ.computeTagPartClosing(this.getName());
					if(StringZZZ.contains(sProperty, sMathValueTag)){
						sBefore = (String) vecSection.get(0);
						sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
						sProperty = StringZZZ.left(sProperty, sMathValueTag);												
					}
										
					FileIniZZZ objFileIni = this.getFileConfigKernelIni();
					if(objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					//20080109 FGL: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
					String sSystemNr = this.getKernelObject().getSystemNumber();
					String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
					
					//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
					if(sValue!=null){
						
						//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
						if(!StringZZZ.isEmpty(sBefore)){
							if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
							vecReturn.add(0, sBefore);
						}else{
							vecReturn.add(0,"");
						}
																
						if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
						vecReturn.add(1, sValue);
						
						if(vecReturn.size()>=3) vecReturn.removeElementAt(2); //Immer den Namen der Property löschen....
						if(!StringZZZ.isEmpty(sRest)){							
							vecReturn.add(2, sRest); //Falls vorhanden einen Restwert eintragen.
						}else{
							vecReturn.add(2,"");
						}		
				}//end if sValue!=null
									
			}//end if isempty(sSection)
			
		}//end main:
		return vecReturn;
	}
	
	@Override
	public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		boolean bReturn=false;
		if(StringZZZ.isEmptyTrimmed(sStringToProof)) bReturn = true;
		return bReturn;
	}

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
	
	/**
	 * Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der
	 * ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element
	 * ist der Ausdruck NACH der ersten Expression.
	 * 
	 * @param sLineWithExpression
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector<String>computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector<String>vecReturn = new Vector<String>();		
		main:{
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sLineWithExpression, this.getTagEmpty(), false, false);
		}
		return vecReturn;
	}
	
	@Override
	public String parse(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
			
			//... also bei Empty Tag genauso verfahren wie bei anderen Tags???
			//Das halte ich fuer nicht korrekt, mal debuggen. Falls das klappt diese Methode hier löschen
			//und nur Elternklassenmethode nutzen
			
			//Bei einfachen Tags den Ersten Vektor holen
			Vector<String> vecAll = this.computeExpressionFirstVector(sLineWithExpression);
			
			//Bei einfachen Tags, den Wert zurückgeben
			sReturn = (String) vecAll.get(1);
			this.setValue(sReturn);
			
			//implode NUR bei CASCADED Tags, NEIN: Es koennen ja einfache String vor- bzw. nachstehend sein.
			String sExpressionImploded = VectorZZZ.implode(vecAll);
			sReturn = sExpressionImploded;  //Merke: Der eigentliche Wert des Tags unterscheidet sich also vom Gesamt-compute
		}//end main:
		return sReturn;
	}	
	
	@Override
	public String[] parseAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(!this.isParseRelevant(sLineWithExpression)) break main;
						
			String sDelimiter;
			if(StringZZZ.isEmpty(sDelimiterIn)) {
				sDelimiter = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
			}else {
				sDelimiter = sDelimiterIn;
			}
			
			String[] saExpression = StringZZZ.explode(sLineWithExpression, sDelimiter);
			
			//Hier einfach das Leerzeichen zurückgeben
			String sValue = null;
			ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>();
			for(String sExpression : saExpression) {
				sValue = this.parse(sExpression);
				listasValue.add(sValue);
			}
			saReturn = listasValue.toStringArray();
			
		}//end main:
		return saReturn;
	}
	
	@Override
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(!this.isParseRelevant(sLineWithExpression)) break main;
			
			//Hier einfach eine leere Expression zurückgeben
			sReturn = "<Z/>";
			
		}//end main:
		return sReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#convert(java.lang.String)
	 */
	@Override
	public String convert(String sLineWithoutExpression) throws ExceptionZZZ{
		String sReturn = sLineWithoutExpression;
		main:{
			if(!this.isStringForConvertRelevant(sLineWithoutExpression)) break main;
			
			//Hier einfach den Leeren-Tag zurückgeben
			sReturn = this.getTagEmpty();
						
		}//end main
		return sReturn;
	}

	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return KernelZFormulaIni_EmptyZZZ.sTAG_NAME;
	}
}//End class