package constraintset.constraint;

import java.util.LinkedList;
import java.util.List;

import variableset.NFLGameSlotSet;
import variableset.variables.NFLGameSlot;

/**
 * Constraint definition that each teams cannot play back to back.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NoBackToBack extends BinaryConstraint<NFLGameSlot> {

	public NoBackToBack(NFLGameSlot v1, NFLGameSlot v2) {
		super(v1, v2);
	}

	public static List<Constraint> getConstraints(NFLGameSlotSet set) {
		
		// order the game slots by week
		List<List<NFLGameSlot>> gamesByWeek = new LinkedList<>();
		for (int i = 0; i < 16; i ++) { // TODO
			gamesByWeek.add(new LinkedList<>());
		}
		for (NFLGameSlot gs : set.getAllGames()) {
			gamesByWeek.get(gs.getWeekNumber()).add(gs);
		}
		
		// create a binary constraint for each arc across weeks
		List<Constraint> constraints = new LinkedList<>();
		
		// previous and current weeks
		List<NFLGameSlot> previousWeek = gamesByWeek.get(0);
		List<NFLGameSlot> currentWeek;
		for (int i = 1; i < 16; i ++) { // TODO
			// get the currentWeek
			currentWeek = gamesByWeek.get(i);
			for (NFLGameSlot pgs : previousWeek) {
				for (NFLGameSlot cgs : currentWeek) {
					constraints.add(new NoBackToBack(pgs, cgs));
				}
			}
			// set previous week
			previousWeek = currentWeek;
		}
		
		// return the constraints
		return constraints;
		
	}
	
	public boolean isAcceptable() {
		
		// if one or both slots are not set, we need not compare the two
		if (!v1.isSet() || !v2.isSet()) {
			return true;
		}
		
		// validate the two matchups are not reflexive
		return v1.getHomeTeam().getIndex() != v2.getAwayTeam().getIndex()
			|| v1.getAwayTeam().getIndex() != v2.getHomeTeam().getIndex();

	}

}
