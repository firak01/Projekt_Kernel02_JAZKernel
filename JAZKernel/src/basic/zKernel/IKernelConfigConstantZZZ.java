package basic.zKernel;

public interface IKernelConfigConstantZZZ {
	public enum MODULEPROPERTY{
		PATH, FILE ; //20220814: EIGENSCHAFTEN, DIE ZUR Definition der ini-Datei Einträge fuer Module genutzt werden können
	}
	
	//Zum Auslesen der Konfigurationseinträge in der INI-Datei
	//Merke: Dies wird in KernelConfigDefaultEntryZZZ, in der Enumeration ggfs. verwendet.
	public final static String sDIRECTORY_CONFIG_DEFAULT="c:\\fglkernel\\kernelconfig";
	public final static String sFILENAME_CONFIG_DEFAULT="ZKernelConfigKernel.ini";
	
	public final static String sMODULE_PREFIX="KernelConfig";
	public final static String sMODULE_DIRECTORY_PREFIX=sMODULE_PREFIX+MODULEPROPERTY.PATH.name();
	public final static String sMODULE_FILENAME_PREFIX=sMODULE_PREFIX+MODULEPROPERTY.FILE.name();
}
