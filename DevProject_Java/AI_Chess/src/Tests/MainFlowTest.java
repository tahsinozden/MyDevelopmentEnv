package Tests;

/**
 *
 * @author Bartek
 * @additional Remember to add library junit in order to run this test. The test
 * is base on the game tree from the compendium.
 */
import game.GameController;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import board.GameEndedException;

public class MainFlowTest {

    public static void main(String[] args) {
        MainFlowTest mainFlowTest = new MainFlowTest();
        try {
            mainFlowTest.GameControllerMainTest();
        } catch (InterruptedException ex) {
            Logger.getLogger(MainFlowTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MainFlowTest() {
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
    public void GameControllerMainTest() throws InterruptedException {
        String[] input = {"w", "boardState.fen", "10"};
        try {
            GameController.main(input);
        } catch (ParseException ex) {
            Logger.getLogger(MainFlowTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFlowTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GameEndedException ex) {
            Logger.getLogger(MainFlowTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
