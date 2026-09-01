package model;

import org.json.JSONObject;

// Represents a Team having a team name, number of wins, numbers of losses, 
// total points scored, total points conceded, and number of matches played
public class Team {

    private String name;                    // team name
    private int wins;                       // number of wins by team 
    private int losses;                     // number of losses by team
    private int totalPointsScored;          // total points scored by team
    private int totalPointsConceded;        // total points conceded by team
    private int matchesPlayed;              // number of matches played by team

    /* 
     * REQUIRES: teamName is of non-zero length 
     * EFFECTS: name of team is set to teamName, otherwise wins,
     *          losses, total points scored, matches played, and total points 
     *          conceded are all set to 0 
     */
    public Team(String teamName) {
        name = teamName;
        wins = 0; 
        losses = 0;
        totalPointsScored = 0;
        totalPointsConceded = 0;
        matchesPlayed = 0;
    }

    public String getTeamName() {
        return name; 
    }

    public int getNumberOfWins() {
        return wins; 
    }

    public int getNumberOfLosses() {
        return losses; 
    }

    public int getTotalPointsScored() {
        return totalPointsScored; 
    }

    public int getTotalPointsConceded() {
        return totalPointsConceded; 
    }

    public int getMatchesPlayed() {
        return matchesPlayed; 
    }

    public void setStats(int wins, int losses, int totalPointsScored, int totalPointsConceded, int matchesPlayed) {
        this.wins = wins;
        this.losses = losses;
        this.totalPointsScored = totalPointsScored;
        this.totalPointsConceded = totalPointsConceded;
        this.matchesPlayed = matchesPlayed;
    }


    // EFFECTS: Returns win rate 
    public double getWinRate() {
        if (matchesPlayed > 0) {
            return (double)wins / (double)matchesPlayed;
        } else {
            return 0;
        }   
    }

    // EFFECTS: Returns point differential 
    public int getPointDifferential() {
        return totalPointsScored - totalPointsConceded;
    }


    /* REQUIRES: pointsScored >= 0, pointsConceded >= 0, 
     *           pointsScored != pointsConceded 
     * MODIFIES: this 
     * EFFECTS: pointsScored is added to totalPointsScored, pointsConceded is added 
     *          to totalPointsConceded, if pointsScored > pointsConceded then increase
     *          numberOfWins by 1, if pointsScored < pointsConceded then increase 
     *          numberOfLoses by 1, increase matchesPlayed by 1. 
     */
    public void playMatch(int pointsScored, int pointsConceded) {
        totalPointsScored += pointsScored;
        totalPointsConceded += pointsConceded;
        matchesPlayed++;
        if (pointsScored > pointsConceded) {
            wins++;
        } else if (pointsScored < pointsConceded) {
            losses++;
        }

    }

    /* REQUIRES: pointsScored >= 0, pointsConceded >= 0, 
     *           pointsScored != pointsConceded, 
     *           totalPointsScored >= pointsScored, 
     *           totalPointsConceded >= pointsConceded,
     *           matchesPlayed > 0, 
     *           wins > 0 or losses > 0
     *           
     * MODIFIES: this 
     * EFFECTS: pointsScored is subtracted from totalPointsScored, pointsConceded is subtracted 
     *          from totalPointsConceded, if pointsScored > pointsConceded then decrease
     *          numberOfWins by 1, if pointsScored < pointsConceded then decrease 
     *          numberOfLoses by 1, decrease matchesPlayed by 1. 
     */
    public void resetMatch(int pointsScored, int pointsConceded) {
        totalPointsScored -= pointsScored;
        totalPointsConceded -= pointsConceded;
        matchesPlayed--;
        if (pointsScored > pointsConceded) {
            wins--;
        } else if (pointsScored < pointsConceded) {
            losses--;
        }
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Team other = (Team) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    // EFFECTS: returns things in this team as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("wins", wins);
        json.put("losses", losses);
        json.put("totalPointsScored", totalPointsScored);
        json.put("totalPointsConceded", totalPointsConceded);
        json.put("matchesPlayed", matchesPlayed);
        return json;
    }

    // EFFECTS: returns name of team as a JSON object
    public JSONObject toJsonOnlyAsName() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }

}
