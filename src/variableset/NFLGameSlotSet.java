package variableset;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import variableset.variables.NFLGameSlot;

/**
 * A structured set of NFL game slots to represent the weeks and day/times per week.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NFLGameSlotSet implements VariableSet<NFLGameSlot> {

	/**
	 * Initializes a set structuring the NFL regular season scheduling.
	 */
	public NFLGameSlotSet(Scanner s) {

		// initialization
		// TODO: same variable in multiple lists
		this.allGames = new LinkedList<>();
		
	}
	
	/**
	 * Gets a single list of all NFL game slots in the season.
	 * @return a single list of all NFL game slots in the season.
	 */
	public List<NFLGameSlot> getAllGames() {
		return this.allGames;
	}
	
	
	public NFLGameSlot getVariableToSet() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean isComplete() {
		for (NFLGameSlot gameSlot : this.allGames) {
			if (!gameSlot.isSet()) {
				return false;
			}
		}
		return true;
	}
	
	// Maybe have two sets by week, primetime, all, etc.

	// variables
	private List<NFLGameSlot> allGames;
	
}
