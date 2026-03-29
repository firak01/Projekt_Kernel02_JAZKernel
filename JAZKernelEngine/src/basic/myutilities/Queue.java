package basic.myutilities;

public class Queue {
  /* Queue abstract data type  by J M Bishop  Jan 1997
   *                           updated August 2000
   * Implements a queue as a bounded circular array.
   * Has a set maximum of 100 elements. */

  public Queue (int m) {
    if (m <= maxQueue)
      size = m;
    else size = maxQueue;
    front = 0;
    back = -1;
    live = 0;
    reset();
  }

  public void add (Object x) throws QueueException {
    // throws an exception if the queue is full
    if (live < size) {
      back = (back + 1) % maxQueue;
      Q[back] = x;
      live++;
    }
    else
      throw new QueueException("Full");
  }

  public Object remove () throws QueueException {
    // throws an exception id the queue is empty
    if (live >=1) {
      Object x = Q[front];
      front = (front + 1) % maxQueue;
      live--;
      return x;
    }
    else
      throw new QueueException("Empty");
  }

  public boolean empty () {
    return live == 0;
  }

  public boolean full () {
    return live == size;
  }

  // Iterator methods
  public void reset () {
    now = front;
  }

  public void succ () {
    now = (now+1) % maxQueue;
  }

  public boolean eol () {
    if (back==maxQueue)
      return now == 0;
    else
      return now > back;
  }

  public Object current () {
    return Q[now];
  }

  private
  int size,        // total vector capacity;
      front, back, // indicators
      live,        // number of used spaces in the circular queue
      now;         // position for displaying the queue
  private Object Q [] = new Object [maxQueue];
  static private int maxQueue = 100;

}

