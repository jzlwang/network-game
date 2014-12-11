package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<T> implements Iterator
{
    private Entry<T> entry;

    public LinkedListIterator(Entry<T> e)
    {
      this.entry = e;
    }

    public boolean hasNext()
    { 
      return !this.entry.getNext().IsSentinel;
    }

    public T next()
    {
      if (this.hasNext())
      {
        this.entry = this.entry.getNext();
        return this.entry.getItem();
      }
      throw new NoSuchElementException("No more elements in list.");
    }

    public void remove()
    {
      throw new UnsupportedOperationException();
    }
}
