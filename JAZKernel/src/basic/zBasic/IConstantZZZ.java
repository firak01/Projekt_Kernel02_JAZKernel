package basic.zBasic;

public interface IConstantZZZ {
	//the constants are the same as in lotusScript - Libary ZZZBasicKernelAssetHandler	
	public static final String sERROR_PARAMETER_MISSING = "Missing parameter: ";
	public static final int iERROR_PARAMETER_MISSING = 99;
	
	public static final String sERROR_PARAMETER_EMPTY = "Empty parameter: ";
	public static final int iERROR_PARAMETER_EMPTY = 98;
	
	public static final String sERROR_PARAMETER_VALUE = "Parameter has wrong value: ";
	public static final int iERROR_PARAMETER_VALUE = 97;
	
	public static final String sERROR_PROPERTY_MISSING = "Missing property: ";
	public static final int iERROR_PROPERTY_MISSING = 89;
	
	public static final String sERROR_PROPERTY_EMPTY = "Empty property: ";
	public static final int iERROR_PROPERTY_EMPTY = 88;	
	
	public static final String sERROR_PROPERTY_VALUE = "Property has wrong value: ";
	public static final int iERROR_PROPERTY_VALUE = 87;
	 
	public static final String sERROR_RUNTIME_TIMEOUT="Timeout: ";
	public static final int iERROR_RUNTIME_TIMEOUT = 59;
	
	public static  final String sERROR_RUNTIME = "Runtime error: ";
	public  static final int iERROR_RUNTIME = 58;	
	
	public static  final String sERROR_CONFIGURATION_MISSING = "Missing configuration: ";
	public static  final int iERROR_CONFIGURATION_MISSING = 79;
	
	public static  final String sERROR_CONFIGURATION_EMPTY = "Empty configuration: ";
	public static  final int iERROR_CONFIGURATION_EMPTY = 78;
	
	public static  final String sERROR_CONFIGURATION_VALUE = "Configuration has wrong value: ";
	public static  final int iERROR_CONFIGURATION_VALUE = 77;
	
	public static  final String sERROR_ZFRAME_METHOD ="A method is not overwritten, but should be: ";
	public static  final int iERROR_ZFRAME_METHOD = 69;
	
	public static  final String sERROR_ZFRAME_DESING = "A design element not found: ";
	public static  final int iERROR_ZFRAME_DESIGN = 68;
	
	
	public static  final String sERROR_NO="No Error";
	public static  final int iERROR_NO=0;
	
	
	
	public String toString(); //Nutzt intern eine Jakarta-Commons Klasse
}
