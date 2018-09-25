package domains;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import values.Matchup;
import values.Team;
import values.TeamData;

public class NFLMatchupSet implements Domain<Matchup> {

	public NFLMatchupSet(Scanner matchupScanner, Scanner teamDataScanner) {
		
		// map of team name to Team for efficient matchup initialization
		// as well as construction of only one team object per team
		Map<String, Team> teams = new TreeMap<>();
		
		// skip header row
		teamDataScanner.nextLine();
		
		// read each team and add to map
		int index = 0;
		while (teamDataScanner.hasNextLine()) {
			String[] teamData = teamDataScanner.nextLine().split(",");
			teams.put(teamData[0], new Team(teamData[0], index, new TeamData(0, 0, 0)));
			index += 1;
		}
		
		// store the collection
		this.teams = teams.values();
		
		// list of Matchups
		this.matchups = new LinkedList<>();
		
		// skip the header row
		matchupScanner.nextLine();
		
		// read each matchup and compute a score
		while (matchupScanner.hasNextLine()) {
			String[] teamNames = matchupScanner.nextLine().split(",");
			this.matchups.add(new Matchup(teams.get(teamNames[0]), teams.get(teamNames[1]), 0));
		}
		
		// shuffle the matchups
		//Collections.shuffle(this.matchups);
		
		// TODO: validation, matchup ranking score, team data definition/extraction
		
	}
	
	
	public Matchup getNext(List<Matchup> valuesTried) {
		// TODO: randomness/priority
		
		// return the first value that has yet to been tried
		// TODO: optimize
		for (int i = 0; i < this.matchups.size(); i ++) {
			if (!valuesTried.contains(this.matchups.get(i))) {
				return this.matchups.remove(i);
			}
		}

		return null;
		
	}
	
	public void addBack(Matchup value) {
		// TODO maybe optimization
		this.matchups.add(value);
	}
	
	/**
	 * Gets a collection of all the teams.
	 * @return a collection of all the teams.
	 */
	public Collection<Team> getTeams() {
		return this.teams;
	}
	
	// variables
	private List<Matchup> matchups;
	private Collection<Team> teams;
	

}
