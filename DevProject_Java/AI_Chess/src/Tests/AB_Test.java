package Tests;

/**
 *
 * @author Bartek
 * @additional Remember to add library junit in order to run this test. The test
 * is base on the game tree from the compendium.
 */
import ab_algorithm.*;
import board.AB_NodesGenerator;
import board.AB_StaticEvaluator;
import board.StaticValues;
import game.GameController;
import static game.IterativeDeepingSearchThread.bestNodeInThePreviousIteration;
import static game.IterativeDeepingSearchThread.iteration;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

class ChessBoard {

    Integer data;

    public ChessBoard(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }
}

class AB_NodesGeneratorImpl implements IAB_NodesGenerator<ChessBoard> {

    @Override
    public Iterator<ChessBoard> generateNeighbours(ChessBoard forNode, int inState) {
        LinkedList<ChessBoard> list = new LinkedList<ChessBoard>();
        for (int i = 1; i <= 3; i++) {
            ChessBoard nextNeighbour = new ChessBoard(forNode.getData() * 3 + i);
            if (nextNeighbour.getData() <= 39) {
                //  System.out.println(nextNeighbour.getData());
                list.add(nextNeighbour);
            }
        }
        return new MyIterator(list);
    }

    class MyIterator implements Iterator<ChessBoard> {

        LinkedList<ChessBoard> list;

        public MyIterator(LinkedList<ChessBoard> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public ChessBoard next() {
            return list.pollFirst();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}

class AB_StaticEvaluatorImpl implements ab_algorithm.IAB_StaticEvaluator<ChessBoard> {

    private int[] staticValues = {8, 7, 2, 9, 1, 6, 2, 4, 1, 1, 3, 5, 3, 9, 2, 6, 5, 2, 1, 2, 3, 9, 7, 2, 16, 6, 4};

    @Override
    public int evaluate(ChessBoard node) {
        return staticValues[ node.getData() - 13];
    }
}

public class AB_Test {

    public static void main(String[] args) {
        AB_Test ab_test = new AB_Test();
       // ab_test.simpleTestOnDataFromBook();
        ab_test.enginStartingMoveTest();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void simpleTestOnDataFromBook() {
    	long timeLimitMillis = 5000;
    	long stopTime;
    	
        IAB_NodesGenerator nodesGenerator = new AB_NodesGeneratorImpl();
        IAB_StaticEvaluator staticEvaluator = new AB_StaticEvaluatorImpl();
        int startGameState = 0;
        AB_Algorithm<ChessBoard> ab_algorithm = new AB_Algorithm<ChessBoard>(nodesGenerator, staticEvaluator);
        ChessBoard startState = new ChessBoard(startGameState);
        stopTime = System.currentTimeMillis()+timeLimitMillis;
        ChessBoard bestState = ab_algorithm.getBestMove(startState, 5);
        assertTrue(ab_algorithm.getNumberOfABAlgorithmCalls() == 27);
        assertTrue(Integer.valueOf(bestState.getData()) == 2);
    }
    
    public void enginStartingMoveTest() {
    	
        AB_StaticEvaluator staticEvaluator = new AB_StaticEvaluator();
        AB_NodesGenerator nodesGenerator = new AB_NodesGenerator();
        AB_Algorithm<board.ChessBoard> ab_algorithm = new AB_Algorithm<board.ChessBoard>(nodesGenerator, staticEvaluator);
        board.ChessBoard chessBoard = null;
        try {
            chessBoard = new board.ChessBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0");
        } catch (ParseException ex) {
            Logger.getLogger(AB_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertTrue(chessBoard.isWhitesTurn());        
        GameController.whatColorWePlay = GameController.WHITE;
        
      //  StaticValues.printStaticArrays();
        board.ChessBoard bestNode = ab_algorithm.getBestMove(chessBoard, 1);
       // System.out.println(bestNode.toString());
        
        /*
        try {
            chessBoard = new board.ChessBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0");
        } catch (ParseException ex) {
            Logger.getLogger(AB_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        bestNode = ab_algorithm.getBestMove(chessBoard, 2);   
        */
    }
}
