package ab_algorithm;
import java.util.Iterator;

/**
 *
 * @author Bartek
 */
public class AB_Algorithm<T> {

    public static final int MIN = 0;
    public static final int MAX = 1;
    private int alpha = Integer.MIN_VALUE;
    private int beta = Integer.MAX_VALUE;
    private static IAB_NodesGenerator nodesGenerator;
    private static IAB_StaticEvaluator staticEvaluator;
    private T resultNode;
    private int numberOfABAlgorithmCalls;
    private static int depthLimit;
    private static int currentDepth = -1;
    private boolean shouldTerminate = false;

    public synchronized boolean isShouldTerminate() {
		return shouldTerminate;
	}

	public void stopSearching() {
        shouldTerminate = true;
    }

    public AB_Algorithm(IAB_NodesGenerator<T> nodesBuilder, IAB_StaticEvaluator<T> staticEvaluator) {
        AB_Algorithm.nodesGenerator = nodesBuilder;
        AB_Algorithm.staticEvaluator = staticEvaluator;
    }

    public T getBestMove(T startNode, int depthLimit) {
        shouldTerminate = false;
        numberOfABAlgorithmCalls = 0;
        AB_Algorithm.depthLimit = depthLimit;
        alphaBeta(alpha, beta, startNode, MAX);
        return resultNode;
    }

    private int alphaBeta(int alpha, int beta, T node, int inState) {
        numberOfABAlgorithmCalls++;
        currentDepth++;

        // TODO resolve the below bug. Create a hasNextLevelOfNodes method for nodesGenerator.
        if (currentDepth == depthLimit) {
            currentDepth--;
            int staticEvaluation = staticEvaluator.evaluate(node);
//            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "alphaBeta finished with alpha: " + alpha 
//            		+ ", beta: " + beta + ", static evaluation: " + staticEvaluation);
            return staticEvaluation;
        }
        // consider generating nodes one by one instead of all at one time ( less calculations? ) 
        Iterator<T> it = nodesGenerator.generateNeighbours(node, inState);

        if (!it.hasNext()) {
            // System.out.println("STATIC: " +node.calculateStaticEvaluation());
            currentDepth--;
            return staticEvaluator.evaluate(node);
        }

        if (inState == MAX) {
            //  Logger.getLogger(AB_Algorithm.class.getName()).log(Level.OFF, "MAX STATE");
            //    System.out.println("AB: MAX");
            while (alpha < beta && it.hasNext() && !shouldTerminate) {
                T nextNode = it.next();
                int v = alphaBeta(alpha, beta, nextNode, MIN);
                if (v > alpha) {
                    if (currentDepth == 0) {
                        resultNode = nextNode;
                    }
                    alpha = v;
                }
            }
            currentDepth--;
            return alpha;
        }

        if (inState == MIN) {
            //   System.out.println("AB: MIN");
            while (alpha < beta && it.hasNext() && !shouldTerminate) {
                T nextNode = it.next();
                int v = alphaBeta(alpha, beta, nextNode, MAX);
                if (v < beta) {
                    beta = v;
                }
            }
            currentDepth--;
            return beta;
        }
        return -1;
    }

    public int getNumberOfABAlgorithmCalls() {
        return numberOfABAlgorithmCalls;
    }

    public T getResult() {
        return resultNode;
    }

    public static int getCurrentDepth() {
        return currentDepth;
    }
}

// System.out.println("F: " + node.toString() + "alfa: " + alpha + " beta: " + beta + " V: " + v);
