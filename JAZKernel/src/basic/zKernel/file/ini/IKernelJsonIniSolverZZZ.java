package basic.zKernel.file.ini;

public interface IKernelJsonIniSolverZZZ {
	public enum FLAGZ{
		USEJSON,USEJSON_MAP,USEJSON_ARRAY
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
	public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue);
	
}
