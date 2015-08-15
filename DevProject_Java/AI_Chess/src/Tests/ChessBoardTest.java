package Tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import board.ChessBoard;
import board.Move;
import board.MoveBuilder;
import board.MoveGenerator;
import board.Piece;

public class ChessBoardTest {

	ChessBoard startingBoard;
	Path filePath;

	@Before
	public void setUp() throws Exception {
		filePath = Paths.get("chessboard_file.fen");
		try {
			startingBoard = new ChessBoard(ChessBoard.WHITE_START_BOARD);
		} catch (ParseException e) {
			fail("could not create chessboard");
		}
		;
	}

	@Test
	public void testFENConstructorAndFileWriter() {
		testCreationOfValidBoards();
		testCreationOfInvalidBoards();

	}

	private void testCreationOfInvalidBoards() {
		String tooManyPiecesString = "PPPPPPPPNK/8/8/8/8/8/8/8 b - - 0 0";
		String tooFewPiecesString = "p/8/8/8/8/8/8/NNNNPPPP w - - 0 0";
		String extraParametersString = ChessBoard.WHITE_START_BOARD
				+ "extra_stuff";
		String wrongParameterString = "/8/8/8/8/8/8/8/8 q";

		try {
			new ChessBoard(tooManyPiecesString);
			fail("ChessBoard with too many pieces was created");
		} catch (ParseException e) {
			// do nothing, we expected an exception
		}

		try {
			new ChessBoard(tooFewPiecesString);
			fail("ChessBoard with too few pieces was created witthout exception");

		} catch (ParseException e) {
			// do nothing, we expected an exception
		}
		try {
			new ChessBoard(
					extraParametersString);
			fail("ChessBoard with too many parameters was created without exception");

		} catch (ParseException e) {
			// do nothing, we expected an exception
		}
		try {
			new ChessBoard(
					wrongParameterString);
			fail("ChessBoard with wrong parameters was created without exception");
		} catch (ParseException e) {
			// do nothing, we expected an exception
		}

	}

	private void testCreationOfValidBoards() {
		String[] boardStrings = {
				"PPRkPnBP/PqP3PP/4kKkK/2Rb2QN/8/8/7p/5bbb w KQ - 0 0",
				"2RkPnB1/PqP3BP/4kq2/2Rb2QN/8/5nbK/7p/4bnb1 w Qkq - 0 10",
				"PPRkPnqP/PqPnnnPP/kbnrkKRK/2Rb2QN/4R3/8/7p/5ppp b - - 0 3" };
		for (int i = 0; i < boardStrings.length; i++) {
			// attempt to write string to file and the read back each
			// boardstring
			System.out.println("starting board:");
			System.out.println(boardStrings[i]);
			ChessBoard boardFromString = null;
			try {
				boardFromString = new ChessBoard(boardStrings[i]);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Path testFilePath = Paths.get("test_board_file_" + i + ".fen");
			try {
				if (Files.exists(testFilePath, LinkOption.NOFOLLOW_LINKS)) {
					// file already exist, so try to delete it
					Files.delete(testFilePath);
				}
				boardFromString.writeToFile(testFilePath, true);
				// now read file back and compare to original
				String boardStringFromFile = ChessBoard
						.getFENFormattedStringFromFile(testFilePath);
				ChessBoard boardFromFile = null;
				try {
					boardFromFile = new ChessBoard(boardStringFromFile);
				} catch (ParseException e) {
					e.printStackTrace();
					fail("Could not create board from string from file: "
							+ e.getMessage());

				}
				System.out.println("board from file:");
				System.out.println(boardFromFile);
				assertEquals(boardFromString.getFENFormattedStringFromBoard(),
						boardFromFile.getFENFormattedStringFromBoard());

			} catch (IOException e) {
				e.printStackTrace();
				fail();
			} finally {
				// clean up
				try {
					Files.delete(testFilePath);
				} catch (IOException e) {
					System.out
							.println("test might not have cleanud up properly. Check for lingering test files in project");
				}
			}

		}
	}

	@Test
	public void testSimplePerformMove() {
		testValidPawnMove();
		testInvalidPawnMove();
		testCastlingChecks();
	}

	@Test
	public void testMoveGeneratorAndPerformMove() {
		try {
			startingBoard = new ChessBoard(
					"k3b2r/1q6/3n4/1p1p1p1p/P1P1P1P1/2N5/6Q1/R2B3K w - - 0 0");
		} catch (ParseException e1) {
			fail("could not create chessboard: " + e1.getMessage());
		}
		MoveGenerator mg = new MoveGenerator(startingBoard);
		LinkedList<Move> blackMoves = mg.getBlackPieceMoves();
		LinkedList<Move> whiteMoves = mg.getWhitePieceMoves();
		LinkedList<Move> reParsedWhiteMoves = new LinkedList<Move>();
		LinkedList<Move> reParsedBlackMoves = new LinkedList<Move>();

		reParsedWhiteMoves = reParseMoveList(whiteMoves, true, startingBoard);
		startingBoard.setWhitesTurn(false);
		reParsedBlackMoves = reParseMoveList(blackMoves, false, startingBoard);

		for (Move m : blackMoves) {
			System.out.println("trying move: " + m);
			ChessBoard newBoard;
			newBoard = startingBoard.performMove(m);
			assertNotNull(newBoard);
			assertNotSame(startingBoard, newBoard);
			assertNotSame(startingBoard.getAllPieces(), newBoard.getAllPieces());
			assertNotSame(startingBoard.getBlackPlayersPieces(),
					newBoard.getBlackPlayersPieces());
			assertNotSame(startingBoard.getWhitePlayersPieces(),
					newBoard.getWhitePlayersPieces());
			Assert.assertThat(startingBoard.getAllPieces(),
					IsNot.not(IsEqual.equalTo(newBoard.getAllPieces())));
			Assert.assertThat(startingBoard.getBlackPlayersPieces(), IsNot
					.not(IsEqual.equalTo(newBoard.getBlackPlayersPieces())));
			// if move is not a capture, the white pieces should not change
			if (m.isCapture()) {
				Assert.assertThat(startingBoard.getWhitePlayersPieces(), IsNot
						.not(IsEqual.equalTo(newBoard.getWhitePlayersPieces())));
			} else {
				assertArrayEquals(startingBoard.getWhitePlayersPieces(),
						newBoard.getWhitePlayersPieces());
			}
		}
		for (Move m : reParsedBlackMoves) {
			System.out.println("trying move: " + m);
			ChessBoard newBoard;
			newBoard = startingBoard.performMove(m);
			assertNotNull(newBoard);
			assertNotSame(startingBoard, newBoard);
			assertNotSame(startingBoard.getAllPieces(), newBoard.getAllPieces());
			assertNotSame(startingBoard.getBlackPlayersPieces(),
					newBoard.getBlackPlayersPieces());
			assertNotSame(startingBoard.getWhitePlayersPieces(),
					newBoard.getWhitePlayersPieces());
			// TODO test fails here, because the parsed king move does not tell
			// us where the king came from.
			Assert.assertThat(startingBoard.getAllPieces(),
					IsNot.not(IsEqual.equalTo(newBoard.getAllPieces())));
			Assert.assertThat(startingBoard.getBlackPlayersPieces(), IsNot
					.not(IsEqual.equalTo(newBoard.getBlackPlayersPieces())));
			if (m.isCapture()) {
				Assert.assertThat(startingBoard.getWhitePlayersPieces(), IsNot
						.not(IsEqual.equalTo(newBoard.getWhitePlayersPieces())));
			} else {
				assertArrayEquals(startingBoard.getWhitePlayersPieces(),
						newBoard.getWhitePlayersPieces());
			}

		}
		startingBoard.setWhitesTurn(true);
		for (Move m : whiteMoves) {
			System.out.println("trying move: " + m);
			ChessBoard newBoard = startingBoard.performMove(m);
			assertNotNull(newBoard);
			assertNotSame(startingBoard, newBoard);
			assertNotSame(startingBoard.getAllPieces(), newBoard.getAllPieces());
			assertNotSame(startingBoard.getBlackPlayersPieces(),
					newBoard.getBlackPlayersPieces());
			assertNotSame(startingBoard.getWhitePlayersPieces(),
					newBoard.getWhitePlayersPieces());
			Assert.assertThat(startingBoard.getAllPieces(),
					IsNot.not(IsEqual.equalTo(newBoard.getAllPieces())));
			Assert.assertThat(startingBoard.getWhitePlayersPieces(), IsNot
					.not(IsEqual.equalTo(newBoard.getWhitePlayersPieces())));
			if (m.isCapture()) {
				Assert.assertThat(startingBoard.getBlackPlayersPieces(), IsNot
						.not(IsEqual.equalTo(newBoard.getBlackPlayersPieces())));
			} else {
				assertArrayEquals(startingBoard.getBlackPlayersPieces(),
						newBoard.getBlackPlayersPieces());
			}

		}
		for (Move m : reParsedWhiteMoves) {
			System.out.println("trying move: " + m);
			ChessBoard newBoard = startingBoard.performMove(m);
			assertNotNull(newBoard);
			assertNotSame(startingBoard, newBoard);
			assertNotSame(startingBoard.getAllPieces(), newBoard.getAllPieces());
			assertNotSame(startingBoard.getBlackPlayersPieces(),
					newBoard.getBlackPlayersPieces());
			assertNotSame(startingBoard.getWhitePlayersPieces(),
					newBoard.getWhitePlayersPieces());
			Assert.assertThat(startingBoard.getAllPieces(),
					IsNot.not(IsEqual.equalTo(newBoard.getAllPieces())));
			Assert.assertThat(startingBoard.getWhitePlayersPieces(), IsNot
					.not(IsEqual.equalTo(newBoard.getWhitePlayersPieces())));
			if (m.isCapture()) {
				Assert.assertThat(startingBoard.getBlackPlayersPieces(), IsNot
						.not(IsEqual.equalTo(newBoard.getBlackPlayersPieces())));
			} else {
				assertArrayEquals(startingBoard.getBlackPlayersPieces(),
						newBoard.getBlackPlayersPieces());
			}
		}
	}

	private LinkedList<Move> reParseMoveList(LinkedList<Move> moves,
			boolean isWhiteMoves, ChessBoard boardToParseWith) {
		LinkedList<Move> reParsedList = new LinkedList<Move>();
		for (Move m : moves) {
			String moveString = m.toString();
			reParsedList.add(boardToParseWith
					.createMoveFromSANString(moveString));
		}
		return reParsedList;
	}

	private void testCastlingChecks() {
		// should test whether the board correctly sets the castling flags after
		// moves that should change them.
		Move moveBottomLeftRook = new MoveBuilder().buildMove(Piece.WHITE_ROOK,
				0x00, 0x30);
		assertTrue(startingBoard.isWhiteCastleQueenSideLegal());
		ChessBoard bottomLeftRookBoard;
		bottomLeftRookBoard = startingBoard.performMove(moveBottomLeftRook);

		assertTrue(startingBoard.isWhiteCastleQueenSideLegal());

		assertNotNull(bottomLeftRookBoard);
		// castling on queen side should now be illegal for white
		assertFalse(bottomLeftRookBoard.isWhiteCastleQueenSideLegal());

		// same test, but for kingside castling for white
		Move moveBottomRightRook = new MoveBuilder().buildMove(
				Piece.WHITE_ROOK, 0x07, 0x06);
		assertTrue(startingBoard.isWhiteCastleKingSideLegal());
		ChessBoard bottomRightRookBoard = startingBoard
				.performMove(moveBottomRightRook);
		assertNotNull(bottomRightRookBoard);
		assertTrue(startingBoard.isWhiteCastleKingSideLegal());

		assertFalse(bottomRightRookBoard.isWhiteCastleKingSideLegal());

		// testing for queenside castling for black
		// Move moveTopLeftRook = new MoveBuilder().buildMove(Piece.BLACK_ROOK,
		// 0x70, 0x50);
		// assertTrue(startingBoard.isBlackCastleQueenSideLegal());
		// ChessBoard top
		// TODO finish tests
	}

	private void testInvalidPawnMove() {
		// test invalid move, meaning one or more positions are outside board
		Move invalidMove = new MoveBuilder().buildMove(Piece.BLACK_KING,
				0x85 /* wrong index */, 0x74);
		ChessBoard invalidMoveBoard = null;
		ChessBoard copyOfStartingBoard = new ChessBoard(startingBoard);
			invalidMoveBoard = startingBoard.performMove(invalidMove);
		assertNull(invalidMoveBoard);
		// make sure that the original starting board was not changed
		assertArrayEquals(copyOfStartingBoard.getAllPieces(),
				startingBoard.getAllPieces());
		assertArrayEquals(copyOfStartingBoard.getWhitePlayersPieces(),
				startingBoard.getWhitePlayersPieces());
		assertArrayEquals(copyOfStartingBoard.getBlackPlayersPieces(),
				startingBoard.getBlackPlayersPieces());
	}

	private void testValidPawnMove() {
		// testing a move of a black pawn
		int oldPawnPosition = 0x61;
		int newPawnPosition = 0x41;
		Piece movingPiece = startingBoard.getBlackPlayersPieces()[oldPawnPosition];
		Move validStartingMove = new MoveBuilder().buildMove(movingPiece,
				oldPawnPosition, newPawnPosition);
		ChessBoard newBoard;
		newBoard = startingBoard.performMove(validStartingMove);

		// new board should be created
		assertNotNull(newBoard);
		// check that the reference has not just been copied
		assertFalse(startingBoard == newBoard);
		// board should be changed
		assertNotSame(startingBoard, newBoard);
		// check that the pawn has not moved in the starting board
		assertEquals(movingPiece, startingBoard.getAllPieces()[oldPawnPosition]);
		assertEquals(movingPiece,
				startingBoard.getBlackPlayersPieces()[oldPawnPosition]);
		assertNull(startingBoard.getAllPieces()[newPawnPosition]);
		assertNull(startingBoard.getBlackPlayersPieces()[newPawnPosition]);
		// the pawn should have moved in the new board
		assertEquals(movingPiece, newBoard.getAllPieces()[newPawnPosition]);
		assertEquals(movingPiece,
				newBoard.getBlackPlayersPieces()[newPawnPosition]);
		assertNull(newBoard.getAllPieces()[oldPawnPosition]);
		assertNull(newBoard.getBlackPlayersPieces()[oldPawnPosition]);

	}

	@Test
	public void testGetFileAndRankString() {
		// iterate over all valid indexes and check that they are converted
		// properly
		// first create expected array
		ArrayList<String> expectedIndexes = new ArrayList<String>();
		for (char rank = '1'; rank <= '9'; rank++) {
			for (char file = 'a'; file <= 'h'; file++) {
				expectedIndexes.add("" + file + rank);
			}
		}
		int expectedIndexesIndex = 0;
		String[] convertedIndexes = new String[ChessBoard.BOARD_SIZE];
		for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
			for (int file = 0x00; file < 0x08; file = file + 0x01) {
				int index = file | rank;
				convertedIndexes[index] = ChessBoard
						.getFileAndRankString(index);
				assertEquals(convertedIndexes[index],
						expectedIndexes.get(expectedIndexesIndex));
				expectedIndexesIndex++;
			}
		}
	}

	// @Test
	// public void testFlipBoard() {
	// try {
	// startingBoard = new ChessBoard(ChessBoard.WHITE_START_BOARD);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// fail("could not create starting chessboard");
	// }
	// System.out.println("Starting board");
	// System.out.println(startingBoard);
	//
	// ChessBoard flippedBoard = null;
	// try {
	// flippedBoard = startingBoard.flipBoard();
	// } catch (ParseException e) {
	// e.printStackTrace();
	// fail("could not flip chessboard");
	// }
	// if (flippedBoard == null) {
	// fail("Flipped board was null");
	// }
	// System.out.println("Flipped board");
	// System.out.println(flippedBoard);
	//
	// }

	@Test
	public void testQueenCheckMove() {
		// this is testing a situation that happend during a game, where the
		// queen was not able to move up and check the king
		String startString = "r1bqkb1r/ppp1pppp/8/3pN3/1P6/N7/PP1PPPPP/R1BQKB1R/ w KQkq - 0 5";
		try {
			startingBoard = new ChessBoard(startString);
		} catch (ParseException e) {
			fail("Could not create chessboard");
		}
		// create move from string, as done in game
		String queenCheckMoveString = "Qd1a4+";
		Move queenCheckMove = startingBoard
				.createMoveFromSANString(queenCheckMoveString);
		ChessBoard boardAfterMove;
		boardAfterMove = startingBoard.performMove(queenCheckMove);
		assertNotNull(boardAfterMove);
		assertTrue(boardAfterMove.isBlackInCheck());

	}

}
