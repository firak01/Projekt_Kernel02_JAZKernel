package basic.zBasic.util.abstractList;

import basic.zBasic.AbstractObjectZZZ;

/**Implentation eines Ringspeichers.
 *  
 * 
 * @author Fritz Lindhauer, 07.11.2023, 16:05:02
 * angelehnt an: 
 * https://www.baeldung.com/java-ring-buffer
 * https://github.com/eugenp/tutorials/blob/master/data-structures/src/main/java/com/baeldung/circularbuffer/CircularBuffer.java
 */
public class CircularBufferZZZ<E>  extends AbstractObjectZZZ {
	private static final int DEFAULT_CAPACITY = 8;

    private final int capacity;
    private final E[] data;
    private volatile int writeSequence, readSequence;
	
	
//	public CircularBufferZZZ() {
//				
//	}
	
	public CircularBufferZZZ(int capacity) {
	    this.capacity = (capacity < 1) ? DEFAULT_CAPACITY : capacity;
	    this.data = (E[]) new Object[this.capacity];
	    this.readSequence = 0;
	    this.writeSequence = -1;
	}
	
	 public boolean offer(E element) {

	        if (isNotFull()) {

	            int nextWriteSeq = writeSequence + 1;
	            data[nextWriteSeq % capacity] = element;

	            writeSequence++;
	            return true;
	        }

	        return false;
	    }

	    public E poll() {

	        if (isNotEmpty()) {

	            E nextValue = data[readSequence % capacity];
	            readSequence++;
	            return nextValue;
	        }

	        return null;
	    }

	    public int capacity() {
	        return capacity;
	    }

	    public int size() {

	        return (writeSequence - readSequence) + 1;
	    }

	    public boolean isEmpty() {

	        return writeSequence < readSequence;
	    }

	    public boolean isFull() {

	        return size() >= capacity;
	    }
	    
	    private boolean isNotEmpty() {

	        return !isEmpty();
	    }

	    private boolean isNotFull() {

	        return !isFull();
	    }
	    
	    
	    //Erweiterungen: Methoden, die auch in einer ArrayList vorhanden sind.
	    public E get(int iIndex) {
	    	if(iIndex<0) {
	    		return null;
	    	}else {
	    		return this.data[iIndex];
	    	}
	    }
	    
	    public E getLast() {
	    	int iIndex = this.writeSequence;
	    	return this.get(iIndex);
	    }
	    
	    public E getPrevious() {
	    	int iIndex = this.writeSequence - 1;	    	
	    	return this.get(iIndex);
	    }
	    
	    public E getPrevious(int iIndexStepsBack) {
	    	int iIndex = this.writeSequence - iIndexStepsBack;
	    	return this.get(iIndex);
	    }
	    
	    public boolean replaceLastWith(E element) {
	    	boolean bReturn = false;
	    	main:{
	    		int iIndex = this.writeSequence;
	    		bReturn = this.replacePositionWith(iIndex, element);	    		
	    	}//end main:
	    	return bReturn;
	    }
	    
	    public boolean replacePreviousWith(E element) {
	    	boolean bReturn = false;
	    	main:{
	    		int iIndex = this.writeSequence - 1;
	    		bReturn = this.replacePositionWith(iIndex, element);	    		
	    	}//end main:
	    	return bReturn;
	    }
	    
	    public boolean replacePositionWith(int iIndex, E element) {
	    	boolean bReturn = false;
	    	main:{
	    		if(iIndex<0)break main;
	    		if(iIndex>this.writeSequence)break main;
	    		
	    		data[iIndex]=element;
	    		
	    		bReturn = true;
	    	}//end main:
	    	return bReturn;
	    }
}
