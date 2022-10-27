package basic.zBasic.util.console.multithread;

public class Key_cancelZZZ extends AbstractKeyZZZ{
	private static IKeyZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_cancelZZZ() {
			super();
		}
	
	public static IKeyZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_cancelZZZ();
		}
		return objKey;
	}
	
	@Override
	public IKeyZZZ getKeyObject() {
		return this.objKey;
	}

	@Override
	public void setKeyObject(IKeyZZZ objKey) {
		this.objKey = objKey;
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyCancel;
	}

	@Override
	public char getKeyChar() {
		return Key_cancelZZZ.getKey();
	}
}
