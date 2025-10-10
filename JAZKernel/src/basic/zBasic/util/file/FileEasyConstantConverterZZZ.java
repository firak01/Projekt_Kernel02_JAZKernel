package basic.zBasic.util.file;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;

public class FileEasyConstantConverterZZZ implements IFileEasyConstantsZZZ {
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn) throws ExceptionZZZ {
		return FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR,false);
	}
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn, char cDirectorySeparator) throws ExceptionZZZ {
		return FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn,cDirectorySeparator,false);
	}	
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn,boolean bReturnAsRelativePath) throws ExceptionZZZ {
		return FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR,bReturnAsRelativePath);
	}
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn, char cDirectorySeparator, boolean bReturnAsRelativePath) throws ExceptionZZZ{
		IFileEasyPathObjectZZZ objReturn = new FileEasyPathObjectZZZ(sFilePathIn);
		
		//ggfs. ueberfluessige Slashes, etc. entfernen
		String sFilePath = StringZZZ.stripFileSeparators(sFilePathIn);		
		main:{			
			String sDirectorySeparator = StringZZZ.char2String(cDirectorySeparator);
			
			String sReturnFilePath="";
			String sReturnRoot="";
			String sReturnFilePathTotal="";
			
			//An empty string is allowed as ROOT-Directory. A null String is the Project/Execution Directory		
			if(sFilePath==null || XmlUtilZZZ.computeTagNull().equals(sFilePath)){
				String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
				sReturnRoot = "";
				sReturnFilePath = sWorkspace;				
				sReturnFilePathTotal = sWorkspace;
			}else if(sFilePath.equals("") || XmlUtilZZZ.computeTagEmpty().equals(sFilePath)){					
				sReturnRoot = FileEasyZZZ.getFileRootPath();
				sReturnFilePath="";
				sReturnFilePathTotal=sReturnRoot;	
			}else if(sFilePath.equals(FileEasyConstantConverterZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER) || sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER) || sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)){
				sReturnRoot = sFilePath;
				sReturnFilePath = "";
				sReturnFilePathTotal=sReturnRoot;
			}else {				
				if(FileEasyZZZ.isPathRelative(sFilePath) & !StringZZZ.isEmpty(sFilePath)) {			
					boolean btemp = StringZZZ.startsWithIgnoreCase((sFilePath + sDirectorySeparator),XmlUtilZZZ.computeTagNull() + sDirectorySeparator);
					if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && btemp ) {
						sReturnRoot = FileEasyZZZ.getDirectoryOfExecutionAsString();
						sReturnFilePath = StringZZZ.rightback(sFilePath, XmlUtilZZZ.computeTagNull()+sDirectorySeparator,false);
						if(bReturnAsRelativePath) {
							sReturnFilePathTotal = sReturnFilePath;
						}else {
							if(!StringZZZ.isEmpty(sReturnRoot)) {
								sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;
							}else {
								sReturnFilePathTotal=sReturnFilePath;
							}
						}
				}else 
					if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER + sDirectorySeparator)) {
						sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
						sReturnFilePathTotal = sFilePath;
						sReturnFilePath = StringZZZ.rightback(sReturnFilePathTotal, sReturnRoot+sDirectorySeparator);																	
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER + sDirectorySeparator)) {
						sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
						sReturnFilePathTotal = sFilePath;
						sReturnFilePath = StringZZZ.rightback(sReturnFilePathTotal, sReturnRoot+sDirectorySeparator);						
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER + sDirectorySeparator)) {
						if(FileEasyZZZ.isOnServer()) {
							sReturnRoot="";
							sReturnFilePath = sFilePath;							
							sReturnFilePathTotal = sFilePath;
						}else if(FileEasyZZZ.isInJarStatic()) {
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;							
							sReturnFilePath = StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
							if(StringZZZ.isEmpty(sReturnFilePath)) {
								sReturnFilePath=sFilePath;
							}
							sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
						}else {
							//Also wie in FileEasyZZZ.isInIDE();
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
							sReturnFilePath = StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
							if(StringZZZ.isEmpty(sReturnFilePath)) {
								sReturnFilePath=sFilePath;
							}
							
							if(StringZZZ.isEmpty(sReturnRoot)) {
								sReturnFilePathTotal = sReturnFilePath;
							}else {
								sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;								
								if(bReturnAsRelativePath) {								
									if(FileEasyZZZ.isPathAbsolute(sReturnFilePathTotal)) {
										sReturnFilePathTotal = sReturnFilePath;
									}
								}								
							}	
						}	
					}else {
						sReturnRoot = FileEasyZZZ.getFileRootPath();
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"sReturnRoot='"+sReturnRoot+"'");
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"sFilePath='"+sFilePath+"'");
						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePath=StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
						}else {
							sReturnFilePath=sFilePath;
						}
						
						if(StringZZZ.isEmpty(sReturnFilePath)) {
							sReturnFilePath=sFilePath;
						}
						if(StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;
							if(bReturnAsRelativePath) {								
								if(FileEasyZZZ.isPathAbsolute(sReturnFilePathTotal)) {
									sReturnFilePathTotal = sReturnFilePath;
								}
							}	
						}										
					}
				}else {
					//absolute Pfade
					if(bReturnAsRelativePath) {
						if(FileEasyZZZ.isInIDE()) {
							//den Pfad zum Workspace suchen
							String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
							if(StringZZZ.startsWith(sFilePathIn, sWorkspace)) {
								//absoluter Pfad in der IDE, diesen zerlegen
								String sReturnFilePathTemp = StringZZZ.rightback(sFilePathIn, sWorkspace + sDirectorySeparator);
								if(StringZZZ.startsWith(sReturnFilePathTemp, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)) {
									sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
									sReturnFilePath = StringZZZ.rightback(sReturnFilePathTemp, sReturnRoot + sDirectorySeparator);
									sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
									if(!bReturnAsRelativePath) {									
										if(!StringZZZ.isEmpty(sWorkspace)) {
											sReturnFilePathTotal=sWorkspace+sDirectorySeparator+sReturnFilePathTotal;
										}
									}
								}else if(StringZZZ.startsWith(sReturnFilePathTemp, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER)) {
									sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
									sReturnFilePath = StringZZZ.rightback(sReturnFilePathTemp, sReturnRoot + sDirectorySeparator);
									sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
									if(!bReturnAsRelativePath) {
										if(!StringZZZ.isEmpty(sWorkspace)) {
											sReturnFilePathTotal=sWorkspace+sDirectorySeparator+sReturnFilePathTotal;
										}
									}
								}else if(StringZZZ.startsWith(sReturnFilePathTemp, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER)) {
									sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
									sReturnFilePath = StringZZZ.rightback(sReturnFilePathTemp, sReturnRoot + sDirectorySeparator);
									sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
									if(!bReturnAsRelativePath) {
										if(!StringZZZ.isEmpty(sWorkspace)) {
											sReturnFilePathTotal=sWorkspace+sDirectorySeparator+sReturnFilePathTotal;
										}
									}
								}else {
									sReturnRoot = "";
									sReturnFilePath = sFilePathIn;
									sReturnFilePathTotal=sFilePathIn;
								}							
													
							}else {
								//absoluter Pfad, irgendwo auf der Festplatte
								sReturnRoot = "";
								sReturnFilePath=sFilePath;
								sReturnFilePathTotal=sFilePath;
							}
						}else {
							//absoluter Pfad, irgendwo auf der Festplatte
							sReturnRoot = "";
							sReturnFilePath=sFilePath;
							sReturnFilePathTotal=sFilePath;
						}
					}else {
						//absoluter Pfad als Input und absoluten Pfad zurückgeben
						sReturnRoot = "";
						sReturnFilePath=sFilePath;
						sReturnFilePathTotal=sFilePath;						
					}
				}
			}
			objReturn.setFilePathTotal(sReturnFilePathTotal);
			objReturn.setFilePath(sReturnFilePath);
			objReturn.setRoot(sReturnRoot);
		}
		return objReturn;
	}
}
