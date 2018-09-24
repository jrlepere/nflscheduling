package variableset;

import java.util.LinkedList;
import java.util.List;

import values.NFLGameDay;
import values.NFLGameTime;
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
	public NFLGameSlotSet() {
		
		/* TODO
		 * same variable in multiple lists
		 * bye week
		 */
		
		// initialization
		this.allGames = new LinkedList<>();
		
		for (int i = 1; i <= 16; i ++) {
			this.allGames.add(new NFLGameSlot(new NFLGameTime(i, NFLGameDay.TH)));
			this.allGames.add(new NFLGameSlot(new NFLGameTime(i, NFLGameDay.SN)));
			this.allGames.add(new NFLGameSlot(new NFLGameTime(i, NFLGameDay.MN)));
			for (int j = 1; j <= 13; j ++) {
				this.allGames.add(new NFLGameSlot(new NFLGameTime(i, NFLGameDay.S)));
			}
		}
		
	}
	
	/**
	 * Gets a single list of all NFL game slots in the season.
	 * @return a single list of all NFL game slots in the season.
	 */
	public List<NFLGameSlot> getAllGames() {
		return this.allGames;
	}
	
	
	public NFLGameSlot getVariableToSet() throws Exception {
		for (NFLGameSlot gameSlot : this.allGames) {
			if (!gameSlot.isSet()) {
				return gameSlot;
			}
		}
		throw new Exception("All Variables are already set!");
	}

	
	public boolean isComplete() {
		for (NFLGameSlot gameSlot : this.allGames) {
			if (!gameSlot.isSet()) {
				return false;
			}
		}
		return true;
	}
	
	public String toString() {
		String s = "";
		for (NFLGameSlot gs : this.allGames) {
			s += gs.toString() + "\n";
		}
		return s;
	}
	
	// Maybe have two sets by week, primetime, all, etc.

	// variables
	private List<NFLGameSlot> allGames;
	
}
