package basic.zBasic.util.file;

import java.io.FileNotFoundException;
import java.io.IOException;

import basic.javagently.Stream;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.KernelLogZZZ;

public class FileTextWriterZZZ extends AbstractObjectZZZ{
	public static final String sFILE_NAME_DEFAULT= "NewTextfile_default.txt";
	private IStreamZZZ objStream = null;
	private String sFileName = null;
	
	public FileTextWriterZZZ() {		
	}
	public FileTextWriterZZZ(String sFileName) {
		this.setFileName(sFileName);
	}
	
	private boolean createStreamInternal(String sFileNameIn){
		boolean bReturn = false;
		try {
			String sFileName;
			if(StringZZZ.isEmpty(sFileNameIn)){
				sFileName = this.getFileName();
			}else{
				sFileName = sFileNameIn;
			}
			this.objStream = new StreamZZZ(sFileName,1); //1 = Write //ggfs. noch das Encoding übergeben in dieser ZZZ-Klasse
			bReturn = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return bReturn;
	}
	
	//##############################################################
	public synchronized boolean writeLine(String stemp){
		boolean bHasStream = true;
		if(this.objStream==null) bHasStream = createStreamInternal("");
		if(bHasStream){
			this.objStream.println(stemp);
		}
		return bHasStream;
	}


	public synchronized boolean write(String stemp){
		boolean bHasStream = true;
		if(this.objStream==null) bHasStream = createStreamInternal("");
		if(bHasStream){
			this.objStream.print(stemp);
		}
		return bHasStream;
	}
	
	//##### Getter / Setter ###################
	public String getFileName() {
		if(StringZZZ.isEmpty(this.sFileName)) {
			this.sFileName = FileTextWriterZZZ.sFILE_NAME_DEFAULT;
		}		
		return this.sFileName;
	}
	public void setFileName(String sFileName) {
		this.sFileName = sFileName;
	}
}
