package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class SeasonTest {

    private Season testSeason;
    
    @BeforeEach
    void runBefore() {
        testSeason= new Season();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testSeason.getTeams().size());
        assertEquals(0, testSeason.getMatchdays().size());
    }

    @Test
    void testAddTeam() {
        testSeason.addTeam(new Team("Ravens"));
        testSeason.addTeam(new Team("Chiefs"));
        testSeason.addTeam(new Team("Bills"));
        assertEquals(3, testSeason.getTeams().size());
        assertEquals(0, testSeason.getMatchdays().size());
        assertEquals(new Team("Ravens"), testSeason.getTeams().get(0));
        assertEquals(new Team("Chiefs"), testSeason.getTeams().get(1));
        assertEquals(new Team("Bills"), testSeason.getTeams().get(2));
    }

    @Test
    void testAddMatchday() {
        testSeason.addMatchday(new Matchday());
        assertEquals(1, testSeason.getMatchdays().size());
        testSeason.addMatchday(new Matchday());
        assertEquals(2, testSeason.getMatchdays().size());
    }
}
