package constraintset.constraint;

import java.util.LinkedList;
import java.util.List;

import variableset.NFLGameSlotSet;
import variableset.variables.NFLGameSlot;

/**
 * Constraint definition that each team has only one game per week.
 * @author jlepere2
 * @date 09/22/2018
 */
public class OneGamePerTeamPerWeek extends BinaryConstraint<NFLGameSlot> {

	public OneGamePerTeamPerWeek(NFLGameSlot v1, NFLGameSlot v2) {
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
		
		// create a binary constraint for each arc within each week
		List<Constraint> constraints = new LinkedList<>();
		for (int weekNum = 0; weekNum < 16; weekNum ++) { // TODO
			List<NFLGameSlot> gamesPerWeek = gamesByWeek.get(weekNum);
			for (int gs1 = 0; gs1 < 16; gs1 ++) {
				for (int gs2 = gs1+1; gs2 < 16; gs2 ++) {
					constraints.add(new OneGamePerTeamPerWeek(gamesPerWeek.get(gs1), gamesPerWeek.get(gs2)));
				}
			}
		}
		
		// return the constraints
		return constraints;
		
	}
	
	public boolean isAcceptable() {
		
		// if one or both matchups are not set, we need not compare the two
		if (!v1.isSet() || !v2.isSet()) {
			return true;
		}
		
		// get indexes
		int v1Home = v1.getHomeTeam().getIndex();
		int v1Away = v1.getAwayTeam().getIndex();
		int v2Home = v2.getHomeTeam().getIndex();
		int v2Away = v2.getAwayTeam().getIndex();
		
		// not acceptable when a team is in both matchups
		return v1Home != v2Home
			&& v1Home != v2Away
			&& v1Away != v2Home
			&& v1Away != v2Away;
	}
	
}
