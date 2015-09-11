package board;

import game.GameController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;

/**
 * Represents a traditional 8x8 chess board. This is also a node in alfa beta
 * algorithm tree.
 * 
 * @author Rasmus
 * 
 */
public class ChessBoard {

	// FIELDS
	/**
	 * Can be used to store the boards current static evaluation. Setting and
	 * using this is optional. The board cannot calculate its own static value.
	 */
	private Integer staticEvaluation = 0;
	/**
	 * Represents the actual fields of the board, containing all Pieces that are
	 * in play. The implementation is a 0x88 Hex-indexed array.
	 */
	private Piece[] allPieces;
	/**
	 * Contains only bottom players pieces. Used for easy retrieval, instead of
	 * doing checks on each piece somewhere
	 */
	private Piece[] whitePieces;
	/**
	 * Contains only top players pieces. Used for easy retrieval, instead of
	 * doing checks on each piece somewhere
	 */
	private Piece[] blackPieces;
	/**
	 * Indicates which players turn it is. True if it is white players turn,
	 * false if it is black players.
	 */
	private boolean isWhitesTurn;
	private boolean isWhiteInCheck = false;
	private boolean isBlackInCheck = false;
	/**
	 * True if the white player is allowed to castle kingside
	 */
	private boolean whiteCastleKingSideLegal = true;
	/**
	 * True if the white player is allowed to castle queenside
	 */
	private boolean whiteCastleQueenSideLegal = true;
	/**
	 * True if the black player is allowed to castle kingside
	 */
	private boolean blackCastleKingSideLegal = true;
	/**
	 * True if the black player is allowed to castle queenside
	 */
	private boolean blackCastleQueenSideLegal = true;
	/**
	 * Mask used for checking if a position is inside the board. If
	 * ((somePiece.getData() & INSIDE_BOARD_MASK) == 0), the position is valid
	 */
	private static final int INSIDE_BOARD_MASK = 0x88;
	public static final int BOARD_SIZE = 128;
	private Move lastMovePerformed = null;
	/**
	 * The number of full moves, meaning a white and black ply. this should be
	 * incremented after each black move
	 */
	private int numOfFullMoves;
	/**
	 * Extension used for files that save a boards current state.
	 */
	private static final String FILE_EXTENSION = ".fen";
	/**
	 * String that can be used to create a chessboard with all pieces in the
	 * correct starting position. Choose this string if the white pieces should
	 * be in the bottom of the board, meaning the 1st and 2nd rank.
	 */
	public static final String WHITE_START_BOARD = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0";
	/**
	 * Flag that indicates the game status. True if one of the players has won,
	 * otherwise false. The winning player is defined by {@link isWhiteWinner}.
	 */
	private boolean isGameFinished = false;
	/**
	 * Indicates who the winning player is, only if {@link isGameFinished} is
	 * true.
	 */
	private boolean isWhiteWinner;

	// CONSTRUCTORS
	/**
	 * Constructs a new ChessBoard from an existing state, designated by a
	 * String in FEN notation. To construct starting boards, use
	 * {@link ChessBoard#WHITE_START_BOARD} and
	 * {@link ChessBoard#BLACK_START_BOARD}
	 * 
	 * @param FENFormattedBoardString
	 *            The String that designates the new board state. Currently,
	 *            only pieces and the current turn is parsed, so a complete FEN
	 *            String will throw
	 * @throws ParseException
	 * @see ChessBoard#getFENFormattedStringFromFile(Path)
	 * @see https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
	 */
	public ChessBoard(String FENFormattedBoardString) throws ParseException {

		this.allPieces = new Piece[BOARD_SIZE];
		this.whitePieces = new Piece[BOARD_SIZE];
		this.blackPieces = new Piece[BOARD_SIZE];

		// split fen string in piece positions and all the rest
		String[] splitBoardString = FENFormattedBoardString.split(" ");
		// string should contain 6 parts: piece positions, players turn,
		// castling moves, en passant moves, halfmoves and fullmoves.
		if (splitBoardString.length != 6) {
			throw new ParseException(
					"Board string does not seem to contain all needed parts", 0);
		}
		// split file in each rank, seperated by a slash
		String[] ranks = splitBoardString[0].split("/");
		// we should have excactly 8 ranks
		if (ranks.length != 8) {
			throw new ParseException(
					"Piece position part does not seem to contain excactly 8 ranks",
					0);
		}
		// FEN notation starts from the 8th rank, so iterate backwards
		// but we also need an index for the string array
		int rankStringIndex = 0;
		for (int rank = 0x70; rank >= 0x00; rank = rank - 0x10) {

			// parse each file-string
			int fileIndex = 0x00;
			for (char c : ranks[rankStringIndex].toCharArray()) {
				// if digit, skip the amount of files the digit represents
				if (Character.isDigit(c)) {
					fileIndex += Character.digit(c, 10);
				} else {
					int parsedPieceindex = (rank | fileIndex);
					Piece parsedPiece = Piece.getPiece(c);
					allPieces[parsedPieceindex] = parsedPiece;
					if (Character.isUpperCase(c)) {
						whitePieces[parsedPieceindex] = parsedPiece;
					} else {
						blackPieces[parsedPieceindex] = parsedPiece;
					}
					// check that we have not parsed too many pieces from one
					// file
					if (fileIndex > 0x07) {
						throw new ParseException(
								"Could not parse board string. Too many pieces in a rank",
								rankStringIndex);
					}
					fileIndex++;
				}
			}
			if (fileIndex != 0x08) {
				throw new ParseException("Parsed too few pieces in a rank",
						rankStringIndex);
			}
			rankStringIndex++;

		}
		// parse whose turn it is
		String turnString = splitBoardString[1];
		if (turnString.toLowerCase().equals("w")) {
			this.isWhitesTurn = true;
		} else if (turnString.toLowerCase().equals("b")) {
			this.isWhitesTurn = false;
		} else {
			throw new ParseException("Could not parse whoose turn it is", 1);
		}
		// parse castling moves
		String castleString = splitBoardString[2];
		if (castleString.equals("-")) {
			this.whiteCastleKingSideLegal = false;
			this.whiteCastleQueenSideLegal = false;
			this.blackCastleKingSideLegal = false;
			this.blackCastleQueenSideLegal = false;
		} else {
			char[] castleChars = castleString.toCharArray();
			for (char c : castleChars) {
				switch (c) {
				case 'K':
					this.whiteCastleKingSideLegal = true;
					break;
				case 'Q':
					this.whiteCastleQueenSideLegal = true;
					break;
				case 'k':
					this.blackCastleKingSideLegal = true;
					break;
				case 'q':
					blackCastleQueenSideLegal = true;
					break;
				default:
					throw new ParseException("Could not parse castling moves",
							2);
				}
			}
		} // castling parsing
			// not parsing en passant moves, just skipping
			// also not parsing number of halfmoves, since we don't currently
			// handle the fifty move draw rule
			// parsing the number of full moves
		try {
			this.numOfFullMoves = Integer.parseInt(splitBoardString[5]);
		} catch (NumberFormatException e) {
			throw new ParseException(
					"could not parse the number of fullmoves: "
							+ e.getMessage(), 5);
		}

	}

	public ChessBoard(ChessBoard board) {
		this(
				board.allPieces, 
				board.blackPieces, 
				board.whitePieces,
				board.staticEvaluation, 
				board.isWhitesTurn,
				board.isWhiteInCheck,
				board.isBlackInCheck,
				board.whiteCastleKingSideLegal,
				board.whiteCastleQueenSideLegal,
				board.blackCastleKingSideLegal,
				board.blackCastleQueenSideLegal, 
				board.lastMovePerformed,
				board.numOfFullMoves,
				board.isGameFinished,
				board.isWhiteWinner);
	}

	/**
	 * 
	 * Private constructor that can be used get a new instance of a board, whith
	 * pieces in specific positions instead of the starting positions.
	 * 
	 * @param allPieces
	 *            Array containing all pieces on the board
	 * @param blackPieces
	 *            Array containing all black pieces on the board
	 * @param whitePieces
	 *            Array containing all white pieces on the board
	 * @param staticEvaluation
	 *            the boards current static evaluation. Set this to null, if no
	 *            evaluation should be stored in the board
	 * 
	 * @see ChessBoard#performMove()
	 */
	private ChessBoard(
			Piece[] allPieces, 
			Piece[] blackPieces,
			Piece[] whitePieces, 
			int staticEvaluation, 
			boolean isWhitesTurn,
			boolean isWhiteInCheck,
			boolean isBlackInCheck,
			boolean whiteCastleKingSideLegal,
			boolean whiteCastleQueenSideLegal,
			boolean blackCastleKingSideLegal,
			boolean blackCastleQueenSideLegal, 
			Move lastMovePerformed,
			int numOfFullMoves,
			boolean isGameFinished,
			boolean isWhiteWinner
			) {

		this.allPieces = allPieces.clone();
		this.blackPieces = blackPieces.clone();
		this.whitePieces = whitePieces.clone();
		this.staticEvaluation = staticEvaluation;
		this.isWhitesTurn = isWhitesTurn;
		this.isWhiteInCheck = isWhiteInCheck;
		this.isBlackInCheck = isBlackInCheck;
		this.isGameFinished = isGameFinished;
		this.isWhiteWinner = isWhiteWinner;
		this.whiteCastleKingSideLegal = whiteCastleKingSideLegal;
		this.whiteCastleQueenSideLegal = whiteCastleQueenSideLegal;
		this.blackCastleKingSideLegal = blackCastleKingSideLegal;
		this.blackCastleQueenSideLegal = blackCastleQueenSideLegal;
		this.lastMovePerformed = lastMovePerformed;
		this.numOfFullMoves = numOfFullMoves;

	}

	// TODO probably delete this
	// public ChessBoard flipBoard() throws ParseException {
	// // uses the FEN formatted string to flip the board easily, although
	// // computationally quite expensive.
	// String oldBoardString = this.getFENFormattedStringFromBoard();
	// // seperate piece positions from all other information, by splitting at
	// // the spaces
	// String[] splitBoard = oldBoardString.split(" ");
	// String[] splitPositions = splitBoard[0].split("/");
	// // reverse first part, and append the rest unchanged
	// Collections.reverse(Arrays.asList(splitPositions));
	// String newBoardString = "";
	// for (int i = 0; i < splitPositions.length; i++) {
	// newBoardString += (splitPositions[i]);
	// // dont append slash to last string
	// if (i < splitPositions.length - 1) {
	// newBoardString += "/";
	//
	// }
	// }
	// // skipping first part, but appending rest of original board string
	// for (int i = 1; i < splitBoard.length; i++) {
	// newBoardString += " " + splitBoard[i];
	// }
	// return new ChessBoard(newBoardString);
	// }

	// METHODS
	/**
	 * Performs the specified move and creates a ChessBoard whith the new piece
	 * positions. Note that this method does not change the instance that the
	 * method is called on.
	 * 
	 * @return A new instance where the move has been performed, or null, if the
	 *         move was illegal
	 * @throws GameEndedException
	 *             is thrown in stead of returning null, only when debugging
	 * 
	 * @see ChessBoard#ChessBoard(Piece[], Piece[], Piece[], Integer)
	 */
	public ChessBoard performMove(Move move) {
		
		if (isGameFinished){
			throw new GameEndedException("Cannot perform moves on this board,  the game has ended", isWhiteWinner);
		}

		ChessBoard newBoard = new ChessBoard(this);

		int oldPosition = move.getOldPosition();
		int newPosition = move.getNewPosition();
		Piece movingPiece = move.getMovingPiece();
		if (movingPiece == null) {
			if (GameController.IS_DEBUGGING) {
				throw new RuntimeException(
						"performMove called with movingPiece == null.");
			} else
				return null;

		}
		// checking if old and new position of movingPiece is inside the board
		if (oldPosition != Move.UNKNOWN_POSITION
				&& (oldPosition & INSIDE_BOARD_MASK) != 0) {
			if (GameController.IS_DEBUGGING) {
				throw new RuntimeException(
						"Old position of moving piece is not inside the board");
			} else
				return null;

		}
		if (newPosition != Move.UNKNOWN_POSITION
				&& (newPosition & INSIDE_BOARD_MASK) != 0) {
			if (GameController.IS_DEBUGGING) {
				throw new RuntimeException(
						"New position of moving piece is not inside the board");
			} else
				return null;

		}

		if (move.isKingSideCastle() || move.isQueenSideCastle()) {
			if (isCastleMoveLegal(move.isKingSideCastle(), isWhitesTurn)) {
				performCastlingMove(move.isKingSideCastle(), isWhitesTurn,
						newBoard);
			}
		}

		// check for castling legality when moving normally
		if (isBlackCastleKingSideLegal() || isBlackCastleQueenSideLegal()
				|| isWhiteCastleKingSideLegal()
				|| isWhiteCastleQueenSideLegal()) {
			switch (movingPiece) {
			case WHITE_KING:
				newBoard.setWhiteCastleKingSideLegal(false);
				newBoard.setWhiteCastleQueenSideLegal(false);
				break;
			case BLACK_KING:
				newBoard.setBlackCastleKingSideLegal(false);
				newBoard.setBlackCastleQueenSideLegal(false);
				break;
			case WHITE_ROOK:
				// check which white rook moved
				if (oldPosition == 0x00) {
					newBoard.setWhiteCastleQueenSideLegal(false);
				} else if (oldPosition == 0x07) {
					newBoard.setWhiteCastleKingSideLegal(false);
				}
				break;
			case BLACK_ROOK:
				// check which rook moved
				if (oldPosition == 0x70) {
					newBoard.setBlackCastleQueenSideLegal(false);
				} else if (oldPosition == 0x77) {
					newBoard.setBlackCastleKingSideLegal(false);
				}
				break;
			default:
				break;
			}
			// check if any rooks are captured
			if (move.isCapture()
					&& allPieces[move.getNewPosition()].getType() == PieceType.ROOK) {
				// check directly on index for rooks
				switch (newPosition) {
				case 0x00: // queen side white rook
					newBoard.setWhiteCastleQueenSideLegal(false);
					break;
				case 0x07: // king side white rook
					setWhiteCastleKingSideLegal(false);
					break;
				case 0x70: // queen side black rook
					newBoard.setBlackCastleQueenSideLegal(false);
					break;
				case 0x77: // king side black rook
					newBoard.setBlackCastleKingSideLegal(false);
					break;
				}
			}
		} // check for castling legality done
		
		// perform the actual array operations for the move, if old position is
		// known
		if (oldPosition != Move.UNKNOWN_POSITION) {
			movePiece(movingPiece, oldPosition, newPosition, newBoard);
			// if old position is not known, then either the old file or rank
			// could be known
		} else if (move.getOldFile() != Move.UNKNOWN_POSITION) {
			// departure file is known, so look for piece in file, and get it's
			// index
			int piecesFound = 0;
			int pieceIndex = -1;
			int oldFile = move.getOldFile();
			for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
				if (allPieces[(rank | oldFile)] == movingPiece) {
					piecesFound++;
					pieceIndex = (rank | oldFile); // this will get overwritten
													// if several pieces are
													// found, but in this case,
													// it will never be used.
				}
			}
			if (piecesFound == 1) {
				// we have found a unique piece in the file, so use it's old
				// position to make the move
				movePiece(movingPiece, pieceIndex, newPosition, newBoard);
			} else {
				// piece was not found in old file, which is not supposed to
				// happen
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"move does not seem valid, because the moving piece was not found in the specified file");
				} else
					return null;

			}

		} else if (move.getOldRank() != Move.UNKNOWN_POSITION) {

			// we only know the departing rank of the moving piece, so find it
			// in the files
			int oldRank = move.getOldRank();
			int piecesFound = 0;
			int pieceIndex = -1;

			for (int file = 0x00; file < 0x07; file++) {
				if (allPieces[oldRank | file] == movingPiece) {
					piecesFound++;
					pieceIndex = oldRank | file;
				}
			}
			if (piecesFound == 1) {
				// we found the index of the moving piece in the rank, so make
				// the move
				movePiece(movingPiece, pieceIndex, newPosition, newBoard);
			} else {
				// piece was not found in old rank, which is not supposed to
				// happen
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"move does not seem valid, because the moving piece was not found in the specified rank");
				} else
					return null;
			}
		} else { // movingPiece oldPosition was not set, so determine by
					// searching through the board
			boolean oldPositionFound = false;

			switch (movingPiece.getType()) {
			case PAWN:
				// we use the new file as old file
				int piecesFound = 0;
				int pieceIndex = -1;
				int oldFile = move.getNewPosition() & 0x0F;
				for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
					if (allPieces[(rank | oldFile)] == movingPiece) {
						piecesFound++;
						pieceIndex = (rank | oldFile);
						/*
						 * this will get overwritten if several pieces are
						 * found, but in this case, it will never be used.
						 */
					}
				}
				if (piecesFound == 1) {
					// we have found a unique piece in the file, so use it's old
					// position to make the move
					movePiece(movingPiece, pieceIndex, newPosition, newBoard);
					oldPositionFound = true;
				} else {
					// piece was not found in old file, which is not supposed to
					// happen
					if (GameController.IS_DEBUGGING) {
						throw new RuntimeException(
								"move does not seem valid, because the moving piece was not found in the specified file");
					} else
						return null;

				}
				break;
			case KNIGHT:
				// look for a knight that is moving
				int foundKnightIndex = -1;
				Piece[] colorArray;
				if (isWhitesTurn) {
					colorArray = whitePieces;
				} else {
					colorArray = blackPieces;
				}
				int[] knightIndexes = { 0x21, 0x12, (0x02 - 0x10),
						(-0x20 + 0x01), (-0x20 - 0x01), (-0x10 - 0x02),
						(0x10 - 0x02), (0x20 - 0x01) };
				for (int i = 0; i < knightIndexes.length; i++) {
					int possibleKnightIndex = newPosition + knightIndexes[i];
					if ((possibleKnightIndex & 0x88) == 0
							&& colorArray[possibleKnightIndex] != null
							&& colorArray[possibleKnightIndex].getType() == PieceType.KNIGHT) {
						foundKnightIndex = possibleKnightIndex;
						oldPosition = possibleKnightIndex;
						break;
					}
				}
				if (foundKnightIndex == -1) {
					if (GameController.IS_DEBUGGING) {
						throw new RuntimeException("knight not found");
					} else
						return null;
				} else {
					movePiece(movingPiece, oldPosition, newPosition, newBoard);
					oldPositionFound = true;
				}
				break;
			case ROOK:
				// find a rook in one of the straight lines
				PseudoMove[] rookDirections = new PseudoMove[] {
						PseudoMove.RANK_DOWN, PseudoMove.RANK_UP,
						PseudoMove.FILE_DOWN, PseudoMove.FILE_UP };
				int foundRooks = 0;
				int foundRookIndex = Move.UNKNOWN_POSITION;
				for (PseudoMove pm : rookDirections) {
					Pair<Boolean, Integer> searchResult = MoveGenerator
							.searchForPieceInDirection(newPosition, pm,
									movingPiece.getType(),
									!movingPiece.isBlack(), allPieces);
					if (searchResult.getFirst()) {
						foundRooks++;
						foundRookIndex = searchResult.getSecond();
					}
				}
				// the move is not ambigous, there is only one possible piece
				// that could have move here
				if (foundRooks == 1 && foundRookIndex != Move.UNKNOWN_POSITION) {
					movePiece(movingPiece, foundRookIndex, newPosition,
							newBoard);
					oldPositionFound = true;
				}
				break;
			case BISHOP:
				// find a bishop in one of the diagonals
				PseudoMove[] bishopDirections = new PseudoMove[] {
						PseudoMove.RANK_DOWN_FILE_DOWN,
						PseudoMove.RANK_DOWN_FILE_UP,
						PseudoMove.RANK_UP_FILE_DOWN,
						PseudoMove.RANK_UP_FILE_UP };
				int foundBishops = 0;
				int foundBishopIndex = Move.UNKNOWN_POSITION;
				for (PseudoMove pm : bishopDirections) {
					Pair<Boolean, Integer> searchResult = MoveGenerator
							.searchForPieceInDirection(newPosition, pm,
									movingPiece.getType(),
									!movingPiece.isBlack(), allPieces);
					if (searchResult.getFirst()) {
						foundBishops++;
						foundBishopIndex = searchResult.getSecond();
					}
				}
				// the move is not ambigous, there is only one possible piece
				// that could have move here
				if (foundBishops == 1
						&& foundBishopIndex != Move.UNKNOWN_POSITION) {
					movePiece(movingPiece, foundBishopIndex, newPosition,
							newBoard);
					oldPositionFound = true;
				}
				break;
			case QUEEN:
				// find a bishop in one of the diagonals
				PseudoMove[] queenDirections = new PseudoMove[] {
						PseudoMove.RANK_DOWN_FILE_DOWN,
						PseudoMove.RANK_DOWN_FILE_UP,
						PseudoMove.RANK_UP_FILE_DOWN,
						PseudoMove.RANK_UP_FILE_UP, PseudoMove.RANK_DOWN,
						PseudoMove.RANK_UP, PseudoMove.FILE_DOWN,
						PseudoMove.FILE_UP };
				int foundQueens = 0;
				int foundQueenIndex = Move.UNKNOWN_POSITION;
				for (PseudoMove pm : queenDirections) {
					Pair<Boolean, Integer> searchResult = MoveGenerator
							.searchForPieceInDirection(newPosition, pm,
									movingPiece.getType(),
									!movingPiece.isBlack(), allPieces);
					if (searchResult.getFirst()) {
						foundQueens++;
						foundQueenIndex = searchResult.getSecond();
					}
				}
				// the move is not ambigous, there is only one possible piece
				// that could have move here
				if (foundQueens == 1
						&& foundQueenIndex != Move.UNKNOWN_POSITION) {
					movePiece(movingPiece, foundQueenIndex, newPosition,
							newBoard);
					oldPositionFound = true;
				}
				break;
			case KING:
				// find king in surrounding area

				for (PseudoMove pm : PseudoMove.values()) {
					int possibleOldIndex = newPosition + pm.indexIncrement;
					// skip index if not inside board
					if ((possibleOldIndex & 0x88) != 0)
						continue;
					Piece investigatedPiece = allPieces[possibleOldIndex];
					if (investigatedPiece != null
							&& investigatedPiece == movingPiece) {
						// we have oldPosition
						movePiece(movingPiece, possibleOldIndex, newPosition,
								newBoard);
						oldPositionFound = true;
					}
				}
				break;
			}
			if (!oldPositionFound) {
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"move: "
									+ move
									+ " : "
									+ move.toDebugString()
									+ " Could not be performed, because old position of moving piece could not be determined");
				} else
					return null;

			}
		}

		// check for pawn promotion
		int destinationRank = newPosition & 0xF0;
		if (move.isPawnPromotion() || 
				((movingPiece.getType() == PieceType.PAWN) && ((destinationRank == 0x00) || destinationRank == 0x70 )) ) {
			// always promote to queen
			// change current movingPiece to queen
			movingPiece = Piece.changePieceColor(PieceType.QUEEN, isWhitesTurn);
			// perform the same change 
			performPawnPromotion(movingPiece,
					newPosition, newBoard);
					
				}

		// check for winning conditions - for now, only checks if in checkmate
		// or king is captured.
		// we can either get information from the move object, or from our
		// internal checking method

		boolean isBlackinCheck = false, isWhiteInCheck = false, isBlackinCheckMate = false, isWhiteInCheckMate = false;

		// the opponent did not set us in check
		// run our own checking mechanism to be sure
		// we have not put ourselves in check, and if we have checked the
		// opponent

		MoveGenerator mg = new MoveGenerator(newBoard);
		try {
			isWhiteInCheck = mg.isInCheck(mg.getKingPosition(true), true);
		} catch (RuntimeException e) {
			if (GameController.IS_DEBUGGING) {
				throw new RuntimeException(
						"Could not determine if white is in check, "
								+ "because the white king was not found in the board. Move: "
								+ move + " : " + move.toDebugString());
			} else
				return null;
		}
		if (isWhiteInCheck) {
			// for evaluating checkmate, we need to determine if the players
			// king can move at all from it's current spot.
			int whiteKingPositionForCheckMate;
			if (movingPiece == Piece.WHITE_KING) {
				// if it is the king itself that is moving, we use the old
				// position, as i has already moved in the array
				whiteKingPositionForCheckMate = oldPosition;
			} else {
				whiteKingPositionForCheckMate = mg.getKingPosition(true);
			}
			isWhiteInCheckMate = mg.isInCheckMate(true,
					whiteKingPositionForCheckMate);
		}

		isBlackinCheck = mg.isInCheck(mg.getKingPosition(false), false);
		if (isBlackinCheck) {
			// for evaluating checkmate, we need to determine if the players
			// king can move at all from it's current spot.
			int blackKingPositionForCheckMate;
			if (movingPiece == Piece.BLACK_KING) {
				// if it is the king itself that is moving, we use the old
				// position, as i has already moved in the array
				blackKingPositionForCheckMate = oldPosition;
			} else {
				blackKingPositionForCheckMate = mg.getKingPosition(false);
			}
			isBlackinCheckMate = mg.isInCheckMate(false,
					blackKingPositionForCheckMate);
		}

		// see if checks are valid
		if (isWhitesTurn) {
			if (isWhiteInCheck) {
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"White cannot put itself in check.Move: "
									+ move.toDebugString() + "\n OldBoard:\n"
									+ this + "\nNewBoard:\n" + newBoard);
				} else
					return null;
			}
			if (isWhiteInCheckMate) {
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"White cannot put itself in checkmate.Move: "
									+ move.toDebugString() + "\n OldBoard:\n"
									+ this + "\nNewBoard:\n" + newBoard);
				} else
					return null;
			}
			if (isBlackinCheck) {
				newBoard.setInCheck(false);
			}

			if (isBlackinCheckMate) {
				newBoard.setWhiteWinner(true);
				newBoard.setGameFinished(true);
//				throw new GameEndedException("Game ended with white player winning by checkmate", true);
			}

		} else { // blacks turn
			if (isBlackinCheck) {
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"Black cannot put itself in check. Move: "
									+ move.toDebugString() + "\nOldBoard:\n"
									+ this + "\nNewBoard:\n" + this);
				} else
					return null;
			}
			if (isBlackinCheckMate) {
				if (GameController.IS_DEBUGGING) {
					throw new RuntimeException(
							"Black cannot put itself in checkmate.Move: "
									+ move.toDebugString() + "\n OldBoard:\n"
									+ this + "\nNewBoard:\n" + this);
				} else
					return null;

			}
			if (isWhiteInCheck) {
				newBoard.setInCheck(true);
			}

			if (isWhiteInCheckMate) {
				newBoard.setWhiteWinner(false);
				newBoard.setGameFinished(true);
//				throw new GameEndedException("Game ended with black player winning by checkmate", false);
			}

		}

		// change turns, and increment full moves if black performed the move
		if (!isWhitesTurn) {
			newBoard.numOfFullMoves++;
		}
		newBoard.setWhitesTurn(!isWhitesTurn);

		newBoard.lastMovePerformed = move;

		return newBoard;

	}

	/**
	 * Used internally by the performMove method. This performs the actual array
	 * operations that is needed to move a piece from one square to another. All
	 * other handling of the move should be handled outside this method.
	 * 
	 * @param movingPiece
	 *            The piece that is moving
	 * @param oldPosition
	 *            The old position of the piece
	 * @param newPosition
	 *            The new position of the piece
	 * @param changingBoard
	 *            The ChessBoard instance, in which the array operations should
	 *            be performed.
	 */
	private void movePiece(Piece movingPiece, int oldPosition, int newPosition,
			ChessBoard changingBoard) {
		Piece[] movingPieceColorArray;
		Piece[] oppositeColorArray;
		
		// sanity check if the moving piece really is the one in the given old position
		if (movingPiece != allPieces[oldPosition]){
			throw new RuntimeException("Cannot perform move of piece: "+movingPiece+ " from " + oldPosition + 
					" because that position contains a: " +allPieces[oldPosition]);
		}
		
		// perform move on color-specific array first
		if (movingPiece.isBlack()) {
			// black player moves
			movingPieceColorArray = changingBoard.blackPieces;
			oppositeColorArray = changingBoard.whitePieces;
		} else {
			movingPieceColorArray = changingBoard.whitePieces;
			oppositeColorArray = changingBoard.blackPieces;
		}
		Piece overWrittenPiece = movingPieceColorArray[newPosition];
		if (overWrittenPiece != null
				&& overWrittenPiece.getType() == PieceType.KING) {
			// sanity check if the overwritten piece is a king
			throw new IllegalArgumentException(
					"Cannot perform move that removes a king from the game");
		}
		// remove moving piece from old position in color specific array
		movingPieceColorArray[oldPosition] = null;

		// put moving piece in new position
		movingPieceColorArray[newPosition] = movingPiece;
		// remove any piece from the new position in opposite color array
		oppositeColorArray[newPosition] = null;

		// perform move on full board array
		changingBoard.allPieces[oldPosition] = null;
		changingBoard.allPieces[newPosition] = movingPiece;
	}

	/**
	 * Used internally by the performMove method to perform a specific castling
	 * move. This is to avoid cluttering up the performMove method, and for code
	 * reuse. NOTE: This does not check for the legality of the castling moves,
	 * but it does set the legal castling flags after moving.
	 * 
	 * @param isKingSide
	 *            Should be set true if the castle is a kingside castle.
	 * @param isWhitePlayer
	 *            Should be set true if the color of the player that is castling
	 *            is white.
	 * @param changingBoard
	 *            The ChessBoard instance on which the actual moves should be
	 *            performed.
	 */
	private void performCastlingMove(boolean isKingSide, boolean isWhitePlayer,
			ChessBoard changingBoard) {
		if (GameController.IS_DEBUGGING) {
			// Logger.getLogger(this.getClass().getName()).log(Level.INFO,
			// "perform castling move called");
		}

		int kingOldPos, rookOldRank, rookOldFile, rookOldPos, kingOffset, rookOffset;
		Piece movingKing, movingRook;

		// set the movingPiece, king position and rook rank
		if (isWhitesTurn) {
			movingKing = Piece.WHITE_KING;
			kingOldPos = 0x04;
			movingRook = Piece.WHITE_ROOK;
			rookOldRank = 0x00;
			setWhiteCastleKingSideLegal(false);
			setWhiteCastleQueenSideLegal(false);
		} else {
			movingKing = Piece.BLACK_KING;
			kingOldPos = 0x74;
			movingRook = Piece.BLACK_ROOK;
			rookOldRank = 0x70;
			setBlackCastleKingSideLegal(false);
			setBlackCastleQueenSideLegal(false);
		}
		// set the index offsets and the rook file
		if (isKingSide) {
			kingOffset = 0x02;
			rookOffset = -0x02;
			rookOldFile = 0x07;
		} else {
			kingOffset = -0x03;
			rookOffset = 0x02;
			rookOldFile = 0x00;
		}
		rookOldPos = (rookOldFile | rookOldRank);
		// perform the actual moves
		movePiece(movingKing, kingOldPos, kingOldPos + kingOffset,
				changingBoard);
		movePiece(movingRook, rookOldPos, rookOldPos + rookOffset,
				changingBoard);
	}

	private void performPawnPromotion(Piece promotedTo, int promotedPieceIndex,
			ChessBoard changingBoard) {
		// replace current piece with promotedPiece
		if (promotedTo == null || (promotedPieceIndex & 0x88) != 0) {
			throw new IllegalArgumentException(
					"Piece cannot be promoted to null");
		}
		// also fail if trying to promote a piece black piece to a white one or
		// vice versa
		Piece oldPiece = changingBoard.allPieces[promotedPieceIndex];
		if (oldPiece.isBlack() ^ promotedTo.isBlack()) {
			throw new IllegalArgumentException(
					"cannot promote a piece to a new piece of a different color");
		}
		changingBoard.allPieces[promotedPieceIndex] = null;
		Piece[] colorArray;
		if (promotedTo.isBlack()) {
			colorArray = changingBoard.blackPieces;
		} else {
			colorArray = changingBoard.whitePieces;
		}
		changingBoard.allPieces[promotedPieceIndex] = promotedTo;
		colorArray[promotedPieceIndex] = promotedTo;

	}

	/**
	 * @return All pieces currently on the board, both black and white
	 * 
	 * @see ChessBoard#getBlackPlayersPieces()
	 * @see ChessBoard#getWhitePlayersPieces()
	 */
	public Piece[] getAllPieces() {
		return allPieces;
	}

	/**
	 * @return All black pieces currently on the board
	 * 
	 *         ChessBoard#getAllPieces() ChessBoard#getWhitePieces()
	 */
	public Piece[] getBlackPlayersPieces() {
		return blackPieces;
	}

	/**
	 * @return All white pieces currently on the board
	 * 
	 *         ChessBoard#getAllPieces() ChessBoard#getBlackPieces()
	 */
	public Piece[] getWhitePlayersPieces() {
		return whitePieces;
	}

	/**
	 * @return The current static evaluation of the board, if any is set.
	 */
	public int getStaticEvaluation() {
		return staticEvaluation;
	}

	/**
	 * @param staticEvaluation
	 *            supply the board with a static evaluation for its current
	 *            state
	 */
	public void setStaticEvaliution(int staticEvaliution) {
		this.staticEvaluation = staticEvaliution;
	}

	public boolean isWhitesTurn() {
		return isWhitesTurn;
	}

	public void setWhitesTurn(boolean isWhitesTurn) {
		this.isWhitesTurn = isWhitesTurn;
	}

	public boolean isInsideBoard(int position) {
		return ((position & INSIDE_BOARD_MASK) == 0);
	}

	public synchronized Move getLastMovePerformed() {
		return lastMovePerformed;
	}

	public boolean isWhiteCastleKingSideLegal() {
		return whiteCastleKingSideLegal;
	}

	public void setWhiteCastleKingSideLegal(boolean whiteCastleKingSideLegal) {
		this.whiteCastleKingSideLegal = whiteCastleKingSideLegal;
	}

	public boolean isWhiteCastleQueenSideLegal() {
		return whiteCastleQueenSideLegal;
	}

	public void setWhiteCastleQueenSideLegal(boolean whiteCastleQueenSideLegal) {
		this.whiteCastleQueenSideLegal = whiteCastleQueenSideLegal;
	}

	public boolean isBlackCastleKingSideLegal() {
		return blackCastleKingSideLegal;
	}

	public void setBlackCastleKingSideLegal(boolean blackCastleKingSideLegal) {
		this.blackCastleKingSideLegal = blackCastleKingSideLegal;
	}

	public boolean isBlackCastleQueenSideLegal() {
		return blackCastleQueenSideLegal;
	}

	public void setBlackCastleQueenSideLegal(boolean blackCastleQueenSideLegal) {
		this.blackCastleQueenSideLegal = blackCastleQueenSideLegal;
	}

	private boolean isCastleMoveLegal(boolean isKingside, boolean isWhitePlayer) {
		int castleType = 0x00;
		if (isKingside) {
			castleType = castleType | 0x10;
		}
		if (isWhitePlayer) {
			castleType = castleType | 0x01;
		}

		switch (castleType) {
		case 0x00: // queen side black castle
			return isBlackCastleQueenSideLegal();
		case 0x01: // queen side white castle
			return isWhiteCastleQueenSideLegal();
		case 0x10: // king side black castle
			return isBlackCastleKingSideLegal();
		case 0x11:
			return isWhiteCastleKingSideLegal();
		default:
			return false;
		}
	}

	public boolean isGameFinished() {
		return isGameFinished;
	}

	public boolean isWhiteWinner() {
		return isWhiteWinner;
	}

	public void setWhiteWinner(boolean isWhiteWinner) {
		this.isWhiteWinner = isWhiteWinner;
	}

	public void setGameFinished(boolean isGameFinished) {
		this.isGameFinished = isGameFinished;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(BOARD_SIZE * 3);
		for (int rank = 0x70; rank >= 0x00; rank = rank - 0x10) {
			builder.append(ChessBoard.getRankString(rank) + " ");
			for (int file = 0x00; file <= 0x07; file++) {

				int index = rank | file;
				Piece curPiece = allPieces[index];
				if (curPiece == null) {
					builder.append("[ ]");
				} else {
					builder.append("[" + curPiece + "]");
				}
			}
			builder.append("\n");
		}
		// add files in the bottom
		builder.append(" ");
		for (int file = 0x00; file < 0x08; file++) {
			builder.append("  " + ChessBoard.getFileString(file));
		}

		return builder.toString();
	}

	/**
	 * Writes the current board state to a file using FEN notation.
	 * 
	 * @see https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
	 * 
	 * @param pathToFile
	 *            A path pointing to the new file. If the Path does not end with
	 *            ".fen" the extension will automatically be added to the end of
	 *            the file
	 * @throws IOException
	 *             If pathToFile points to an existing file, an exception is
	 *             thrown
	 */
	public void writeToFile(Path pathToFile, boolean isWhitesTurn)
			throws IllegalArgumentException, IOException {
		if (Files.exists(pathToFile, LinkOption.NOFOLLOW_LINKS)) {
			throw new IllegalArgumentException("File already exists");
		}
		// set extension on filename if not already set
		if (!pathToFile.toString().endsWith(FILE_EXTENSION)) {
			pathToFile = Paths.get(pathToFile.toString() + FILE_EXTENSION);
		}

		String FENString = this.getFENFormattedStringFromBoard().concat("\n");

		Files.write(pathToFile, FENString.getBytes());
	}

	public void appendToFile(Path pathToFile) throws IOException {
		String FENString = this.getFENFormattedStringFromBoard().concat("\n");
		Files.write(pathToFile, FENString.getBytes(), StandardOpenOption.APPEND);
	}

	/**
	 * Get a FEN formatted String representation of the board
	 * 
	 * @return returns a String in FEN format of the board.
	 */
	public String getFENFormattedStringFromBoard() {
		StringBuilder sb = new StringBuilder(50);

		for (int rank = 0x70; rank >= 0x00; rank = rank - 0x10) {
			int emptyCount = 0;
			for (int file = 0x00; file < 0x08; file = file + 0x01) {

				int index = rank | file;
				if (this.allPieces[index] != null) {

					// write the number of empty squares in this file before
					// this piece, if any.
					if (emptyCount > 0) {
						sb.append(emptyCount);
						emptyCount = 0;
					}
					sb.append(this.allPieces[index]);
				} else {
					emptyCount++;
				}
			}
			// when finished with file, write any empty squares out
			if (emptyCount > 0) {
				sb.append(emptyCount);
				emptyCount = 0;
			}
			sb.append('/');
		}

		// write the current players turn
		if (isWhitesTurn) {
			sb.append(" w ");
		} else {
			sb.append(" b ");
		}
		if (!whiteCastleKingSideLegal && !whiteCastleQueenSideLegal
				&& !blackCastleKingSideLegal && !blackCastleQueenSideLegal) {
			sb.append("- ");
		} else {
			if (whiteCastleKingSideLegal) {
				sb.append("K");
			}
			if (whiteCastleQueenSideLegal) {
				sb.append("Q");
			}
			if (blackCastleKingSideLegal) {
				sb.append("k");
			}
			if (blackCastleQueenSideLegal) {
				sb.append("q ");
			}
		}
		// en passant moves are not handled in this implementation, so we just
		// set it to none
		sb.append("- ");
		// halfmove clock is also not handled because we don't consider draw by
		// the fifty-move rule, so always set to 0
		sb.append("0 ");
		// number of full moves. This should be incremented after each black
		// move
		sb.append(numOfFullMoves);

		return sb.toString();
	}

	public boolean isWhiteInCheck() {
		return isWhiteInCheck;
	}

	public boolean isBlackInCheck() {
		return isBlackInCheck;
	}

	public void setInCheck(boolean isWhitePlayer) {
		if (isWhitePlayer) {
			isWhiteInCheck = true;
		} else {
			isBlackInCheck = true;
		}
	}

	/**
	 * Parses a String in SAN format and creates a Move object for the engine.
	 * The move is parsed according to the current state of the ChessBoard
	 * 
	 * @param SANString
	 *            A SAN formatted String that representsa chessmove
	 * @return a Move object that corresponds to the move String.
	 */
	public Move createMoveFromSANString(String SANString) {
		MoveBuilder mb = new MoveBuilder();

		char[] moveArray = SANString.toCharArray();
		int moveLength = moveArray.length;
		int moveArrayIndex = moveLength - 1;
		// parsing move backwards, as this seems simpler in SAN notation

		// is move a checkmate?
		if (moveArray[moveArrayIndex] == '#') {
			mb.isCheckMating(true);
			moveArrayIndex--;
		} // is move a check?
		else if (moveArray[moveArrayIndex] == '+') {
			mb.isCheck(true);
			moveArrayIndex--;
		}
		/*
		 * is move a castling move? Because the notation does not specify which
		 * player is castling, we must rely on the ChessBoard to castle
		 * correctly with the player whoose turn it is.
		 */
		// create a substring from remaining characters, with check or checkmate
		// indicator removed, if any.
		String castleSubString = new String(moveArray, 0, moveArrayIndex + 1);
		if (castleSubString.equals("O-O") || castleSubString.equals("O-O-O")) {
			int oldKingFile = 0x04;
			int newKingFile;
			if (SANString.equals("O-O")) {
				newKingFile = 0x06;
				mb.isKingSideCastle(true);
			} else {
				newKingFile = 0x02;
				mb.isQueenSideCastle(true);
			}
			int kingRank;
			Piece movingPiece = Piece.changePieceColor(PieceType.KING,
					isWhitesTurn);
			if (isWhitesTurn) {
				kingRank = 0x00;
			} else {
				kingRank = 0x70;
			}
			return mb.buildMove(movingPiece, kingRank | oldKingFile, kingRank
					| newKingFile);
		} // if move is castle

		// not a castle move
		// check for pawn promotion. Skip the current char, because that
		// specifies the promoted piece, if any.
		// Instead look at the char before that, because it will be the equals
		// sign, if the move is a pawn promotion
		if (moveArray[moveArrayIndex - 1] == '=') {
			mb.isPawnPromotion(true);
			// decide which piece the pawn is promoted to, by looking at the
			// current char
			PieceType type = Piece.getPiece(moveArray[moveArrayIndex])
					.getType();
			// convert the PieceType into the color of the current player
			mb.promotedTo(Piece.changePieceColor(type, isWhitesTurn));
			// skip both pawn promotion chars
			moveArrayIndex = moveArrayIndex - 2;
		} // end of pawn promotion parsing

		// parse destination of moving piece.
		// get the rank that the piece is moving to
		// by converting from ascii to correct hex index
		int toRank = getRankFromChar(moveArray[moveArrayIndex]);
		moveArrayIndex--;
		// get the file that the piece is moving to
		int toFile = getFileFromChar(moveArray[moveArrayIndex]);
		moveArrayIndex--;
		// combine file and rank to index, that is used in buildMove
		int newPosition = toFile | toRank;

		// Check if move is a capture. If true, an x is inserted before the
		// destination file and rank
		if (moveArrayIndex >= 0 && moveArray[moveArrayIndex] == 'x') {
			mb.isCapture(true);
			moveArrayIndex--;
		}

		// if next char is a digit, it is the rank that the moving piece is
		// departing
		if (moveArrayIndex >= 0 && Character.isDigit(moveArray[moveArrayIndex])) {
			int oldRank = getRankFromChar(moveArray[moveArrayIndex]);
			mb.oldRank(oldRank);
			moveArrayIndex--;
		}

		// if next char is lower case a-h, it is the file that the moving piece
		// is departing
		if (moveArrayIndex >= 0
				&& Character.isLowerCase(moveArray[moveArrayIndex])) {
			int oldFile = getFileFromChar(moveArray[moveArrayIndex]);
			mb.oldFile(oldFile);
			moveArrayIndex--;
		}
		// read the moving piece from the byte array
		Piece movingPiece = getMovingPieceFromSanBytes(moveArray);
		return mb.buildMove(movingPiece, Move.UNKNOWN_POSITION, newPosition);

	}

	private Piece getMovingPieceFromSanBytes(char[] moveStringChars) {
		char pieceChar = moveStringChars[0];
		Piece p;
		if (Character.isUpperCase(pieceChar)) {
			p = Piece.getPiece(pieceChar);
			if (p == null)
				throw new IllegalArgumentException(
						"input char was not recognized as a Piece character");
			return Piece.changePieceColor(p, isWhitesTurn);
		} else {
			return Piece.changePieceColor(PieceType.PAWN, isWhitesTurn);
		}
	}

	public static int getRankFromChar(char rankChar) {
		int unShiftedRank = Character.digit(rankChar, 16) - 1;
		return unShiftedRank << 4;
	}

	public static int getFileFromChar(char fileChar) {
		return (fileChar & 0x0F) - 1;

	}

	public static String getFileString(int index) {
		return "" + (char) (((index & 0x0F) | 0x60) + 1);
	}

	public static String getRankString(int index) {
		return "" + (char) (((index >> 4) | 0x30) + 1);
	}

	/**
	 * Used to convert a board index to a ran and file string, eg. 0x35 -> f4
	 * 
	 * @param index
	 *            the integer to convert to an index string
	 * @return returns the a string representing the index on a 8x8 board in AN
	 *         (Algebraic Notation)
	 */
	public static String getFileAndRankString(int index) {
		// converting each part of file index to lower case ASCII. a is 0x61, b
		// is 0x62 etc.
		return getFileString(index) + getRankString(index);
	}

	/**
	 * Reads a text file and returns a string with the corresponding board
	 * state.
	 * 
	 * @param pathToFile
	 *            Path to the file containing the board in FEN notation
	 * @return A String containing the board state in FEN notation
	 * @throws IOException
	 *             if an I/O error occurs reading from the stream
	 */
	public static String getFENFormattedStringFromFile(Path pathToFile)
			throws IOException {
		if (!Files.exists(pathToFile, LinkOption.NOFOLLOW_LINKS)) {
			throw new IllegalArgumentException("File does not exist");
		}
		byte[] bytes = Files.readAllBytes(pathToFile);
		String boardString = new String(bytes, 0, bytes.length - 1);
		return boardString;
	}

	// private boolean isMoveValidOnBoard(Move m){
	// // is basic neccessary fields set?
	// boolean isInValid = false;
	// // verify that the move makes sense in it's own internal logic
	// isInValid = !Move.isInternallyValid(m);
	// // verify that the move makes sense to perform on the current board
	//
	// // regarding move positions
	//
	// isInValid = (allPieces[m.])
	//
	//
	// }

}
