package board;

import static board.PieceType.BISHOP;
import static board.PieceType.KNIGHT;
import static board.PieceType.PAWN;
import static board.PieceType.QUEEN;
import static board.PieceType.ROOK;
import static board.StaticValues.White_Pawn;

/**
 *
 * @author klausjensen
 */
public class StaticValues {
	
	private static final boolean usePieceValues = true;

    static int[] White_Queen = new int[ChessBoard.BOARD_SIZE];
    static int[] White_Bishop = new int[ChessBoard.BOARD_SIZE];
    static int[] White_Rook = new int[ChessBoard.BOARD_SIZE];
    static int[] White_Knight = new int[ChessBoard.BOARD_SIZE];
    static int[] White_Pawn = new int[ChessBoard.BOARD_SIZE];
    static int[] Black_Queen = new int[ChessBoard.BOARD_SIZE];
    static int[] Black_Bishop = new int[ChessBoard.BOARD_SIZE];
    static int[] Black_Rook = new int[ChessBoard.BOARD_SIZE];
    static int[] Black_Knight = new int[ChessBoard.BOARD_SIZE];
    static int[] Black_Pawn = new int[ChessBoard.BOARD_SIZE];

//white is 0, black is 1
    public static int calculateBoardValue(ChessBoard board, boolean forWhitePlayer) {
        Piece[] usedPieces;
        int whiteScore = calculatePieceArrayValue(board.getWhitePlayersPieces(), true);
        int blackScore = calculatePieceArrayValue(board.getBlackPlayersPieces(), false);
        int staticValue;
        if (forWhitePlayer) {
            staticValue = whiteScore-blackScore;
            
        } else {
           staticValue = blackScore-whiteScore;
        }

        if (board.isGameFinished() && (forWhitePlayer == board.isWhiteWinner())){
        	staticValue += 10000;
        }
        if (board.isGameFinished() && (forWhitePlayer != board.isWhiteWinner())){
        	staticValue -= 10000;
        }
        return staticValue;


    }

    private static int calculatePieceArrayValue(Piece[] pieces, boolean forWhite) {
        int res = 0;
        int[][] valueArrays = new int[5][];

        if (forWhite) {
            valueArrays[0] = White_Pawn;
            valueArrays[1] = White_Bishop;
            valueArrays[2] = White_Knight;
            valueArrays[3] = White_Rook;
            valueArrays[4] = White_Queen;
        } else {
            valueArrays[0] = Black_Pawn;
            valueArrays[1] = Black_Bishop;
            valueArrays[2] = Black_Knight;
            valueArrays[3] = Black_Rook;
            valueArrays[4] = Black_Queen;
        }


        for (int rank = 0x00; rank < 0x80; rank = rank + 0x10) {
            for (int file = 0x00; file < 0x08; file = file + 0x01) {
                int index = rank | file;
                Piece curPiece = pieces[index];

                if (curPiece != null) {
                    switch (curPiece.getType()) {
                        case PAWN:
                            res = res + valueArrays[0][index];
                            if (usePieceValues) res+=10;
                            break;
                        case BISHOP:
                            res = res + valueArrays[1][index];
                            if (usePieceValues) res+=33;
                            break;
                        case KNIGHT:
                            res = res + valueArrays[2][index];
                            if (usePieceValues) res+=32;
                            break;
                        case ROOK:
                            res = res + valueArrays[3][index];
                            if (usePieceValues) res+=50;
                            break;
                        case QUEEN:
                            res = res + valueArrays[4][index];
                            if (usePieceValues) res+=90;
                            break;
                        case KING:
                            break;
                        default:
                            //System.out.println("FAIL - calculatePieceArrayValue");
                            break;
                    }
                }
            }

        }


        // System.out.println("res"+res);


        return res;
    }

    //    PAWN(1), KNIGHT(2), BISHOP(3), 
//	ROOK(4), QUEEN(5), KING(6),
    static void WB(int place, int type, int value) {
        int white = place;
        int black = 119 - place;


        switch (type) {
            //pawn
            case 1:
                White_Pawn[white] = value;
                Black_Pawn[black] = value;
                break;
            case 2:
                White_Knight[white] = value;
                Black_Knight[black] = value;
                break;
            case 3:
                White_Bishop[white] = value;
                Black_Bishop[black] = value;
                break;
            case 4:
                White_Rook[white] = value;
                Black_Rook[black] = value;
                break;
            case 5:
                White_Queen[white] = value;
                Black_Queen[black] = value;
                break;
            default:
                //System.out.println("FAIL - WB");
                break;
        }


    }
//    PAWN(1), KNIGHT(2), BISHOP(3), 
//	ROOK(4), QUEEN(5), KING(6),

    static void FillArray() {
        //queen
        WB(112, 5, 2);
        WB(113, 5, 3);
        WB(114, 5, 4);
        WB(115, 5, 3);
        WB(116, 5, 4);
        WB(117, 5, 3);
        WB(118, 5, 3);
        WB(119, 5, 2);

        WB(96, 5, 2);
        WB(97, 5, 3);
        WB(98, 5, 4);
        WB(99, 5, 4);
        WB(100, 5, 4);
        WB(101, 5, 4);
        WB(102, 5, 3);
        WB(103, 5, 2);

        WB(80, 5, 3);
        WB(81, 5, 4);
        WB(82, 5, 4);
        WB(83, 5, 4);
        WB(84, 5, 4);
        WB(85, 5, 4);
        WB(86, 5, 4);
        WB(87, 5, 3);

        WB(64, 5, 3);
        WB(65, 5, 3);
        WB(66, 5, 4);
        WB(67, 5, 4);
        WB(68, 5, 4);
        WB(69, 5, 4);
        WB(70, 5, 3);
        WB(71, 5, 3);

        WB(48, 5, 2);
        WB(49, 5, 3);
        WB(50, 5, 3);
        WB(51, 5, 4);
        WB(52, 5, 4);
        WB(53, 5, 3);
        WB(54, 5, 3);
        WB(55, 5, 2);

        WB(32, 5, 2);
        WB(33, 5, 2);
        WB(34, 5, 2);
        WB(35, 5, 3);
        WB(36, 5, 3);
        WB(37, 5, 2);
        WB(38, 5, 2);
        WB(39, 5, 2);

        WB(16, 5, 2);
        WB(17, 5, 2);
        WB(18, 5, 2);
        WB(19, 5, 2);
        WB(20, 5, 2);
        WB(21, 5, 2);
        WB(22, 5, 2);
        WB(23, 5, 2);

        WB(0, 5, 0);
        WB(1, 5, 0);
        WB(2, 5, 0);
        WB(3, 5, 0);
        WB(4, 5, 0);
        WB(5, 5, 0);
        WB(6, 5, 0);
        WB(7, 5, 0);

        //rook

        WB(112, 4, 9);
        WB(113, 4, 9);
        WB(114, 4, 9);
        WB(115, 4, 11);
        WB(116, 4, 10);
        WB(117, 4, 11);
        WB(118, 4, 9);
        WB(119, 4, 9);

        WB(96, 4, 4);
        WB(97, 4, 6);
        WB(98, 4, 7);
        WB(99, 4, 9);
        WB(100, 4, 9);
        WB(101, 4, 7);
        WB(102, 4, 6);
        WB(103, 4, 4);

        WB(80, 4, 9);
        WB(81, 4, 10);
        WB(82, 4, 10);
        WB(83, 4, 11);
        WB(84, 4, 11);
        WB(85, 4, 10);
        WB(86, 4, 10);
        WB(87, 4, 9);

        WB(64, 4, 8);
        WB(65, 4, 8);
        WB(66, 4, 8);
        WB(67, 4, 9);
        WB(68, 4, 9);
        WB(69, 4, 8);
        WB(70, 4, 8);
        WB(71, 4, 8);

        WB(48, 4, 6);
        WB(49, 4, 6);
        WB(50, 4, 5);
        WB(51, 4, 6);
        WB(52, 4, 6);
        WB(53, 4, 5);
        WB(54, 4, 6);
        WB(55, 4, 6);

        WB(32, 4, 4);
        WB(33, 4, 5);
        WB(34, 4, 5);
        WB(35, 4, 5);
        WB(36, 4, 5);
        WB(37, 4, 5);
        WB(38, 4, 5);
        WB(39, 4, 4);

        WB(16, 4, 3);
        WB(17, 4, 4);
        WB(18, 4, 4);
        WB(19, 4, 6);
        WB(20, 4, 6);
        WB(21, 4, 4);
        WB(22, 4, 4);
        WB(23, 4, 3);

        WB(0, 4, 0);
        WB(1, 4, 0);
        WB(2, 4, 0);
        WB(3, 4, 0);
        WB(4, 4, 0);
        WB(5, 4, 0);
        WB(6, 4, 0);
        WB(7, 4, 0);

        //Bishop

        WB(112, 3, 2);
        WB(113, 3, 3);
        WB(114, 3, 4);
        WB(115, 3, 4);
        WB(116, 3, 4);
        WB(117, 3, 4);
        WB(118, 3, 3);
        WB(119, 3, 2);

        WB(96, 3, 4);
        WB(97, 3, 7);
        WB(98, 3, 7);
        WB(99, 3, 7);
        WB(100, 3, 7);
        WB(101, 3, 7);
        WB(102, 3, 7);
        WB(103, 3, 4);

        WB(80, 3, 3);
        WB(81, 3, 5);
        WB(82, 3, 6);
        WB(83, 3, 6);
        WB(84, 3, 6);
        WB(85, 3, 6);
        WB(86, 3, 5);
        WB(87, 3, 3);

        WB(64, 3, 3);
        WB(65, 3, 5);
        WB(66, 3, 7);
        WB(67, 3, 7);
        WB(68, 3, 7);
        WB(69, 3, 7);
        WB(70, 3, 5);
        WB(71, 3, 3);

        WB(48, 3, 4);
        WB(49, 3, 5);
        WB(50, 3, 6);
        WB(51, 3, 8);
        WB(52, 3, 8);
        WB(53, 3, 6);
        WB(54, 3, 5);
        WB(55, 3, 4);

        WB(32, 3, 4);
        WB(33, 3, 5);
        WB(34, 3, 5);
        WB(35, 3, -2);
        WB(36, 3, -2);
        WB(37, 3, 5);
        WB(38, 3, 5);
        WB(39, 3, 4);

        WB(16, 3, 5);
        WB(17, 3, 5);
        WB(18, 3, 5);
        WB(19, 3, 3);
        WB(20, 3, 3);
        WB(21, 3, 5);
        WB(22, 3, 5);
        WB(23, 3, 5);

        WB(0, 3, 0);
        WB(1, 3, 0);
        WB(2, 3, 0);
        WB(3, 3, 0);
        WB(4, 3, 0);
        WB(5, 3, 0);
        WB(6, 3, 0);
        //Knight
        WB(112, 2, -2);
        WB(113, 2, 2);
        WB(114, 2, 7);
        WB(115, 2, 9);
        WB(116, 2, 9);
        WB(117, 2, 7);
        WB(118, 2, 2);
        WB(119, 2, -2);

        WB(96, 2, 1);
        WB(97, 2, 4);
        WB(98, 2, 12);
        WB(99, 2, 13);
        WB(100, 2, 13);
        WB(101, 2, 12);
        WB(102, 2, 4);
        WB(103, 2, 1);

        WB(80, 2, 5);
        WB(81, 2, 11);
        WB(82, 2, 18);
        WB(83, 2, 19);
        WB(84, 2, 19);
        WB(85, 2, 18);
        WB(86, 2, 11);
        WB(87, 2, 5);

        WB(64, 2, 3);
        WB(65, 2, 10);
        WB(66, 2, 14);
        WB(67, 2, 14);
        WB(68, 2, 14);
        WB(69, 2, 14);
        WB(70, 2, 10);
        WB(71, 2, 3);

        WB(48, 2, 0);
        WB(49, 2, 5);
        WB(50, 2, 8);
        WB(51, 2, 9);
        WB(52, 2, 9);
        WB(53, 2, 8);
        WB(54, 2, 5);
        WB(55, 2, 0);

        WB(32, 2, -3);
        WB(33, 2, 1);
        WB(34, 2, 3);
        WB(35, 2, 4);
        WB(36, 2, 4);
        WB(37, 2, 3);
        WB(38, 2, 1);
        WB(39, 2, -3);

        WB(16, 2, -5);
        WB(17, 2, -3);
        WB(18, 2, -1);
        WB(19, 2, 0);
        WB(20, 2, 0);
        WB(21, 2, -1);
        WB(22, 2, -3);
        WB(23, 2, -5);

        WB(0, 2, -7);
        WB(1, 2, -5);
        WB(2, 2, -4);
        WB(3, 2, -2);
        WB(4, 2, -2);
        WB(5, 2, -4);
        WB(6, 2, -5);
        WB(7, 2, -7);

        //Pawn

        WB(96, 1, 7);
        WB(97, 1, 7);
        WB(98, 1, 13);
        WB(99, 1, 23);
        WB(100, 1, 26);
        WB(101, 1, 13);
        WB(102, 1, 7);
        WB(103, 1, 7);

        WB(80, 1, -2);
        WB(81, 1, -2);
        WB(82, 1, 4);
        WB(83, 1, 12);
        WB(84, 1, 15);
        WB(85, 1, 4);
        WB(86, 1, -2);
        WB(87, 1, -2);

        WB(64, 1, -3);
        WB(65, 1, -3);
        WB(66, 1, 2);
        WB(67, 1, 9);
        WB(68, 1, 11);
        WB(69, 1, 2);
        WB(70, 1, -3);
        WB(71, 1, -3);

        WB(48, 1, -4);
        WB(49, 1, -4);
        WB(50, 1, 0);
        WB(51, 1, 6);
        WB(52, 1, 8);
        WB(53, 1, 0);
        WB(54, 1, -4);
        WB(55, 1, -4);

        WB(32, 1, -4);
        WB(33, 1, -4);
        WB(34, 1, 0);
        WB(35, 1, 4);
        WB(36, 1, 6);
        WB(37, 1, 0);
        WB(38, 1, -4);
        WB(39, 1, -4);

        WB(16, 1, -1);
        WB(17, 1, -1);
        WB(18, 1, 1);
        WB(19, 1, 5);
        WB(20, 1, 6);
        WB(21, 1, 1);
        WB(22, 1, -1);
        WB(23, 1, -1);
    }
}
