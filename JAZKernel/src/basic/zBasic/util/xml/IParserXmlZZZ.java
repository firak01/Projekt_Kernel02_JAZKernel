package basic.zBasic.util.xml;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.IVectorExtended4XmlZZZ;
import basic.zBasic.util.abstractList.VectorExtended4XmlTagSimpleZZZ;
import basic.zBasic.util.abstractList.VectorExtended4XmlTagStringZZZ;
import basic.zBasic.util.datatype.tree.ITreeNodeZZZ;
import basic.zBasic.util.datatype.tree.TreeNodeZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;

public interface IParserXmlZZZ {

	//Merke: also entweder nebeneinander liegende Tags, oder auch Tags, die Kindtags haben (d.h. sie enthalten auch einen Vector von Tags)
	public IVectorExtended4XmlZZZ<?> parseToVectorTagSimple(String sText) throws ExceptionZZZ;
	public IVectorExtended4XmlZZZ<?> parseToVectorTagString(String sText) throws ExceptionZZZ;
		
	public HashMapMultiIndexedZZZ<String,String> parseToMap(String sText) throws ExceptionZZZ;
	public ITreeNodeZZZ<ITagSimpleZZZ> parseToTree(String sText) throws ExceptionZZZ;
	

	//Mit den Mapped EnumWerten der Tag-Factory hat man dann die Möglichkeit alle Tags - die es zu finden gibt - im String abzuprüfen.
	//TODOGOON20240521: Es Fehlt noch der Zugriff auf die TagFactory, in der dann in einem Enum alle Tags liegen...
	
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		PARSE_WITHOUTTEXT
	}
			
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
		
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
