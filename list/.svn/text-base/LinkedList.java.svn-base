package list;

public class LinkedList<T>
{
  private Entry<T> sentinel;
  private int length;

  public LinkedList()
  {
    this.sentinel = new Entry<T>();
    this.length = 0;
  }

  public LinkedListIterator<T> iterator()
  {
    return new LinkedListIterator<T>(this.sentinel);
  }

  public int length()
  {
    return this.length;
  }

  public boolean isEmpty()
  {
    return this.length == 0;
  }

  public void append(T item)
  {
    length++;
    this.sentinel.InsertPrev(item);
  }

  public void Prepend(T item)
  {
    length++;
    this.sentinel.InsertNext(item);
  }

  public void RemoveEnd()
  {
    if (length > 0)
    {
      length--;
      this.sentinel.getPrev().Remove();
    }
  }
  
  private Entry<T> GetEntry(int index)
  {
    if (index >= length)
    {
      throw new IndexOutOfBoundsException("" + index);
    }
    
    if (length - index < index)
    {
      index = -(length - index) - 1;
    }

    Entry node = this.sentinel.getNext();
    
    for (int pos = 0; pos < index; pos++)
    {
      node = node.getNext();
    }
    for (int pos = 0; pos > index; pos--)
    {
      node = node.getPrev();
    }

    return node;
  }

  public T getItem(int index)
  {
    return GetEntry(index).getItem();
  }
  
  public void SetItem(int index, T item)
  {
    GetEntry(index).SetItem(item);
  }

  public void InsertAt(int index, T item)
  {
    length++;
    if (index == length - 1)
    {
      this.sentinel.InsertPrev(item);
      return;
    }
    GetEntry(index).InsertPrev(item);
  }

  public void RemoveAt(int index)
  {
    Entry<T> e = GetEntry(index);
    if (e != this.sentinel)
    {
      e.Remove();
      length--;
    }
    else
    {
      throw new IndexOutOfBoundsException();
    }
  }
  
  public T first()
  {
    if (length > 0)
    {
      return this.sentinel.getNext().getItem();
    }
    
    throw new IndexOutOfBoundsException();
  }

  public String toString()
  {
    String s = "[";
    Entry node = this.sentinel.getNext();
    while (node != this.sentinel)
    {
      s += node.getItem().toString() + ", ";
      node = node.getNext();
    }

    return s.substring(0, s.length() - 2) + "]";
  }

  public static void main(String[] args)
  {
    LinkedList list = new LinkedList<Object>();
    list.append(2);
    list.append(3);
    list.Prepend(1);
    list.InsertAt(1, 3);
    list.InsertAt(1, 2);
    System.out.println(list.toString());
    list.SetItem(0, 5);
    System.out.println(list.toString());
    list.SetItem(4, 5);
    System.out.println(list.toString());
    list.InsertAt(5, 0);
    System.out.println(list.toString());
  }
}
