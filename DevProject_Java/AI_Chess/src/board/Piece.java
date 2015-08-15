package board;

import java.util.HashMap;

/**
 * @author Rasmus
 * 
 * An class for pieces that can be used with a ChessBoard 
 * This class is a simple wrapper for an integer value. 
 */
public enum Piece {
	
	WHITE_PAWN(0x01), WHITE_KNIGHT(0x02), WHITE_BISHOP(0x03), 
	WHITE_ROOK(0x04), WHITE_QUEEN(0x05), WHITE_KING(0x06), 
	
	BLACK_PAWN(0x21), BLACK_KNIGHT(0x22), BLACK_BISHOP(0x23),
	BLACK_ROOK(0x24), BLACK_QUEEN(0x25), BLACK_KING(0x26);
	
	/**
	 * map to allow easy and fast conversion from integer to Piecetype
	 */
	private static HashMap<Integer, PieceType> pieceTypeMap;
		
	static {
		pieceTypeMap = new HashMap<Integer, PieceType>();
		pieceTypeMap.put(WHITE_PAWN.data, PieceType.PAWN);
		pieceTypeMap.put(BLACK_PAWN.data, PieceType.PAWN);
		pieceTypeMap.put(WHITE_BISHOP.data, PieceType.BISHOP);
		pieceTypeMap.put(BLACK_BISHOP.data, PieceType.BISHOP);
		pieceTypeMap.put(WHITE_KNIGHT.data, PieceType.KNIGHT);
		pieceTypeMap.put(BLACK_KNIGHT.data, PieceType.KNIGHT);
		pieceTypeMap.put(WHITE_ROOK.data, PieceType.ROOK);
		pieceTypeMap.put(BLACK_ROOK.data, PieceType.ROOK);
		pieceTypeMap.put(WHITE_QUEEN.data, PieceType.QUEEN);
		pieceTypeMap.put(BLACK_QUEEN.data, PieceType.QUEEN);
		pieceTypeMap.put(WHITE_KING.data, PieceType.KING);
		pieceTypeMap.put(BLACK_KING.data, PieceType.KING);
	}
	
	/**
	 * Map that allows easy convertion from integer to Piece
	 */
	private static HashMap<Integer, Piece> pieceMap;
	
	static {
		pieceMap = new HashMap<Integer, Piece>();
		for (Piece p: Piece.values()){
			pieceMap.put(p.data, p);
		}
	}
	
	/**
	 * Map that allows easy convertion from Character to Piece
	 */
	private static HashMap<Character, Piece> characterPieceMap = new HashMap<Character, Piece>();
	
	static {
		for (Piece p: Piece.values()){
			characterPieceMap.put(p.toString().charAt(0), p);
		}
	}
	
	
	/**
	 * A single byte that holds onformation about the Piece color and type
	 */
	private int data;
	/**
	 * Bitmask that can be used to determine the color of the Piece.
	 * 
	 *  ((somePiece & BLACK_COLOR_MASK) == BLACK_COLOR_MASK) is true when the Piece is black 
	 * 
	 * 0b00100000
	 */
	public static final int BLACK_COLOR_MASK = 0x20; // 32 or 0b00100000
	
	/**
	 * Constructs a new Piece with a byte that indicates the type and color.
	 * 
	 * @param data This byte holds information about the type and color of the Piece.
	 * The 4 least significant bytes designate the Piece type, one of:
	 * {@link Piece#WHITE_PAWN}, 
	 * {@link Piece#WHITE_KNIGHT}, 
	 * {@link Piece#WHITE_BISHOP}, 
	 * {@link Piece#WHITE_ROOK}, 
	 * {@link Piece#WHITE_QUEEN}, 
	 * {@link Piece#WHITE_KING}
	 */
	private Piece(int data){
		this.data = data;
	}
	
	public int getData(){
		return this.data;
	}
	// I think return (data ^ BLACK_COLOR_MASK) == 0; should be return (data & BLACK_COLOR_MASK) == BLACK_COLOR_MASK;
	public boolean isBlack(){
		// if the 6th bit is 1, color is black, else color is white
		return (data & BLACK_COLOR_MASK) == BLACK_COLOR_MASK;
	}
	
	/**
	 * Gets a PieceType value for the Piece.
	 * Use this method to check for type like so:
	 * if (somePiece.getType == PieceType.QUEEN)
	 * 
	 * @return An integer value corresponding to the PieceType of the Piece
	 */
	public PieceType getType(){
		return pieceTypeMap.get(this.data);
	}
	
	public static Piece getPiece(int pieceData){
		return pieceMap.get(pieceData);
	}
	
	/**
	 * Get a Piece from a character
	 * 
	 * @param pieceChar the character representing the Piece
	 * @return A Piece that corresponds to that character, 
	 * according to SAN and assuming english language for Piece names.
	 */
	public static Piece getPiece (char pieceChar){
		return characterPieceMap.get(pieceChar);
	}
	
	@Override
	public String toString(){
		
		String pieceString = "";
		
		switch (this.getType()) {
		case PAWN:
			pieceString += 'P';
			break;
		case KNIGHT:
			pieceString += 'N';
			break;
		case BISHOP:
			pieceString += 'B';
			break;
		case ROOK:
			pieceString += 'R';
			break;
		case QUEEN:
			pieceString += 'Q';
			break;
		case KING:
			pieceString += 'K';
			break;
		default:
			pieceString +='?';
			break;
		}
	
		if (this.isBlack()){
			pieceString = pieceString.toLowerCase();
		}
		return pieceString;
		
	}
	
	public static Piece changePieceColor(Piece oldPiece, boolean isWhiteColor){
		// first mask out the color bits, and set the new color
		int maskedPieceData = oldPiece.data & 0x0F;
		if (!isWhiteColor){
			// white color is just 0's, but we need to set the black color bit for the black color
			maskedPieceData = maskedPieceData | BLACK_COLOR_MASK;
		}
		return getPiece(maskedPieceData);
	}
	
	public static Piece changePieceColor(PieceType oldPieceType, boolean isWhiteColor){
		int pieceTypeData = oldPieceType.getData();
		if (!isWhiteColor){
			pieceTypeData = pieceTypeData | BLACK_COLOR_MASK;
		}
		return getPiece(pieceTypeData);
	}
	
	

}


