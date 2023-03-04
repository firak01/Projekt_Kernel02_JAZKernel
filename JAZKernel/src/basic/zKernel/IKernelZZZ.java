package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.cache.IKernelCacheUserZZZ;
import basic.zKernel.flag.IFlagUserZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelZZZ extends IKernelConfigSectionEntryUserZZZ,IKernelCacheUserZZZ,IFlagUserZZZ{
	//FLAGZ, die dann zum "Rechnen in der Konfigurations Ini Datei" gesetzt sein müssen.
	public enum FLAGZ{
		USEFORMULA, USEFORMULA_MATH;
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	public boolean[] setFlag(IKernelZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue);	
	
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ;
	
	public LogZZZ getLogObject();
	
	public String getApplicationKey() throws ExceptionZZZ;
	public String getSystemKey() throws ExceptionZZZ;	
	public String getSystemNumber() throws ExceptionZZZ;
			
	public boolean isOnServer() throws ExceptionZZZ;
	public boolean isInJar() throws ExceptionZZZ;		
}
