package basic.zBasic;

import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public abstract class AbstractTagSimpleZZZ<T>  extends AbstractObjectWithValueZZZ<T> implements IValueInLineZZZ{
	private static final long serialVersionUID = -5785934791199206030L;
	
	public AbstractTagSimpleZZZ() throws ExceptionZZZ{
		super();
		AbstractTagNew_(null);
	}
	
	public AbstractTagSimpleZZZ(String sValue) throws ExceptionZZZ{
		super();
		AbstractTagNew_(sValue);
	}
	
	
			
	private boolean AbstractTagNew_(String sValue) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
		 this.setValue(sValue);
		 	
	 	}//end main:
		return bReturn;
	 }//end function AbstractTagNew_
	
	//### aus IValueInLineZZZ
	@Override
	public boolean isExpression(String sLine) throws ExceptionZZZ{
		return KernelConfigSectionEntryUtilZZZ.isExpression(sLine, this.getExpressionTagName());
	}
		
	//###### Getter / Setter
	/**
	 * @return
	 * @author Fritz Lindhauer, 06.07.2024, 10:50:01
	 */
	public abstract String getExpressionTagName();
	
	public String getExpressionTagStarting() throws ExceptionZZZ{
		return AbstractTagSimpleZZZ.computeExpressionTagStarting(this.getExpressionTagName());		
	}

	public String getExpressionTagClosing() throws ExceptionZZZ{
		return AbstractTagSimpleZZZ.computeExpressionTagClosing(this.getExpressionTagName());		
	}	
	public String getExpressionTagEmpty()throws ExceptionZZZ{
		return AbstractTagSimpleZZZ.computeExpressionTagEmpty(this.getExpressionTagName());		
	}
	
	public static String computeExpressionTagStarting(String sTagName) throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "<" + sTagName + ">";
	}
	
	public static String computeExpressionTagClosing(String sTagName)throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "</" + sTagName + ">"; 
	}
	
	public static String computeExpressionTagEmpty(String sTagName)throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagName.", iERROR_PARAMETER_MISSING, AbstractTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return "<" + sTagName + "/>";
	}
	

	//### Aus Interface IKernelZTagIniZZZ			
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.IKernelZTagIniZZZ#compute(java.lang.String)
	 */
//	@Override
//	public String compute(String sLineWithExpression) throws ExceptionZZZ{
//		String sReturn = sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//				
//			//Bei einfachen Tags den Ersten Vektor holen
//			Vector<String> vecAll = this.computeExpressionFirstVector(sLineWithExpression);
//			
//			//Bei einfachen Tags, den Wert zurückgeben
//			sReturn = (String) vecAll.get(1);
//			this.setValue(sReturn);
//			
//			//implode NUR bei CASCADED Tags
////			String sExpressionImploded = VectorZZZ.implode(vecAll);
////			sReturn = sExpressionImploded;
////			this.setValue(sReturn);
//		}//end main:
//		return sReturn;
//	}	
//	
//	@Override
//	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ{
//		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//											
//			Vector<String>vecAll = this.computeExpressionAllVector(sLineWithExpression);
//			
//			//Das ist bei einfachen Tag Werten so
//			String sReturn = (String) vecAll.get(1);
//			this.setValue(sReturn); //Merke: Internes Entry-Objekt nutzen. Darin wurden in den vorherigen Methoden auch Zwischenergebnisse gespeichert.
//			
//			//Bei verschachtelten (CASCADED) Tag Werten so 
////			String sExpressionImploded = VectorZZZ.implode(vecAll);
////			this.setValue(sExpressionImploded);
//			
//			
//			objReturn = this.getEntry();			
//		}//end main:
//		return objReturn;
//	}	
//	
//	@Override
//	public String[] computeAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ{
//		String[] saReturn = null; //new String[];//sLineWithExpression;
//		main:{
//			if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//			
//			String sDelimiter;
//			if(StringZZZ.isEmpty(sDelimiterIn)) {
//				sDelimiter = IniFile.sINI_MULTIVALUE_SEPARATOR; 
//			}else {
//				sDelimiter = sDelimiterIn;
//			}
//				   
//			String sExpressionTotal = this.compute(sLineWithExpression); //Hole erst einmal den Kernel-Tag-Wert.
//			if(!StringZZZ.isEmpty(sExpressionTotal)) {
//				String[] saExpression = StringZZZ.explode(sExpressionTotal, sDelimiter); //Dann löse Ihn als Mehrfachwert auf.
//				
//				String sValue = null;
//				ArrayListExtendedZZZ<String> listasValue = new ArrayListExtendedZZZ<String>();
//				for(String sExpression : saExpression) {
//					
//					//Nur für den etwas komplizierteren Fall einer Verschachtelung ...
//					if(this.isExpression(sExpression)){
//						sValue = this.compute(sExpression);
//					}else {
//						sValue = sExpression;
//					}
//					listasValue.add(sValue);
//				}
//								
//				saReturn = listasValue.toStringArray();				
//			}
//		}//end main:
//		return saReturn;
//	}
//	
//	@Override
//	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ{
//	String sReturn = sLineWithExpression;
//	main:{
//		if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
//		
//		Vector<String> vecAll = this.computeExpressionAllVector(sLineWithExpression);
//		
//		//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
//		sReturn = VectorZZZ.implode(vecAll);
//		this.setValue(sReturn);
//		
//	}//end main:
//	return sReturn;
//	}	
	
	
}//End class