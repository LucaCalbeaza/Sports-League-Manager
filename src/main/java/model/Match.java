package model;

import org.json.JSONObject;

// Represents a Match having a home team, away team, home team score, 
// away team score, and status 
public class Match {

    private Team homeTeam;          // Home team of Match
    private Team awayTeam;          // Away team of Match
    private int homeScore;          // Points scored by home team 
    private int awayScore;          // Points scored by away team 
    private String status;          // Match stutus: "Future", "Underway", or "Complete"

    /* 
     * EFFECTS: home team of match is set to homeTeam, away team of 
     *          match is set to awayTeam, home team score and away 
     *          team score both set to 0, status is set to "Future"
     */
    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        homeScore = 0; 
        awayScore = 0;
        status = "Future"; 
    }

    public Team getHomeTeam() {
        return homeTeam; 
    }

    public Team getAwayTeam() {
        return awayTeam; 
    }

    public int getHomeScore() {
        return homeScore; 
    }

    public int getAwayScore() {
        return awayScore; 
    }

    public String getStatus() {
        return status; 
    }

    public void updateMatch(int homeScore, int awayScore, String status) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = status;
    }

    /* REQUIRES: status = "Future"
     * MODIFIES: this
     * EFFECTS: sets status to "Underway"
     */
    public void startMatch() {
        status = "Underway";
        EventLog.getInstance().logEvent(new Event("Started Match: " + homeTeam.getTeamName() 
                + " vs " + awayTeam.getTeamName()));
    }

    /* REQUIRES: status = "Underway", 
     *           homeScore + homePoints >= 0, 
     *           awayScore + awayPoints >= 0
     * MODIFIES: this
     * EFFECTS: adds homePoints to home team score and 
     *          adds awayPoints to away team Score
     */
    public void addScore(int homePoints, int awayPoints) {
        homeScore += homePoints;
        awayScore += awayPoints;
    }

    /* REQUIRES: status = "Underway", homeScore != awayScore
     * MODIFIES: this
     * EFFECTS: sets status to "Complete" and plays the match 
     *          for both teams 
     */
    public void endMatch() {
        status = "Complete"; 
        homeTeam.playMatch(homeScore, awayScore);
        awayTeam.playMatch(awayScore, homeScore);
        EventLog.getInstance().logEvent(new Event("Ended Match: " + homeTeam.getTeamName() 
                + " vs " + awayTeam.getTeamName()));
    }

    /* REQUIRES: status = "Complete"
     * MODIFIES: this
     * EFFECTS: sets status to "future", sets both homeScore 
     *          and awayScore to 0, and resets the match 
     *          for both teams 
     */
    public void resetMatch() {
        status = "Future"; 
        homeTeam.resetMatch(homeScore, awayScore);
        awayTeam.resetMatch(awayScore, homeScore);
        homeScore = 0;
        awayScore = 0;
        EventLog.getInstance().logEvent(new Event("Reset Match: " + homeTeam.getTeamName() 
                + " vs " + awayTeam.getTeamName()));
    }   
    
    // EFFECTS: Returns things in this match as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("homeTeam", homeTeam.getTeamName());
        json.put("awayTeam", awayTeam.getTeamName());
        json.put("homeScore", homeScore);
        json.put("awayScore", awayScore);
        json.put("status", status);
        return json;
    }

}
