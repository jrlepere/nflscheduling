import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import constraints.Constraint;
import csp.CSP;
import domains.NFLMatchupSet;
import variableset.NFLGameSlotSet;

public class NFLSchedling {

	public static void main(String[] args) throws Exception {
		
		Scanner matchupScanner = new Scanner(new File("data/nfl-matchups_2018.csv"));
		Scanner teamDataScanner = new Scanner(new File("data/nfl-team-data_2018.csv"));
		
		NFLGameSlotSet variableSet = new NFLGameSlotSet();
		NFLMatchupSet domain = new NFLMatchupSet(matchupScanner, teamDataScanner);
		List<Constraint<NFLGameSlotSet>> constraints = new LinkedList<>();
		
		CSP<NFLGameSlotSet,NFLMatchupSet,?,?,?> csp = new CSP<>();
		System.out.println(csp.solve(variableSet, domain, constraints));
	}
	
}
