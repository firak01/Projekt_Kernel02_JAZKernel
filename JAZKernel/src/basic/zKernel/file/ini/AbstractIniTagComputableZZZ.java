package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithFormulaZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithFormulaZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

public abstract class AbstractIniTagComputableZZZ<T> extends AbstractObjectWithFormulaZZZ<T> implements IIniTagSimpleZZZ {
	private static final long serialVersionUID = 4049221887081114236L;
	
	//Fuer Interface IKernelConfigSectionEntryUserZZZ
	protected IKernelConfigSectionEntryZZZ objEntry = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.
	
	//Fuer Interface ITagBasicZZZ
	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sTagName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
		
	
	public AbstractIniTagComputableZZZ() throws ExceptionZZZ{
		super();
		AbstractObjectWithFormulaNew_();
	}
	
	public AbstractIniTagComputableZZZ(String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);
		AbstractObjectWithFormulaNew_();
	}
	
	public AbstractIniTagComputableZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		AbstractObjectWithFormulaNew_();
	}
	
	private boolean AbstractObjectWithFormulaNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{			 
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
				
				
		 	}//end main:
			return bReturn;
		 }//end function AbstractObjectWithFormulaNew_

	
	//### aus IKernelConfigSectionEntryUserZZZ 	
	@Override
	public IKernelConfigSectionEntryZZZ getEntry() {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ();			
		}
		return this.objEntry;
	}
	
	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	
	//### aus IValueSolvedUserZZZ
	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() {
		return this.getEntry().getValueVector();
	}

	@Override
	public String getValue() {
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() {
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() {
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) {
		this.getEntry().setValue(sRaw);
	}
	//##############################
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Details aus ITagBasicsZZZ +++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//#### Aus Interfacace ITagBasicZZZ
	//Merke: Der Name wird auf unterschiedliche Arten geholt. Z.B. aus dem TagTypeZZZ, darum diese Methode dann ueberschreiben.
	@Override
	public String getName() throws ExceptionZZZ{
		if(this.sTagName==null) {
			return this.getNameDefault();
		}else {
			return this.sTagName;
		}
	}	
	
	//Merke: Der Default-Tagname wird in einer Konstanten in der konkreten Klasse verwaltet.
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface	
	@Override
	public abstract String getNameDefault() throws ExceptionZZZ; 
	
	@Override
	public void setName(String sTagName) throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		this.sTagName = sTagName;		
	}
			
	@Override
	public String getTagStarting() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartStarting(sTagName);
	}
	
	@Override
	public String getTagClosing() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartClosing(sTagName);
	}	
	
	@Override
	public String getTagEmpty() throws ExceptionZZZ{
		String sTagName = this.getName();
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return XmlUtilZZZ.computeTagPartEmpty(sTagName);
	}	
	
	@Override
	public String getEmpty() throws ExceptionZZZ{
		return this.getTagEmpty();
	}	
	
	//#########################
	TODOGOON 20240717; //Hier die Methoden hin verlagern.
	@Override
	public Vector<String> computeAsExpressionFirstVector(String sExpression) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<String> computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getElementString() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String compute() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String computeAsExpression() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExpression(String sExpression) throws ExceptionZZZ {
		return AbstractIniTagComputableZZZ.isExpressionStatic(sExpression);
	}
	
	public static boolean isExpressionStatic(String sLine){
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, XmlUtilZZZ.computeTagPartStarting(ZTagFormulaIni_NullZZZ.sTAG_NAME), false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, XmlUtilZZZ.computeTagPartClosing(ZTagFormulaIni_NullZZZ.sTAG_NAME), false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
}
