package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.TreeMap;

import basic.zBasic.ObjectZZZ;

public class HashtableZZZ extends ObjectZZZ{

	public static HashMap<String,String> toHashMap_StringString(HashtableIndexedZZZ<Integer,String> htIndexed){
		HashMap<String,String>hmReturn=null;
		main:{
			if(htIndexed==null) break main;
			hmReturn=new HashMap<String,String>();
			
			//Prüfe, ob überhaupt ein Objekt in der Hashtable (im Index) steht.
			VectorExtendedZZZ<Integer> vecIndex = htIndexed.getVectorIndex();
			if(vecIndex==null) break main;
			if(!vecIndex.hasAnyElement()) break main;
	
			//#############################################################
			//Merke: Der Indexvektor soll aufsteigend sortiert sein.
			for(Object objIndex : vecIndex) {
				Integer intIndex = (Integer) objIndex;
				String sValue = (String) htIndexed.getValue(intIndex);				
				hmReturn.put(intIndex.toString(), sValue);
			}
			
			/* ANDERE IDEE, z.B.: 
			HashtableIndexedZZZ objHt =  objBatchReader.getLines();
			VectorExtendedZZZ objVec = objHt.getVectorIndex();
			TreeMap<Integer,Object>tm = objHt.getTreeMap();
			hmReturn.putAll(tm);
			ABER: Wäre das dann zwangsläufig in der Richtigen Reihenfolge sortiert?	
			*/
		
		}
		return hmReturn;
	}
}
