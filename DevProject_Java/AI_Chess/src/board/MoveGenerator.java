/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import game.GameController;

import java.util.ArrayList;
import java.util.LinkedList;

//import board.ChessBoard;

/**
 * 
 * MoveGenerator class using getAllMoves(), all possible legal allMoves can be
 * obtained.
 * 
 * @author tahsin
 */
public class MoveGenerator {

	private Piece[] allPieces, blackPieces, whitePieces;
	private ChessBoard chessBoard;
	
	private LinkedList<Move> promotionAndCaptures;
	private LinkedList<Move> captureMoves;
	private LinkedList<Move> promotionMoves;
	private LinkedList<Move> castlingMoves;
	private LinkedList<Move> normalMoves;
	private LinkedList<Move> allMoves;
	
	public MoveGenerator(ChessBoard board) {
		this.chessBoard = board;
		this.allPieces = board.getAllPieces();
		this.whitePieces = board.getWhitePlayersPieces();
		this.blackPieces = board.getBlackPlayersPieces();
		
		this.promotionAndCaptures = new LinkedList<Move>();
		this.captureMoves = new LinkedList<Move>();
		this.promotionMoves = new LinkedList<Move>();
		this.castlingMoves = new LinkedList<Move>();
		this.normalMoves = new LinkedList<Move>();
		
		this.allMoves = new LinkedList<Move>();
	}


	public LinkedList<Move> getWhitePieceMoves() {

		if (!allMoves.isEmpty())
			allMoves = new LinkedList<Move>(); // if the array is not empty,
		if (!normalMoves.isEmpty())
			normalMoves = new LinkedList<Move>();
		if (!captureMoves.isEmpty())
			captureMoves = new LinkedList<Move>();

		for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
			for (int file = 0x00; file < 0x08; file++) {
				int i = file | rank;
				if (whitePieces[i] != null && allPieces[i] != null) {
					if (whitePieces[i].getType() == PieceType.PAWN) {
						PawnMoveChecker(i, true);
					} else if (whitePieces[i].getType() == PieceType.ROOK) {
						RookMoveChecker(i, true);
					} else if (whitePieces[i].getType() == PieceType.BISHOP) {
						BishopMoveChecker(i, true);
					} else if (whitePieces[i].getType() == PieceType.KNIGHT) {
						KnightMoveChecker(i, true);
					} else if (whitePieces[i].getType() == PieceType.QUEEN) {
						QueenMoveChecker(i, true);
					} else if (whitePieces[i].getType() == PieceType.KING) {
						KingMoveChecker(i, true);
					}
				}
			}

		}
		allMoves.addAll(promotionAndCaptures);
		allMoves.addAll(captureMoves);
		allMoves.addAll(promotionMoves);
		allMoves.addAll(castlingMoves);
		allMoves.addAll(normalMoves);
		
		if (GameController.IS_DEBUGGING){
			GameController.totalMovesGeneratedInTurn+=allMoves.size();
		}

		return allMoves;
	}

	public LinkedList<Move> getBlackPieceMoves() {

		if (!allMoves.isEmpty())
			allMoves = new LinkedList<Move>(); // if the array is not empty,
		if (!normalMoves.isEmpty())
			normalMoves = new LinkedList<Move>();
		if (!captureMoves.isEmpty())
			captureMoves = new LinkedList<Move>();

		for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
			for (int file = 0x00; file < 0x08; file++) {
				int i = file | rank;
				if (blackPieces[i] != null && allPieces[i] != null) {
					if (blackPieces[i].getType() == PieceType.PAWN) {
						PawnMoveChecker(i, false);
					} else if (blackPieces[i].getType() == PieceType.ROOK) {
						RookMoveChecker(i, false);
					} else if (blackPieces[i].getType() == PieceType.BISHOP) {
						BishopMoveChecker(i, false);
					} else if (blackPieces[i].getType() == PieceType.KNIGHT) {
						KnightMoveChecker(i, false);
					} else if (blackPieces[i].getType() == PieceType.QUEEN) {
						QueenMoveChecker(i, false);
					} else if (blackPieces[i].getType() == PieceType.KING) {
						KingMoveChecker(i, false);
					}
				}
			}

		}

		allMoves.addAll(promotionAndCaptures);
		allMoves.addAll(captureMoves);
		allMoves.addAll(promotionMoves);
		allMoves.addAll(castlingMoves);
		allMoves.addAll(normalMoves);
		
		if (GameController.IS_DEBUGGING){
			GameController.totalMovesGeneratedInTurn+=allMoves.size();
		}


		return allMoves;
	}

	private void PawnMoveChecker(int index, boolean isWhite) {

		// various pseudo allMoves and indexes
		int forward; // index increment for moving pawn forward
		int doubleForward; // index increment for moving a pawn forward two
							// squares
		int capRight; // index increment for capturing a piece to the front
						// right of the pawn
		int capLeft; // index increment for capturing a piece to the front left
						// of the pawn
		int startRank; // the rank that the pawns starts on
		int promotionRank; // the rank from where a pawn promotion move is
							// possible
		Piece movingPiece;
		Piece promotedToPiece;
		MoveBuilder mb = new MoveBuilder();

		if (isWhite) {
			forward = 0x10;
			doubleForward = forward + forward;
			// capRight = 0x11;
			capRight = 0x10 + 0x01;
			// capLeft = 0x09;
			capLeft = (0x10 - 0x01);
			startRank = 0x10;
			promotionRank = 0x60;
			movingPiece = Piece.WHITE_PAWN;
			promotedToPiece = Piece.WHITE_QUEEN;
		} else {
			forward = -0x10;
			doubleForward = forward + forward;
			// capRight = -0x11;
			capRight = -(0x10 + 0x01);
			// capLeft = -0x09;
			capLeft = -(0x10 - 0x01);
			startRank = 0x60;
			promotionRank = 0x10;
			movingPiece = Piece.BLACK_PAWN;
			promotedToPiece = Piece.BLACK_QUEEN;
		}

		int indexFile = index & 0x0F; // MASK
		int indexRank = index & 0xF0;

		// all pawns that are allowed to do a forward and a double forward move
		if (indexRank == startRank) {
			if (allPieces[index + forward] == null
					&& allPieces[index + doubleForward] == null) {
				normalMoves.add(mb.buildMove(movingPiece,
						(indexFile | indexRank), (indexFile | indexRank)
								+ doubleForward));
			}

		}

		// all pawns that are allowed to move once forward
		if (indexRank >= 0x10 && indexRank <= 0x60) {
			// forward move
			if (allPieces[index + forward] == null) {
				if (index == promotionRank) {
					promotionMoves.add(new MoveBuilder().isPawnPromotion(true)
							.promotedTo(promotedToPiece)
							.buildMove(movingPiece, index, index + forward));
				} else {
					normalMoves.add(mb.buildMove(movingPiece, index, index
							+ forward));
				}
			}
		}

		// all pawns that are allowed to capture right
		if (indexRank >= 0x10 && indexRank <= 0x60
				&& (((index + capRight) & 0x88) == 0)) {
			Piece capturedPiece = allPieces[index + capRight];
			// xor is used on the color of the piece and the moving player. One
			// of the pieces should be white, but not both
			if (capturedPiece != null && ((!capturedPiece.isBlack()) ^ isWhite)) {
				if (index == promotionRank) {
					promotionAndCaptures.add(new MoveBuilder().isPawnPromotion(true)
							.promotedTo(promotedToPiece).isCapture(true)
							.capturedPiece(capturedPiece)
							.buildMove(movingPiece, index, index + capRight));
				} else {
					captureMoves.add(new MoveBuilder()
							.capturedPiece(capturedPiece).isCapture(true)
							.buildMove(movingPiece, index, index + capRight));
				}

			}
		}
		// all pawns that are allowed to capture left
		if (indexRank >= 0x10 && indexRank <= 0x60
				&& (((index + capLeft) & 0x88) == 0)) {
			Piece capturedPiece = allPieces[index + capLeft];
			// xor is used on the color of the piece and the moving player. One
			// of the pieces should be white, but not both
			if (capturedPiece != null && ((!capturedPiece.isBlack()) ^ isWhite)) {
				if (index == promotionRank) {
					promotionAndCaptures.add(new MoveBuilder().isPawnPromotion(true)
							.promotedTo(promotedToPiece).isCapture(true)
							.capturedPiece(capturedPiece)
							.buildMove(movingPiece, index, index + capLeft));
				} else {
					captureMoves.add(new MoveBuilder()
							.capturedPiece(capturedPiece).isCapture(true)
							.buildMove(movingPiece, index, index + capLeft));
				}
			}
		}

	}

	// ..ModeSelection methods provides movement on the board

	private int RookMoveCheckerModeSelection(int index, int mode) {
		/*
		 * Rook Legal Moves (-10) (-1) X (+1) (+10)
		 */

		if (mode == 0) {
			index = index - 0x10;
		} else if (mode == 1) {
			index = index + 0x10;
		} else if (mode == 2) {
			index++;
		} else if (mode == 3) {
			--index;
		}

		// I will add CASTLING later
		return index;
	}

	private void RookMoveChecker(int index, boolean isWhite) {
		int pos = index;
		for (int i = 0; i < 4; i++) {
			while (true) {
				pos = RookMoveCheckerModeSelection(pos, i);
				if (!((pos & 0x88) == 0)) {
					break;
				}

				// if the piece is white
				if (isWhite) {
					// the next square is empty ?
					if (allPieces[pos] == null) {
						// allMoves.add(new
						// MoveBuilder().buildMove(allPieces[index],
						// index, pos));
						normalMoves.add(new MoveBuilder().buildMove(
								allPieces[index], index, pos));
					} // the next square has a black piece ?
					else if (allPieces[pos].isBlack()) {
						// allMoves.add(new
						// MoveBuilder().isCapture(true).capturedPiece(allPieces[pos])
						// .buildMove(allPieces[index], index, pos));
						captureMoves.add(new MoveBuilder().isCapture(true)
								.capturedPiece(allPieces[pos])
								.buildMove(allPieces[index], index, pos));
						break;
					} // the next square has a white piece ?
					else if (!(allPieces[pos].isBlack())) {
						break;
					}

				} else {

					if (allPieces[pos] == null) {
						// allMoves.add(new
						// MoveBuilder().buildMove(allPieces[index],
						// index, pos));
						normalMoves.add(new MoveBuilder().buildMove(
								allPieces[index], index, pos));
					} // the next square has a white piece ?
					else if (!allPieces[pos].isBlack()) {
						// allMoves.add(new
						// MoveBuilder().isCapture(true).capturedPiece(allPieces[pos])
						// .buildMove(allPieces[index], index, pos));
						captureMoves.add(new MoveBuilder().isCapture(true)
								.capturedPiece(allPieces[pos])
								.buildMove(allPieces[index], index, pos));
						break;
					} // the next square has a black piece ?
					else if ((allPieces[pos].isBlack())) {
						break;
					}

				}

			}
			pos = index;
		}
	}

	private int BishopMoveCheckerModeSelection(int index, int mode) {
		/*
		 * for isWhiteAtTheBottom = True right up cross and left down cross :
		 * -+0x09 left up cross and right down cross : -+0x11
		 * 
		 * (-11)( )( -9) X ( +9)( )(+11)
		 */

		if (mode == 0) {
			// index = index - 0x09; // right up cross
			index = index - (0x10 - 0x01); // right up cross
		} else if (mode == 1) {
			// index = index + 0x09; // left down cross
			index = index + (0x10 - 0x01);
		} else if (mode == 2) {
			// index = index - 0x11; // left up cross
			index = index - (0x10 + 0x01);
		} else if (mode == 3) {
			// index = index + 0x11; // right down cross
			index = index + (0x10 + 0x01);
		}
		return index;
	}

	private void BishopMoveChecker(int index, boolean isWhite) {
		// TODO I will add the property of changing the board
		int pos = index;
		for (int i = 0; i < 4; i++) {
			while (true) {

				pos = BishopMoveCheckerModeSelection(pos, i);
				if (!((pos & 0x88) == 0)) {
					break; // is it in the board ?
				}
				if (isWhite) {
					// the next square is empty ?
					if (allPieces[pos] == null) {
						normalMoves.add(new MoveBuilder().buildMove(
								allPieces[index], index, pos));
					} // the next square has a black piece ?
					else if (allPieces[pos].isBlack()) {
						captureMoves.add(new MoveBuilder().isCapture(true)
								.capturedPiece(allPieces[pos])
								.buildMove(allPieces[index], index, pos));
						break;
					} // the next square has a white piece ?
					else if (!(allPieces[pos].isBlack())) {
						break;
					}

				} else {
					// the next square is empty ?
					if (allPieces[pos] == null) {
						normalMoves.add(new MoveBuilder().buildMove(
								allPieces[index], index, pos));
					} // the next square has a white piece ?
					else if (!allPieces[pos].isBlack()) {
						captureMoves.add(new MoveBuilder().isCapture(true)
								.capturedPiece(allPieces[pos])
								.buildMove(allPieces[index], index, pos));
						break;
					} // the next square has a black piece ?
					else if ((allPieces[pos].isBlack())) {
						break;
					}

				}

			}
			pos = index;
		}
	}

	private int KnightMoveCheckerModeSelection(int index, int mode) {
		/*
		 * Legal Knight Moves
		 * 
		 * (-21) (-19) (-12) (-8) X (+8) (+12) (+19) (+21)
		 */

		if (mode == 0) {
			index = index - (0x10 - 0x02);
		} else if (mode == 1) {
			index = index + (0x10 - 0x02);
		} else if (mode == 2) {
			index = index - (0x20 - 0x01);
		} else if (mode == 3) {
			index = index + (0x20 - 0x01);
		} else if (mode == 4) {
			index = index - (0x20 + 0x01);
		} else if (mode == 5) {
			index = index + (0x20 + 0x01);
		} else if (mode == 6) {
			index = index - (0x10 + 0x02);
		} else if (mode == 7) {
			index = index + (0x10 + 0x02);
		}
		return index;
	}

	private void KnightMoveChecker(int index, boolean isWhite) {
		int pos = index;
		for (int i = 0; i < 8; i++) {

			pos = KnightMoveCheckerModeSelection(pos, i);
			if (!((pos & 0x88) == 0)) {
				pos = index;
				continue; // is it in the board ? CONTINUE with the other mode
			}
			if (isWhite) {

				// the next square is empty ?
				if (allPieces[pos] == null) {
					// allMoves.add(new MoveBuilder().buildMove(allPieces[index],
					// index, pos));
					normalMoves.add(new MoveBuilder().buildMove(
							allPieces[index], index, pos));
				} // the next square has a black piece ?
				else if (allPieces[pos].isBlack() && allPieces[pos].getType() != PieceType.KING) {
					// allMoves.add(new
					// MoveBuilder().isCapture(true).capturedPiece(allPieces[pos])
					// .buildMove(allPieces[index], index, pos));
					captureMoves.add(new MoveBuilder().isCapture(true)
							.capturedPiece(allPieces[pos])
							.buildMove(allPieces[index], index, pos));
				} else if (!allPieces[pos].isBlack()) {
					pos = index;
					continue; // if the piece is white, go on
				}
			} else {
				// the next square is empty ?
				if (allPieces[pos] == null) {
					// allMoves.add(new MoveBuilder().buildMove(allPieces[index],
					// index, pos));
					normalMoves.add(new MoveBuilder().buildMove(
							allPieces[index], index, pos));
				} // the next square has a white piece ?
				else if (!allPieces[pos].isBlack() && allPieces[pos].getType() != PieceType.KING) {
					// allMoves.add(new
					// MoveBuilder().isCapture(true).capturedPiece(allPieces[pos])
					// .buildMove(allPieces[index], index, pos));
					captureMoves.add(new MoveBuilder().isCapture(true)
							.capturedPiece(allPieces[pos])
							.buildMove(allPieces[index], index, pos));
				} else if (allPieces[pos].isBlack()) {
					pos = index;
					continue; // if the piece is black, go on
				}

			}

			pos = index;
		}
	}

	private int QueenMoveCheckerModeSelection(int index, int mode) {

		// Queen Legal Moves are combination of Rook and Bishop allPieces except
		// CASTLING

		if (mode == 0) {
			// index = index - 0x09; // right up cross
			index = index - (0x10 - 0x01);
		} else if (mode == 1) {
			// index = index + 0x09; // left down cross
			index = index + (0x10 - 0x01);
		} else if (mode == 2) {
			// index = index - 0x11; // left up cross
			index = index - (0x10 + 0x01);
		} else if (mode == 3) {
			// index = index + 0x11; // right down cross
			index = index + (0x10 + 0x01);
		} else if (mode == 4) {
			index = index - 0x10;
		} else if (mode == 5) {
			index = index + 0x10;
		} else if (mode == 6) {
			index++;
		} else if (mode == 7) {
			--index;
		}
		return index;
	}

	private void QueenMoveChecker(int index, boolean isWhite) {
		// TODO I will add the property of changing the board
		int pos = index;
		for (int i = 0; i < 8; i++) {
			while (true) {

				pos = QueenMoveCheckerModeSelection(pos, i);
				if (!((pos & 0x88) == 0)) {
					break; // is it in the board ?
				}
				if (isWhite) {
					// the next square is empty ?
					if (allPieces[pos] == null) {
						// allMoves.add(new
						// MoveBuilder().buildMove(allPieces[index],
						// index, pos));
						normalMoves.add(new MoveBuilder().buildMove(
								allPieces[index], index, pos));
					} // the next square has a black piece ?
					else if (allPieces[pos].isBlack() && allPieces[pos].getType() != PieceType.KING) {
						// allMoves.add(new
						// MoveBuilder().isCapture(true).capturedPiece(allPieces[pos])
						// .buildMove(allPieces[index], index, pos));
						captureMoves.add(new MoveBuilder().isCapture(true)
								.capturedPiece(allPieces[pos])
								.buildMove(allPieces[index], index, pos));
						break;
					} // the next square has a white piece ?
					else if (!(allPieces[pos].isBlack())) {
						break;
					}

				} else {
					// the next square is empty ?
					if (allPieces[pos] == null) {
						// allMoves.add(new
						// MoveBuilder().buildMove(allPieces[index],
						// index, pos));
						normalMoves.add(new MoveBuilder().buildMove(
								allPieces[index], index, pos));
					} // the next square has a white piece ?
					else if (!allPieces[pos].isBlack()  && allPieces[pos].getType() != PieceType.KING) {
						// allMoves.add(new
						// MoveBuilder().isCapture(true).capturedPiece(allPieces[pos])
						// .buildMove(allPieces[index], index, pos));
						captureMoves.add(new MoveBuilder().isCapture(true)
								.capturedPiece(allPieces[pos])
								.buildMove(allPieces[index], index, pos));
						break;
					} // the next square has a black piece ?
					else if ((allPieces[pos].isBlack())) {
						break;
					}

				}

			}
			pos = index;
		}
	}

	private int KingMoveCheckerModeSelection(int index, int mode) {

		/*
		 * King Legal Moves
		 * 
		 * (-11)(-10)(-9) (-1) X (+1) (+9)(+10)(+11)
		 */

		if (mode == 0) {
			index = index - (0x10 - 0x01);
		} else if (mode == 1) {
			index = index + (0x10 - 0x01);
		} else if (mode == 2) {
			index = index - (0x10 + 0x01);
		} else if (mode == 3) {
			index = index + (0x10 + 0x01);
		} else if (mode == 4) {
			index = index - 0x10;
		} else if (mode == 5) {
			index = index + 0x10;
		} else if (mode == 6) {
			index++;
		} else if (mode == 7) {
			--index;
		}
		return index;
	}

	private void KingMoveChecker(int index, boolean isWhite) {
		int pos = index;
		for (int i = 0; i < 8; i++) {

			pos = KingMoveCheckerModeSelection(pos, i);
			if (!((pos & 0x88) == 0)) {
				pos = index;
				continue; // is it in the board ? CONTINUE with the other mode
			}
			Piece nextPiece = allPieces[pos];
			Piece movingKing = allPieces[index];

			if (!isWhite) { // Is it Black ?
				// the next square is empty ?
				if (nextPiece == null) {
					normalMoves.add(new MoveBuilder().buildMove(
							movingKing, index, pos));
				} // the next square has a white piece ?
				else if (!nextPiece.isBlack()) {
					captureMoves.add(new MoveBuilder().isCapture(true)
							.capturedPiece(nextPiece)
							.buildMove(movingKing, index, pos));
				} // CASTLING from the right side
				else if (chessBoard.isBlackCastleKingSideLegal()
						&& index == 0x74 && // if the KING is in the initial
						// poistion ?
						allPieces[index + 1] == null &&
						/* if the right square of the KING is empty ? */
						allPieces[index + 2] == null &&
						/* if the second right square of the KING is empty ? */
						allPieces[index + 3].getType() == PieceType.ROOK) {
					/*
					 * if the ROOK is in the initail position ?
					 */

					castlingMoves.add(new MoveBuilder().isKingSideCastle(true)
							.buildMove(movingKing, index, index + 3));
					/*
					 * Move the KING to the ROOK 's side
					 */

				} // CASTLING from the left side
				else if (chessBoard.isBlackCastleQueenSideLegal()
						&& index == 0x74
						&&
						// if the KING is in the initial poistion ?
						allPieces[index - 1] == null
						&&
						// if the right square of the KING is empty ?
						allPieces[index - 2] == null
						&&
						// if the second right square of the KING is empty ?
						allPieces[index - 3] == null
						&&
						// if the third right square of the KING is empty ?
						allPieces[index - 4] != null
						&& allPieces[index - 4].getType() == PieceType.ROOK) {
					// if the ROOK is in the initail position ?

					castlingMoves.add(new MoveBuilder().isQueenSideCastle(true)
							.buildMove(movingKing, index, index - 4));

					/*
					 * Move the KING to the ROOK 's side
					 */

				} else if (nextPiece.isBlack()) {
					continue;
				}

			} else { // king is white
				// the next square is empty ?
				if (nextPiece == null) {
					normalMoves.add(new MoveBuilder().buildMove(
							movingKing, index, pos));
				} // the next square has a white piece ?
				else if (nextPiece.isBlack()) {
					captureMoves.add(new MoveBuilder().isCapture(true)
							.capturedPiece(nextPiece)
							.buildMove(movingKing, index, pos));
				} // CASTLING from the right side
				else if (chessBoard.isWhiteCastleKingSideLegal() && index == 0x04 &&
				/*
				 * if the KING is in the initial poistion ?
				 */
				allPieces[index + 1] == null &&
				/*
				 * if the right square of the KING is empty ?
				 */
				allPieces[index + 2] == null &&
				/*
				 * if the second right square of the KING is empty ?
				 */
				allPieces[index + 3] != null
						&& allPieces[index + 3].getType() == PieceType.ROOK) {
					/*
					 * if the ROOK is in the initail position ?
					 */

					castlingMoves.add(new MoveBuilder().isKingSideCastle(true)
							.buildMove(movingKing, index, index + 3));
					/*
					 * Move the KING to the ROOK's side
					 */

				} // CASTLING from the left side
				else if (chessBoard.isWhiteCastleQueenSideLegal() && index == 0x04
						&& // if the KING is in the initial poistion ?
						allPieces[index - 1] == null
						&& // if the right square
							// of
							// the KING is empty ?
						allPieces[index - 2] == null
						&& // if the second right
							// square of the KING is
							// empty ?
						allPieces[index - 3] == null
						&& // if the third right
							// square of the KING is
							// empty ?
						allPieces[index - 4] != null
						&& allPieces[index - 4].getType() == PieceType.ROOK) {
					/*
					 * if the ROOK is in the initail position ?
					 */

					castlingMoves.add(new MoveBuilder().isQueenSideCastle(true)
							.buildMove(movingKing, index, index - 4));
					/*
					 * Move the KING to the ROOK's side
					 */

				} else if (!nextPiece.isBlack()) {
					pos = index;
					continue;
				}

			}

			pos = index;
		}
	}

	ArrayList<Move> kingCheckCheckerMoves = new ArrayList<Move>();

	private int KingCheckCheckerModeSelection(int index, int mode) {

		// TODO HEX numbers should be changed!!!
		/*
		 * KingCheck Legal Moves
		 */
		/*
		 * // Queen Moves if (mode == 0) index = index - 0x09; // right up cross
		 * else if(mode == 1) index = index + 0x09; // left down cross else
		 * if(mode == 2) index = index - 0x11; // left up cross else if(mode ==
		 * 3) index = index + 0x11; // right down cross else if(mode == 4) index
		 * = index - 0x10; else if(mode == 5) index = index + 0x10; else if(mode
		 * == 6) index++; else if(mode == 7) --index; // Knight Moves else
		 * if(mode == 8) index = index - 0x08; else if(mode == 9) index = index
		 * + 0x08; else if(mode == 10) index = index - 0x19; else if(mode == 11)
		 * index = index + 0x19; else if(mode == 12) index = index - 0x21; else
		 * if(mode == 13) index = index + 0x21; else if(mode == 14) index =
		 * index - 0x12; else if(mode == 15) index = index + 0x12;
		 * //***********************
		 */
		// Modes for Queen and Rook

		if (mode == 0) {
			index = index - 0x10;
		} else if (mode == 1) {
			index = index + 0x10;
		} else if (mode == 2) {
			index++;
		} else if (mode == 3) {
			--index;
		} // Modes for Queen and Bishop
		else if (mode == 4) {
			// index = index - 0x09; // right up cross
			index = index - (0x10 - 0x01);
		} else if (mode == 5) {
			// index = index + 0x09; // left down cross
			index = index + (0x10 - 0x01);
		} else if (mode == 6) {
			// index = index - 0x11; // left up cross
			index = index - (0x10 + 0x01);
		} else if (mode == 7) {
			// index = index + 0x11; // right down cross
			index = index + (0x10 + 0x01);
		} // Modes for Queen and King
		else if (mode == 8) {
			index = index - 0x10;
		} else if (mode == 9) {
			index = index + 0x10;
		} else if (mode == 10) {
			index++;
		} else if (mode == 11) {
			--index;
		} else if (mode == 12) {
			// index = index - 0x09; // right up cross
			index = index - (0x10 - 0x01);
		} else if (mode == 13) {
			// index = index + 0x09; // left down cross
			index = index + (0x10 - 0x01);
		} else if (mode == 14) {
			// index = index - 0x11; // left up cross
			index = index - (0x10 + 0x01);
		} else if (mode == 15) {
			// index = index + 0x11; // right down cross
			index = index + (0x10 + 0x01);
		} // Modes for Knight
		else if (mode == 16) {
			// index = index - 0x08;
			index = index - (0x10 - 0x02);
		} else if (mode == 17) {
			// index = index + 0x08;
			index = index + (0x10 - 0x02);
		} else if (mode == 18) {
			// index = index - 31;
			index = index - (0x20 - 0x01);
		} else if (mode == 19) {
			// index = index + 0x19;
			index = index + (0x20 - 0x01);
		} else if (mode == 20) {
			// index = index - 0x21;
			index = index - (0x20 + 0x01);
		} else if (mode == 21) {
			// index = index + 0x21;
			index = index + (0x20 + 0x01);
		} else if (mode == 22) {
			// index = index - 0x12;
			index = index - (0x10 + 0x02);
		} else if (mode == 23) {
			// index = index + 0x12;
			index = index + (0x10 + 0x02);
		}

		return index;
	}

	public boolean isWhiteInCheck(int index, boolean isWhite) {

		int pos = index;
		for (int i = 0; i < 24; i++) {
			while (true) {

				pos = KingCheckCheckerModeSelection(pos, i);
				if (!((pos & 0x88) == 0)) {
					break; // is it in the board ?
				}
				if (isWhite) {

					if (allPieces[pos].isBlack()) {
						if (i < 4) {
							if ((allPieces[pos].getType() == PieceType.QUEEN)
									|| (allPieces[pos].getType() == PieceType.ROOK)) {
								return true;
							}

						} else if (i >= 4 && i < 8) {
							if ((allPieces[pos].getType() == PieceType.QUEEN)
									|| (allPieces[pos].getType() == PieceType.BISHOP)) {
								return true;
							}

						} else if (i >= 8 && i < 16) {
							if (i == 13 || i == 15) { // cross up left and right
								if ((allPieces[pos].getType() == PieceType.QUEEN)
										|| (allPieces[pos].getType() == PieceType.KING)
										|| (allPieces[pos].getType() == PieceType.PAWN)) {
									return true; // especially checking for PAWN
								}
								break;
							} else if ((allPieces[pos].getType() == PieceType.QUEEN)
									|| (allPieces[pos].getType() == PieceType.KING)) {
								return true;
							}
							break; // King can move only one square in each move
						} else if (i >= 16 && i < 24) {
							if ((allPieces[pos].getType() == PieceType.KNIGHT)) {
								return true;
							}
							break;
						}
					} else if (allPieces[pos].isBlack()) {
						break; // if the piece is Black, stop searching
					}

				}

			}
			pos = index;
		}

		return false;
	}

	public boolean isBlackInCheck(int index, boolean isWhite) {

		int pos = index;
		for (int i = 0; i < 24; i++) {
			while (true) {

				pos = KingCheckCheckerModeSelection(pos, i);
				if (!((pos & 0x88) == 0)) {
					break; // is it in the board ?
				}
				if (!isWhite) {

					if (allPieces[pos] != null && !allPieces[pos].isBlack()) {
						if (i < 4) {
							if ((allPieces[pos].getType() == PieceType.QUEEN)
									|| (allPieces[pos].getType() == PieceType.ROOK)) {
								return true;
							}

						} else if (i >= 4 && i < 8) {
							if ((allPieces[pos].getType() == PieceType.QUEEN)
									|| (allPieces[pos].getType() == PieceType.BISHOP)) {
								return true;
							}

						} else if (i >= 8 && i < 16) {
							if (i == 12 || i == 14) { // cross up left and right
								if ((allPieces[pos].getType() == PieceType.QUEEN)
										|| (allPieces[pos].getType() == PieceType.KING)
										|| (allPieces[pos].getType() == PieceType.PAWN)) {
									return true; // especially checking for PAWN
								}
								break;
							} else if ((allPieces[pos].getType() == PieceType.QUEEN)
									|| (allPieces[pos].getType() == PieceType.KING)) {
								return true;
							}
							break; // King can move only one square in each move
						} else if (i >= 16 && i < 24) {
							if ((allPieces[pos].getType() == PieceType.KNIGHT)) {
								return true;
							}
							break;
						}
					} else if (allPieces[pos] != null
							&& !allPieces[pos].isBlack()) {
						break; // if the piece is White, stop searching
					}

				}

			}
			pos = index;
		}

		// TODO I will add the code for Black Pieces
		return false;
	}

	int getKingPosition(boolean isWhiteKing) throws RuntimeException {
		Piece[] pieces;
		if (isWhiteKing)
			pieces = whitePieces;
		else
			pieces = blackPieces;

		for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
			for (int file = 0x00; file < 0x08; file++) {
				int i = file | rank;
				if (pieces[i] != null && pieces[i].getType() == PieceType.KING)
					return i;
			}
		}
		// if king does not exists, our board is in an invalid state
		throw new RuntimeException("King not found");
	}


	public boolean isInCheckMate(boolean isWhitePlayer, int positionOfKing) {
		boolean weCanEscapeCheck = false;
		// check all sourrounding squares of original position, if we are in check
		for (PseudoMove pm: PseudoMove.values()){
			int newKingPosition = positionOfKing + pm.indexIncrement;
			if ((newKingPosition & 0x88) == 0) {
				// we are inside the board
				if (!isInCheck(newKingPosition, isWhitePlayer)) {
					weCanEscapeCheck = true;
				}
			}
		}
		return !weCanEscapeCheck;
	}

	public boolean isInCheck(int kingPosition, boolean isKingWhite) {
		// checking if an opponent piece can capture the king, by virtual
		// substitution of the king with another piece

		for (PieceType type : PieceType.values()) {
			// convert pieceType to piece of opposite color than the king
			Piece matchingPiece = Piece.changePieceColor(type, !isKingWhite);
			int[] captureIndexes;
			PseudoMove[] pseudoMoves = new PseudoMove[8];
			switch (matchingPiece) {
			case BLACK_PAWN:
				// black pawn can capture diagonally downwards, so look for pawns above king
				captureIndexes = new int[] {
						PseudoMove.RANK_UP_FILE_DOWN.indexIncrement,
						PseudoMove.RANK_UP_FILE_UP.indexIncrement };
				// captureIndexes should be initialized for either of the pawn
				// colors
				for (int inc : captureIndexes) {
					if ((kingPosition + inc & 0x88) != 0)
						continue;
					Piece investigatedPiece = allPieces[kingPosition + inc];
					if (investigatedPiece != null
							&& investigatedPiece == matchingPiece) {
						return true;
					}
				}
				break;
			case WHITE_PAWN:
				// white pawn can capture diagonally upwards, so look for pawns underneath king
				captureIndexes = new int[] {
						PseudoMove.RANK_DOWN_FILE_DOWN.indexIncrement,
						PseudoMove.RANK_DOWN_FILE_UP.indexIncrement };

				// captureIndexes should be initialized for either of the pawn
				// colors
				for (int inc : captureIndexes) {
					if ((kingPosition + inc & 0x88) != 0)
						continue;
					Piece investigatedPiece = allPieces[kingPosition + inc];
					if (investigatedPiece != null
							&& investigatedPiece == matchingPiece) {
						return true;
					}
				}
				break;
			case BLACK_KNIGHT:
			case WHITE_KNIGHT:
				// fill capture indexes with knightmoves
				captureIndexes = new int[] {
						PseudoMove.FILE_UP.indexIncrement
								+ PseudoMove.RANK_UP.indexIncrement * 2,
						PseudoMove.FILE_UP.indexIncrement * 2
								+ PseudoMove.RANK_UP.indexIncrement,

						PseudoMove.FILE_UP.indexIncrement
								+ PseudoMove.RANK_DOWN.indexIncrement * 2,
						PseudoMove.FILE_UP.indexIncrement * 2
								+ PseudoMove.RANK_DOWN.indexIncrement,

						PseudoMove.FILE_DOWN.indexIncrement
								+ PseudoMove.RANK_UP.indexIncrement * 2,
						PseudoMove.FILE_DOWN.indexIncrement * 2
								+ PseudoMove.RANK_UP.indexIncrement,

						PseudoMove.FILE_DOWN.indexIncrement
								+ PseudoMove.RANK_DOWN.indexIncrement * 2,
						PseudoMove.FILE_DOWN.indexIncrement * 2
								+ PseudoMove.RANK_DOWN.indexIncrement, };
				// see if any knights of the opposite color are in these
				// positions
				for (int inc : captureIndexes) {
					if ((kingPosition + inc & 0x88) != 0)
						continue;
					Piece investigatedPiece = allPieces[kingPosition + inc];
					if (investigatedPiece != null
							&& investigatedPiece == matchingPiece) {
						return true;
					}
				}
				break;

			case BLACK_BISHOP:
			case WHITE_BISHOP:
				// use helper method to look through all possible diagonals
				pseudoMoves = new PseudoMove[] {
						PseudoMove.RANK_DOWN_FILE_DOWN,
						PseudoMove.RANK_DOWN_FILE_UP,
						PseudoMove.RANK_UP_FILE_DOWN,
						PseudoMove.RANK_UP_FILE_UP };

				for (PseudoMove direction : pseudoMoves) {
					Pair<Boolean, Integer> searchResult = searchForPieceInDirection(kingPosition, direction,
							PieceType.BISHOP, !isKingWhite, allPieces); 
					if (searchResult.getFirst()) {
						return true;
					}
				}
				break;

			case BLACK_ROOK:
			case WHITE_ROOK:
				// use helper method to look through all possible straight lines
				pseudoMoves = new PseudoMove[] { PseudoMove.RANK_DOWN,
						PseudoMove.RANK_UP, PseudoMove.FILE_DOWN,
						PseudoMove.FILE_UP };

				for (PseudoMove direction : pseudoMoves) {
					Pair<Boolean, Integer> searchResult = searchForPieceInDirection(kingPosition, direction,
							PieceType.ROOK, !isKingWhite, allPieces); 
					if (searchResult.getFirst()) {
						return true;
					}
				}

				break;

			case BLACK_QUEEN:
			case WHITE_QUEEN:
				// use helper method to look through all possible diagonals and
				// straight lines
				pseudoMoves = new PseudoMove[] { PseudoMove.RANK_DOWN,
						PseudoMove.RANK_UP, PseudoMove.FILE_DOWN,
						PseudoMove.FILE_UP, PseudoMove.RANK_DOWN_FILE_DOWN,
						PseudoMove.RANK_DOWN_FILE_UP,
						PseudoMove.RANK_UP_FILE_DOWN,
						PseudoMove.RANK_UP_FILE_UP };

				for (PseudoMove direction : pseudoMoves) {
					Pair<Boolean, Integer> searchResult =searchForPieceInDirection(kingPosition, direction,
							PieceType.QUEEN, !isKingWhite, allPieces);
					if (searchResult.getFirst()) {
						return true;
					}
				}
				break;
			case BLACK_KING:
			case WHITE_KING:
				for (PseudoMove pm: PseudoMove.values()){
					int investigatedIndex = kingPosition + pm.indexIncrement; 
					if ((investigatedIndex & 0x88) != 0)
						continue;
					Piece investigatedPiece = allPieces[investigatedIndex];
					if (investigatedPiece != null
							&& investigatedPiece == matchingPiece) {
						return true;
					}
				}
				break;
			default:
				return false;
			}
		} // switch end
		return false;
	}

	/**
	 * Looks for a type and color of piece in a direction through the board.
	 * 
	 * @param direction
	 * @return A pair with a boolean :True, if a piece of the chosen type and color was found in that
	 *         direction on the board, false if not.
	 *         and an Integer that is the position of the first found piece, or Move.UNKNOWN_POSITION if no piece was found
	 */
	public static Pair<Boolean, Integer> searchForPieceInDirection(int startPosition,
			PseudoMove direction, PieceType type, boolean isPieceWhite, 
			Piece[] searchedArray) {
		int nextPosition = startPosition;
		Piece matchingPiece = Piece.changePieceColor(type, isPieceWhite);

		nextPosition = nextPosition + direction.indexIncrement;
		while ((nextPosition & 0x88) == 0) {
			Piece investigatedPiece = searchedArray[nextPosition];
			if (investigatedPiece != null) {
				// if a piece that is not the matching piece is found, 
				// stop looking, as the piece is
				// blocking the path further
				
				if (investigatedPiece == matchingPiece) {
					return new Pair<Boolean, Integer>(true, nextPosition);
				}else {
					return new Pair<Boolean, Integer>(false, Move.UNKNOWN_POSITION);
			}
				}
			nextPosition = nextPosition + direction.indexIncrement;
		}
		return new Pair<Boolean, Integer>(false, Move.UNKNOWN_POSITION);
	}

	

}
