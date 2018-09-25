package values;

/**
 * A class representing the scheduling information for an NFL game.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NFLGameSlotInfo implements Value {

	/**
	 * Creates an object for representing the matchup and time of an NFL game.
	 * @param matchup the matchup of the game.
	 * @param gameTime the day/time information of the game.
	 */
	public NFLGameSlotInfo(Matchup matchup, NFLGameTime gameTime) {
		
		// initialization
		this.matchup = matchup;
		this.gameTime = gameTime;
		
	}
	
	/**
	 * Sets the matchup for the game.
	 * @param matchup the matchup.
	 */
	public void setMatchup(Matchup matchup) {
		this.matchup = matchup;
	}
	
	/**
	 * Tests whether the matchup is set.
	 * @return true if the matchup is set, false otherwise.
	 */
	public boolean isSet() {
		return this.matchup != null;
	}
	
	public Team getHomeTeam() {
		return this.matchup.getHomeTeam();
	}
	
	public Team getAwayTeam() {
		return this.matchup.getAwayTeam();
	}
	
	public int getWeekNumber() {
		return this.gameTime.getWeekNumber();
	}
	
	public boolean isPrimeTime() {
		return gameTime.isPrimeTime();
	}
	
	public int getIndex() {
		return this.matchup.getIndex();
	}
	
	public String toString() {
		return this.matchup.toString();
	}
	
	// variables
	public Matchup matchup;
	protected NFLGameTime gameTime;
	
}
