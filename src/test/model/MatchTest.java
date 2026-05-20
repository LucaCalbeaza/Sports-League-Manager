package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class MatchTest {
    private Match testMatch;
    private Team  testTeamH;
    private Team  testTeamA;
    
    @BeforeEach
    void runBefore() {
        testTeamH = new Team("H");
        testTeamA = new Team("A");
        testMatch = new Match(testTeamH, testTeamA);
    }

    @Test
    void testConstructor() {
        assertEquals(testTeamH, testMatch.getHomeTeam());
        assertEquals(testTeamA, testMatch.getAwayTeam());   
        assertEquals(0, testMatch.getHomeScore()); 
        assertEquals(0, testMatch.getAwayScore());  
        assertTrue(testMatch.getStatus().equals("Future"));
    }

    @Test
    void testAddScore() {
        testMatch.startMatch();
        testMatch.addScore(2, 1);
        assertEquals(2, testMatch.getHomeScore());
        assertEquals(1, testMatch.getAwayScore());
    }

    @Test
    void testAddScoreMultipleTimes() {
        testMatch.startMatch();
        testMatch.addScore(2, 1);
        testMatch.addScore(2, 5);
        assertEquals(4, testMatch.getHomeScore());
        assertEquals(6, testMatch.getAwayScore());
    }

    @Test
    void testEndMatch() {
        testMatch.startMatch();
        testMatch.addScore(2, 1);
        testMatch.endMatch();
        assertTrue(testMatch.getStatus().equals("Complete"));
        assertEquals(1, testMatch.getHomeTeam().getNumberOfWins());
        assertEquals(1, testMatch.getAwayTeam().getNumberOfLosses());
    }
    
    @Test
    void testResetMatch() {
        testMatch.startMatch();
        testMatch.addScore(2, 1);
        testMatch.endMatch();
        testMatch.resetMatch();
        assertEquals(0, testMatch.getHomeScore());
        assertEquals(0, testMatch.getAwayScore());
        assertTrue(testMatch.getStatus().equals("Future"));
        assertEquals(0, testMatch.getHomeTeam().getPointDifferential());
        assertEquals(0, testMatch.getAwayTeam().getPointDifferential());  
    }
    
}