package constraints;

import java.util.LinkedList;
import java.util.List;

import variableset.NFLGameSlotSet;
import variableset.variables.NFLGameSlot;

/**
 * Constraint definition that each team has only one game per week.
 * @author jlepere2
 * @date 09/22/2018
 */
public class OneGamePerTeamPerWeek implements Constraint {

	public OneGamePerTeamPerWeek(NFLGameSlotSet set) {
		
		// order the game slots by week for efficiency
		this.gamesPerWeek = new LinkedList<>();
		for (int i = 0; i < 16; i ++) {
			this.gamesPerWeek.add(new LinkedList<>());
		}
		for (NFLGameSlot gs : set.getAllGames()) {
			this.gamesPerWeek.get(gs.getWeekNumber()).add(gs);
		}
		
	}
	
	public boolean isAcceptable() {

		
		// for each week
		for (int weekNum = 0; weekNum < 16; weekNum ++) { // TODO: BYE
			// reset team count
			int[] teamCount = new int[32]; // TODO
			// check each game slot for duplicate per week
			for (NFLGameSlot gameSlot : this.gamesPerWeek.get(weekNum)) {
				if (gameSlot.isSet()) {
					int homeIndex = gameSlot.getHomeTeam().getIndex();
					int awayIndex = gameSlot.getAwayTeam().getIndex();
					teamCount[homeIndex] += 1;
					teamCount[awayIndex] += 1;
					if (teamCount[homeIndex] == 2 || teamCount[awayIndex] == 2) {
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
	
	// variables
	private List<List<NFLGameSlot>> gamesPerWeek;
	
}
