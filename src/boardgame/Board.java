package boardgame;

import boardgame.exceptions.BoardException;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Erro ao criar tabuleiro, são necessarias ao menos 1 coluna e 1 linha");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];

	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int colunm) {
		if (!positionExists(row, colunm)) {
			throw new BoardException("Posição nao localizada no tabuleiro");
		}
		return pieces[row][colunm];
	}

	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Posição nao localizada no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça nesta posicao " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	public Boolean positionExists(int row, int column) {
		if (row >= 0 && row < rows && column >= 0 && column < columns) {
			return true;
		}
		return false;
	}

	public Boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Posição nao localizada no tabuleiro");
		}
		return piece(position) != null;
	}
}
