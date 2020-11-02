package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.zip.FileDirectoryFilterZipZZZ;
import basic.zBasic.util.file.zip.FileDirectoryWithContentFilterZipZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zBasic.util.file.zip.FileDirectoryFilterInZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterEndingZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterMiddleZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPrefixZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterSuffixZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryWithContentFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileDirectoryFilterInJarZZZ extends ObjectZZZ implements IFileDirectoryPartFilterZipUserZZZ{
	protected IFileDirectoryFilterZipZZZ objPartFilterDirectory;	
	protected IFileDirectoryWithContentFilterZipZZZ objPartFilterDirectoryWithContent;
	protected String sDirectoryPath="";
		
	public AbstractFileDirectoryFilterInJarZZZ() throws ExceptionZZZ {
		this("","init");
	}		
	public AbstractFileDirectoryFilterInJarZZZ(String sDirectoryPath, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, saFlagControl);
	}
	public AbstractFileDirectoryFilterInJarZZZ(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, saFlagControlIn);
	} 
	public AbstractFileDirectoryFilterInJarZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, null);
	} 
	private void AbstractDirectoryFilterInJarNew_(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
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

		this.setDirectoryPath(sDirectoryPath);
		
		}//end main:		
	}
	
	public boolean accept(ZipEntry ze) {
		boolean bReturn=false;
		main:{
			if(ze==null) break main;				
			
			//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
			
			//Falls das Verzeichnis nicht passt	
			try {
				if(!StringZZZ.isEmpty(this.getDirectoryPath())){
					this.objPartFilterDirectory.setCriterion(this.getDirectoryPath());
					if(this.objPartFilterDirectory.accept(ze)==true) {
						bReturn = true;
						break main;
					}
						
					
					this.objPartFilterDirectoryWithContent.setCriterion(this.getDirectoryPath());
					if(this.objPartFilterDirectory.accept(ze)==true) {
						bReturn = true;
						break main;
					}
					
				}
				
				
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//END main:
		return bReturn;		
	}
	
	//##### GETTER / SETTER			
		protected void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ {
			String sDirectoryPath = JarEasyUtilZZZ.toJarDirectoryPath(sDirectoryPathIn);
			this.sDirectoryPath = sDirectoryPath;
		}
		protected String getDirectoryPath() throws ExceptionZZZ {
			if(StringZZZ.isEmpty(this.sDirectoryPath)) {
				this.setDirectoryPath("");
			}
			return this.sDirectoryPath;
		}
		
		@Override
		public void setDirectoryPartFilter(IFileDirectoryFilterZipZZZ objDirectoryFilterZip) {
			this.objPartFilterDirectory = objDirectoryFilterZip;
		}
		@Override
		public IFileDirectoryFilterZipZZZ getDirectoryPartFilter() throws ExceptionZZZ {
			if(this.objPartFilterDirectory==null) {
				this.objPartFilterDirectory = new FileDirectoryFilterZipZZZ(this.getDirectoryPath());
			}
			return this.objPartFilterDirectory;
		}
		
		
		@Override
		public void setDirectoryPartFilterWithContent(IFileDirectoryWithContentFilterZipZZZ objDirectoryFilterZip) {
			this.objPartFilterDirectoryWithContent = objDirectoryFilterZip;
		}

		@Override
		public IFileDirectoryWithContentFilterZipZZZ getDirectoryPartFilterWithConent() throws ExceptionZZZ {
			if(this.objPartFilterDirectoryWithContent==null) {
				this.objPartFilterDirectoryWithContent = new FileDirectoryWithContentFilterZipZZZ(this.getDirectoryPath());
			}
			return this.objPartFilterDirectoryWithContent;
		}
		
}//END class