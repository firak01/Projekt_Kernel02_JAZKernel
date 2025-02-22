package basic.zBasic.xml.tagtype;

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
	
	private boolean AbstractTagTypeNew_(String sTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
			this.sTagName = sTagName;
			bReturn =true;
		}
		return bReturn;
	}
	
	//Kein setter. Der Name wird über eine Konstante in der TagType-Klasse definiert
	//Darum gibt es keine Setter-Methode.
	@Override
	public String getTagName() throws ExceptionZZZ {
		return this.sTagName;
	} 
					
	//#### Aus Interfacace
	//s. Analog zu z.B. KernelZFormulaIni_NullZZZ, dort aber als Static Methoden.
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface		
	@Override
	public String getTagPartOpening() throws ExceptionZZZ{
		String sTagName = this.getTagName();
		return XmlUtilZZZ.computeTagPartOpening(sTagName);
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{				
		String sTagName = this.getTagName();
		return XmlUtilZZZ.computeTagPartClosing(sTagName);
	}	
	
	@Override
	public String getTagPartEmpty() throws ExceptionZZZ{
		String sTagName = this.getTagName();
		return XmlUtilZZZ.computeTagEmpty(sTagName);
	}
}
