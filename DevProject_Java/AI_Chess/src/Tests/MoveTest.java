package Tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import board.ChessBoard;
import board.Move;
import board.MoveBuilder;
import board.MoveGenerator;
import board.Piece;

public class MoveTest {
	private ChessBoard startingBoard;
	MoveBuilder mb;

	@Before
	public void setUp() throws Exception {
		startingBoard = new ChessBoard(ChessBoard.WHITE_START_BOARD);
		mb = new MoveBuilder();
	}

	@Test
	public void testCreateMoveFromSANString() {
		MoveGenerator mg = new MoveGenerator(startingBoard);
		LinkedList<Move> moves = mg.getWhitePieceMoves();
		
		for (Move m: moves){
			// parse move to string SAN, loosing much of the information contained in it.
			String moveStringOrig = m.toString();
			// parse it back to a move object.
			Move parsedMove = startingBoard.createMoveFromSANString(moveStringOrig);
			// reparse to string second time.
			String moveStringNew = parsedMove.toString();
			// compare the two move strings.
			assertEquals(moveStringOrig, moveStringNew);
		}
	}
	
	@Test
	public void testMove(){
		Piece capturedPiece = Piece.BLACK_QUEEN;
		int oldPosition = 0x45;
		int newPosition = 0x26;
		Piece movingPiece = Piece.WHITE_KNIGHT;
		boolean isCapture = true;
		boolean isCheck = true;
		boolean isCheckMating = true;
		boolean isKingSideCastle = true;
		boolean isQueenSideCastle = true;
		boolean isPawnPromotion = true;
		Move m = mb.capturedPiece(capturedPiece)
				.isCapture(isCapture)
				.isCheck(isCheck)
				.isCheckMating(isCheckMating)
				.isKingSideCastle(isKingSideCastle)
				.isQueenSideCastle(isQueenSideCastle)
				.isPawnPromotion(isPawnPromotion)
		.buildMove(movingPiece, oldPosition, newPosition);
		assertEquals(capturedPiece, m.getCapturedPiece());
		assertEquals(oldPosition, m.getOldPosition());
		assertEquals(newPosition, m.getNewPosition());
		assertEquals(movingPiece, m.getMovingPiece());
		assertEquals(isCapture, m.isCapture());
		assertEquals(isCheck, m.isCheck());
		assertEquals(isCheckMating, m.isCheckMating());
		assertEquals(isKingSideCastle, m.isKingSideCastle());
		assertEquals(isQueenSideCastle, m.isQueenSideCastle());
		assertEquals(isPawnPromotion, m.isPawnPromotion());
	}
	
	@Test
	public void testEmptyMove(){
		Piece movingPiece = Piece.WHITE_KNIGHT;
		Move m = mb.buildMove(movingPiece, Move.UNKNOWN_POSITION, Move.UNKNOWN_POSITION);
		assertEquals(movingPiece, m.getMovingPiece());
		assertEquals(Move.UNKNOWN_POSITION, m.getOldFile());
		assertEquals(Move.UNKNOWN_POSITION, m.getOldRank());
		assertEquals(Move.UNKNOWN_POSITION, m.getOldRank());
	}
	
	@Test
	public void testFileAndRankCombination(){
		for (int rank = 0x00; rank < 0x80; rank = rank + 0x10){
			for (int file = 0x00; file < 0x08; file++){
				mb = new MoveBuilder();
				Move m = mb.oldFile(file).oldRank(rank).buildMove(Piece.BLACK_KING, Move.UNKNOWN_POSITION, Move.UNKNOWN_POSITION);
				assertEquals(file, m.getOldFile());
				assertEquals(rank, m.getOldRank());
				assertEquals((file | rank), m.getOldPosition());
			}
		}
		
	}

}
