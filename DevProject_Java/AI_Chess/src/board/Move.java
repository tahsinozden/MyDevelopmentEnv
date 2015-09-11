package board;

/**
 * Represents a chess move
 * 
 * @author Rasmus
 * 
 */
public class Move {

	public static final int UNKNOWN_POSITION = -1;

	/**
	 * flags to indicate special characteristics of the move
	 */
	private boolean isCapture, isKingSideCastle, isQueenSideCastle,
			isPawnPromotion, isCheck, isCheckMating;

	/**
	 * The Piece that is moving
	 */
	private Piece movingPiece;
	/**
	 * The Piece that is captured by the moving Piece, if any.
	 */
	private Piece capturedPiece;
	/**
	 * The index of the captured piece
	 */
	private int capturedPieceIndex;
	/**
	 * If this move is a pawn promotion, this is the piece that the pawn is
	 * promoted to
	 */
	private Piece promotedTo;
	/**
	 * The position of the moving Piece, before the move is performed
	 */
	private int oldPosition;

	/**
	 * Partial information about the moving pieces last position. These fields
	 * are used for pawn moves, where little or no knowledge of the moving
	 * pieces old position exists
	 */
	private int oldFile, oldRank;
	/**
	 * The position of the moving Piece, after the move is performed
	 */
	private int newPosition;

	/**
	 * Constructs a Move with all necessary information about the move
	 * 
	 * @param movingPiece
	 *            The Piece that is moving
	 * @param oldPosition
	 *            The old position of the movingPiece
	 * @param newPosition
	 *            The new posittion of the movingPiece
	 * @moveType an integer specifying the type of move.
	 * @param capturedPiece
	 *            if a Piece is captured in the move, set it here. Otherwise set
	 *            this to null
	 * @see Move#moveType
	 */
	public Move(Piece movingPiece, int oldPosition, int oldFile, int oldRank,
			int newPosition, Piece capturedPiece, Piece promotedTo,
			boolean isCapture, boolean isKingSideCastle,
			boolean isQueenSideCastle, boolean isPawnPromotion,
			boolean isCheck, boolean isCheckMating) {

		this.setMovingPiece(movingPiece);
		this.setOldPosition(oldPosition);
		this.setOldFile(oldFile);
		this.setOldRank(oldRank);
		this.setNewPosition(newPosition);
		this.setCapturedPiece(capturedPiece);
		this.promotedTo = promotedTo;
		this.isCapture = isCapture;
		this.isKingSideCastle = isKingSideCastle;
		this.isQueenSideCastle = isQueenSideCastle;
		this.isPawnPromotion = isPawnPromotion;
		this.isCheck = isCheck;
		this.isCheckMating = isCheckMating;
	}

	public int getStaticMoveValue() {
		// TODO here I would use the staticEvaluation class to calculate the
		// value of the move
		throw new RuntimeException("Not Implemented");
	}

	public int getOldPosition() {
		// if the old position is not set directly, but indirectly by the file
		// and rank, use those values instead.
		if (oldPosition == Move.UNKNOWN_POSITION && oldFile != UNKNOWN_POSITION
				&& oldRank != UNKNOWN_POSITION) {
			return oldFile | oldRank;
		}
		return oldPosition;
	}

	public void setOldPosition(int oldPosition) {
		this.oldPosition = oldPosition;
	}

	public void setOldFile(int oldFile) {
		this.oldFile = oldFile;
	}

	public int getOldRank() {
		return this.oldRank;
	}

	public int getOldFile() {
		return this.oldFile;
	}

	public void setOldRank(int oldRank) {
		this.oldRank = oldRank;
	}

	public int getNewPosition() {
		return newPosition;
	}

	public void setNewPosition(int newPosition) {
		this.newPosition = newPosition;
	}

	public Piece getMovingPiece() {
		return movingPiece;
	}

	public void setMovingPiece(Piece movingPiece) {
		this.movingPiece = movingPiece;
	}

	public synchronized int getCapturedPieceIndex() {
		return capturedPieceIndex;
	}

	public synchronized void setCapturedPieceIndex(int capturedPieceIndex) {
		this.capturedPieceIndex = capturedPieceIndex;
	}

	public Piece getCapturedPiece() {
		return capturedPiece;
	}

	public void setCapturedPiece(Piece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public boolean isKingSideCastle() {
		return isKingSideCastle;
	}

	public boolean isQueenSideCastle() {
		return isQueenSideCastle;
	}

	public boolean isPawnPromotion() {
		return isPawnPromotion;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public boolean isCheckMating() {
		return isCheckMating;
	}

	public String toCoordinateNotationString() {
		if (oldPosition == UNKNOWN_POSITION || newPosition == UNKNOWN_POSITION) {
			throw new IllegalAccessError(
					"move object does not contain information about new or old position of moving piece");
		}
		String returnString = "";
		returnString += ChessBoard.getFileAndRankString(oldPosition)
				+ ChessBoard.getFileAndRankString(newPosition);
		if (isPawnPromotion)
			returnString += "q";
		return returnString;
	}

	@Override
	public String toString() {
		String returnString = "";

		// check if castling
		if (isKingSideCastle || isQueenSideCastle) {
			if (isKingSideCastle) {
				returnString += "O-O";
			} else if (isQueenSideCastle) {
				returnString += "O-O-O";
			}
		}

		else if (isCapture || isPawnPromotion) {

			if (isCapture) {
				// add piece letter (except for pawns, add file-letter) and new
				// position.
				if (movingPiece.getType() != PieceType.PAWN) {
					returnString += movingPiece.toString().toUpperCase();
				} else {
					// when a pawn is capturing, add only the file from which it
					// came
					if (oldPosition != Move.UNKNOWN_POSITION) {
						returnString += ChessBoard.getFileString(oldPosition);
					} else {
						returnString += ChessBoard.getFileString(oldFile);
					}

				}
				// add 'x' for capture
				returnString += 'x';
			} else {
				// add piece letter (except for pawns) and new position.
				if (movingPiece.getType() != PieceType.PAWN) {
					returnString += movingPiece.toString().toUpperCase();
				}
				returnString += ChessBoard.getFileAndRankString(newPosition);
			}

			// now checking if we should append sign for pawn promotion
			if (isPawnPromotion) {
				if (promotedTo == null) {
					throw new RuntimeException(
							"Move is pawn promotion, but did not specify which piece it is promoted to");
				}
				returnString += "=" + promotedTo.toString().toUpperCase();
			}
		} else {
			// add piece letter if not pawn
			if (movingPiece.getType() != PieceType.PAWN) {
				returnString += movingPiece.toString().toUpperCase();
			}

		}
		// add destination file and rank
		returnString += ChessBoard.getFileAndRankString(newPosition);

		// check and checkmate can happen independently of the preceeding type
		// of the move.
		if (isCheck) {
			if (isCheckMating) {
				returnString += '#';
			} else { // move was only check
				returnString += '+';
			}
		}

		return returnString;
	}

	public static boolean isInternallyValid(Move m) {
		if ((m.movingPiece == null)
				|| (m.isCapture && m.capturedPiece == null)
				|| (m.oldPosition == Move.UNKNOWN_POSITION
						&& m.oldFile == Move.UNKNOWN_POSITION && m.oldRank == Move.UNKNOWN_POSITION)
				|| (m.newPosition == Move.UNKNOWN_POSITION)
				|| ((m.newPosition & 0x88) != 0)
				|| (m.oldPosition != Move.UNKNOWN_POSITION && (m.oldPosition & 0x88) != 0)
				|| (m.oldFile != Move.UNKNOWN_POSITION && (m.oldFile & 0x88) != 0)
				|| (m.oldRank != Move.UNKNOWN_POSITION && (m.oldRank & 0x88) != 0)
				|| (m.isPawnPromotion && m.promotedTo == null)
				|| (m.isCheckMating && !m.isCheck)) {
			return false;
		} else
			return true;
	}

	public String toDebugString() {
		return "Move [isCapture=" + isCapture + ", isKingSideCastle="
				+ isKingSideCastle + ", isQueenSideCastle=" + isQueenSideCastle
				+ ", isPawnPromotion=" + isPawnPromotion + ", isCheck="
				+ isCheck + ", isCheckMating=" + isCheckMating
				+ ", movingPiece=" + movingPiece
				+ ", capturedPiece=" + capturedPiece
				+ ", capturedPieceIndex=" + capturedPieceIndex
				+ ", promotedTo=" + promotedTo + ", oldPosition=" + oldPosition
				+ ", oldFile=" + oldFile + ", oldRank=" + oldRank
				+ ", newPosition=" + newPosition + "]";
	}
	
	
	
}
