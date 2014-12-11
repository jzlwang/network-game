/* ArrayList.java */

package list;

import java.util.Iterator;

/*
 * Implementation of an ArrayList with remove functionality.
 *
 */
public class ArrayList<T> implements Iterable<T>
{
  private static final int INITIAL_SIZE = 10;

  private T[] items;
  private int size;

  private T[] CreateArray(int size)
  {
    return (T[])(new Object[size]);
  }

  public ArrayList()
  {
    this(ArrayList.INITIAL_SIZE);
  }

  public ArrayList(int InitialSize)
  {
    this.items = CreateArray(InitialSize);
    this.size = 0;
  }

  public int length()
  {
    return this.size;
  }

  public void Insert(T item, int index)
  {
    if (index < 0 || index > this.size)
    {
      throw new ArrayIndexOutOfBoundsException(index);
    }

    this.size++;

    if (this.size >= this.items.length)
    {
      Resize(this.size * 2);
    }

    this.ArrayShiftRight(index);
    this.items[index] = item;
  }

  public void append(T item)
  {
    this.Insert(item, this.size);
  }

  public void append(Iterable<T> list)
  {
      for (T item : list)
      {
          this.append(item);
      }
  }

  public ArrayList<T> merge(ArrayList<T> list)
  {
    ArrayList<T> merged = new ArrayList<T>();
    merged.append(this);
    merged.append(list);
    return merged;
  }

  public T Get(int index)
  {
    if (index < 0 || index >= this.size)
    {
      throw new ArrayIndexOutOfBoundsException(index); 
    }

    return this.items[index];
  }

  private void Resize(int size)
  {
    T[] newArray = CreateArray(size);
    
    for (int i = 0; i < this.items.length; i++)
    {
      newArray[i] = this.items[i];
    }

    this.items = newArray;
  }

  public void Remove(int i)
  {
    if (i < 0 || i >= this.size)
    {
      return;
    }

    this.size--;
    this.ArrayShiftLeft(i);
    this.items[size] = null;
  }

  public void Remove(T item)
  {
    this.Remove(FindOne(item));
  }

  public void Remove(Iterable<T> list)
  {
    for (T item : list)
    {
      this.Remove(item);
    }
  }

  public int FindOne(T item)
  {
    for (int i = 0; i < this.size; i++)
    {
      if (this.items[i] == item)
      {
        return i;
      }
    }

    return -1;
  }

  public T first()
  {
    if (this.size > 0)
    {
      return this.items[0];
    }

    throw new ArrayIndexOutOfBoundsException();
  }

  private void ArrayShiftLeft(int start)
  {
    for (int i = start; i < this.size; i++)
    {
      this.items[i] = this.items[i + 1];
    }
  }

  private void ArrayShiftRight(int start)
  {
    for (int i = start; i < this.size; i++)
    {
      this.items[i + 1] = this.items[i];
    }
  }

  @Override
  public String toString()
  {
    String s = "ArrayList[" + this.size + "]{ ";

    for (int i = 0; i < this.items.length; i++)
    {
      s += this.items[i] == null ? "null " : this.items[i].toString() + " ";
    }

    return s + "}";
  }

  @Override
  public Iterator<T> iterator()
  {
	return new ArrayListIterator<T>(this);
  }
  
  public static void main(String[] args)
  {
    ArrayList<Integer> a = new ArrayList<Integer>(2);
    System.out.println(a);
    a.append(1);
    System.out.println(a);
    a.append(2);
    System.out.println(a);
    a.append(3);
    System.out.println(a);
    a.append(4);
    a.append(5);
    System.out.println(a);
  }
}