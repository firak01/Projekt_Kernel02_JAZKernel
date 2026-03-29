package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IExpressionEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IParseEnabledZZZ extends IExpressionEnabledZZZ{
	boolean isParserEnabledGeneral() throws ExceptionZZZ;
	boolean isParserEnabledThis() throws ExceptionZZZ;	 //normalerweise der Wert vom getFlag(... USE ...) des entsprechenden Interface des Solves/Parsers
	boolean isParserEnabledCustom() throws ExceptionZZZ; //darin können auch die "KindTags" beruecksichtigt werden, also auch deren getFlag(... USE ...) Werte.
}
