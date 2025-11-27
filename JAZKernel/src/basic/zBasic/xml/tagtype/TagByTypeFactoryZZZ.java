package basic.zBasic.xml.tagtype;

import java.util.EnumSet;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.reflection.position.TagTypeFileNameZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeClassNameZZZ;
import basic.zBasic.reflection.position.TagTypeDateZZZ;
import basic.zBasic.reflection.position.TagTypeThreadIdZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class TagByTypeFactoryZZZ extends AbstractObjectWithExceptionZZZ{
	private static final long serialVersionUID = -8672998939542120263L;



	//################### ENUM ALLER "EINFACHEN" KERNEL-TAG-TYPEN ##############
	//ALIAS("Uniquename","Statusmeldung","Beschreibung, wird nicht genutzt....",)
	public enum TAGTYPE implements IEnumSetMappedTagTypeZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		DATE("date",TagTypeDateZZZ.sTAGNAME, "Name des Datums (z.B. in einer Logzeile)"),
		THREADID("threadid",TagTypeThreadIdZZZ.sTAGNAME, "Thread als ID-Nummer (z.B. in einer Logzeile)"),
		CLASSNAME("classname",TagTypeClassNameZZZ.sTAGNAME, "Name der Klasse im Quellcode"),
		LINENUMBER("linenumber",TagTypeLineNumberZZZ.sTAGNAME, "Zeilennummer im Quellcode"),
		FILENAME("filename",TagTypeFileNameZZZ.sTAGNAME,"Name der (Java) Datei"),
		METHOD("method",TagTypeMethodZZZ.sTAGNAME,"Name der aufgerufenen Methode"),
		POSITION_IN_FILE("position_in_file",TagTypeFilePositionZZZ.sTAGNAME,"Postion, mit Angabe von Dateinamne und Zeilennummer - Format ist so, das dies in Eclipse Console ein clickbarer Link ist."),
		POSITIONCURRENT("positioncurrent",TagTypePositionCurrentZZZ.sTAGNAME,"Umgebender Tag fuer die CLASSMETHOD und CLASSFILE_POSITION Tags. Damit werden die Werte, die per ReflectCodeZZZ.getPositionCalling() gekapselt.")
		;
		
		
		private String sAbbreviation,sTag,sDescription;
	
		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		TAGTYPE(String sAbbreviation, String sTag, String sDescription) {
		    this.sAbbreviation = sAbbreviation;
		    this.sTag = sTag;
		    this.sDescription = sDescription;
		}
		
		@Override
		public String getAbbreviation() {
		 return this.sAbbreviation;
		}
		
		@Override
		public String getTag() {
			 return this.sTag;
		}
		
		@Override
		public String getDescription() {
			return this.sDescription;
		}
		//+++++++++++++++++++++++++
				
		public EnumSet<?>getEnumSetUsed(){
			return TAGTYPE.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<TAGTYPE> enumClass = TagByTypeFactoryZZZ.TAGTYPE.class;
			EnumSet<TAGTYPE> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
			
			 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			 for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((TAGTYPE) obj);
			}
			return set;
			
		}
	
		//TODO: Mal ausprobieren was das bringt
		//Convert Enumeration to a Set/List
		private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
			  EnumSet<E> set=EnumSet.noneOf(enumClass);
			  long mask=1;
			  for (  E e : enumClass.getEnumConstants()) {
			    if ((mask & vector) == mask) {
			      set.add(e);
			    }
			    mask<<=1;
			  }
			  return set;
			}
	
		//+++ Das könnte auch in einer Utility-Klasse sein.
		//the valueOfMethod <--- Translating from DB
		public static TAGTYPE fromAbbreviation(String s) {
		for (TAGTYPE state : values()) {
		   if (s.equals(state.getAbbreviation()))
		       return state;
		}
		throw new IllegalArgumentException("Not a correct abbreviation: " + s);
		}
	
		//##################################################
		//#### Folgende Methoden bring Enumeration von Hause aus mit. 
				//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
		@Override
		public String getName() {	
			return super.name();
		}
	
		@Override
		public String toString() {//Mehrere Werte mit # abtennen
		    return this.sAbbreviation+"="+this.sDescription;
		}
	
		@Override
		public int getIndex() {
			return ordinal();
		}
	
		//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
		@Override
		public int getPosition() {
			return getIndex()+1; 
		}		
	}//End internal Class

	
	
	//#################### STATISCHE METHODEN ######################
	public static ITagTypeZZZ createTagTypeByName(IEnumSetMappedTagTypeZZZ objEnumMappedTagType) throws ExceptionZZZ{
		ITagTypeZZZ objReturn = null;
		main:{			
			if(objEnumMappedTagType==null)break main;
			
			String sTagName = objEnumMappedTagType.getTag();
			objReturn = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
										
		}//end main
		return objReturn;
	}
	
	public static ITagTypeZZZ createTagTypeByName(IEnumSetMappedZZZ objEnumMapped) throws ExceptionZZZ{
		ITagTypeZZZ objReturn = null;
		main:{			
			if(objEnumMapped==null)break main;
			if(!(objEnumMapped instanceof IEnumSetMappedTagTypeZZZ)) {
				break main;

			}else {
				IEnumSetMappedTagTypeZZZ objEnumMappedTagType = (IEnumSetMappedTagTypeZZZ)objEnumMapped;
				TagByTypeFactoryZZZ.createTagTypeByName(objEnumMappedTagType);
			}							
		}//end main
		return objReturn;
	}
	
	public static ITagTypeZZZ createTagTypeByName(String sTagName) throws ExceptionZZZ{
		ITagTypeZZZ objReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sTagName))break main;
			
			String sTagNameUsed = sTagName.toLowerCase();
			switch (sTagNameUsed) {
			case TagTypeLineNumberZZZ.sTAGNAME:
				objReturn = new TagTypeLineNumberZZZ();
				break;	
			case TagTypeFileNameZZZ.sTAGNAME:
				objReturn = new TagTypeFileNameZZZ();
				break;
			case TagTypeDateZZZ.sTAGNAME:
				objReturn = new TagTypeDateZZZ();
				break;
			case TagTypeThreadIdZZZ.sTAGNAME:
				objReturn = new TagTypeThreadIdZZZ();
				break;			
			case TagTypeClassNameZZZ.sTAGNAME:
				objReturn = new TagTypeClassNameZZZ();
				break;
			case TagTypeMethodZZZ.sTAGNAME:
				objReturn = new TagTypeMethodZZZ();
				break;
			case TagTypeFilePositionZZZ.sTAGNAME:
				objReturn = new TagTypeFilePositionZZZ();
				break;
			case TagTypePositionCurrentZZZ.sTAGNAME:
				objReturn = new TagTypePositionCurrentZZZ();
				break;
			default:
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled tagtype '" + sTagName + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;				
			}
		
		}//end main
		return objReturn;
		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++
	public static ITagByTypeZZZ createTagByName(IEnumSetMappedTagTypeZZZ objEnumMappedTagType) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{			
			if(objEnumMappedTagType==null)break main;
			
			String sTagName = objEnumMappedTagType.getTag();
			ITagTypeZZZ objTagType = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
			if(objTagType==null)break main;
			
			objReturn = TagByTypeFactoryZZZ.createTagByName(objTagType.getTagName());							
		}//end main
		return objReturn;
	}
	
	public static ITagByTypeZZZ createTagByName(IEnumSetMappedZZZ objEnumMapped) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{			
			if(objEnumMapped==null)break main;
			if(!(objEnumMapped instanceof IEnumSetMappedTagTypeZZZ)) {
				break main;

			}else {
				IEnumSetMappedTagTypeZZZ objEnumMappedTagType = (IEnumSetMappedTagTypeZZZ)objEnumMapped;
				ITagTypeZZZ objTagType = TagByTypeFactoryZZZ.createTagTypeByName(objEnumMappedTagType);
				if(objTagType==null)break main;
				
				objReturn = TagByTypeFactoryZZZ.createTagByName(objTagType.getTagName());
			}							
		}//end main
		return objReturn;
	}
	
	public static ITagByTypeZZZ createTagByName(String sTagName) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{ 
			if(StringZZZ.isEmpty(sTagName))break main;
			
			ITagTypeZZZ objType = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
			if(objType==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+" unable to create tag for name '"+ sTagName +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			objReturn = new TagByTypeZZZ(objType);
		}//END main
		return objReturn;
	}
	
	//++++ Mit Wert erstellen +++++++++++++++++++++++++
	//A) INTEGER
	public static ITagByTypeZZZ createTagByName(IEnumSetMappedTagTypeZZZ objEnumMappedTagType, int iValue) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{			
			if(objEnumMappedTagType==null)break main;
			
			String sTagName = objEnumMappedTagType.getTag();
			ITagTypeZZZ objTagType = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
			if(objTagType==null)break main;
			
			objReturn = TagByTypeFactoryZZZ.createTagByName(objTagType.getTagName());
			if(objReturn==null)break main;
			
			String sValue = Integer.toString(iValue);
			objReturn.setValue(sValue);
		}//end main
		return objReturn;
	}
	
	public static ITagByTypeZZZ createTagByName(IEnumSetMappedZZZ objEnumMapped, int iValue) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{			
			if(objEnumMapped==null)break main;
			if(!(objEnumMapped instanceof IEnumSetMappedTagTypeZZZ)) {
				break main;

			}else {
				IEnumSetMappedTagTypeZZZ objEnumMappedTagType = (IEnumSetMappedTagTypeZZZ)objEnumMapped;
				ITagTypeZZZ objTagType = TagByTypeFactoryZZZ.createTagTypeByName(objEnumMappedTagType);
				if(objTagType==null)break main;
				
				objReturn = TagByTypeFactoryZZZ.createTagByName(objTagType.getTagName());
				if(objReturn==null)break main;
				
				String sValue = Integer.toString(iValue);
				objReturn.setValue(sValue);
			}							
		}//end main
		return objReturn;
	}
	
	public static ITagByTypeZZZ createTagByName(String sTagName, int iValue) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{ 
			if(StringZZZ.isEmpty(sTagName))break main;
			
			ITagTypeZZZ objType = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
			if(objType==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+" unable to create tag for name '"+ sTagName +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			objReturn = new TagByTypeZZZ(objType);
			
			String sValue = Integer.toString(iValue);
			objReturn.setValue(sValue);
		}//END main
		return objReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//B) STRING
	public static ITagByTypeZZZ createTagByName(IEnumSetMappedTagTypeZZZ objEnumMappedTagType, String sValue) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{			
			if(objEnumMappedTagType==null)break main;
			
			String sTagName = objEnumMappedTagType.getTag();
			ITagTypeZZZ objTagType = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
			if(objTagType==null)break main;
			
			objReturn = TagByTypeFactoryZZZ.createTagByName(objTagType.getTagName());
			if(objReturn==null)break main;
			
			objReturn.setValue(sValue);
		}//end main
		return objReturn;
	}
	
	public static ITagByTypeZZZ createTagByName(IEnumSetMappedZZZ objEnumMapped, String sValue) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{			
			if(objEnumMapped==null)break main;
			if(!(objEnumMapped instanceof IEnumSetMappedTagTypeZZZ)) {
				break main;

			}else {
				IEnumSetMappedTagTypeZZZ objEnumMappedTagType = (IEnumSetMappedTagTypeZZZ)objEnumMapped;
				ITagTypeZZZ objTagType = TagByTypeFactoryZZZ.createTagTypeByName(objEnumMappedTagType);
				if(objTagType==null)break main;
				
				objReturn = TagByTypeFactoryZZZ.createTagByName(objTagType.getTagName());
				if(objReturn==null)break main;
				
				objReturn.setValue(sValue);
			}							
		}//end main
		return objReturn;
	}
	
	public static ITagByTypeZZZ createTagByName(String sTagName, String sValue) throws ExceptionZZZ{
		ITagByTypeZZZ objReturn = null;
		main:{ 
			if(StringZZZ.isEmpty(sTagName))break main;
			
			ITagTypeZZZ objType = TagByTypeFactoryZZZ.createTagTypeByName(sTagName);
			if(objType==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+" unable to create tag for name '"+ sTagName +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			objReturn = new TagByTypeZZZ(objType);			
			objReturn.setValue(sValue);
		}//END main
		return objReturn;
	}


	
	
	
}
