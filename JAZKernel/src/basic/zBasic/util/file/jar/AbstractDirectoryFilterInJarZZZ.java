package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.zip.DirectoryFilterZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterEndingZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterMiddleZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPrefixZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterSuffixZipZZZ;
import basic.zBasic.util.file.zip.IDirectoryFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IDirectoryFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractDirectoryFilterInJarZZZ extends ObjectZZZ implements IDirectoryFilterZipUserZZZ{
	protected IDirectoryFilterZipZZZ objFilterDirectory;			
	protected String sDirectoryPath="";
		
	public AbstractDirectoryFilterInJarZZZ() throws ExceptionZZZ {
		this("","init");
	}		
	public AbstractDirectoryFilterInJarZZZ(String sDirectoryPath, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, saFlagControl);
	}
	public AbstractDirectoryFilterInJarZZZ(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, saFlagControlIn);
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


		//Die konkreten Ausprägungen können erst in der accept Methode gefüllt werden, mit den konkreten Werten.
		//Z.B. für Middle-Wert steht in der FilenamePartFilter - accept-Methode:
		//		this.objFilterMiddle.setCriterion(this.getMiddle());
		//      if(this.objFilterMiddle.accept(ze)==false) break main;
		objFilterDirectory = this.getDirectoryFilter();
		
		}//end main:		
	}
	
	public boolean accept(ZipEntry ze) {
		boolean bReturn=false;
		main:{
			if(ze==null) break main;				
			
			//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
			
			//Falls das Verzeichnis nicht passt	
			if(!StringZZZ.isEmpty(this.getDirectoryPath())){
				this.objFilterDirectory.setCriterion(this.getDirectoryPath());
				if(this.objFilterDirectory.accept(ze)==false) break main;
			}
														
			bReturn = true;
		}//END main:
		return bReturn;		
	}
	
	//##### GETTER / SETTER			
		protected void setDirectoryPath(String sDirectoryPath) {
			this.sDirectoryPath = sDirectoryPath;
		}
		protected String getDirectoryPath() {
			if(StringZZZ.isEmpty(this.sDirectoryPath)) {
				this.setDirectoryPath("");
			}
			return this.sDirectoryPath;
		}
		
		public void setDirectoryFilter(IDirectoryFilterZipZZZ objFilterDirectory) {
			this.objFilterDirectory = objFilterDirectory;
		}
		public IDirectoryFilterZipZZZ getDirectoryFilter() {
			if(this.objFilterDirectory==null) {
				this.objFilterDirectory = new DirectoryFilterZipZZZ();
			}
			return this.objFilterDirectory;
		}
		
		

}//END class