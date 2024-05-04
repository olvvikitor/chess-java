package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exceptions.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color courrentPlayer;
	
	private Board board;
	
	private List<Piece>piecesOnTheBoard = new ArrayList<>();
	private List<Piece>capturedpieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		courrentPlayer = Color.WHITE;	
		 initialSetup();
	}
	
	
	public int getTurn() {
		return turn;
	}



	public Color getCourrentPlayer() {
		return courrentPlayer;
	}
	
	

	public boolean[][]possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peca escolhida nao pode ser movida para a posicao de destino");
		}
		if(board.piece(source).possibleMove(target)) {
			System.out.println("Success");
		}
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedpieces.add(capturedPiece);
			
		}
		return capturedPiece;
	}

	private void validateSourcePosition(Position source) {
		if(!board.thereIsAPiece(source)) {
			throw new ChessException("Não existpeça na posição de origem");
		}
		if(courrentPlayer != ((ChessPiece)board.piece(source)).getColor()) {
			throw new ChessException("A peca escolhida nao eh sua");
		}
		if(!board.piece(source).isThereAnyPossibleMove()) {
			throw new ChessException("Nao existe movimeentos disponiveis para a peca "+ board.piece(source).toString());
		}
	}
	private void nextTurn() {
		turn++;
		courrentPlayer = (courrentPlayer == Color.WHITE)? Color.BLACK : Color.WHITE;
	}

	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for( int j = 0; j < board.getColumns();j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	
	private void initialSetup() {
	    // Colocar 6 torres negras en posiciones únicas
	    placeNewPiece('a', 1, new Rook(board, Color.BLACK));
	    placeNewPiece('b', 1, new Rook(board, Color.BLACK));
	    placeNewPiece('c', 1, new Rook(board, Color.BLACK));
	    placeNewPiece('d', 1, new Rook(board, Color.BLACK));
	    placeNewPiece('e', 1, new Rook(board, Color.BLACK));
	    placeNewPiece('f', 1, new Rook(board, Color.BLACK));

	    // Colocar 6 torres blancas en posiciones únicas
	    placeNewPiece('a', 8, new Rook(board, Color.WHITE));
	    placeNewPiece('b', 8, new Rook(board, Color.WHITE));
	    placeNewPiece('c', 8, new Rook(board, Color.WHITE));
	    placeNewPiece('d', 8, new Rook(board, Color.WHITE));
	    placeNewPiece('e', 8, new Rook(board, Color.WHITE));
	    placeNewPiece('f', 8, new Rook(board, Color.WHITE));

	    // Colocar 2 reyes
	    placeNewPiece('e', 2, new King(board, Color.BLACK));
	    placeNewPiece('e', 7, new King(board, Color.WHITE));
	}

}
