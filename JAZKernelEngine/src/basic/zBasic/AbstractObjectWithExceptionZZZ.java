package basic.zBasic;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zKernel.AbstractKernelLogZZZ;

public abstract class AbstractObjectWithExceptionZZZ <T> extends AbstractObjectZZZ<T> implements IObjectWithExceptionZZZ{
	private static final long serialVersionUID = 1L;	
	protected volatile ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	//fuer IOutputDebugNormedWithKeyZZZ
	//protected volatile String sDebugKeyDelimiterUsed = null; 
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithExceptionZZZ() {	
		super();	
	}
	
	//### aus IObjectZZZ
	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	} 

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}	
}
