package board;
import game.GameController;
import ab_algorithm.IAB_StaticEvaluator;
/**
 *
 * @author Bartek
 * 
 * Static evaluation should be implemented in this class, it is integrated with alfa beta algorithm implemenation. 
 * Refer to AB_Test.java to see an example. 
 */

public class AB_StaticEvaluator implements IAB_StaticEvaluator<ChessBoard> {

    public AB_StaticEvaluator() {
        StaticValues.FillArray();
    }
          
    @Override
    public int evaluate(ChessBoard node) {
    	//TODO static value should reflect which player it is calculated for
        return StaticValues.calculateBoardValue(node, GameController.whatColorWePlay==GameController.WHITE);
    }
}
