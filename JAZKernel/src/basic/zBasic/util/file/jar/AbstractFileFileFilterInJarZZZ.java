package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterEndingZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterMiddleZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPrefixZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterSuffixZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileFileFilterInJarZZZ extends ObjectZZZ implements IFileFilePartFilterZipUserZZZ, ZipEntryFilter,IFileExpansionUserZZZ{
	protected FilenamePartFilterPathZipZZZ objFilterPath;
	protected FilenamePartFilterPrefixZipZZZ objFilterPrefix;
	protected FilenamePartFilterMiddleZipZZZ objFilterMiddle;
	protected FilenamePartFilterSuffixZipZZZ objFilterSuffix;	
	protected FilenamePartFilterEndingZipZZZ objFilterEnding;
		
	//wg. des Interfaces IFileExpansionUserZZZ
	protected IFileExpansionZZZ objExpansion = null;
	
	
	public AbstractFileFileFilterInJarZZZ() throws ExceptionZZZ {
		this("");
	}		
	public AbstractFileFileFilterInJarZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();
		AbstractOVPNFileFilterInJarNew_(sDirectoryPath, null);
	} 
	public AbstractFileFileFilterInJarZZZ(String sDirectoryPath, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractOVPNFileFilterInJarNew_(sDirectoryPath, saFlagControl);
	}
	public AbstractFileFileFilterInJarZZZ(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractOVPNFileFilterInJarNew_(sDirectoryPath, saFlagControlIn);
	} 
	private void AbstractOVPNFileFilterInJarNew_(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
		String stemp; boolean btemp;
		main:{
		//setzen der übergebenen Flags	
		if(saFlagControlIn != null){
			for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				stemp = saFlagControlIn[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ( sERROR_FLAG_UNAVAILABLE + stemp, iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					   //doesn�t work. Only works when > JDK 1.4
					   //Exception e = new Exception();
					   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					   throw ez;		 
				}
			}
			}

		//+++ Falls das Debug-Flag gesetzt ist, muss nun eine Session �ber das Factory-Objekt erzeugt werden. 
		// Damit kann auf andere Datenbanken zugegriffen werden (z.B. im Eclipse Debugger)
		// Besser jedoch ist es beim Debuggen mit einem anderen Tool eine Notes-ID zu verwenden, die ein leeres Passwort hat.
		btemp = this.getFlag("init");
		if(btemp==true) break main;
		
		//Die konkreten Ausprägungen können erst in der accept Methode gefüllt werden, mit den konkreten Werten.
		//Z.B. für Middle-Wert steht in der accept-Methode:
		//		this.objFilterMiddle.setCriterion(this.getMiddle());
		//      if(this.objFilterMiddle.accept(ze)==false) break main;
		objFilterPath = new FilenamePartFilterPathZipZZZ(sDirectoryPath);
		objFilterPrefix = new FilenamePartFilterPrefixZipZZZ();
		objFilterMiddle = new FilenamePartFilterMiddleZipZZZ();
		objFilterSuffix = new FilenamePartFilterSuffixZipZZZ();
		objFilterEnding = new FilenamePartFilterEndingZipZZZ();
		
		}//end main:		
	}
	
	public boolean accept(ZipEntry ze) {
		boolean bReturn=false;
		main:{
			if(ze==null) break main;				
			
			//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
			
			//Falls das Verzeichnis nicht passt	
			if(!StringZZZ.isEmpty(this.getDirectoryPath())){
				this.objFilterPath.setCriterion(this.getDirectoryPath());
				if(this.objFilterPath.accept(ze)==false) break main;
			}
			
			//Falls der OvpnContext nicht passt
			this.objFilterMiddle.setCriterion(this.getMiddle());
			if(this.objFilterMiddle.accept(ze)==false) break main;
	
			//Template-Dateinamen fangen eben mit einem bestimmten String an.
			this.objFilterPrefix.setCriterion(this.getPrefix());
			if(this.objFilterPrefix.accept(ze)==false) break main;
								
			//Falls die Endung nicht passt
			this.objFilterEnding.setCriterion(this.getEnding());
			if(this.objFilterEnding.accept(ze)==false) break main;
					
			//Falls das Suffix nicht passt
			this.objFilterSuffix.setCriterion(this.getSuffix());
			if(this.objFilterSuffix.accept(ze)==false) break main;
												
			bReturn = true;
		}//END main:
		return bReturn;		
	}
	
	//##### GETTER / SETTER
	public void setDirectoryPartFilter(FilenamePartFilterPathZipZZZ objDirectoryFilterZip) {
		this.objFilterPath = objDirectoryFilterZip;
	}
	public FilenamePartFilterPathZipZZZ getDirectoryPartFilter() {
		return this.objFilterPath;
	}
	
	public void setPrefixPartFilter(FilenamePartFilterPrefixZipZZZ objPrefixFilterZip) {
		this.objFilterPrefix = objPrefixFilterZip;
	}
	public FilenamePartFilterPrefixZipZZZ getPrefixPartFilter() {
		return this.objFilterPrefix;
	}
		
	public void setMiddlePartFilter(FilenamePartFilterMiddleZipZZZ objMiddleFilterZip) {
		this.objFilterMiddle = objMiddleFilterZip;
	}
	public FilenamePartFilterMiddleZipZZZ getMiddlePartFilter() {
		return this.objFilterMiddle;
	}
	
	
	public void setSuffixPartFilter(FilenamePartFilterSuffixZipZZZ objSuffixFilterZip) {
		this.objFilterSuffix = objSuffixFilterZip;
	}
	public FilenamePartFilterSuffixZipZZZ getSuffixPartFilter() {
		return this.objFilterSuffix;
	}
	
	public void setEndingPartFilter(FilenamePartFilterEndingZipZZZ objEndingFilterZip) {
		this.objFilterEnding = objEndingFilterZip;
	}
	public FilenamePartFilterEndingZipZZZ getEndingPartFilter() {
		return this.objFilterEnding;
	}
	
	
		protected void setDirectoryPath(String sDirectoryPath) {
			this.getDirectoryPartFilter().setDirectoryPath(sDirectoryPath);			
		}
		protected String getDirectoryPath() {
			return this.getDirectoryPartFilter().getDirectoryPath();
		}
		
		protected void setPrefix(String sPrefix) {
			this.getPrefixPartFilter().setPrefix(sPrefix);
		}
		protected String getPrefix() {
			return this.getPrefixPartFilter().getPrefix();
		}
		
		protected void setMiddle(String sMiddle) {
			this.getMiddlePartFilter().setMiddle(sMiddle);
		}
		protected String getMiddle() {
			return this.getMiddlePartFilter().getMiddle();
		}

		
		protected void setSuffix(String sSuffix) {
			this.getSuffixPartFilter().setSuffix(sSuffix);
		}
		protected String getSuffix() {
			return this.getSuffixPartFilter().getSuffix();
		}
		
		protected void setEnding(String sEnding){
			this.getEndingPartFilter().setEnding(sEnding);
		}
		protected String getEnding() {
			return this.getEndingPartFilter().getEnding();
		}				
		
		public IFileExpansionZZZ getFileExpansionObject() {
			return this.objExpansion;
		}
		public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) {
			this.objExpansion = objFileExpansion;
		}
}//END class