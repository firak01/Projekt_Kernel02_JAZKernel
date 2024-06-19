package basic.zBasic.util.xml.tagsimple;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

public abstract class AbstractTagSimpleZZZ extends AbstractTagBasicsZZZ implements ITagSimpleZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;
	protected String sName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.
	
	public AbstractTagSimpleZZZ() throws ExceptionZZZ{
		super();
	}
	
	public AbstractTagSimpleZZZ(String sName, String sValue) throws ExceptionZZZ{
		super();
		AbstractTagSimpleNew_(sName, sValue);
	}
		
	private void AbstractTagSimpleNew_(String sName, String sValue) throws ExceptionZZZ{
		main:{
			if(StringZZZ.isEmpty(sName)){
				ExceptionZZZ ez = new ExceptionZZZ("TagName", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sValue==null){
				ExceptionZZZ ez = new ExceptionZZZ("Element-Value", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					

			this.setName(sName);
			this.setValue(sValue);			
		}//End main
	}
		
	//######## Getter / Setter ##################
	//#### Aus Interfacace
	@Override
	public String getName(){
		return this.sName;
	}	
	
	@Override
	public void setName(String sTagName) {
		this.sName = sTagName;		
	}
	
	@Override
	public String toString() {
		return XmlUtilZZZ.computeTag(this.getName(), this.getValue());
	}
}
