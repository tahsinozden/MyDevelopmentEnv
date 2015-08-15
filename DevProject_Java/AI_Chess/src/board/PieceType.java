package board;

/**
 * @author Rasmus
 *
 */
public enum PieceType{
	
	PAWN(0x01), KNIGHT(0x02), BISHOP(0x03),
	ROOK(0x04), QUEEN(0x05), KING(0x06);
	
	
	/**
	 * Bitmask that can be used to convert a {@link Piece} to a {@PieceType} by bitwise AND.
	 * 
	 * (somePiece & TYPE_MASK) will result in a PieceType 
	 * 
	 * 0b00001111
	 */
	public static final int TYPE_MASK = 		0x0F;		// 15 or 0b00001111
	
	/**
	 * Type data
	 */
	private int data;
	
	
	private PieceType(int data){
		this.data = data;
	}
	
	public int getData(){
		return this.data;
	}
}
