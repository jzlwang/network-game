/* Position.java */

package player;

/**
 *  Immutable class. A coordinate on a game board.
 */
public final class Position
{
  public final int x;
  public final int y;

  /**
   * Position constructor
   * @param x is x-coordinate
   * @param y is y-coordinate
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public String toString()
  {
      return "" + this.x + ", " + this.y;
  }
}
