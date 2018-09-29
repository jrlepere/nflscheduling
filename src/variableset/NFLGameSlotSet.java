package variableset;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import constraints.ConsecutiveAwayGames;
import constraints.ConsecutiveHomeGames;
import constraints.Constraint;
import constraints.MaxPrimeTimeGames;
import constraints.MaxPrimeTimeHomeGames;
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
			// TH
			NFLGameSlot th = new NFLGameSlot(this, index, i, NFLGameDay.TH);
			this.domainReductions.put(th, new DomainReduction<>(this.sharedDomain.size()));
			this.constraints.put(th, new LinkedList<>());
			this.freeVariables.add(th);
			index += 1;
			// SN
			NFLGameSlot sn = new NFLGameSlot(this, index, i, NFLGameDay.SN);
			this.domainReductions.put(sn, new DomainReduction<>(this.sharedDomain.size()));
			this.constraints.put(sn, new LinkedList<>());
			this.freeVariables.add(sn);
			index += 1;
			// MN
			NFLGameSlot mn = new NFLGameSlot(this, index, i, NFLGameDay.MN);
			this.domainReductions.put(mn, new DomainReduction<>(this.sharedDomain.size()));
			this.constraints.put(mn, new LinkedList<>());
			this.freeVariables.add(mn);
			index += 1;
			for (int j = 0; j < 13; j ++) {
				// S
				NFLGameSlot s = new NFLGameSlot(this, index, i, NFLGameDay.S);
				this.domainReductions.put(s, new DomainReduction<>(this.sharedDomain.size()));
				this.constraints.put(s, new LinkedList<>());
				this.freeVariables.add(s);
				index += 1;
			}
		}
		
		//Collections.shuffle(this.sharedDomain);
		
		List<Constraint<NFLGameSlot>> allConstraints = new LinkedList<>();
		allConstraints.addAll(OneGamePerTeamPerWeek.getConstraints(this));
		allConstraints.addAll(MaxPrimeTimeHomeGames.getConstraints(this, 2));
		allConstraints.addAll(MaxPrimeTimeGames.getConstraints(this, 3));
		allConstraints.addAll(ConsecutiveHomeGames.getConstraints(this, 2));
		allConstraints.addAll(ConsecutiveAwayGames.getConstraints(this, 2));
		for (Constraint<NFLGameSlot> constraint : allConstraints) {
			for (NFLGameSlot variable : constraint.getVariables()) {
				this.constraints.get(variable).add(constraint);
			}
		}
		
	}
	
	public String toString() {
		Collections.sort(setVariables, new Comparator<NFLGameSlot>() {
			public int compare(NFLGameSlot arg0, NFLGameSlot arg1) {
				// TODO Auto-generated method stub
				return arg0.getIndex() - arg1.getIndex();
			}
		});
		String s = "";
		for (NFLGameSlot v : setVariables) {
			s += v.toString() + "\n";
		}
		return s;
	}
	
}
