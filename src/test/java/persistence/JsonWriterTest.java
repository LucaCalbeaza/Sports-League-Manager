package persistence;

import model.Season;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySeason() {
        try {
            Season season = new Season();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySeason.json");
            writer.open();
            writer.writeSeason(season);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySeason.json");
            season = reader.read();
            assertEquals(0, season.getTeams().size());
            assertEquals(0, season.getMatchdays().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSeason() {
        try {
            Season season = new Season();
            JsonReader generalReader = new JsonReader("./data/testReaderGeneralSeason.json");
            season = generalReader.read();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSeason.json");
            writer.open();
            writer.writeSeason(season);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralSeason.json");
            season = reader.read();
            assertEquals(4, season.getTeams().size());
            checkTeam("Ravens", 2, 0, 20, 10, 2, season.getTeams().get(0));
            checkTeam("Chiefs", 1, 1, 15, 15, 2, season.getTeams().get(1));
            checkTeam("Bills", 1, 1, 15, 15, 2, season.getTeams().get(2));
            checkTeam("Browns", 0, 2, 10, 20, 2, season.getTeams().get(3));
            assertEquals(2, season.getMatchdays().get(0).getMatches().size());
            assertEquals(4, season.getMatchdays().get(0).getPlayingTeams().size());
            assertEquals(2, season.getMatchdays().get(1).getMatches().size());
            assertEquals(4, season.getMatchdays().get(1).getPlayingTeams().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}