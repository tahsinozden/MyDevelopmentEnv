package board;

public class GameEndedException extends RuntimeException {
	
	private boolean isWhitePlayerWinner;

	public GameEndedException(String string, boolean isWhitePlayerWinner) {
		super(string);
		this.isWhitePlayerWinner = isWhitePlayerWinner;
	}
	
	public boolean isWhitePlayerWinner(){
		return this.isWhitePlayerWinner;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
