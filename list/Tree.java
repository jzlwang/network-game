package list;

public class Tree<T>
{
	private T item;
	private ArrayList<Tree<T>> children;
	
	public Tree(T item)
	{
		this.item = item;
		this.children = new ArrayList<Tree<T>>();
	}

    public T getItem()
    {
        return item;
    }

    public ArrayList<Tree<T>> getChildren()
    {
        return children;
    }

	public void append(T item)
	{
		this.children.append(new Tree(item));
	}
	
	public int depth()
	{
        int maxDepth = 0;
        for (Tree<T> t : this.children)
        {
            maxDepth = Math.max(maxDepth, t.depth());
        }

		return 1 + maxDepth;
	}
	
	public int width()
	{
		ArrayList<Tree<T>> flattened = this.children;
        int maxWidth = this.children.length();

        while (flattened.length() > 0)
        {
            ArrayList<Tree<T>> flattenedChildren = new ArrayList<Tree<T>>();
            for (Tree<T> subTree : flattened)
            {
                flattenedChildren.append(subTree.children);
            }

            maxWidth = Math.max(maxWidth, flattenedChildren.length());
            flattened = flattenedChildren;
        }

        return maxWidth;
	}
}