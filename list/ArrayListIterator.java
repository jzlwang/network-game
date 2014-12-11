package list;

import java.util.Iterator;

public class ArrayListIterator<T> implements Iterator<T>
{
	private ArrayList<T> list;
	private int index;
	
	protected ArrayListIterator(ArrayList<T> list)
	{
	  this.list = list;
	  this.index = 0;
	}
	
	@Override
	public boolean hasNext()
	{
		return index < list.length();
	}
	
	@Override
	public T next()
	{
		return list.Get(index++);
	}
	
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}