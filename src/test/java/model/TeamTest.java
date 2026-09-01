package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TeamTest {

    private Team testTeam;
    
    @BeforeEach
    void runBefore() {
        testTeam = new Team("TeamA");
    }

    @Test
    void testConstructor() {
        assertEquals("TeamA", testTeam.getTeamName());
        assertEquals(0, testTeam.getNumberOfWins());
        assertEquals(0, testTeam.getNumberOfLosses());
        assertEquals(0, testTeam.getTotalPointsConceded());
        assertEquals(0, testTeam.getTotalPointsScored());
        assertEquals(0, testTeam.getMatchesPlayed());
        assertEquals(0, testTeam.getWinRate(), 0);
    }

    @Test
    void testPlayMatchWin() {
        testTeam.playMatch(2, 1);
        assertEquals(2, testTeam.getTotalPointsScored());
        assertEquals(1, testTeam.getTotalPointsConceded());
        assertEquals(1, testTeam.getNumberOfWins());
        assertEquals(0, testTeam.getNumberOfLosses());
        assertEquals(1, testTeam.getMatchesPlayed());
    }

    @Test
    void testPlayMatchLoss() {
        testTeam.playMatch(1, 2);
        assertEquals(1, testTeam.getTotalPointsScored());
        assertEquals(2, testTeam.getTotalPointsConceded());
        assertEquals(0, testTeam.getNumberOfWins());
        assertEquals(1, testTeam.getNumberOfLosses());
        assertEquals(1, testTeam.getMatchesPlayed());
    }

    @Test
    void testPlayMultipleMatches() {
        testTeam.playMatch(3, 0);
        testTeam.playMatch(2, 4);
        testTeam.playMatch(3, 2);
        assertEquals(8, testTeam.getTotalPointsScored());
        assertEquals(6, testTeam.getTotalPointsConceded());
        assertEquals(2, testTeam.getNumberOfWins());
        assertEquals(1, testTeam.getNumberOfLosses());
        assertEquals(3, testTeam.getMatchesPlayed());
        assertEquals(0.67, testTeam.getWinRate(), 0.01);
    }

    @Test
    void testResetMatch() {
        testTeam.playMatch(2, 1);
        testTeam.resetMatch(2, 1);
        assertEquals(0, testTeam.getTotalPointsScored());
        assertEquals(0, testTeam.getTotalPointsConceded());
        assertEquals(0, testTeam.getNumberOfWins());
        assertEquals(0, testTeam.getNumberOfLosses());
        assertEquals(0, testTeam.getMatchesPlayed());
    }

    @Test
    void testResetMatchFromMany() {
        testTeam.playMatch(2, 1);
        testTeam.playMatch(1, 4);
        testTeam.resetMatch(2, 1);
        assertEquals(1, testTeam.getTotalPointsScored());
        assertEquals(4, testTeam.getTotalPointsConceded());
        assertEquals(0, testTeam.getNumberOfWins());
        assertEquals(1, testTeam.getNumberOfLosses());
        assertEquals(1, testTeam.getMatchesPlayed());
    }
}