package persistence;

import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkTeam(String name, int wins, int losses, int totalPointsScored, 
                            int totalPointsConceded, int matchesPlayed, Team team) {
        assertEquals(name, team.getTeamName());
        assertEquals(wins, team.getNumberOfWins());
        assertEquals(losses, team.getNumberOfLosses());
        assertEquals(totalPointsScored, team.getTotalPointsScored());
        assertEquals(totalPointsConceded, team.getTotalPointsConceded());
        assertEquals(matchesPlayed, team.getMatchesPlayed());
    }
}
