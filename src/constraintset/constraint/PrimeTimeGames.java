package constraintset.constraint;

import java.util.LinkedList;
import java.util.List;

import variableset.NFLGameSlotSet;
import variableset.variables.NFLGameSlot;

public class PrimeTimeGames implements Constraint {
	
	/**
	 * Constrains the number of prime time games for a team.
	 * @param set the set of all time slots.
	 * @param max the maximum number of prime time games.
	 * @param min the minimum number of prime time games.
	 */
	public PrimeTimeGames(NFLGameSlotSet set, int max, int min) {
		
		// get only prime time slots for efficiency
		this.primeTimeGames = new LinkedList<>();
		for (NFLGameSlot gs : set.getAllGames()) {
			if (gs.isPrimeTime()) {
				this.primeTimeGames.add(gs);
			}
		}
		
		// need set for is complete
		this.gameSlotSet = set;
		
		// max and min
		this.max = max;
		this.min = min;
		
	}
	
	public boolean isAcceptable() {
		
		// count for number of prime time games per team
		int[] teamCount = new int[32]; // TODO
		
		// for each prime time game
		for (NFLGameSlot gs : this.primeTimeGames) {
			
			// validate is set
			if (gs.isSet()) {
			
				// get team indexes
				int homeIndex = gs.getHomeTeam().getIndex();
				int awayIndex = gs.getAwayTeam().getIndex();
				
				// increment
				teamCount[homeIndex] += 1;
				teamCount[awayIndex] += 1;
				
				// validate maximum is not reached
				if (teamCount[homeIndex] > this.max || teamCount[awayIndex] > this.max) {
					return false;
				}
				
			}
		}
		
		// validate minimum only if all games set
		if (this.gameSlotSet.isComplete()) {
			for (int i = 0; i < 32; i ++) { // TODO
				if (teamCount[i] < this.min) {
					return false;
				}
			}
		}
		
		// validated
		return true;
	}
	
	private List<NFLGameSlot> primeTimeGames;
	private NFLGameSlotSet gameSlotSet;
	private int max;
	private int min;
	
}
