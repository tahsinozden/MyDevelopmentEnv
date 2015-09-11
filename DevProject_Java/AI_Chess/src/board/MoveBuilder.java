package board;

/**
 * Helper class to create new instances of Move objects, 
 * where you only need to specify the needed parameters for the move.
 *<p>
 * Example usage:<p>
 * {@code Move newMove = new MoveBuilder().isCapture(true).capturedPiece(somePiece).buildMove(movingPiece, oldPosition, newPosition);}
 * <p>
 * This allows you to disregard all parameters that are not valid or neccesarry for the move, 
 * as they will be set to the default values contained in this class. 
 * 
 * @author Rasmus
 */
public class MoveBuilder{
	// default values
	private boolean isCapture = false, isKingSideCastle = false, isQueenSideCastle = false,
	isPawnPromotion = false, isCheck = false, isCheckMating = false;
	
	private Piece capturedPiece = null;
	private Piece promotedTo = null;
	
	private int oldFile = Move.UNKNOWN_POSITION;
	private int oldRank = Move.UNKNOWN_POSITION;
	
	public MoveBuilder () {}
	
	public Move buildMove(Piece movingPiece, int oldPosition, int newPosition){
		return new Move(movingPiece, oldPosition, oldFile, oldRank, newPosition, this.capturedPiece, this.promotedTo, 
				this.isCapture, this.isKingSideCastle, this.isQueenSideCastle, 
				this.isPawnPromotion, this.isCheck, this.isCheckMating);
	}
	
	public MoveBuilder capturedPiece(Piece _capturedPiece){
		this.capturedPiece = _capturedPiece;
		return this;
	}
	
	public MoveBuilder promotedTo(Piece _promotedTo){
		this.promotedTo = _promotedTo;
		return this;
	}
	
	public MoveBuilder isCapture(boolean _isCapture){
		this.isCapture = _isCapture;
		return this;
	}
	
	public MoveBuilder isKingSideCastle(boolean _isKingSideCastle){
		this.isKingSideCastle = _isKingSideCastle;
		return this;
	}
	
	public MoveBuilder isQueenSideCastle(boolean _isQueenSideCastle){
		this.isQueenSideCastle = _isQueenSideCastle;
		return this;
	}
	
	public MoveBuilder isPawnPromotion(boolean _isPawnPromotion){
		this.isPawnPromotion = _isPawnPromotion;
		return this;
	}
	
	public MoveBuilder isCheck(boolean _isCheck){
		this.isCheck = _isCheck;
		return this;
	}
	
	public MoveBuilder isCheckMating(boolean _isCheckMating){
		this.isCheckMating = _isCheckMating;
		return this;
	}
	
	public MoveBuilder oldFile(int _oldFile){
		this.oldFile = _oldFile;
		return this;
	}
	
	public MoveBuilder oldRank(int _oldRank){
		this.oldRank = _oldRank;
		return this;
	}

	
}
