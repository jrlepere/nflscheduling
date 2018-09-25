package variableset.variables;

import values.Matchup;
import values.NFLGameSlotInfo;
import values.NFLGameTime;
import values.Team;
import variableset.NFLGameSlotSet;

/**
 * A slot for an NFL matchup with static game time variables.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NFLGameSlot implements Variable<Matchup, NFLGameSlotInfo> {

	/**
	 * Creates a game slot for an NFL game, to be filled with matchup.
	 * @param parentSet the parent set container for efficient complete checking.
	 * @param gameTime the time/day information for the game.
	 */
	public NFLGameSlot(NFLGameSlotSet parentSet, NFLGameTime gameTime) {
		
		// initialization
		this.parentSet = parentSet;
		this.gameSlotInfo = new NFLGameSlotInfo(null, gameTime);
		
	}
	
	public void setVariable(Matchup m) {
		if (this.gameSlotInfo.matchup == null) this.parentSet.variableSet();
		this.gameSlotInfo.setMatchup(m);
	}

	public NFLGameSlotInfo getVariable() {
		return this.gameSlotInfo;
	}
	
	public void freeVariable() {
		this.parentSet.variableFreed();
		this.gameSlotInfo.setMatchup(null);
	}
	
	public boolean isSet() {
		return gameSlotInfo.isSet();
	}
	
	public Team getHomeTeam() {
		return this.gameSlotInfo.getHomeTeam();
	}
	
	public Team getAwayTeam() {
		return this.gameSlotInfo.getAwayTeam();
	}
	
	public int getWeekNumber() {
		return this.gameSlotInfo.getWeekNumber();
	}
	
	public String toString() {
		return this.gameSlotInfo.toString();
	}
	
	// variables
	private NFLGameSlotSet parentSet;
	private NFLGameSlotInfo gameSlotInfo;

}
