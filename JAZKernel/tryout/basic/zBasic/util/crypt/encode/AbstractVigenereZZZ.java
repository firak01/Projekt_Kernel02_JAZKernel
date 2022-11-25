package basic.zBasic.util.crypt.encode;

import base.files.DateiUtil;

public abstract class AbstractVigenereZZZ {
	private String sFilePath=null;
	private String sSchluesselwort=null;
	
	private DateiUtil datei=null;
	
	public AbstractVigenereZZZ() {		
	}
	
	public AbstractVigenereZZZ(String sFilePath, String sSchluesselWort) {
		this.setFilePath(sFilePath);
		this.setSchluesselWort(sSchluesselWort);
	}
	
	
	
	//+++++++ Getter / Setter
	public void setFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}
	public String getFilePath() {
		return this.sFilePath;
	}
	
	public void setSchluesselWort(String sSchluesselwort) {
		this.sSchluesselwort = sSchluesselwort;
	}
	public String getSchluesselWort() {
		return this.sSchluesselwort;
	}
	
	public void setDateiOriginal(DateiUtil datei) {
		this.datei = datei;
	}
	public DateiUtil getDateiOriginal() {
		return this.datei;
	}
	
	
}
