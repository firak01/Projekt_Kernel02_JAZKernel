package basic.zBasic.util.abstractList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


//#### Aus IVector3ZZZ

/** Vektor, der immer maximal 3 Elemente haben darf.
 *  Aber auch immer aus 3 Elementen besteht!
 *  
 *  Merke: 
 *  Vorgesehen fuer die Zerlegeung von Strings in einen Teil links, mittig, rechts.
 *  Z.B. beim Parsen von (XML-)Tags.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 25.09.2024, 07:58:44
 * 
 */
public class Vector3ZZZ<T> extends VectorLimitedZZZ<T> implements IVector3ZZZ<T>{
	private static final long serialVersionUID = -4863797296442866090L;

	public Vector3ZZZ() {
		super(3);
	}
	
	@Override
	public void replace(Object objValue) throws ExceptionZZZ {
		main:{
			if(objValue==null) break main;
			
			if(objValue instanceof String) {
				replace_((String) objValue);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Objekt with this type not handled yet: '" + objValue.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
				
		}//end main:
	}
	
	
	private void replace_(String sValue) throws ExceptionZZZ{
		
		if(this.size()==0) this.add(0, "");
		
		if(this.size()>=2) this.removeElementAt(1);
		if(!StringZZZ.isEmpty(sValue)){
			this.add(1, sValue);
		}else {
			this.add(1, "");
		}
			
		if(this.size()==2) this.add(2, "");
	}
	
	
	@Override
	public void replace(Object objValueLeft, Object objValueMid, Object objValueRight ) throws ExceptionZZZ {
		main:{
			if(objValueMid==null) break main;
			if(objValueLeft==null & objValueRight==null) {
				this.replace(objValueMid);
				break main;
			}
			
			if(objValueMid instanceof String) {
				replace_((String) objValueLeft, (String) objValueMid, (String) objValueRight, false, null, null);
			}
		}//end main:
	}
	
	@Override
	public void replace(Object objValueLeft, Object objValueMid, Object objValueRight, boolean bReturnSeparators, String sSepLeft, String sSepRight ) throws ExceptionZZZ {
		main:{
			if(objValueMid==null) break main;
			if(objValueLeft==null & objValueRight==null) {
				this.replace(objValueMid);
				break main;
			}
			
			if(objValueMid instanceof String) {
				replace_((String) objValueLeft, (String) objValueMid, (String) objValueRight, bReturnSeparators, sSepLeft, sSepRight);
			}
		}//end main:
	}
	
	private void replace_(String sLeft, String sMid, String sRight, boolean bReturnSeparators, String sSepLeft, String sSepRight) throws ExceptionZZZ {
		
		ObjectUtilZZZ.ensureTypeEquals(sLeft, sMid); //Merke: ensure... wirft eine Exception
		ObjectUtilZZZ.ensureTypeEquals(sRight, sMid);
		
		//Nun die Werte in den ErgebnisVector zusammenfassen
		if(bReturnSeparators ==true && !StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft + sSepLeft);
			}else {
				this.add(0, sSepLeft);
			}
								
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, sMid);//zentral wichtig: In der Mitte immer das "Extrakt".
			
			if(this.size()>=3) this.removeElementAt(2);											
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sSepRight + sRight);
			}else {
				this.add(2,  sSepRight);
			}
		}else if(bReturnSeparators ==false && !StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);						
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, "");
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, sMid);
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, "");
			}
		
		} else if(bReturnSeparators ==true && StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);						
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft + sSepLeft);
			}else {
				this.add(0, sSepLeft);
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, "");
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sSepRight + sRight);
			}else {
				this.add(2, sSepRight);
			}
		}  else {
			if(this.size()>=1) this.removeElementAt(0);						
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, "");
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, "");
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, "");
			}
		}
	}
	
	//++++++++++++++++++++++++++++++++++++++++
	@Override
	public void replace(Object objValueLeft, Object objValueRight, boolean bReturnSeparators, String sSepMid ) throws ExceptionZZZ {
		main:{
			if(objValueLeft==null || objValueRight==null) break main;
			
			if(objValueLeft instanceof String) {
				replace_((String) objValueLeft, (String) objValueRight, bReturnSeparators, sSepMid);
			}
		}//end main:
	}
	
	private void replace_(String sLeft, String sRight, boolean bReturnSeparators, String sSepMid) throws ExceptionZZZ {
		ObjectUtilZZZ.ensureTypeEquals(sLeft, sRight); //Merke: ensure... wirft eine Exception
		
		//Nun die Werte in den ErgebnisVector zusammenfassen
		if(bReturnSeparators ==true){
			if(this.size()>=1) this.removeElementAt(1);
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, "");
			}
			
			if(this.size()>=2) this.removeElementAt(2);
			this.add(1, sSepMid); //zentral wichtig: In der Mitte immer das "Extrakt". HIER ABER LEER, bzw. nur Separator
			
			if(this.size()>=3) this.removeElementAt(3);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, "");
			}
		
		}else if(bReturnSeparators == false){
			if(this.size()>=1) this.removeElementAt(1);										
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, "");
			}
			
			if(this.size()>=2) this.removeElementAt(2);						
			this.add(1, "");  //Also ein Leerstring
			
			if(this.size()>=3) this.removeElementAt(3);										
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, "");
			}
		}
	}
}
