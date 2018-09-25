package constraints;

import java.util.LinkedList;
import java.util.List;

import variableset.NFLGameSlotSet;
import variableset.variables.NFLGameSlot;

/**
 * Constraint definition that each teams cannot play back to back.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NoBackToBack implements Constraint {

	public NoBackToBack(NFLGameSlotSet set) {
		
		this.gamesPerWeek = new LinkedList<>();
		for (int i = 0; i < 16; i ++) {
			this.gamesPerWeek.add(new LinkedList<>());
		}
		for (NFLGameSlot gs : set.getAllGames()) {
			this.gamesPerWeek.get(gs.getWeekNumber()).add(gs);
		}

	}
	
	public boolean isAcceptable() {
		
		List<NFLGameSlot> previousWeek = this.gamesPerWeek.get(0);
		List<NFLGameSlot> currentWeek;
		
		for (int i = 1; i < 16; i ++) {
			currentWeek = this.gamesPerWeek.get(i);
			for (NFLGameSlot pgs : previousWeek) {
				if (pgs.isSet()) {
					for (NFLGameSlot cgs : currentWeek) {
						if (cgs.isSet()) {
							if (pgs.getHomeTeam().equals(cgs.getAwayTeam()) && pgs.getAwayTeam().equals(cgs.getHomeTeam())) {
								//System.out.println(pgs.getHomeTeam() + "  " + cgs.getAwayTeam() + "  " +  pgs.getAwayTeam() + "  " + cgs.getHomeTeam());
								return false;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private List<List<NFLGameSlot>> gamesPerWeek;

}
