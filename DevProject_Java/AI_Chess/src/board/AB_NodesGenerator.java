package board;

import Tests.MainFlowTest;
import ab_algorithm.AB_Algorithm;
import ab_algorithm.IAB_NodesGenerator;
import game.GameController;
import game.IterativeDeepingSearchThread;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Bartek
 * 
 *         Generates listOfMoves for a given board instance that are next
 *         visited in alpha beta alrogithm tree.
 */
public class AB_NodesGenerator implements IAB_NodesGenerator<ChessBoard> {

	@Override
	public Iterator<ChessBoard> generateNeighbours(ChessBoard forNode,
			int inState) {

		MoveGenerator moveGenerator = new MoveGenerator(forNode);
		LinkedList<Move> listOfMoves = new LinkedList<Move>();

		switch (inState) {
		case AB_Algorithm.MAX:
			if (GameController.whatColorWePlay == GameController.WHITE) {
				listOfMoves = moveGenerator.getWhitePieceMoves();
			} else {
				listOfMoves = moveGenerator.getBlackPieceMoves();
			}

			// put best node in front
			// Verify that that it is not the first iteration!
			if (AB_Algorithm.getCurrentDepth() == 0
					&& IterativeDeepingSearchThread.iteration > 1) {
				listOfMoves
						.addFirst(IterativeDeepingSearchThread.bestNodeInThePreviousIteration
								.getLastMovePerformed());
			}

			break;
		case AB_Algorithm.MIN:
			if (GameController.whatColorWePlay == GameController.WHITE) {
				listOfMoves = moveGenerator.getBlackPieceMoves();
			} else {
				listOfMoves = moveGenerator.getWhitePieceMoves();
			}
			break;
		}
		return new MovesIterator(listOfMoves, forNode);
	}

	class MovesIterator implements Iterator<ChessBoard> {

		LinkedList<Move> listOfMoves;
		ChessBoard forNode;
		private ChessBoard nextNode = null;
		boolean found = false;

		public MovesIterator(LinkedList<Move> listOfMoves, ChessBoard forNode) {
			this.forNode = forNode;
			this.listOfMoves = listOfMoves;
		}

		@Override
		public boolean hasNext() {
			if (nextNode == null){
				try {
					initializeNextValidNode();
				}catch (NoSuchElementException e){
					// backing list is empty
					return false;
				}
			}
			return true;
		}

		@Override
		public ChessBoard next() {
			if (nextNode == null){
				try{
					initializeNextValidNode();
				}catch (NoSuchElementException e) {
					throw e;
				}
			}
			
			ChessBoard returnNode = new ChessBoard(nextNode);
			listOfMoves.removeFirst();
			nextNode = null;
			return returnNode;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported yet."); 
		}
		
		private void initializeNextValidNode(){
			if (nextNode == null) {
				if (listOfMoves.isEmpty()){
					GameController.numOfNoSuchElementExceptions++;
					throw new NoSuchElementException("list is empty");
				}
				// we have not verified whether nextnode is valid
				
				try{
					nextNode = forNode.performMove(listOfMoves.peekFirst());
					if (GameController.IS_DEBUGGING){
						GameController.movesPerformedInTurn++;
					}
						
				}catch (GameEndedException ge){
					GameController.numOfGameEndedExceptions++;
				}
				catch (RuntimeException e){
					GameController.numOfRuntimeExceptions++;
				}
				if (nextNode == null) {
					// only for debug
					if (GameController.IS_DEBUGGING){
						GameController.numOfInvalidMovesInTurn++;
					}
					
					listOfMoves.removeFirst();
					this.initializeNextValidNode();
				}
			}
		}


	}
}
