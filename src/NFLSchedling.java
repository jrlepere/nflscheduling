import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import constraintset.constraint.Constraint;
import constraintset.constraint.NoBackToBack;
import constraintset.constraint.OneGamePerTeamPerWeek;
import constraintset.constraint.PrimeTimeGames;
import csp.CSP;
import domains.NFLMatchupSet;
import variableset.NFLGameSlotSet;

public class NFLSchedling {

	public static void main(String[] args) throws Exception {
		
		Scanner matchupScanner = new Scanner(new File("data/nfl-matchups_2018.csv"));
		Scanner teamDataScanner = new Scanner(new File("data/nfl-team-data_2018.csv"));
		
		NFLGameSlotSet variableSet = new NFLGameSlotSet();
		NFLMatchupSet domain = new NFLMatchupSet(matchupScanner, teamDataScanner);
		List<Constraint> constraints = new LinkedList<>();
		constraints.addAll(OneGamePerTeamPerWeek.getConstraints(variableSet));
		constraints.addAll(NoBackToBack.getConstraints(variableSet));
		constraints.add(new PrimeTimeGames(variableSet, 3, 3));
		
		CSP<NFLGameSlotSet,NFLMatchupSet,?,?,?> csp = new CSP<>();
		System.out.println(csp.solve(variableSet, domain, constraints));
	}
	
}
