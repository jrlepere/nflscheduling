package constraints;

import java.util.LinkedList;
import java.util.List;

import variables.NFLGameSlot;
import variableset.NFLGameSlotSet;


public class MaxPrimeHomeTimeGames implements Constraint<NFLGameSlot> {

	public MaxPrimeHomeTimeGames(List<NFLGameSlot> primeTimeGames, int max) {
		this.primeTimeGames = primeTimeGames;
		this.max = max;
	}

	public static List<Constraint<NFLGameSlot>> getConstraints(NFLGameSlotSet set, int max) {
		
		// get list of all primetime games
		List<NFLGameSlot> primeTimeGames = new LinkedList<>();
		
		for (NFLGameSlot gs : set) {
			if (gs.isPrimeTime()) {
				primeTimeGames.add(gs);
			}
		}
		
		List<Constraint<NFLGameSlot>> constraints = new LinkedList<>();
		constraints.add(new MaxPrimeHomeTimeGames(primeTimeGames, max));
		
		// return the constraints
		return constraints;
		
	}
	
	public boolean isAcceptable() {
		int[] gameCount = new int[32]; //TODO
		for (NFLGameSlot gs : primeTimeGames) {
			if (gs.isSet()) {
				gameCount[gs.getHomeTeam().getIndex()] += 1;
			}
		}
		for (int i = 0; i < 32; i ++) { // TODO
			if (gameCount[i] > max) {
				return false;
			}
		}
		return true;
	}
	
	public List<NFLGameSlot> getVariables() {
		return this.primeTimeGames;
	}
	
	private List<NFLGameSlot> primeTimeGames;
	int max;
	
}
