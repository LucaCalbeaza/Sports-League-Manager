package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a Matchday with a day index, list of matches, and list of 
// playing teams 
public class Matchday {

    private ArrayList<Match> matches;           // List of matches in the Matchday
    private ArrayList<Team> playingTeams;       // List of teams playing in the Matchday 

    /* 
     * EFFECTS: day index is set to 1 greater than the previous matchday
     *          instance created
     */
    public Matchday() {
        matches = new ArrayList<Match>();
        playingTeams = new ArrayList<Team>();
    }


    public ArrayList<Match> getMatches() {
        return matches;  
    }

    public ArrayList<Team> getPlayingTeams() {
        return playingTeams; 
    }


    // EFFECTS: return match at the given matchIndex in matches
    public Match selectMatch(int matchIndex) {
        return matches.get(matchIndex);  
    }

    public void setMatchday(ArrayList<Team> playingTeams, ArrayList<Match> matches) {
        this.playingTeams = playingTeams;
        this.matches = matches;
    }

    /* REQUIRES: playingTeams !contain() homeTeam or awayTeam, 
     *           homeTeam != awayTeam
     * MODIFIES: this
     * EFFECTS: adds a match with homeTeam and awayTeam 
     *          to matches, adds homeTeam and awayTeam to playingTeams
     */
    public void addMatch(Team homeTeam, Team awayTeam) {
        matches.add(new Match(homeTeam, awayTeam)); 
        playingTeams.add(homeTeam);
        playingTeams.add(awayTeam);
        EventLog.getInstance().logEvent(new Event("Added Match to Matchday: " 
                + homeTeam.getTeamName() + " vs " + awayTeam.getTeamName()));
    }

    /* REQUIRES: matchIndex <= matches.size(), 
     *           status of match at the given index must be set
     *           to "Future" 
     * MODIFIES: this
     * EFFECTS: Removes the match from matches at matchIndex, 
     *          removes the teams from the match from playingTeams
     */
    public void removeMatch(int matchIndex) {
        Match match = matches.get(matchIndex);
        EventLog.getInstance().logEvent(new Event("Removed Match to Matchday: " + match.getHomeTeam().getTeamName() 
                + " vs " + match.getAwayTeam().getTeamName()));
        matches.remove(match);
        playingTeams.remove(match.getHomeTeam());
        playingTeams.remove(match.getAwayTeam());
    }

    // EFFECTS: Returns things in this matchday as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playingTeams", playingTeamsToJson());
        json.put("matches", matchesToJson());
        return json;
    }

    // EFFECTS: returns playingTeams as a JSON array
    private JSONArray playingTeamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team pt: playingTeams) {
            jsonArray.put(pt.toJsonOnlyAsName());
        }

        return jsonArray;
    }
    
    // EFFECTS: returns things in this list of matches as a JSON array
    private JSONArray matchesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Match m: matches) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

}
