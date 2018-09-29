package constraints;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import values.Team;
import variables.NFLGameSlot;
import variableset.NFLGameSlotSet;


public class MaxPrimeTimeHomeGames implements Constraint<NFLGameSlot> {

	public MaxPrimeTimeHomeGames(List<NFLGameSlot> primeTimeGames) {
		this.primeTimeGames = primeTimeGames;
	}

	public static List<Constraint<NFLGameSlot>> getConstraints(NFLGameSlotSet set, int max) {
		
		// get list of all primetime games
		Queue<NFLGameSlot> primeTimeGames = new LinkedList<>();
		
		for (NFLGameSlot gs : set) {
			if (gs.isPrimeTime()) {
				primeTimeGames.add(gs);
			}
		}
		
		List<Constraint<NFLGameSlot>> constraints = new LinkedList<>();
		addConstraints(constraints, primeTimeGames, new LinkedList<>(), max+1);
		
		// return the constraints
		return constraints;
		
	}
	
	public static void addConstraints(List<Constraint<NFLGameSlot>> constraints, Queue<NFLGameSlot> gamesLeft,
			List<NFLGameSlot> path, int max) {
		
		if (max == 0) {
			constraints.add(new MaxPrimeTimeHomeGames(path));
			return;
		}
		int n = gamesLeft.size() - max + 1;
		for (int i = 0; i < n; i ++) {
			List<NFLGameSlot> newPath = new LinkedList<>(path);
			newPath.add(gamesLeft.remove());
			Queue<NFLGameSlot> newGamesLeft = new LinkedList<>(gamesLeft);
			addConstraints(constraints, newGamesLeft, newPath, max-1);
		}
		
		
	}
	
	public boolean isAcceptable() {
		Team homeTeam = null;
		for (NFLGameSlot gs : primeTimeGames) {
			if (!gs.isSet()) {
				return true;
			} else {
				if (homeTeam == null) {
					homeTeam = gs.getHomeTeam();
				} else {
					if (homeTeam != gs.getHomeTeam()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public List<NFLGameSlot> getVariables() {
		return this.primeTimeGames;
	}
	
	private List<NFLGameSlot> primeTimeGames;
	
}