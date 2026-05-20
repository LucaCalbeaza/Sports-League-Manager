package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class MatchdayTest {
    
    private Matchday testMatchday;
    private Team testTeamA; 
    private Team testTeamB; 
    private Team testTeamC; 
    private Team testTeamD; 

    
    @BeforeEach
    void runBefore() {
        testMatchday = new Matchday();
        testTeamA = new Team("A");
        testTeamB = new Team("B"); 
        testTeamC = new Team("C"); 
        testTeamD = new Team("D");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testMatchday.getMatches().size());
        assertEquals(0, testMatchday.getPlayingTeams().size());
    }

    @Test
    void testAddMatch() {
        testMatchday.addMatch(testTeamA, testTeamB);
        assertEquals(1, testMatchday.getMatches().size());
        assertEquals(testTeamA, testMatchday.getMatches().get(0).getHomeTeam());
        assertEquals(testTeamB, testMatchday.getMatches().get(0).getAwayTeam());
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamA));
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamB));
    }

    @Test
    void testAddMultipleMatches() {
        testMatchday.addMatch(testTeamA, testTeamB);
        testMatchday.addMatch(testTeamC, testTeamD);
        assertEquals(2, testMatchday.getMatches().size());
        assertEquals(testTeamA, testMatchday.getMatches().get(0).getHomeTeam());
        assertEquals(testTeamB, testMatchday.getMatches().get(0).getAwayTeam());
        assertEquals(testTeamC, testMatchday.getMatches().get(1).getHomeTeam());
        assertEquals(testTeamD, testMatchday.getMatches().get(1).getAwayTeam());
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamA));
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamB));
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamC));
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamD));
    }

    @Test
    void testSelectMatch() {
        testMatchday.addMatch(testTeamA, testTeamB);
        testMatchday.addMatch(testTeamC, testTeamD);
        Match selectedMatch = testMatchday.selectMatch(1);
        assertEquals(testTeamC, selectedMatch.getHomeTeam());
        assertEquals(testTeamD, selectedMatch.getAwayTeam());
    }

    @Test
    void testRemoveOnlyMatch() {
        testMatchday.addMatch(testTeamA, testTeamB);
        testMatchday.removeMatch(0);
        assertEquals(0, testMatchday.getMatches().size());
        assertEquals(0, testMatchday.getPlayingTeams().size());
    }

    @Test
    void testRemoveSpecificMatch() {
        testMatchday.addMatch(testTeamA, testTeamB);
        testMatchday.addMatch(testTeamC, testTeamD);
        testMatchday.removeMatch(1);
        assertEquals(1, testMatchday.getMatches().size());
        assertEquals(testTeamA, testMatchday.getMatches().get(0).getHomeTeam());
        assertEquals(testTeamB, testMatchday.getMatches().get(0).getAwayTeam());
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamA));
        assertTrue(testMatchday.getPlayingTeams().contains(testTeamB));
    }

}
