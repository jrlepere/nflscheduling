package variableset.variables;

import values.Matchup;
import values.NFLGameSlotInfo;
import values.NFLGameTime;

/**
 * A slot for an NFL matchup with static game time variables.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NFLGameSlot implements Variable<Matchup, NFLGameSlotInfo> {

	/**
	 * Creates a game slot for an NFL game, to be filled with matchup
	 * @param gameTime the time/day information for the game.
	 */
	public NFLGameSlot(NFLGameTime gameTime) {
		
		// initialize the information for this game slot with the given time
		this.gameSlotInfo = new NFLGameSlotInfo(null, gameTime);
		
	}
	
	public void setVariable(Matchup m) {	
		this.gameSlotInfo.setMatchup(m);
	}

	public NFLGameSlotInfo getVariable() {
		return this.gameSlotInfo;
	}
	
	public boolean isSet() {
		return gameSlotInfo.isSet();
	}
	
	public String toString() {
		return this.gameSlotInfo.toString();
	}
	
	// variables
	private NFLGameSlotInfo gameSlotInfo;

}
