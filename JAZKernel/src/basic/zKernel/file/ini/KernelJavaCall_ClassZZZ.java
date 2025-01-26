package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public class KernelJavaCall_ClassZZZ<T> extends AbstractIniTagSimpleZZZ<T>{//AbstractIniTagWithExpressionBasicZZZ<T>{
	private static final long serialVersionUID = 8075675133317942190L;
	public static String sTAG_NAME = "Z:Class";
	
	public KernelJavaCall_ClassZZZ() throws ExceptionZZZ{
		super();
		KernelJavaCallClassNew_();
	}
			
	private boolean KernelJavaCallClassNew_() throws ExceptionZZZ {
//		 boolean bReturn = false;
//		 main:{
//				if(this.getFlag("init")==true){
//					bReturn = true;
//					break main;
//				}
//				
//		 	}//end main:
			return true;
	 }//end function KernelJavaCallClassNew_
		
	
	//###### Getter / Setter
	
	
	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return KernelJavaCall_ClassZZZ.sTAG_NAME;
	}
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}
	
	//### aus IParseEnabled
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parsePostCustom_(vecExpression, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.parsePostCustom_(vecExpression, bRemoveSurroundingSeparators);
	}
	
	//Methode mit Reference Objekt
	private Vector3ZZZ<String> parsePostCustom_(Vector3ZZZ<String> vecExpressionIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn; String sReturn; String sReturnTag = null;
		String sExpression = null;		
		boolean bUseParse = false;
		
		main:{			
			if(vecExpressionIn==null) break main;
		
			//Ziel: Entferne ein ggfs. von vorherigen Operationen noch vorhandenen Z-Tag, damit wirklich nur die reine Java-Klasse als Wert uebrigbleibt.
			sExpression = (String) vecExpressionIn.get(1);
			if(StringZZZ.isEmpty(sExpression)) break main;
			
            //++++ der Custom Code: Sicherstellen, das kein Z-Tag um die gefundene Java Klasse steht. Der könnte als Rest einer vorherigen Berechnung vorhanden sein.
			//Als echten Ergebniswert aber die konkreten <Z>-Tags (z.B. eines Solves) ggfs. rausrechnen, falls gewuenscht
			//Z...-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
			bUseParse = this.isParserEnabledThis();
			if(bUseParse) {			
				if(bRemoveSurroundingSeparators) {
					String sTagStart = "<Z>"; //this.getTagStarting();
					String sTagEnd = "</Z>";  //this.getTagClosing();
					sReturnTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStart, sTagEnd);  //also von innen nach aussen
					sReturn = sReturnTag;
					this.setValue(sReturnTag);
					
					vecExpressionIn.replace(sReturn);
				}	
			}else {
				//Wenn der Parser herausgenommen ist, seine Tags nicht entfernen.
			}
	        //++++ der custom Code
                      
		}//end main:
				
		//#################################
		return vecReturn;
	}


}//End class