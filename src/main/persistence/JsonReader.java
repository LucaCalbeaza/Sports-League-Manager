package persistence;

import model.Match;
import model.Matchday;
import model.Season;
import model.Team;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads a season including teams and matchdays 
// from JSON data stored in file
// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads season from file and returns it
    // throws IOException if an error occurs reading data from file
    public Season read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSeason(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses season from JSON object 
    private Season parseSeason(JSONObject jsonObject) {
        Season season = new Season();
        addTeams(season, jsonObject);
        addMatchdays(season, jsonObject);
        return season;
    }

    // MODIFIES: season
    // EFFECTS: parses each team from JSON object and adds them to teams
    private void addTeams(Season season, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            String name = nextTeam.getString("name");
            int wins = nextTeam.getInt("wins");
            int losses = nextTeam.getInt("losses");
            int totalPointsScored = nextTeam.getInt("totalPointsScored");
            int totalPointsConceded = nextTeam.getInt("totalPointsConceded");
            int matchesPlayed = nextTeam.getInt("matchesPlayed");
            Team team = new Team(name);
            team.setStats(wins, losses, totalPointsScored, totalPointsConceded, matchesPlayed);
            season.getTeams().add(team);
        }
    }

    // MODIFIES: season
    // EFFECTS: parses each matchday from JSON object and adds them to matchdays
    private void addMatchdays(Season season, JSONObject jsonObject) {
        JSONArray matchdays = jsonObject.getJSONArray("matchdays");
        for (Object matchday : matchdays) {
            JSONObject nextMatchday = (JSONObject) matchday;
            JSONArray matches = nextMatchday.getJSONArray("matches");
            JSONArray playingTeams = nextMatchday.getJSONArray("playingTeams");
            ArrayList<Match> newMatches = new ArrayList<>();
            ArrayList<Team> newPlayingTeams = new ArrayList<>();
            for (Object match : matches) {
                JSONObject nextMatch = (JSONObject) match;
                Match newMatch = giveMatch(season, nextMatch);
                newMatches.add(newMatch);
            }
            for (Object team : playingTeams) {
                JSONObject nextTeam = (JSONObject) team;
                Team newTeam = addPlayingTeam(season, nextTeam);  
                newPlayingTeams.add(newTeam);         
            }
            Matchday newMatchday = new Matchday();
            newMatchday.setMatchday(newPlayingTeams, newMatches);
            season.getMatchdays().add(newMatchday);
        }
    }

    // EFFECTS: parses each match from JSON object and returns them
    private Match giveMatch(Season season, JSONObject nextMatch) {
        Team homeTeam = null;
        Team awayTeam = null;
        String homeTeamName = nextMatch.getString("homeTeam");
        String awayTeamName = nextMatch.getString("awayTeam");
        for (Team team : season.getTeams()) {
            if (team.getTeamName().equals(homeTeamName)) {
                homeTeam = team;
            } else if (team.getTeamName().equals(awayTeamName)) {
                awayTeam = team;
            }
        }
        int homeScore = nextMatch.getInt("homeScore");
        int awayScore = nextMatch.getInt("awayScore");
        String status = nextMatch.getString("status");
        Match match = new Match(homeTeam, awayTeam);
        match.updateMatch(homeScore, awayScore, status);
        return match;
    }

    // EFFECTS: parses each match from JSON object and returns them
    private Team addPlayingTeam(Season season, JSONObject nextTeam) {
        String teamName = nextTeam.getString("name"); 
        Team correctTeam = null;
        for (Team team : season.getTeams()) {
            if (team.getTeamName().equals(teamName)) {
                correctTeam = team;
            }
        }

        return correctTeam;
    }
}

