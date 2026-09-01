package persistence;

import model.Matchday;
import model.Season;
import model.Team;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            Season season = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySeason() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySeason.json");
        try {
            Season season = reader.read();
            assertEquals(0, season.getTeams().size());
            assertEquals(0, season.getMatchdays().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSeason() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSeason.json");
        try {
            Season season = reader.read();
            List<Team> teams = season.getTeams();
            List<Matchday> matchdays = season.getMatchdays();
            assertEquals(4, teams.size());
            checkTeam("Ravens", 2, 0, 20, 10, 2, teams.get(0));
            checkTeam("Chiefs", 1, 1, 15, 15, 2, teams.get(1));
            checkTeam("Bills", 1, 1, 15, 15, 2, teams.get(2));
            checkTeam("Browns", 0, 2, 10, 20, 2, teams.get(3));
            assertEquals(2, matchdays.get(0).getMatches().size());
            assertEquals(4, matchdays.get(0).getPlayingTeams().size());
            assertEquals(2, matchdays.get(1).getMatches().size());
            assertEquals(4, matchdays.get(1).getPlayingTeams().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
