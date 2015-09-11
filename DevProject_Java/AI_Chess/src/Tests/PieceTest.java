package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import board.Piece;
import board.PieceType;

public class PieceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsBlack() {
		assertTrue(Piece.BLACK_PAWN.isBlack());
		assertTrue(Piece.BLACK_BISHOP.isBlack());
		assertTrue(Piece.BLACK_KNIGHT.isBlack());
		assertTrue(Piece.BLACK_ROOK.isBlack());
		assertTrue(Piece.BLACK_QUEEN.isBlack());
		assertTrue(Piece.BLACK_KING.isBlack());
		
	}

	@Test
	public void testGetType() {
		assertEquals(Piece.BLACK_PAWN.getType(), PieceType.PAWN);
		assertEquals(Piece.WHITE_PAWN.getType(), PieceType.PAWN);
		
		assertEquals(Piece.BLACK_BISHOP.getType(), PieceType.BISHOP);
		assertEquals(Piece.WHITE_BISHOP.getType(), PieceType.BISHOP);
		
		assertEquals(Piece.BLACK_KNIGHT.getType(), PieceType.KNIGHT);
		assertEquals(Piece.WHITE_KNIGHT.getType(), PieceType.KNIGHT);
		
		assertEquals(Piece.BLACK_ROOK.getType(), PieceType.ROOK);
		assertEquals(Piece.WHITE_ROOK.getType(), PieceType.ROOK);
		
		assertEquals(Piece.BLACK_QUEEN.getType(), PieceType.QUEEN);
		assertEquals(Piece.WHITE_QUEEN.getType(), PieceType.QUEEN);
		
		assertEquals(Piece.BLACK_KING.getType(), PieceType.KING);
		assertEquals(Piece.WHITE_KING.getType(), PieceType.KING);
		
		
	}
	
	@Test
	public void testChangePieceColor(){
		boolean changedToWhite = true;
		for (Piece p: Piece.values()){
			Piece changedPiece = Piece.changePieceColor(p, changedToWhite);
			assertFalse(changedPiece.isBlack());
			changedPiece = Piece.changePieceColor(changedPiece, !changedToWhite);
			assertTrue(changedPiece.isBlack());
		}
	}
	
	@Test
	public void testChangePieceTypeColor(){
		boolean changedToWhite = true;
		for (PieceType pt: PieceType.values()){
			Piece newPiece = Piece.changePieceColor(pt, changedToWhite);
			assertFalse(newPiece.isBlack());
			newPiece = Piece.changePieceColor(newPiece, !changedToWhite);
			assertTrue(newPiece.isBlack());
		}
	}

}
