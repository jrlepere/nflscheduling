package variables;

import java.util.List;

import values.Matchup;
import values.NFLGameDay;
import values.Team;
import variableset.NFLGameSlotSet;

/**
 * A slot for an NFL matchup with static game time variables.
 * @author jlepere2
 * @date 09/22/2018
 */
public class NFLGameSlot implements Variable<Matchup>, Comparable<NFLGameSlot> {

	
	public NFLGameSlot(NFLGameSlotSet parentSet, int index, int weekNumber, NFLGameDay gameDay) {
		
		this.parentSet = parentSet;
		this.index = index;
		this.matchup = null;
		this.weekNumber = weekNumber;
		this.gameDay = gameDay;
		
	}
	
	public Matchup getMatchup() {
		return this.matchup;
	}
	
	public Team getHomeTeam() {
		return this.matchup.getHomeTeam();
	}
	
	public Team getAwayTeam() {
		return this.matchup.getAwayTeam();
	}
	
	public int getWeekNumber() {
		return this.weekNumber;
	}
	
	public boolean isPrimeTime() {
		return this.gameDay == NFLGameDay.TH || this.gameDay == NFLGameDay.SN || this.gameDay == NFLGameDay.MN;
	}
	
	public List<Matchup> orderDomainValues() {
		return this.parentSet.getDomain(this);
	}

	public void assign(Matchup value) {
		this.matchup = value;
		this.parentSet.notifyAssignment(this, value);
	}

	public void unassign() {
		this.parentSet.notifyUnassignment(this, this.matchup);
		this.matchup = null;
	}
	
	public boolean isSet() {
		return this.matchup != null;
	}
	
	public int compareTo(NFLGameSlot arg0) {
		return this.index - arg0.index;
	}
	
	public String toString() {
		return this.matchup.toString();
	}
	
	private NFLGameSlotSet parentSet;
	private int index;
	private Matchup matchup;
	private int weekNumber;
	private NFLGameDay gameDay;

}
