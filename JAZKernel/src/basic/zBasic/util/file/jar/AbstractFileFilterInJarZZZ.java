package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.zip.FileFilterEndingZipZZZ;
import basic.zBasic.util.file.zip.FileFilterMiddleZipZZZ;
import basic.zBasic.util.file.zip.FileFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FileFilterPrefixZipZZZ;
import basic.zBasic.util.file.zip.FileFilterSuffixZipZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileFilterInJarZZZ extends ObjectZZZ implements ZipEntryFilter{
	protected FileFilterPathZipZZZ objFilterPath;
	protected FileFilterPrefixZipZZZ objFilterPrefix;
	protected FileFilterMiddleZipZZZ objFilterMiddle;
	protected FileFilterSuffixZipZZZ objFilterSuffix;	
	protected FileFilterEndingZipZZZ objFilterEnding;
	
	protected String sOvpnContext="";
	
	protected String sDirectoryPath="";
	protected String sPrefix="";
	protected String sMiddle="";
	protected String sSuffix="";
	protected String sEnding="";
	
	//wg. des Interfaces IFileExpansionUserZZZ
	protected IFileExpansionZZZ objExpansion = null;
	
	
	public AbstractFileFilterInJarZZZ() throws ExceptionZZZ {
		this("");
	}		
	public AbstractFileFilterInJarZZZ(String sOvpnContextServerOrClient) throws ExceptionZZZ {
		super();
		AbstractOVPNFileFilterInJarNew_(sOvpnContextServerOrClient, null);
	} 
	public AbstractFileFilterInJarZZZ(String sOvpnContextServerOrClient, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractOVPNFileFilterInJarNew_(sOvpnContextServerOrClient, saFlagControl);
	}
	public AbstractFileFilterInJarZZZ(String sOvpnContextServerOrClient, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractOVPNFileFilterInJarNew_(sOvpnContextServerOrClient, saFlagControlIn);
	} 
	private void AbstractOVPNFileFilterInJarNew_(String sOvpnContextServerOrClient, String[] saFlagControlIn) throws ExceptionZZZ {
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
		
		
		this.setOvpnContext(sOvpnContextServerOrClient);
		
//Diese Angaben gelten eben nicht für alle FileFilter, darum nicht in dieser abstrakten Elternklasse verwenden.
//		this.setPrefix(ConfigFileTemplateOvpnOVPN.sFILE_TEMPLATE_PREFIX);
//		this.setMiddle(this.getOvpnContext());

//Auch die konkreten Ausprägungen können erst in der konkreten Kindklasse gefüllt werden.
		objFilterPath = new FileFilterPathZipZZZ();
		objFilterPrefix = new FileFilterPrefixZipZZZ();
		objFilterMiddle = new FileFilterMiddleZipZZZ();
		objFilterSuffix = new FileFilterSuffixZipZZZ();
		objFilterEnding = new FileFilterEndingZipZZZ();
		
		}//end main:		
	}
	
	public boolean accept(ZipEntry ze) {
		boolean bReturn=false;
		main:{
			if(ze==null) break main;				
			
			//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
			
			//Falls das Verzeichnis nicht passt
			this.objFilterPath.setCriterion(this.getDirectoryPath());
			
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
		public void setOvpnContext(String sContext) {
			this.sOvpnContext=sContext;
		}
		public String getOvpnContext() {
			return this.sOvpnContext;
		}
	
		protected void setDirectoryPath(String sDirectoryPath) {
			this.sDirectoryPath = sDirectoryPath;
		}
		protected String getDirectoryPath() {
			if(StringZZZ.isEmpty(this.sDirectoryPath)) {
				this.setDirectoryPath("");
			}
			return this.sDirectoryPath;
		}
		
		protected void setPrefix(String sPrefix) {
			this.sPrefix = sPrefix;
		}
		protected String getPrefix() {
			if(StringZZZ.isEmpty(this.sPrefix)) {
				this.setPrefix("");
			}
			return this.sPrefix;
		}
		
		protected void setMiddle(String sMiddle) {
			this.sMiddle = sMiddle;
		}
		protected String getMiddle() {
			if(StringZZZ.isEmpty(this.sMiddle)) {
				this.setMiddle("");
			}
			return this.sMiddle;
		}

		
		protected void setSuffix(String sSuffix) {
			this.sSuffix = sSuffix;
		}
		protected String getSuffix() {
			if(StringZZZ.isEmpty(this.sSuffix)) {
				this.setSuffix("");
			}
			return this.sSuffix;
		}
		
		protected void setEnding(String sEnding){
			this.sEnding = sEnding;
		}
		protected String getEnding() {
			if(StringZZZ.isEmpty(this.sEnding)) {
				this.setEnding("");
			}
			return this.sEnding;
		}				
}//END class