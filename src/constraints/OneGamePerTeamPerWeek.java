package constraints;

import variableset.VariableSet;
import variableset.variables.NFLGameSlot;

/**
 * Constraint definition that each team has only one game per week.
 * @author jlepere2
 * @date 09/22/2018
 */
public class OneGamePerTeamPerWeek implements Constraint<NFLGameSlot>{

	public boolean isAcceptable(VariableSet<NFLGameSlot> variables) {
		// TODO Auto-generated method stub
		return false;
	}

}
