package basic.zBasic.util.file;

import java.io.FileNotFoundException;
import java.io.IOException;

import basic.javagently.Stream;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelLogZZZ;

public class FileTextWriterZZZ extends ObjectZZZ{
	public static final String sFILE_NAME_DEFAULT= "NewTextfile_default.txt";
	private Stream objStream = null;
	
	private boolean createStreamInternal(String sFileNameIn){
		boolean bReturn = false;
		try {
			String sFileName;
			if(StringZZZ.isEmpty(sFileNameIn)){
				sFileName = FileTextWriterZZZ.sFILE_NAME_DEFAULT;
			}else{
				sFileName = sFileNameIn;
			}
			this.objStream = new Stream(sFileName,1); //1 = Write
			bReturn = true;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} 
		return bReturn;
	}
	
	//##############################################################
	public synchronized boolean WriteLine(String stemp){
		boolean bHasStream = true;
		if(this.objStream==null) bHasStream = createStreamInternal("");
		if(bHasStream){
			this.objStream.println(stemp);
		}
		return bHasStream;
		}


}
