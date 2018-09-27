package variableset;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import constraints.ConsecutiveAwayGames;
import constraints.ConsecutiveHomeGames;
import constraints.Constraint;
import constraints.MaxPrimeHomeTimeGames;
import constraints.OneGamePerTeamPerWeek;
import values.Matchup;
import values.NFLGameDay;
import values.Team;
import values.TeamData;
import variables.NFLGameSlot;

public class NFLGameSlotSet extends SharedDomainVariableSet<NFLGameSlot, Matchup> {

	public NFLGameSlotSet(Scanner matchupScanner, Scanner teamDataScanner) {
		
		// extract Team information
		Map<String, Team> teams = new TreeMap<>();
		teamDataScanner.nextLine();
		int index = 0;
		while (teamDataScanner.hasNextLine()) {
			String[] teamData = teamDataScanner.nextLine().split(",");
			teams.put(teamData[0], new Team(teamData[0], index, new TeamData(0, 0, 0)));
			index += 1;
		}
		
		// extract all matchups
		matchupScanner.nextLine();
		index = 0;
		while (matchupScanner.hasNextLine()) {
			String[] teamNames = matchupScanner.nextLine().split(",");
			this.sharedDomain.add(new Matchup(teams.get(teamNames[0]), teams.get(teamNames[1]), index, 0));
			index += 1;
		}
		
		index = 0;
		for (int i = 0; i < 16; i ++) { // TODO
			this.freeVariables.add(new NFLGameSlot(this, index, i, NFLGameDay.TH));
			this.freeVariables.add(new NFLGameSlot(this, index, i, NFLGameDay.SN));
			this.freeVariables.add(new NFLGameSlot(this, index, i, NFLGameDay.MN));
			for (int j = 0; j < 13; j ++) {
				this.freeVariables.add(new NFLGameSlot(this, index, i, NFLGameDay.S));
			}
			index += 1;
		}
		
		List<Constraint<NFLGameSlot>> allConstraints = new LinkedList<>();
		allConstraints.addAll(OneGamePerTeamPerWeek.getConstraints(this));
		allConstraints.addAll(MaxPrimeHomeTimeGames.getConstraints(this, 3));
		allConstraints.addAll(ConsecutiveHomeGames.getConstraints(this, 2));
		allConstraints.addAll(ConsecutiveAwayGames.getConstraints(this, 2));
		for (Constraint<NFLGameSlot> constraint : allConstraints) {
			for (NFLGameSlot variable : constraint.getVariables()) {
				if (!this.constraints.containsKey(variable)) {
					List<Constraint<NFLGameSlot>> constraintsPerVariable = new LinkedList<>();
					constraintsPerVariable.add(constraint);
					this.constraints.put(variable, constraintsPerVariable);
				} else {
					List<Constraint<NFLGameSlot>> constraintsPerVariable = this.constraints.get(variable);
					constraintsPerVariable.add(constraint);
					this.constraints.put(variable, constraintsPerVariable);
				}
			}
		}
		
	}
	
}
