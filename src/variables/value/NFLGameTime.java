package variables.value;

/**
 * A class describing the time of an NFL game.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NFLGameTime {

	/**
	 * Creates an NFLGameTime object representing the time of an NFL matchup.
	 * @param weekNumber the regular season week number.
	 * @param day a unique representer for the day/time slot within a week.
	 */
	public NFLGameTime(int weekNumber, NFLGameDay day) {
		
		// initialization
		this.weekNumber = weekNumber;
		this.day = day;
		
	}
	
	/**
	 * Gets the week number.
	 * @return the week number.
	 */
	public int getWeekNumber() {
		return this.weekNumber;
	}
	
	/**
	 * Gets a unique representation of the day/time.
	 * @return the day/time for the game.
	 */
	public NFLGameDay getDay() {
		return this.day;
	}
	
	// object variables
	private int weekNumber;
	private NFLGameDay day;
	
}
