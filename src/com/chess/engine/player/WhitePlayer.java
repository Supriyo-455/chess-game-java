package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentLegalMoves) {
        super(board, legalMoves, opponentLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            //King-side castle
            if(!this.board.getTile(61).isTileOccupied() &&
                    !this.board.getTile(62).isTileOccupied()
            ){
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttackOnTile(61, opponentLegals).isEmpty() &&
                        Player.calculateAttackOnTile(62, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()
                    ){
                        //TODO: ADD A CASTLEMOVE
                        kingCastles.add(null);
                    }
                }
            }

            //queen-side castle
            if(!this.board.getTile(59).isTileOccupied() &&
                    !this.board.getTile(58).isTileOccupied() &&
                    !this.board.getTile(57).isTileOccupied()
            ) {
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttackOnTile(59, opponentLegals).isEmpty() &&
                            Player.calculateAttackOnTile(58, opponentLegals).isEmpty() &&
                            Player.calculateAttackOnTile(57, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()
                    ){
                        //TODO: ADD A CASTLEMOVE
                        kingCastles.add(null);
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
