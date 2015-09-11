package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.LinkedList;

import org.junit.Test;

import board.ChessBoard;
import board.Move;
import board.MoveGenerator;

public class MoveGeneratorTest {
	ChessBoard startingBoard;
	MoveGenerator mg;

	@Test
	public void testMoveGenerator() {
		try {
			MoveGenerator mg1 = new MoveGenerator(new board.ChessBoard(
					ChessBoard.WHITE_START_BOARD));
			LinkedList<Move> mm = mg1.getWhitePieceMoves();
			assertEquals(20, mm.size());

		} catch (ParseException ex) {
			fail("could not create chessboard: " + ex.getMessage());
		}

	}

	@Test
	public void testCheckMoveGeneration() {
		try {
			startingBoard = new ChessBoard("K7/8/1r6/8/8/8/8/8 b - - 0 0");
		} catch (ParseException e) {
			fail("could not create chessboard: " + e.getMessage());
		}
		mg = new MoveGenerator(startingBoard);
		LinkedList<Move> blackMoves = mg.getBlackPieceMoves();
		// there should be 14 rook moves
		assertEquals(14, blackMoves.size());
		// there should be 2 check moves, and no checkmate moves
		int checkMoves = 0;
		int checkMateMoves = 0;
		for (Move m : blackMoves) {
			if (m.isCheck())
				checkMoves++;
			if (m.isCheckMating())
				checkMateMoves++;
		}
		// the movegenerator does not add check or checkmate flags, so they
		// should be 0
		assertEquals(0, checkMoves);
		assertEquals(0, checkMateMoves);

	}

	@Test
	public void testIfMovesAreValid() {
		try {
			startingBoard = new ChessBoard(ChessBoard.WHITE_START_BOARD);
		} catch (ParseException e) {
			fail("could not create board " + e.getMessage());
		}
		mg = new MoveGenerator(startingBoard);
		LinkedList<Move> blackMoves = mg.getBlackPieceMoves();
		LinkedList<Move> whiteMoves = mg.getWhitePieceMoves();

		for (Move m : blackMoves) {
			assertTrue(Move.isInternallyValid(m));
		}
		for (Move m : whiteMoves) {
			assertTrue(Move.isInternallyValid(m));
		}
	}

	@Test
	public void recursiveTestOfMoves() {
		try {
			startingBoard = new ChessBoard(ChessBoard.WHITE_START_BOARD);
		} catch (ParseException e) {
			fail("could not create board " + e.getMessage());
		}

		mg = new MoveGenerator(startingBoard);
		LinkedList<Move> blackMoves = mg.getBlackPieceMoves();
		LinkedList<Move> whiteMoves = mg.getWhitePieceMoves();

		for (Move m : blackMoves) {
			assertTrue(Move.isInternallyValid(m));

		}
		for (Move m : whiteMoves) {
			assertTrue(Move.isInternallyValid(m));
		}
		int maxRecursions = 2;
		testGenerateMovesFromList(whiteMoves, 0, maxRecursions, startingBoard);
		// set player turn to black before performing black players moves
		startingBoard.setWhitesTurn(false);
		testGenerateMovesFromList(blackMoves, 0, maxRecursions, startingBoard);

	}

	private void testGenerateMovesFromList(LinkedList<Move> moves,
			int recursionLevel, int maxRecursions, ChessBoard startingBoard) {
		if (recursionLevel > maxRecursions)
			return;

		for (Move m : moves) {
			// perform move on board
			ChessBoard newBoard = startingBoard.performMove(m);
			// generate new moves from resulting board
			if (newBoard != null) {
				MoveGenerator mg = new MoveGenerator(newBoard);
				LinkedList<Move> generatedBlackMoves = mg.getBlackPieceMoves();
				LinkedList<Move> generatedWhiteMoves = mg.getWhitePieceMoves();

				for (Move bm : generatedBlackMoves) {
					assertTrue(Move.isInternallyValid(bm));

				}
				for (Move wm : generatedWhiteMoves) {
					assertTrue("invalid move: " + wm.toDebugString(),
							Move.isInternallyValid(wm));
				}

				testGenerateMovesFromList(generatedBlackMoves,
						recursionLevel + 1, maxRecursions, newBoard);
				testGenerateMovesFromList(generatedWhiteMoves,
						recursionLevel + 1, maxRecursions, newBoard);
			}

		}
	}

	@Test
	public void testKingMoveGenerator() {
		try {
			startingBoard = new ChessBoard("4k3/8/8/8/8/8/8/8 b - - 0 0");
		} catch (ParseException e) {
			fail("Could not create board " + e.getMessage());
		}
		mg = new MoveGenerator(startingBoard);
		LinkedList<Move> kingMoves = mg.getBlackPieceMoves();
		for (Move m : kingMoves) {
			assertTrue(Move.isInternallyValid(m));
		}

	}

}