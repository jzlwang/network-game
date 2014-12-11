package player;

import list.*;

public class Evaluator
{
	/**
	* @return the BestMove that the player can make
	* @arg color the color of the player
	* @arg turn the player's turn
	* @arg board the board so far
	* @arg alpha best possible score
	* @arg beta worst possible score
	* @arg depth the level of the search
	**/
	public static BestMove findBestMove(int color, int turn, Board board , double alpha, double beta, int depth)
	{
        boolean player = (color == turn);

		BestMove bestMove = new BestMove();
		BestMove reply;
		
		ArrayList<Move> validMoves = board.findValidMoves(turn);

        if (depth == 0 || board.hasWin())
        {
        	bestMove.move = validMoves.Get(0);
            bestMove.score = Math.pow(depth + 1, 2) * board.evaluate(color);
            return bestMove;
        }
		
		if (player) //We are the player
		{
			bestMove.score = alpha;
		}
		else //Not the player is the opponent
		{
			bestMove.score = beta;
		}
		
		bestMove.move = validMoves.Get(0);
		
		for (Move m : validMoves)
		{
			board.makeMove(m, turn);
		    reply = findBestMove(color, 1 - turn, board, alpha, beta, depth - 1);
            board.undoMove(m, turn);
			
			if (player && reply.score > bestMove.score)
			{
				bestMove.move = m;
				bestMove.score = reply.score;
				alpha = reply.score;
			}
			else if (!player && reply.score < bestMove.score)
			{
				bestMove.move = m;
				bestMove.score = reply.score;
				beta = reply.score;
			}
			
			if (alpha >= beta)
			{
				return bestMove;
			}
		}

		return bestMove;
	}
}