package values;

/**
 * An NFL team.
 * @author jlepere2
 * @date 09/22/2018
 */
public class Team implements Comparable<Team> {

	/**
	 * Creates an object to represent a Team.
	 * @param name the name of the team.
	 * @param index unique index for the team [0,31]
	 * @param teamData relevant data for the specific team.
	 */
	public Team(String name, int index, TeamData teamData) {
		
		// initialize
		this.name = name;
		this.index = index;
		this.teamData = teamData;
		
	}
	
	/**
	 * Gets the name of the Team.
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the team data for this team.
	 * @return the team data.
	 */
	public TeamData getTeamData() {
		return this.teamData;
	}
	
	/**
	 * Get the unique index for the team.
	 * @return the unique index for the team.
	 */
	public int getIndex() {
		return this.index;
	}
	
	public String toString() {
		return this.name;
	}
	
	public int hashCode() {
		return index;
	}
	
	public int compareTo(Team arg0) {
		return this.name.compareTo(arg0.name);
	}
	
	// variables
	private String name;
	private int index;
	private TeamData teamData;
	
}
