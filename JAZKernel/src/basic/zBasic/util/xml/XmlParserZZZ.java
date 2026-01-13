package basic.zBasic.util.xml;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.IVector4XmlZZZ;
import basic.zBasic.util.abstractList.Vector4XmlTagSimpleZZZ;
import basic.zBasic.util.abstractList.Vector4XmlTagStringZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.tree.ITreeNodeZZZ;
import basic.zBasic.util.datatype.tree.TreeNodeZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/** Einfacher Parser um einen String in seine XML Bestandteile zu zerlegen.
 *  Einfach deshalb, weil kein (JDOM) XML Dokument benoetigt wird.
 *  Es wird nur Stringverarbeitung gemacht.
 *  Allerdings werden ITagZZZ und ITagTypeZZZ verwendet.
 *  Die Liste der Tags kommt aus einem Enum, besser noch aus DER TagFactoryZZZ - Klasse.
 *  
 *  
 * Merke: Zum Parsen eines JDOM XML Dokument gibt es "ParserXMLDOMZZZ" 
 *        Aus dieser Klasse stammen dann auch einige Methodennamen, um das einheitlich zu gestalten.
 * 
 * @author fl86kyvo
 */
public class XmlParserZZZ extends AbstractObjectWithFlagZZZ implements IParserXmlZZZ{
	private static final long serialVersionUID = 1L;

	public XmlParserZZZ() {
		super();
	}
	
	@Override
	public IVector4XmlZZZ<?> parseToVectorTagString(String sText) throws ExceptionZZZ {
		IVector4XmlZZZ<?> vecReturn = null;
		main:{			
			boolean bWithoutText = this.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			vecReturn = XmlTagMatcherZZZ.parseElementsAsVector((Vector4XmlTagStringZZZ<String>) vecReturn, sText, !bWithoutText);			
		}
		return vecReturn;
	}
		
	/* (non-Javadoc)
	 * @see basic.zBasic.util.xml.IParserXmlZZZ#parse(java.lang.String)
	 */
	@Override
	public IVector4XmlZZZ<?> parseToVectorTagSimple(String sText) throws ExceptionZZZ{
		IVector4XmlZZZ<?> vecReturn = null; 
		main:{								
			boolean bWithoutText = this.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);			
			vecReturn = XmlTagMatcherZZZ.parseElementsAsVectorTagSimple((Vector4XmlTagSimpleZZZ<ITagSimpleZZZ>) vecReturn, sText, !bWithoutText);			
		}
		return vecReturn;
	}

	@Override
	public HashMapMultiIndexedZZZ<String, String> parseToMap(String sText) throws ExceptionZZZ {
		HashMapMultiIndexedZZZ<String,String> hmReturn = null;		
		main:{			
			boolean bWithoutText =  this.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			hmReturn = XmlTagMatcherZZZ.parseElementsAsMap(hmReturn, sText, !bWithoutText);
		}//end main:
		return hmReturn;
	}

	@Override
	public ITreeNodeZZZ<ITagSimpleZZZ> parseToTree(String sText) throws ExceptionZZZ {
		ITreeNodeZZZ<ITagSimpleZZZ> objReturn = null;
		main:{			
			boolean bWithoutText =  this.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			objReturn = XmlTagMatcherZZZ.parseElementsAsTree(sText, !bWithoutText);
		}//end main:
		return objReturn;
	}



	//###################################################
	//### FLAGS #########################################
	//###################################################
	//Methoden hier, da im Interface eingebunden	
	@Override
	public boolean getFlag(IParserXmlZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IParserXmlZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IParserXmlZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IParserXmlZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IParserXmlZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IParserXmlZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................

}
