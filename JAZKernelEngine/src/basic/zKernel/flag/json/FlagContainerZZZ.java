package basic.zKernel.flag.json;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Flag-Container-Objekt.
 *  Wenn man diese nur intern verwendete Klasse anbietet,
 *  kann man über JSON ein entsprechendes Objekt einlesen, z.B. als Argument im main(args[]) Aufruf.
 * 
 * sArg = "{\"FlagZZZ\":{\"hmFlag\":{\"XYZ\":true,\"abc\":true}}}";
 * @author Fritz Lindhauer, 26.03.2021, 08:27:29
 * 
 */
public class FlagContainerZZZ implements IFlagContainerZZZ{
	//private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	//???? !!! NUR PUBLIC PROPERTIES KÖNNEN ÜBETRAGEN WERDEN PER JSON !!!
	public HashMap<String, Boolean>HmFlag; // = new HashMap<String, Boolean>(); //Neu 20130721

	public FlagContainerZZZ() {		
	}
	
	//??? Nur Variablen, die auch in einem Konstruktor übergeben werden können per JSON übertragen werden.
	public FlagContainerZZZ(HashMap<String, Boolean>hmFlag) {
		this.HmFlag = hmFlag;
	}
	
	//??? nur wenn der Getter und Setter so heisst wie die interne Variable ist eine Übetragung per JSON möglich
	@Override
	public HashMap<String, Boolean> getHmFlag(){
		return HmFlag;
	}
	
	@Override
	public void setHmFlag(HashMap<String, Boolean>hmFlag) {
		this.HmFlag = hmFlag;
	}
	
	@Override
	public HashMap<String, Boolean> getHashMap() {	
		if(this.HmFlag==null) {
			this.HmFlag=new HashMap<String,Boolean>();
		}
		return HmFlag;
	}
	
	@Override
	public void setHashMap(HashMap<String,Boolean>hmFlag) {
		this.HmFlag=hmFlag;
	}

	@Override
	public boolean getFlag(String sFlag) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlag)) break main;
			
			HashMap<String, Boolean>hm = this.getHashMap();
			if(hm==null) break main;
			
			//Normalerweise sind FlagZ immer caseinsensitiv
			//Aber: Die Initialisierung per Gson kann das Flag auch anders gesetzt haben.
			if(hm.containsKey(sFlag)){
				bReturn = hm.get(sFlag);
			}else {
				//suche, caseinsensitv, wie FLAGZ halt sind
				String sFlagLowercase = sFlag.toLowerCase();
				if(hm.containsKey(sFlagLowercase)) {
					bReturn = hm.get(sFlagLowercase);
				}else{
					//vielleicht als uppercase irgendwie
					String sFlagUppercase = sFlag.toUpperCase();
					if(hm.containsKey(sFlagUppercase)) {
						bReturn = hm.get(sFlagUppercase);
					}
				};				
			}
						
		}//end main:
		return bReturn;
	}

	@Override
	public void setFlag(String sFlag, boolean bValue) throws ExceptionZZZ {		
		main:{
			if(StringZZZ.isEmpty(sFlag)) break main;
			
			HashMap<String, Boolean>hm = this.getHashMap();
			if(hm==null) break main;
			
			//FlagZ sind immer caseinsensitv, darum als Lowercase abspeichern.
			hm.put(sFlag.toLowerCase(), bValue);			
		}//end main:	
	}
	
}
