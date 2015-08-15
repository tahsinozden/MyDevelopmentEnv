package ab_algorithm;
import java.util.Iterator;

/**
 *
 * @author Bartek
 */
public interface IAB_NodesGenerator<T> {
    public Iterator<T> generateNeighbours(T forNode, int inState);
   // public boolean hasNextLevel();
}
