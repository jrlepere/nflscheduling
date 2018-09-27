import java.io.File;
import java.util.Scanner;

import backtracking.Backtracking;
import values.Matchup;
import variables.NFLGameSlot;
import variableset.NFLGameSlotSet;
import variableset.SharedDomainVariableSet;

public class NFLSchedling {

	public static void main(String[] args) throws Exception {
		
		Scanner matchupScanner = new Scanner(new File("data/nfl-matchups_2018.csv"));
		Scanner teamDataScanner = new Scanner(new File("data/nfl-team-data_2018.csv"));
		
		SharedDomainVariableSet<NFLGameSlot, Matchup> variableSet = new NFLGameSlotSet(matchupScanner, teamDataScanner);
		
		Backtracking.SOLVE(variableSet);
		System.out.println(variableSet);
		
	}
	
}
