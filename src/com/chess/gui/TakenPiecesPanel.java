package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.chess.gui.Table.defaultPieceImagePath;

public class TakenPiecesPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);

    public TakenPiecesPanel(){
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog){
        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for(final Move move : moveLog.getMoves()){
            if(move.isAttack()){
                final Piece takenPiece = move.getAttackPiece();
                if(takenPiece.getPieceAlliance().isWhite()){
                    whiteTakenPieces.add(takenPiece);
                }else if(takenPiece.getPieceAlliance().isBlack()){
                    blackTakenPieces.add(takenPiece);
                }else {
                    throw new RuntimeException("Should not reach here");
                }
            }
        }

        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(final Piece o1, final Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(final Piece o1, final Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });


        printTakenPieces(whiteTakenPieces);

        printTakenPieces(blackTakenPieces);

        validate();
    }

    private void printTakenPieces(List<Piece> pieces) {
        for(final Piece takenPiece: pieces){
            try{
                final BufferedImage image = ImageIO.read(
                        new File(
                                defaultPieceImagePath +
                                        takenPiece.getPieceAlliance().toString().charAt(0) +
                                        takenPiece.toString() +
                                        ".gif"
                        )   //For example pieceIconPath+"W"+"B"+".gif"
                );
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(
                        new ImageIcon(
                                icon.getImage().getScaledInstance(
                                        icon.getIconWidth() - 10,
                                        icon.getIconHeight() - 10,
                                        Image.SCALE_SMOOTH
                                )
                        )
                );
                this.southPanel.add(imageLabel);
            }catch (final IOException e){
                e.printStackTrace();
            }
        }
    }
}
