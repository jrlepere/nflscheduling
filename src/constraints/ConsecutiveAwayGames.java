package constraints;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import values.Team;
import variables.NFLGameSlot;
import variableset.NFLGameSlotSet;

/**
 * Constraint definition that each team cannot play three consecutive home or away games.
 * @author jlepere2
 * @date 09/22/2018
 */
public class ConsecutiveAwayGames implements Constraint<NFLGameSlot> {

	public ConsecutiveAwayGames(List<NFLGameSlot> gamePath) {
		this.gamePath = gamePath;
	}

	public static List<Constraint<NFLGameSlot>> getConstraints(NFLGameSlotSet set, int max) {
		
		// order the game slots by week
		List<List<NFLGameSlot>> gamesByWeek = new LinkedList<>();
		for (int i = 0; i < 16; i ++) { // TODO
			gamesByWeek.add(new LinkedList<>());
		}
		for (NFLGameSlot gs : set) {
			gamesByWeek.get(gs.getWeekNumber()).add(gs);
		}
		
		List<Constraint<NFLGameSlot>> constraints = new LinkedList<>();
		
		for (int i = 0; i < 16-max; i ++) { // TODO
			Queue<List<NFLGameSlot>> paths = new LinkedList<>();
			for (NFLGameSlot gs : gamesByWeek.get(i)) {
				List<NFLGameSlot> start = new LinkedList<>();
				start.add(gs);
				paths.add(start);
			}
			int pathLength = 1;
			for (int j = i+1; j < i + max + 1; j ++) {
				pathLength += 1;
				while (true) {
					if (paths.peek().size() == pathLength) {
						break;
					}
					List<NFLGameSlot> path = paths.remove();
					for (NFLGameSlot gs : gamesByWeek.get(j)) {
						List<NFLGameSlot> newPath = new LinkedList<>(path);
						newPath.add(gs);
						paths.add(newPath);
					}
				}
			}
			for (List<NFLGameSlot> path : paths) {
				constraints.add(new ConsecutiveHomeGames(path));
			}
			
		}
		
		// return the constraints
		return constraints;
		
	}
	
	public boolean isAcceptable() {
		Team awayTeam = null;
		for (NFLGameSlot gs : gamePath) {
			if (!gs.isSet()) {
				return true;
			} else {
				if (awayTeam == null) {
					awayTeam = gs.getAwayTeam();
				} else {
					if (awayTeam != gs.getAwayTeam()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public List<NFLGameSlot> getVariables() {
		return this.gamePath;
	}
	
	// variables
	private List<NFLGameSlot> gamePath;
	
}
