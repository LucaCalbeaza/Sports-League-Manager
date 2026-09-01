package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a season with a list of teams and a list of 
// matchdays
public class Season {

    private ArrayList<Team> teams;              // List of teams in the season
    private ArrayList<Matchday> matchdays;      // List of matchdays in the season

    /* 
     * REQUIRES: teamName is of non-zero length 
     * EFFECTS: teams is set to teams and matchday is set 
     *          to matchdays
     */
    public Season() {
        teams = new ArrayList<Team>();
        matchdays = new ArrayList<Matchday>();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Matchday> getMatchdays() {
        return matchdays;
    }

    public void addTeam(Team team) {
        teams.add(team);
        EventLog.getInstance().logEvent(new Event("Added Team: " + team.getTeamName()));
    }

    public void addMatchday(Matchday md) {
        matchdays.add(md);
        EventLog.getInstance().logEvent(new Event("Added New Matchday"));
    }

    // EFFECTS: returns things in this season as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teams", teamsToJson());
        json.put("matchdays", matchdaysToJson());
        return json;
    }

    // EFFECTS: returns things in teams as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t: teams) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns things in matchdays as a JSON array
    private JSONArray matchdaysToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Matchday md: matchdays) {
            jsonArray.put(md.toJson());
        }

        return jsonArray;
    }

}
