package game;

import java.util.logging.Level;
import java.util.logging.Logger;

import ab_algorithm.AB_Algorithm;
import board.ChessBoard;
import board.StaticValues;

/**
 *
 * @author Bartek
 */
public class IterativeDeepingSearchThread extends Thread {

    public static int iteration;
    public static ChessBoard bestNodeInThePreviousIteration;
    
    private ChessBoard startNode = null;
    private AB_Algorithm<ChessBoard> ab_algorithm;
    private int maxIterations = 50;

    public IterativeDeepingSearchThread(ChessBoard startNode, AB_Algorithm ab_algorithm) {
        this.startNode = startNode;
        this.ab_algorithm = ab_algorithm;
    }
    
    @Override
    public void interrupt() {
    	if (GameController.IS_DEBUGGING){
    		Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "Iterative deepening thread interrupted");
    	}
        ab_algorithm.stopSearching();
    }

    @Override
    public void run() {
    	if (GameController.IS_DEBUGGING){
    		Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "Iterative deepening thread started");
    	}
    	System.out.println("Search in depth 1 started");
        iteration = 1;
        // iterative search
        bestNodeInThePreviousIteration = ab_algorithm.getBestMove(startNode, iteration);
//        if (GameController.IS_DEBUGGING){
//        	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "Best node from previous:\n" + bestNodeInThePreviousIteration);
//        	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "evaluations: White: " + StaticValues.calculateBoardValue(bestNodeInThePreviousIteration	, true));
//        	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "evaluations: Black: " + StaticValues.calculateBoardValue(bestNodeInThePreviousIteration	, false));
//        }
        while (iteration <= maxIterations && !ab_algorithm.isShouldTerminate()) {
           iteration++;
           if (GameController.IS_DEBUGGING){
        	   Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "Iterative deepening trying search in depth: {0}", iteration);
           }
           System.out.println("Search in depth " + iteration + " started");
           bestNodeInThePreviousIteration = ab_algorithm.getBestMove(startNode, iteration);
//           if (GameController.IS_DEBUGGING){
//           	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "Best node from previous:\n" + bestNodeInThePreviousIteration);
//           	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "evaluations: White: " + StaticValues.calculateBoardValue(bestNodeInThePreviousIteration	, true));
//           	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "evaluations: Black: " + StaticValues.calculateBoardValue(bestNodeInThePreviousIteration	, false));
//           }
        }
        if (GameController.IS_DEBUGGING){
        	Logger.getLogger(IterativeDeepingSearchThread.class.getName()).log(Level.INFO, "Iterative deepening thread stopped");
        }
    }      
}
