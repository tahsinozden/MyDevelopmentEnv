package game;

import ab_algorithm.AB_Algorithm;
import board.AB_NodesGenerator;
import board.AB_StaticEvaluator;
import board.ChessBoard;
import board.GameEndedException;
import board.Move;
import board.StaticValues;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main game loop.
 * 
 * @author Bartek
 */
public class GameController {
	/*
	 * TODO I have also implemented many or all of these variables in the
	 * ChessBoard class, so we should remove them from one of the classes. I'm
	 * using them when converting A ChessBoard to and from Strings, but I'm open
	 * to suggestions :) Rasmus
	 * 
	 * I don't think that I can use any of those variables except for
	 * isWhitesTurn method. Bartek
	 */

	public static final char WHITE = 'w';
	public static final char BLACK = 'b';
	private static final int ENGINE_TURN = 0;
	private static final int OPPONENT_TURN = 1;
	private static int whosTurn;
	public static char whatColorWePlay;
	// debug fields
	public static final boolean IS_DEBUGGING = false;
	public static int numOfInvalidMovesInTurn = 0;
	public static int totalMovesGeneratedInTurn = 0;
	public static int movesPerformedInTurn = 0;
	// counters for exceptions from ChessBoard
	public static int numOfRuntimeExceptions = 0;
	public static int numOfGameEndedExceptions = 0;
	public static int numOfNoSuchElementExceptions = 0;
	

	/*
	 * Parameters for main: $1 'w' or 'b' - the color this engine is playing $2
	 * fen file containg the board state $3 time for turn in secounds Example:
	 * java -jar chessGame.jar w boardState.fen 10
	 */
	public static void main(String[] args) throws ParseException, IOException,
			GameEndedException {
		if (args.length < 3){
			System.out.println("Usage: ChessEngine.jar [color of the engines pieces - w|b ] [filename for fen formatted text file] [engine move time in seconds]");
			System.out.println("example: ChessEngine.jar b standard_chess_board.fen 10");
			return;
		}

		String fileNameForGameRecord = new SimpleDateFormat(
				"MM-dd-yyyy_HH.mm.ss").format(new Date());
		Path pathToFileForGameRecord = FileSystems.getDefault().getPath(
				fileNameForGameRecord.concat("_game_record.fen"));
		// initialize alfa beta algorithm objects
		AB_StaticEvaluator staticEvaluator = new AB_StaticEvaluator();
		AB_NodesGenerator nodesGenerator = new AB_NodesGenerator();
		AB_Algorithm<ChessBoard> ab_algorithm = new AB_Algorithm<ChessBoard>(
				nodesGenerator, staticEvaluator);

		Scanner inputFromWinBoard = new Scanner(System.in);

		// first parameter is either 'w' or 'b' indicating what colour this
		// engine is playing
		whatColorWePlay = args[0].charAt(0);
		// second parameter is a .fen file
		Path pathToFenFile = FileSystems.getDefault().getPath(args[1]);
		System.out.println("#INITIALIZATION# PATH TO FEN FILE FROM INPUT:" + pathToFenFile.toAbsolutePath().toString());
				 
		String fenFormattedString = null;
		try {
			// get fen-formatted string
			fenFormattedString = ChessBoard
					.getFENFormattedStringFromFile(pathToFenFile);
					 System.out.println("#INITIALIZATION# CONTENT OF FEN FILE: "+ fenFormattedString);
		} catch (IOException ex) {
			Logger.getLogger(GameController.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		// third parameter is time for turn in secounds
		int timeForTurnInSeconds = Integer.valueOf(args[2]);
		System.out.println("#INITIALIZATION# TIME FOR MOVE: "+ timeForTurnInSeconds);

		// create chess board object
		ChessBoard chessBoard = new ChessBoard(fenFormattedString);
		System.out.println(chessBoard.toString());
		// create and write to file
		chessBoard.writeToFile(pathToFileForGameRecord,
				chessBoard.isWhitesTurn());
		// find out whos turn is next, it does not necessarily have to whites
		// turn as we always initialize by loading a .fen file
		whosTurn = OPPONENT_TURN;
		if (chessBoard.isWhitesTurn() && whatColorWePlay == WHITE) {
			whosTurn = ENGINE_TURN;
		}
		if (!chessBoard.isWhitesTurn() && whatColorWePlay == BLACK) {
			whosTurn = ENGINE_TURN;
		}
		while (true) {

			switch (whosTurn) {
			case ENGINE_TURN:
				System.out.println("ENGINE TURN");
				IterativeDeepingSearchThread findBestMoveThread = new IterativeDeepingSearchThread(
						chessBoard, ab_algorithm);
				findBestMoveThread.start();
				int secondsLeft = timeForTurnInSeconds;
				while (secondsLeft > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						Logger.getLogger(GameController.class.getName()).log(
								Level.SEVERE, null, ex);
					}
					secondsLeft--;
				}
				// Hopefully this will stop alfa beta searching and kill the
				// thread :)
				findBestMoveThread.interrupt();
				// get best node currently found
				chessBoard = ab_algorithm.getResult();
				if (IS_DEBUGGING){
					System.out.println("ignored " +numOfInvalidMovesInTurn + " invalid moves in turn");
					numOfInvalidMovesInTurn = 0;
					System.out.println("generated " +totalMovesGeneratedInTurn
							+ " moves in turn");
					totalMovesGeneratedInTurn = 0;
					System.out.println("performed " +movesPerformedInTurn
							+ " moves in turn");
					movesPerformedInTurn = 0;
					System.out.println("caught " +numOfGameEndedExceptions
							+ " GameEndedExceptions in turn");
					numOfGameEndedExceptions = 0;
					System.out.println("caught " +numOfRuntimeExceptions
							+ " RuntimeExceptions in turn");
					numOfRuntimeExceptions = 0;
					System.out.println("caught " +numOfNoSuchElementExceptions
							+ " NoSuchElementExceptions in turn");
					numOfNoSuchElementExceptions= 0;
				}
				
				// if game is finished and the winner is the same color as the
				// color the opponent plays{
				if (chessBoard.isGameFinished()) {
					if ((chessBoard.isWhiteWinner() == (whatColorWePlay == WHITE))) {
						System.out.println("Congratulations engine, you have won the game!");
					} else {
					System.out.println("Congratulations opponent, you have won the game!");
					}
					System.out.println("Final board was:\n"+chessBoard.toString());
					return;
				}

				System.out.println(chessBoard.toString());
				
				if (chessBoard.isWhiteInCheck()) {
					System.out.println("white is in check");
				}
				if (chessBoard.isBlackInCheck()) {
					System.out.println("black is in check");
				}
				if (IS_DEBUGGING) {
					System.out.println("BEST CHESSBOARD NODE FOUND IS: "+ chessBoard.getFENFormattedStringFromBoard());
					int whiteScore = StaticValues.calculateBoardValue(
							chessBoard, true);
					int blackScore = StaticValues.calculateBoardValue(
							chessBoard, false);
					Logger l = Logger.getLogger(GameController.class.getName());
					l.log(Level.INFO, "Best node static evaluation is W: "
							+ whiteScore + ", B: " + blackScore);
				}

				// get best move object
				Move engineMoveObj = chessBoard.getLastMovePerformed();
				if (IS_DEBUGGING){
					Logger.getLogger(GameController.class.getName()).log(
							Level.INFO, "engine move obj: {0}",
							engineMoveObj.toDebugString());
					if (engineMoveObj.getMovingPiece() == null) {
						Logger.getLogger(GameController.class.getName()).log(
								Level.SEVERE, "engine move movingPiece was null");
					}
				}
				
				// convert best move object to string and write it to console
				System.out.println("Engine move: " + engineMoveObj.toString());
				// append file with game record
				chessBoard.appendToFile(pathToFileForGameRecord);
				whosTurn = OPPONENT_TURN;

			case OPPONENT_TURN:
				System.out.println("OPPONENT TURN");
				while (true){
					String opponentMoveStr = inputFromWinBoard.nextLine();

					Move opponentMove = chessBoard
							.createMoveFromSANString(opponentMoveStr);
					if (IS_DEBUGGING){
						if (opponentMove.getMovingPiece() == null) {
							System.out.println("opponent move movingPiece was null");
						}
					}
					
					 System.out.println("OPPONENT MOVE: "+opponentMove.toString());
					try{
						chessBoard = chessBoard.performMove(opponentMove);
					}catch (RuntimeException e){
						System.out.println("move input error: "+e.getMessage());
						System.out.println("Please try again");
						continue;
					}
					// we passed the exception, so break loop
					break;
				}
				
				
				// if game is finished and the winner is the same color as the
				// color the opponent plays
				if (chessBoard.isGameFinished()) {
					if ((chessBoard.isWhiteWinner() == (whatColorWePlay == WHITE))) {
						System.out.println("Congratulations engine, you have won the game!");
					} else {
						System.out.println("Congratulations opponent, you have won the game!");
					}
					System.out.println("Final board was:\n"+chessBoard.toString());
					return;
				}

				if (IS_DEBUGGING) {
					int whiteScore = StaticValues.calculateBoardValue(
							chessBoard, true);
					int blackScore = StaticValues.calculateBoardValue(
							chessBoard, false);
					Logger.getLogger(GameController.class.getName()).log(
							Level.INFO,
							"Static evaluation of move is W: " + whiteScore
									+ ", B: " + blackScore);
				}

				System.out.println(chessBoard.toString());

				chessBoard.appendToFile(pathToFileForGameRecord);
				whosTurn = ENGINE_TURN;
			}
		}
	}
}