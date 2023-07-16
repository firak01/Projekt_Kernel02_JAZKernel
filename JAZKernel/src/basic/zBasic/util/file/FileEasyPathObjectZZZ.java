package basic.zBasic.util.file;

/** Objekt, in dem Pfadinformationen festgehalten werden,
 *  die durch den FileEasyConstantConverterZZZ erzeugt werden.
 *  
 *  Methoden wie FileEasyZZZ.join.... können dann die Bestandteile weiternutzen.
 * 
 * @author Fritz Lindhauer, 13.07.2023, 08:32:15
 * 
 */
public class FileEasyPathObjectZZZ implements IFileEasyPathObjectZZZ{
	private String sRoot=null;
	private String sFilePath=null;
	private String sFilePathTotal=null;
	
	public FileEasyPathObjectZZZ() {		
	}
	
	public FileEasyPathObjectZZZ(String sFilePath) {
		this.setFilePathTotal(sFilePath);
	}
	
	@Override
	public String getFilePath() {
		return this.sFilePath;
	}

	@Override
	public void setFilePath(String sFilepath) {
		this.sFilePath = sFilepath;
	}

	@Override
	public String getRoot() {
		return this.sRoot;
	}

	@Override
	public void setRoot(String sRoot) {
		this.sRoot = sRoot;
	}

	@Override
	public String getFilePathTotal() {
		return this.sFilePathTotal;
	}

	@Override
	public void setFilePathTotal(String sFilePathTotal) {
		this.sFilePathTotal = sFilePathTotal;
	}

}
