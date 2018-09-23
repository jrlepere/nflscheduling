package domains;

/**
 * An NFL team.
 * @author jlepere2
 * @date 09/22/2018
 */
public class Team {

	/**
	 * Creates an object to represent a Team.
	 * @param name the name of the team.
	 * @param teamData relevant data for the specific team.
	 */
	public Team(String name, TeamData teamData) {
		
		// initialize
		this.name = name;
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
	
	// variables
	private String name;
	private TeamData teamData;
	
}
