package list;

public class Entry<T>
{
  public final boolean IsSentinel;
  private T item;
  private Entry<T> next;
  private Entry<T> prev;
  
  public Entry(T item, Entry<T> prev, Entry<T> next)
  {
    if (next != null)
    {
      next.prev = this;
    }
    if (prev != null)
    {
      prev.next = this;
    }

    this.IsSentinel = false;
    this.item = item;
    this.next = next;
    this.prev = prev;
  }

  public Entry(T item, Entry<T> sentinel)
  {
    this(item, sentinel, sentinel);
  }

  public Entry()
  {
    this.IsSentinel = true;
    this.item = null;
    this.next = this;
    this.prev = this;
  }

  public void Remove()
  {
    this.next.prev = this.prev;
    this.prev.next = this.next;
  }

  public void InsertNext(T item)
  {
    new Entry<T>(item, this, this.next);
  }

  public void InsertPrev(T item)
  {
    new Entry<T>(item, this.prev, this);
  }

  public Entry<T> getNext()
  {
    return this.next;
  }

  public Entry<T> getPrev()
  {
    return this.prev;
  }

  public T getItem()
  {
    return this.item;
  }

  public void SetItem(T item)
  {
    if (this.IsSentinel)
    {
      throw new UnsupportedOperationException("Value cannot be assigned to a sentinel Entry.");
    }

    this.item = item;
  }
}
