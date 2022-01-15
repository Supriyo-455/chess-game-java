package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class Minimax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public Minimax(final int searchDepth) {
        this.searchDepth = searchDepth;
        this.boardEvaluator = new StandardBoardEvaluator();
    }

    @Override
    public String toString(){
        return "Minimax";
    }

    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        System.out.println(board.currentPlayer()+" thinking with depth: "+this.searchDepth);

        int numMoves = board.currentPlayer().getLegalMoves().size();

        for (final Move move: board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                if(board.currentPlayer().getAlliance().isWhite()){
                   currentValue = min(moveTransition.getTransitionBoard(), this.searchDepth-1);
                   if(currentValue >= highestSeenValue){
                       highestSeenValue = currentValue;
                       bestMove = move;
                   }
                }else {
                    currentValue = max(moveTransition.getTransitionBoard(), this.searchDepth-1);
                    if(currentValue <= lowestSeenValue){
                        lowestSeenValue = currentValue;
                        bestMove = move;
                    }
                }
            }
        }

        final long executionTime = System.currentTimeMillis() - startTime;

        return bestMove;
    }

    private static boolean isEndGameScenario(final Board board) {
        return board.currentPlayer().isInCheckMate() || board.currentPlayer().isInStaleMate();
    }

    public int max(final Board board, final int depth){
       if(depth == 0 || isEndGameScenario(board)){
           return this.boardEvaluator.evaluate(board, depth);
       }
       int highestSeenValue = Integer.MIN_VALUE;
       for(final Move move: board.currentPlayer().getLegalMoves()){
           final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
           if(moveTransition.getMoveStatus().isDone()){
               final int currentValue = min(moveTransition.getTransitionBoard(), depth-1);
               if(currentValue >= highestSeenValue){
                   highestSeenValue = currentValue;
               }
           }
       }
       return highestSeenValue;
    }

    public int min(final Board board, final int depth){
        if(depth == 0 || isEndGameScenario(board)){
            return this.boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move: board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionBoard(), depth-1);
                if(currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }
}
