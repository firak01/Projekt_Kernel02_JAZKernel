package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public abstract class AbstractIniTagCascadedZZZ<T> extends AbstractIniTagSimpleZZZ<T> implements IIniTagCascadedZZZ{
	private static final long serialVersionUID = 3041123191138631240L;

	public AbstractIniTagCascadedZZZ() throws ExceptionZZZ {
		super();
		AbstractKernelIniTagNew_();
	}

	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
		return true;
	}// end function AbstractKernelIniTagNew_

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
		
	//### Aus Interface IKernelExpressionIniZZZ		
	@Override
	public String parse(String sExpressionIn) throws ExceptionZZZ{
		String sReturnLine = sExpressionIn;
		String sReturnTag = null;
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
					
			//Bei CASCADED Tags alle Untertags holen.
			//TODOGOON: Das muesste bei cascaded aber eigentlich AllVector sein.
			//Vector<String>vecAll = this.parseAllVector(sLineWithExpression);
			Vector3ZZZ<String>vecReturn = this.parseFirstVector(sExpressionIn);
			if(vecReturn==null) break main;
			if(StringZZZ.isEmpty((String)vecReturn.get(1))) break main; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			
			sReturnTag = (String) vecReturn.get(1);
			this.setValue(sReturnTag);
			
			vecReturn = this.parseFirstVectorPost(vecReturn);
			if(vecReturn==null) break main;
			sReturnTag = (String) vecReturn.get(1); //nun ist der Tag ggfs. leer
			this.setValue(sReturnTag);
			
			//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
			sReturnLine = VectorUtilZZZ.implode(vecReturn);			
		}//end main:
		return sReturnLine;
	}
}// End class