/* Board.java */

package player;

import list.*;
import list.ArrayList;

import java.util.*;

/**
 *  A data structure to store nodes on a board.
 *
 *
 */
public class Board {

  public static final int COLOR_BLACK = 0;
  public static final int COLOR_WHITE = 1;
  public static final int COLOR_NONE = 3;
  public static final int COLOR_FORBIDDEN = 2;

  public static final double WIN_SCORE = 1000.0;

  private Node[][] board;
  private int[] chipcount;

  /**
   * Board constructor w/ no params
   * creates 8x8 2D Node array
   */
  protected Board() {
    board = new Node[8][8];
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        board[x][y] = new Node(COLOR_NONE, new Position(x, y));
      }
    }

    board[0][0] = new Node(COLOR_FORBIDDEN, new Position(0, 0));
    board[0][7] = new Node(COLOR_FORBIDDEN, new Position(0, 7));
    board[7][0] = new Node(COLOR_FORBIDDEN, new Position(7, 0));
    board[7][7] = new Node(COLOR_FORBIDDEN, new Position(7, 7));

    chipcount = new int[]{0, 0};
  }

  private void setChip(int color, int x, int y) {
    int type = Node.NORMAL;
    if (x == 0 || y == 0)
    {
      type = Node.START;
    }
    else if (x == 7 || y == 7)
    {
      type = Node.END;
    }

    board[x][y] = new Node(color, new Position(x, y), type);
  }

  private void clearChip(int x, int y) {
    board[x][y] = new Node(COLOR_NONE, new Position(x, y));
  }

  /**
   * @param x is x-coordinate
   * @param y is y-coordinate
   * @return Node from specified xy-coordinate
   */
  public Node getNode(int x, int y) {
    if (x >= 0 && x < 8 && y >= 0 && y < 8) {
      return board[x][y];
    } else {
      return new Node(COLOR_NONE, new Position(x, y));
    }
  }

  /**
   * @param p     is the Position to check
   * @param color is black or white
   * @check if Position is good for specified player color
   */
  public boolean validPosition(Position p, int color) {
    //Corner pieces
    if (getNode(p.x, p.y).color == COLOR_FORBIDDEN) {
      return false;
    }

    if (color == COLOR_BLACK) {
      //if the color is black and it's in goal of white or in a corner, return false
      if (p.x == 0 || p.x == 7) {
        return false;
      }
    } else {
      //if the color is white and it's in the goal of black or in a corner, return false
      if (p.y == 0 || p.y == 7) {
        return false;
      }
    }

    return true;
  }


  private ArrayList<Position> adjacent(Position p, int color) {
    ArrayList<Position> nodes = new ArrayList<Position>();
    for (int i = p.x - 1; i <= p.x + 1; i++) {
      for (int j = p.y - 1; j <= p.y + 1; j++) {
        if (i == p.x && j == p.y) {
          continue;
        }

        if (getNode(i, j).color == color) {
          nodes.append(new Position(i, j));
        }
      }
    }

    return nodes;
  }

  /**
   * @return Number of neighbors. Guranteed to be LESS than or EQUAL than the actual number, but accurate up to 2.
   */
  public int countCluster(Position p, int color) {
    ArrayList<Position> nodes = adjacent(p, color);

    int max = nodes.length();

    for (int i = 0; i < nodes.length(); i++) {
      max = Math.max(max, adjacent(nodes.Get(i), color).length() + 1);
    }

    return max;
  }

  public boolean makeMove(Move move, int color) {
    if (isValidMove(move, color)) {
      if (move.moveKind == Move.STEP) {
        clearChip(move.x2, move.y2);
      } else {
        chipcount[color]++;
      }

      setChip(color, move.x1, move.y1);
      return true;
    }

    return false;
  }

  public void undoMove(Move move, int color) {
    clearChip(move.x1, move.y1);

    if (move.moveKind == Move.STEP) {
      setChip(color, move.x2, move.y2);
    } else {
      chipcount[color]--;
    }
  }

  public boolean isValidMove(Move move, int color) {
    if (move == null)
    {
      return false;
    }

    //If target location is occupied, move fails;
    if (getNode(move.x1, move.y1).color != COLOR_NONE) {
      return false;
    }
    if (!(validPosition(new Position(move.x1, move.y1), color))) {
      return false;
    }

    if (move.moveKind == Move.ADD) {
      if (chipcount[color] >= 10) {
        return false;
      }
      if (countCluster(new Position(move.x1, move.y1), color) >= 2) {
        return false;
      }
    } else {
      if (chipcount[color] != 10) {
        return false;
      }
      if (getNode(move.x2, move.y2).color != color) {
        return false;
      }
      if (move.x1 == move.x2 && move.y1 == move.y2) {
        return false;
      }

      clearChip(move.x2, move.y2);
      chipcount[color]--;

      if (!isValidMove(new Move(move.x1, move.y1), color)) {
        setChip(color, move.x2, move.y2);
        chipcount[color]++;
        return false;
      }

      setChip(color, move.x2, move.y2);
      chipcount[color]++;
    }

    return true;
  }

  private ArrayList<Node> findNodes(int color)
  {
    return findNodes(color, -1);
  }

  private ArrayList<Node> findNodes(int color, int type)
  {
    ArrayList<Node> nodes = new ArrayList<Node>();
    for (int x = 0; x < 8; x++)
    {
      for (int y = 0; y < 8; y++)
      {
        if (board[x][y].color == color && (type == -1 || board[x][y].type == type))
        {
          nodes.append(board[x][y]);
        }
      }
    }
    return nodes;
  }

  private ArrayList<Position> findLocations(int color)
  {
    ArrayList<Position> locations = new ArrayList<Position>();
    for (Node n : findNodes(color))
    {
      locations.append(n.pos);
    }

    return locations;
  }

  public ArrayList<Move> findAddMoves(int color)
  {
    ArrayList<Move> moves = new ArrayList<Move>();
    ArrayList<Position> locations = findLocations(COLOR_NONE);

    for (Position p : locations)
    {
      Move add = new Move(p.x, p.y);
      if (isValidMove(add, color))
      {
        moves.append(add);
      }
    }

    return moves;
  }

  /**
   * makes a ArrayList of Moves; the list of all moves a player can make at the moment.
   * @param color is the color of the player we are evaluating
   */
  public ArrayList<Move> findValidMoves(int color) {
    //make an array moves to keep all valid moves
    ArrayList<Move> moves = new ArrayList<Move>();

    if (chipcount[color] < 10)
    {
      return findAddMoves(color);
    }
    else
    {
      ArrayList<Position> locations = findLocations(color);

      for (Position p : locations)
      {
        //temp remove chip
        clearChip(p.x, p.y);
        chipcount[color]--;

        ArrayList<Move> addMoves = findAddMoves(color);
        for (Move m : addMoves)
        {
          if (m.x1 != p.x || m.y1 != p.y)
          {
            moves.append(new Move(m.x1, m.y1, p.x, p.y));
          }
        }

        //restore the chip
        setChip(color, p.x, p.y);
        chipcount[color]++;
      }
    }

    return moves;
  }

  /**
   * find all partial networks containing certain chip
   * @param certain chip represented by position x, position y, and color
   * @return LinkedList contains all partial networks
   */
  public ArrayList<Tree<Node>> findNetworks(int color)
  {
    ArrayList<Tree<Node>> networks = new ArrayList<Tree<Node>>();
    ArrayList<Node> nodes = findNodes(color);
    ArrayList<Adjacency> adjacencies = createAdjacencyList(nodes);

    for (Node n : nodes)
    {
      Tree<Node> network = new Tree<Node>(n);
      networks.append(network);
      findNetwork(network, nodes, adjacencies);
    }

    return networks;
  }

  public boolean hasWin()
  {
    return (hasWin(COLOR_BLACK) || hasWin(COLOR_WHITE));
  }

  public boolean hasWin(int color)
  {
    if (chipcount[color] < 6)
    {
      return false;
    }

    ArrayList<Tree<Node>> networks = new ArrayList<Tree<Node>>();
    ArrayList<Node> nodes = findNodes(color, Node.NORMAL);
    ArrayList<Node> startNodes = findNodes(color, Node.START);
    ArrayList<Node> endNodes = findNodes(color, Node.END);
    ArrayList<Adjacency> adjacencies = createAdjacencyList(nodes.merge(startNodes));

    for (Node n : startNodes)
    {
      Tree<Node> network = new Tree<Node>(n);
      networks.append(network);
      findNetwork(network, nodes, adjacencies);
    }

    for (Tree<Node> network : networks)
    {
      if (network.depth() < 5)
      {
        continue;
      }

      ArrayList<Tree<Node>> childrenNodes = network.getChildren();

      for (int i = 1; i < network.depth(); i++)
      {
        if (i >= 4)
        {
          for (Tree<Node> trees : childrenNodes)
          {
            for (Node en : endNodes)
            {
              if (getAdjacency(trees.getItem(), en) != null)
              {
                return true;
              }
            }
          }
        }

        ArrayList<Tree<Node>> children = new ArrayList<Tree<Node>>();
        for (Tree<Node> subtree : childrenNodes)
        {
          children.append(subtree.getChildren());
        }

        childrenNodes = children;
      }
    }

    return false;
  }

  private void findNetwork(Tree<Node> start, ArrayList<Node> nodes, ArrayList<Adjacency> adjacencies)
  {
    this.findNetwork(start, nodes, adjacencies, new ArrayList<Node>(), Direction.NONE, new boolean[]{false, false});
  }

  private void findNetwork(Tree<Node> start, ArrayList<Node> nodes, ArrayList<Adjacency> adjacencies, ArrayList<Node> visited, Direction blacklist, boolean[] endUsed)
  {
    Node startNode = start.getItem();

    visited.append(start.getItem());
    for (Node n : nodes)
    {
      if (visited.FindOne(n) != -1)
      {
        continue;
      }

      for (Adjacency a : adjacencies)
      {
        if (a.first == startNode && a.second == n && a.direction != blacklist)
        {
          if (a.second.type == Node.START)
          {
            if (endUsed[0])
            {
              break;
            }
            endUsed[0] = true;
          }
          else if (a.second.type == Node.END)
          {
            if (endUsed[1])
            {
              break;
            }
            endUsed[1] = true;
          }

          start.append(n);
          break;
        }
      }
    }

    for (Tree<Node> child : start.getChildren())
    {
      Node nextNode = child.getItem();
      findNetwork(child, nodes, adjacencies, visited, getAdjacency(startNode, nextNode), endUsed);
      visited.Remove(visited.length() - 1);
    }
  }

  private class Adjacency
  {
    public final Node first;
    public final Node second;
    public final Direction direction;

    public Adjacency(Node first, Node second, Direction direction)
    {
      this.first = first;
      this.second = second;
      this.direction = direction;
    }

    public String toString()
    {
      return "(" + this.first.toString() + ") - (" + this.second.toString() + ")";
    }
  }

  private ArrayList<Adjacency> createAdjacencyList(ArrayList<Node> nodes)
  {
    ArrayList<Adjacency> adjacencies = new ArrayList<Board.Adjacency>();

    for (Node n : nodes)
    {
      for (Node m : nodes)
      {
        if (n != m)
        {
          Direction d = getAdjacency(n, m);
          if (d != null)
          {
            adjacencies.append(new Adjacency(n, m, d));
          }
        }
      }
    }

    return adjacencies;
  }

  public Direction getAdjacency(Node a, Node b)
  {
    Direction d = Direction.NONE;

    int dx = b.pos.x - a.pos.x;
    int dy = b.pos.y - a.pos.y;

    int dist = Math.max(Math.abs(dx), Math.abs(dy));

    if (dx == 0 && dy == 0) //Same position, invalid.
    {
      return null;
    }

    if (dx == 0)
    {
      if (dy > 0)
      {
        d = Direction.DOWN;
      }
      else {
        d = Direction.UP;
      }
    }
    else if (dy == 0)
    {
      if (dx > 0)
      {
        d = Direction.RIGHT;
      }
      else {
        d = Direction.LEFT;
      }
    }
    else if (dx - dy == 0 || dx + dy == 0) //Crude version of absolute value equality, means diagonal in this case.
    {
      if (dy > 0) //Going up
      {
        if (dx > 0)
        {
          d = Direction.DOWNRIGHT;
        }
        else {
          d = Direction.DOWNLEFT;
        }
      }
      else {
        if (dx > 0) {
          d = Direction.UPRIGHT;
        } else {
          d = Direction.UPLEFT;
        }
      }
    }

    if (canPath(a, b, d))
    {
      return d;
    }

    return null;
  }

  private boolean canPath(Node start, Node end, Direction d)
  {
    if (d == Direction.NONE)
    {
      return false;
    }

    int x = start.pos.x;
    int y = start.pos.y;

    while(x >= 0 && x < 8 && y >= 0 && y < 8)
    {
      x += d.xIncrement;
      y += d.yIncrement;

      if (x == end.pos.x && y == end.pos.y)
      {
        return true;
      }
      else if (getNode(x, y).color != COLOR_NONE)
      {
        return false;
      }
    }

    return false;
  }


  public double evaluate(int color)
  {
    if (hasWin(1 - color))
    {
      return -1 * WIN_SCORE;
    }
    else if (hasWin(color))
    {
      return WIN_SCORE;
    }

    return networkScore(color) - networkScore(1 - color);
  }

  private double networkScore(int color)
  {
    ArrayList<Tree<Node>> networks = findNetworks(color);
    int maxDepth = 0;
    int maxWidth = 0;

    for(Tree<Node> network : networks)
    {
      maxDepth = Math.max(maxDepth, network.depth());
      maxWidth = Math.max(maxWidth, network.width());
    }

    return 20.0 * maxDepth + 10.0 * maxWidth;
  }

  @Override
  public String toString()
  {
    String s = "";
    for (int i = 0; i < 8; i++)
    {
      for (int j = 0; j < 8; j++)
      {
        s += getNode(j, i).color;
      }

      s += "\r\n";
    }

    return s;
  }
}
