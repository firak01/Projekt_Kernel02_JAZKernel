 package basic.myutilities;

  import basic.javagently.*;
  import java.io.*;


 
  class QuickSortTest {

    /* The Quick Sort test program  by J M Bishop   Jan 1997
     * ===========================  Java 1.1
     *                              improved August 2000
     * sorts sequence using quicksort
     *
     * Illustrates recursion
     */

   static Item a [] = new Item [10];
   static Item t = new Item(0);

   static Item [] quicksort (Item [] a, int l, int r) {
     if (l < r) {
       display(a,l,r);
       int i = l;
       int j = r;
       int k = (int) ((l+r) / 2);
       Item pivot = a[k];
       do {
        while (a[i].less(pivot)) i++;
        while (pivot.less(a[j])) j--;
         if (i<=j) {
           t = (Item) a[i].clone();
           a[i] = a[j];
           a[j] = t;
           i++; j--;
        }
      } while (i<j);
      a = quicksort (a, l, j);
      a = quicksort (a, i, r);
    }
    return a;
  }

    public static void main (String args [])
      			   throws Exception {

      Stream in = new Stream(System.in);
      System.out.println("**** Testing quicksort ****");
      System.out.println("Type in 10 number "+
            "separated by spaces");
      for (int i=0; i<a.length;i++)
        a[i] = new Item (in.readInt());

      quicksort (a, 0, 9);

      display (a, 0, 9);
    }

    static void display (Item [] a, int left, int right) {
      for (int j = 0; j < left; j++)
        System.out.print("   ");
      for (int j = left; j <= right; j++)
        System.out.print(a[j].data +"  ");
      System.out.println();
    }

  }

  class Item  {
  // ---------
  // the objects being sorted

      Item (int i) {
        data = i;
      }

      boolean equals (Item x) {
        return data==x.data;
      }

      boolean less (Item x) {
        return data < x.data;
      }

      public Object clone() {
		Item x = new Item (data);
		return x;
      }

      int data;
    }

  class ItemNotFoundException extends Exception { }

