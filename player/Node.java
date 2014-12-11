/* Node.java */

package player;

/**
 *  A node on a game board.
 */
public final class Node
{
	public static final int NORMAL = 0;
	public static final int START = 1;
	public static final int END = 2;

    public final int color;
    public final Position pos;
    public final int type;

    public Node(int color, Position position)
    {
    	this(color, position, Node.NORMAL);
    }
    /**
     * Node struct constructor
     */
    public Node(int color, Position position, int type)
    {
        this.color = color;
        this.pos = position;
        this.type = type;
    }

    public String toString()
    {
        return this.pos.toString();
    }
}
