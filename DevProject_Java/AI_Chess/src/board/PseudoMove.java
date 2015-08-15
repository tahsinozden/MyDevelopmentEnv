package board;

public enum PseudoMove {

	RANK_UP(0x10), RANK_DOWN(-0x10), FILE_UP(0x01), FILE_DOWN(-0x01), RANK_UP_FILE_UP(
			0x11), RANK_UP_FILE_DOWN(0x0F), RANK_DOWN_FILE_UP(0xFFFFFFF1), RANK_DOWN_FILE_DOWN(
			0xFFFFFFEF);

	public int indexIncrement;

	PseudoMove(int indexIncrement) {
		this.indexIncrement = indexIncrement;
	}
	
}
