package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class Minimax implements MoveStrategy{

    private final BoardEvaluator boardEvaluator;

    public Minimax() {
        this.boardEvaluator = null;
    }

    @Override
    public String toString(){
        return "Minimax";
    }

    @Override
    public Move execute(Board board, int depth) {
        return null;
    }


}
