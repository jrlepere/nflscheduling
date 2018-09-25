package values;

/**
 * An NFL matchup class.
 * @author jlepere2
 * @date 09/22/2018
 */
public class Matchup implements Value {

	/**
	 * Creates an NFL matchup.
	 * @param homeTeam the home team.
	 * @param awayTeam the away team.
	 * @param index a unique index [0,255] for the matchup.
	 * @param score the computed score of the matchup.
	 */
	public Matchup(Team homeTeam, Team awayTeam, int index, int score) {
		
		// initialize
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.index = index;
		this.score = score; // TODO: maybe calculate score instead of pass
		
	}
	
	/**
	 * Gets the home team.
	 * @return the home team.
	 */
	public Team getHomeTeam() {
		return this.homeTeam;
	}
	
	/**
	 * Gets the away team.
	 * @return the away team.
	 */
	public Team getAwayTeam() {
		return this.awayTeam;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	/**
	 * Gets the matchup score.
	 * @return the score of the matchup.
	 */
	public int getScore() {
		return this.score;
	}
	
	public String toString() {
		return this.awayTeam + " @ " + this.homeTeam;
	}
	
	// variables
	private Team homeTeam;
	private Team awayTeam;
	private int index;
	private int score;
	
}
