package constraints;

import variableset.NFLGameSlotSet;

/**
 * Constraint definition that each team has only one game per week.
 * @author jlepere2
 * @date 09/22/2018
 */
public class OneGamePerTeamPerWeek implements Constraint<NFLGameSlotSet> {

	public boolean isAcceptable(NFLGameSlotSet set) {
		// TODO Auto-generated method stub
		return false;
	}

}
