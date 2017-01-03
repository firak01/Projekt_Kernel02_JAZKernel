package basic.myutilities;

public class Stats {

  /* Class with elementary stats functions   NT Bishop 1990
   * =====================================   J M Bishop 1999
   *
   * Provides a mean and statdard deviation
   *
   */

  public static double mean (double a[], int n) {
    double sum = 0;
	for (int i = 1; i<=n; i++)
	  sum += a[i];
	return sum / n;
  }

  public static double stddev (double a[], int n, double ave) {
    double sum = 0;
	for (int i = 1; i <= n; i++)
	  sum += Math.pow((ave - a[i]),2);
	return Math.sqrt(sum / (n-1));
  }
}