package basic.zBasic.util.file;

import java.io.File;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;

public abstract class AbstractFileTextZZZ extends AbstractObjectWithExceptionZZZ{
	private static final long serialVersionUID = -1464375530224033955L;
	protected IStreamZZZ objStream = null;
	protected String sFileName = null;
	protected File objFile = null;
	
	public AbstractFileTextZZZ() {		
	}
	public AbstractFileTextZZZ(String sFileName) throws ExceptionZZZ{
		this.setFileName(sFileName);
	}
	
	public AbstractFileTextZZZ(File objFile) throws ExceptionZZZ{
		this.setFileObject(objFile);
	}
	
	//##### Getter / Setter ###################
	public abstract String getFileNameDefault() throws ExceptionZZZ;
	
	public String getFileName() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sFileName)) {
			if(this.objFile!=null) {
				this.sFileName = objFile.getPath();
			}else {
				this.sFileName = this.getFileNameDefault();
			}
		}		
		return this.sFileName;
	}
	public void setFileName(String sFileName) {
		this.sFileName = sFileName;
	}
	
	public File getFileObject() throws ExceptionZZZ {
		if(this.objFile==null) {
			String sFileName = this.getFileName();
			if(StringZZZ.isEmpty(sFileName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Filename or File-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			File objFile = new File(sFileName);
			if(!FileEasyZZZ.exists(objFile)) {
				ExceptionZZZ ez = new ExceptionZZZ("File does not exist '" + sFileName + "'", iERROR_PROPERTY_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			this.objFile = objFile;
		}
		return this.objFile;
	}
	
	/** Null ist erlaubt.
	 *  Aber wenn eine Datei übergeben wird, dann sollte die auch vorhanden sein.
	 * @param objFile
	 * @throws ExceptionZZZ
	 */
	public void setFileObject(File objFile) throws ExceptionZZZ{
		if(objFile!=null) {
			if(!FileEasyZZZ.exists(objFile)) {
				ExceptionZZZ ez = new ExceptionZZZ("File does not exist '" + sFileName + "'", iERROR_PROPERTY_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}			
		}
		this.setFileName(null); //egal ob Datei oder NULL, wenn benötigt den Dateinamen also wieder neu aus der Datei holen ODER DEFAULT.
		this.objFile = objFile;
	}
}
