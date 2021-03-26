package basic.zKernel.flag;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Flag-Container-Objekt.
 *  Wenn man diese nur intern verwendete Klasse anbietet,
 *  kann man über JSON ein entsprechendes Objekt einlesen, z.B. als Argument im main(args[]) Aufruf.
 * 
 * @author Fritz Lindhauer, 26.03.2021, 08:27:29
 * 
 */
public class FlagZZZ implements IFlagZZZ{
	//private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	//???? !!! NUR PUBLIC PROPERTIES KÖNNEN ÜBETRAGEN WERDEN PER JSON !!!
	public HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721

	public FlagZZZ() {		
	}
	
	//??? Nur Variablen, die auch in einem Konstruktor übergeben werden können per JSON übertragen werden.
	public FlagZZZ(HashMap<String, Boolean>hmFlag) {
		this.hmFlag = hmFlag;
	}
	
	//??? nur wenn der Getter und Setter so heisst wie die interne Variable ist eine Übetragung per JSON möglich
	public HashMap<String, Boolean> getHmFlag(){
		return hmFlag;
	}
	public void setHmFlag(HashMap<String, Boolean>hmFlag) {
		this.hmFlag = hmFlag;
	}
	
	
	
	
	@Override
	public HashMap<String, Boolean> getHashMap() {		
		return hmFlag;
	}
	
	@Override
	public void setHashMap(HashMap<String,Boolean>hmFlag) {
		this.hmFlag=hmFlag;
	}

	@Override
	public boolean getFlag(String sFlag) {
		return this.getHashMap().get(sFlag);
	}

	@Override
	public void setFlag(String sFlag, boolean bValue) {
		this.getHashMap().put(sFlag, bValue);
		
	}
	
}
