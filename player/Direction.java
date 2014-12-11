package player;

public final class Direction
{
  public static final Direction NONE = new Direction(0, 0);
  public static final Direction LEFT = new Direction(-1, 0);
  public static final Direction RIGHT = new Direction(1, 0);
  public static final Direction UP = new Direction(0, -1);
  public static final Direction DOWN = new Direction(0, 1);
  public static final Direction UPLEFT = new Direction(-1, -1);
  public static final Direction UPRIGHT = new Direction(1, -1);
  public static final Direction DOWNLEFT = new Direction(-1, 1);
  public static final Direction DOWNRIGHT = new Direction(1, 1);
  public static final Direction[] DIRECTIONS = new Direction[]{LEFT, RIGHT, UP, DOWN, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT};

  public final int xIncrement;
  public final int yIncrement;

  private Direction(int xIncrement, int yIncrement)
  {
    this.xIncrement = xIncrement;
    this.yIncrement = yIncrement;
  }

  public static final Direction reverse(Direction direction)
  {
    if (direction == Direction.NONE)
    {
      return Direction.NONE;
    }
    for (Direction d : Direction.DIRECTIONS)
    {
      if (direction.xIncrement == -d.xIncrement && direction.yIncrement == -d.yIncrement)
      {
        return d;
      }
    }

    throw new IllegalArgumentException("Could not find the reverse of direction.");
  }
}