import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import domains.Matchup;
import domains.Team;
import domains.TeamData;

public class NFLSchedling {

	public static void main(String[] args) {
		
		
		
	}
	
	/**
	 * Gets a list of all matchups for the season.
	 * @param matchupScanner a scanner for the file containing all matchups for the season.
	 * @param teamDataScanner a scanner for the file containing all relevant team information.
	 * @return a list of all matchups for the season
	 */
	public static List<Matchup> getAllMatchups(Scanner matchupScanner, Scanner teamDataScanner) {
		
		// map of team name to Team for efficient matchup initialization
		Map<String, Team> teams = new TreeMap<>();
		
		// skip header row
		teamDataScanner.nextLine();
		
		// read each team and add to map
		while (teamDataScanner.hasNextLine()) {
			String[] teamData = matchupScanner.nextLine().split(",");
			teams.put(teamData[0], new Team(teamData[0], new TeamData(0, 0, 0)));
		}
		
		// list of Matchups
		List<Matchup> matchups = new LinkedList<>();
		
		// skip the header row
		matchupScanner.nextLine();
		
		// read each matchup and compute a score
		while (matchupScanner.hasNextLine()) {
			String[] teamNames = matchupScanner.nextLine().split(",");
			matchups.add(new Matchup(teams.get(teamNames[0]), teams.get(teamNames[1]), 0));
		}
		
		// TODO: validation, matchup ranking score, team data definition/extraction
		
		return matchups;
	}
	
}
