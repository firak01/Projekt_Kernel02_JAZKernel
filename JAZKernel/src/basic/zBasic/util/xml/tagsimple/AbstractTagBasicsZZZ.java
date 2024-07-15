package basic.zBasic.util.xml.tagsimple;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithValueZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;

public abstract class AbstractTagBasicsZZZ extends AbstractObjectWithValueZZZ implements ITagBasicsZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;
	
	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
	
	public AbstractTagBasicsZZZ() throws ExceptionZZZ{
		super();
	}
	
			
	//######## Getter / Setter ##################
	@Override	
	public String getValue(){
		return this.sValue;
	}

	@Override
	public void setValue(String sValue){
		this.sValue = sValue;
	}
	
	
	
	@Override 
	public String getElementString() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sValue = this.getValue();
			if(sValue==null) {
				sReturn = this.getEmpty();
				break main;
			}
			
			sReturn = this.getTagPartStarting() + sValue + this.getTagPartClosing();
		}
		return sReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Details aus ITagBasicsZZZ +++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//#### Aus Interfacace
	//Merke: Der Name wird auf unterschiedliche Arten geholt. Z.B. aus dem TagTypeZZZ
	@Override
	public abstract String getName() throws ExceptionZZZ;
	
	@Override
	public String getTagPartStarting() throws ExceptionZZZ{
		return XmlUtilZZZ.computeTagPartStarting(this.getName());
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{				
		return XmlUtilZZZ.computeTagPartClosing(this.getName());
	}	
	
	@Override
	public String getTagPartEmpty() throws ExceptionZZZ{				
		return XmlUtilZZZ.computeTagPartEmpty(this.getName());
	}	
	
	@Override
	public String getEmpty() throws ExceptionZZZ{
		return this.getTagPartEmpty();
	}	
}
