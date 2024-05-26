package basic.zBasic.xml;

import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;

public abstract class AbstractTagTypeZZZ extends AbstractObjectZZZ implements ITagTypeZZZ{
	protected String sTagName="";
	
	public AbstractTagTypeZZZ() throws ExceptionZZZ{
		super();
	}
	
	public AbstractTagTypeZZZ(String sName) throws ExceptionZZZ{
		super();
		AbstractTagTypeNew_(sName);
	}
	
	private boolean AbstractTagTypeNew_(String sName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			this.setTagName(sName);
			bReturn =true;
		}
		return bReturn;
	}
	
	@Override
	public String getTagName() {
		return this.sTagName;
	}
	
	@Override
	public void setTagName(String sTagName) {
		this.sTagName = sTagName;
	}
	
	//#### Aus Interfacace
	//s. Analog zu z.B. KernelZFormulaIni_NullZZZ, dort aber als Static Methoden.
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface
		
	@Override
	public String getTagPartStarting() throws ExceptionZZZ{
		String sTagName = this.getTagName();
		return XmlUtilZZZ.computeTagPartStarting(sTagName);
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{				
		String sTagName = this.getTagName();
		return XmlUtilZZZ.computeTagPartClosing(sTagName);
	}	
	
	@Override
	public String getTagPartEmpty() throws ExceptionZZZ{
		String sTagName = this.getTagName();
		return XmlUtilZZZ.computeTagPartEmpty(sTagName);
	}
}
