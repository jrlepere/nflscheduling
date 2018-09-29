package constraints;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import values.Team;
import variables.NFLGameSlot;
import variableset.NFLGameSlotSet;


public class MaxPrimeTimeGames implements Constraint<NFLGameSlot> {
	
	private static int GLOBAL_MAX;
	
	public MaxPrimeTimeGames(List<NFLGameSlot> primeTimeGames) {
		this.primeTimeGames = primeTimeGames;
	}

	public static List<Constraint<NFLGameSlot>> getConstraints(NFLGameSlotSet set, int max) {
		GLOBAL_MAX = max;
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
			constraints.add(new MaxPrimeTimeGames(path));
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
		Map<Team, Integer> gameCount = new HashMap<>(32); // TODO
		for (NFLGameSlot gs : primeTimeGames) {
			if (gs.isSet()) {
				Team homeTeam = gs.getHomeTeam();
				Team awayTeam = gs.getAwayTeam();
				if (gameCount.containsKey(homeTeam)) {
					gameCount.put(homeTeam, gameCount.get(homeTeam) + 1);
				} else {
					gameCount.put(homeTeam, 1);
				}
				if (gameCount.containsKey(awayTeam)) {
					gameCount.put(awayTeam, gameCount.get(awayTeam) + 1);
				} else {
					gameCount.put(awayTeam, 1);
				}
			}
		}
		for (Team t : gameCount.keySet()) {
			if (gameCount.get(t) > GLOBAL_MAX) {
				return false;
			}
		}
		return true;
	}
	
	public List<NFLGameSlot> getVariables() {
		return this.primeTimeGames;
	}
	
	private List<NFLGameSlot> primeTimeGames;
	
}