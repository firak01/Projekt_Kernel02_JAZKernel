package basic.zBasic.util.math;

import java.util.ArrayList;

/* Ermittle die Primzahlen von ... bis
 * 
 * 
https://javabeginners.de/Algorithmen/Sieb_des_Eratosthenes.php
 
Was ist das Sieb des Eratosthenes?

Das Sieb des Eratosthenes ist ein aus der Antike stammender Algorithmus zur Bestimmung der Primzahlen unterhalb einer beliebigen Obergrenze.
Funktionsweise

Das Sieb des Eratosthenes dient der Ermittlung aller Primzahlen zwischen 2 und einer Obergrenze. Hierbei werden alle Zahlen zwischen 2 und der Obergrenze zunächst als potentielle Primzahlen markiert. Die kleinste potentielle Primzahl (2) muss eine solche sein und wird ausgegeben. Dann werden alle Vielfachen dieser Zahl bis zur Obergrenze durchlaufen und als Nicht-Primzahlen markiert, da sie als Vielfache keine Primzahlen sein können. Im nächsten Schritt wird die zweitkleinste, noch als Primzahl markierte Zahl (3) ausgegeben und deren Vielfache als Nicht-Primzahlen markiert. Dann wird die drittkleinste noch als Primzahl markierte Zahl (5, denn 4 wurde als Vielfaches von 2 bereits als Nicht-Primzahl markiert) ausgegeben, die Vielfachen von 5 markiert etc.
Das Ergebnis ist die Ausgabe aller Primzahlen zwischen 2 und der angegebenen Obergrenze. 
 */
public class PrimeFinderZZZ {

    private static final int MAX = 100;
    private static boolean[] isPrim = new boolean[MAX];

    public static int[] asIntegerArray() {
        int[] arr = new int[MAX];
        for (int i = 2; i <= arr.length; ++i) {
            arr[i-2] = i;
            isPrim[i-2] = i == 2 || i%2 == 1 ? true : false;
        }
        return arr;
    }

    private static ArrayList<Integer> siebe(int[] n) {
        ArrayList<Integer> prim = new ArrayList<Integer>();
        for (int i = 2; i <= MAX; ++i) {
            if (isPrim[i-2]) {
                prim.add(n[i-2]);
                for (int j = i*i; j <= MAX; j += i) {
                    isPrim[j-2] = false;
                }
            }
        }
        return prim;
    }
    
    private static void debug(ArrayList<Integer> list) {
        for(int i : list) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        debug(siebe(asIntegerArray()));
    }
}
