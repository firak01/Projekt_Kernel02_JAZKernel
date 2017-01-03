package basic.myutilities;

public class Sort {

    /* The Sort class   by J M Bishop  Feb 1997
     *                  revised October 1997
     * Provides one sorting method
     * for arrays of objects of any length.
     * where the objects' class implements the
     * Sortable interface.
     */

  public static void selectionSort(Sortable a [], int n) {
    Sortable temp;
    int chosen;

    for (int leftmost = 0; leftmost < n-1; leftmost++) {
      chosen = leftmost;
      for (int j = leftmost+1; j < n; j++) {
        if (a[j].lessThan(a[chosen])) {
          chosen = j;
        }
      }
      temp = a[chosen];
      a[chosen] = a[leftmost];
      a[leftmost] = temp;
    }
  }
}
