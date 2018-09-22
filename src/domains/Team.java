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
	 * TODO: locations for flight travel estimation?
	 */
	public Team(String name) {
		
		// initialize
		this.name = name;
		
	}
	
	/**
	 * Gets the name of the Team.
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}
	
	// variables
	private String name;
	
}
