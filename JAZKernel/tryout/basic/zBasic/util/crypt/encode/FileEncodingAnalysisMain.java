package basic.zBasic.util.crypt.encode;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import basic.zBasic.util.datatype.string.FileEncodingUtil;

public class FileEncodingAnalysisMain {

	public static void main(String[] args) {
		try {
			String dateiname=null;
			String pfad=null;
			
			 if(dateiname == null) {
			      Frame f = new Frame();
			      FileDialog fd = new FileDialog(f);
			      fd.setMode(FileDialog.LOAD);
			      fd.show();
			      dateiname = fd.getFile();
			      pfad = fd.getDirectory();
			      f = null;
			      fd = null;
			    }
			
			File objFile = new File (pfad + dateiname);
			
			String sEncoding = FileEncodingUtil.getFileEncoding(objFile);
			System.out.println("Encoding: " + sEncoding);
			
			//Konvertiere die Datei nach ANSI		
			File objFileAnsi = new File("c:\\temp\\ANSI.txt");
			FileEncodingUtil.convertFileToANSI(objFile, objFileAnsi);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
