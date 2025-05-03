package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IParseEnabledZZZ {
	boolean isParserEnabledGeneral() throws ExceptionZZZ;
	boolean isParserEnabledThis() throws ExceptionZZZ;	
}
