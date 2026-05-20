package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Match;
import model.Matchday;
import model.Season;

// Represents application's team standings frame 
public class ViewMatchPage extends ApplicationPage {
    private Matchday matchday;
    private Match match;

    private JLabel titleTextLabel;
    private JLabel scoreLabel;
    private JLabel statusLabel;
    private JPanel scoreBoard;
    private JPanel homeButtonScoreBoard;
    private JPanel awayButtonScoreBoard;

    private JButton changeStateButton;
    private JButton removeButton;
    private JButton viewMatchdayButton;
    
    private JButton plusOneHomeScoreButton;
    private JButton plusTenHomeScoreButton;
    private JButton minusOneHomeScoreButton;
    private JButton minusTenHomeScoreButton;
    private JButton plusOneAwayScoreButton;
    private JButton plusTenAwayScoreButton;
    private JButton minusOneAwayScoreButton;
    private JButton minusTenAwayScoreButton;
    
    

    // EFFECTS: Constructs match menu frame
    ViewMatchPage(Season season, Matchday matchday, Match match) {
        super("Match Details", season);
        this.matchday = matchday;
        this.match = match;
        drawTitleText();
        drawScoreboard();
        drawButtons();
        updateButtonText();
    }

    // MODIFIES: this 
    // EFFECTS: Draws title text label to frame 
    private void drawTitleText() {
        titleTextLabel = new JLabel("Match Menu");
        titleTextLabel.setBounds(200, 10, WIDTH - 400, 80);
        titleTextLabel.setHorizontalAlignment(JLabel.CENTER);
        titleTextLabel.setVerticalAlignment(JLabel.CENTER);
        titleTextLabel.setFont(new Font(null, Font.PLAIN, 60));
        add(titleTextLabel);
    }

    // MODIFIES: this 
    // EFFECTS: Draws scoreboard to frame
    private void drawScoreboard() {
        scoreBoard = new JPanel(new GridLayout(3, 3));
        scoreBoard.setBounds(200, 150, WIDTH - 400, 350);
        scoreLabel = createScoreBoardLabel(match.getHomeScore() + "-" + match.getAwayScore(), 70);
        statusLabel = createScoreBoardLabel("Status: " + match.getStatus(), 30);
        createScoreBoardButtons();
        addElementsToScoreboard();
        add(scoreBoard);
    }

    // EFFECTS: Returns a scoreboard label with given text and font size
    private JLabel createScoreBoardLabel(String text, int textSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(null, Font.PLAIN, textSize));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        return label;
    }

    // EFFECTS: Returns a scoreboard button with given text
    private JButton createScoreBoardButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font(null, Font.PLAIN, 35));
        button.setFocusable(false);
        button.addActionListener(this);
        button.setEnabled(false);
        

        return button;
    }

    // MODIFIES: this 
    // EFFECTS: creates +,- buttons for scoreboard
    private void createScoreBoardButtons() {
        homeButtonScoreBoard = new JPanel(new GridLayout(1, 4, 10, 10));
        plusOneHomeScoreButton = createScoreBoardButton("+1");
        minusOneHomeScoreButton = createScoreBoardButton("-1");
        plusTenHomeScoreButton = createScoreBoardButton("+10");
        minusTenHomeScoreButton = createScoreBoardButton("-10");
        homeButtonScoreBoard.add(plusTenHomeScoreButton);
        homeButtonScoreBoard.add(plusOneHomeScoreButton);
        homeButtonScoreBoard.add(minusOneHomeScoreButton);
        homeButtonScoreBoard.add(minusTenHomeScoreButton);

        awayButtonScoreBoard = new JPanel(new GridLayout(1, 4, 10, 10));
        plusOneAwayScoreButton = createScoreBoardButton("+1");
        minusOneAwayScoreButton = createScoreBoardButton("-1");
        plusTenAwayScoreButton = createScoreBoardButton("+10");
        minusTenAwayScoreButton = createScoreBoardButton("-10");
        awayButtonScoreBoard.add(plusTenAwayScoreButton);
        awayButtonScoreBoard.add(plusOneAwayScoreButton);
        awayButtonScoreBoard.add(minusOneAwayScoreButton);
        awayButtonScoreBoard.add(minusTenAwayScoreButton);
    }

    // MODIFIES: this 
    // EFFECTS: adds labels to 3x3 scoreboard grid
    private void addElementsToScoreboard() {
        scoreBoard.add(createScoreBoardLabel("Home Team", 50));
        scoreBoard.add(createScoreBoardLabel("Score", 50));
        scoreBoard.add(createScoreBoardLabel("Away Team", 50));
        scoreBoard.add(createScoreBoardLabel(match.getHomeTeam().getTeamName(), 50));
        scoreBoard.add(scoreLabel);
        scoreBoard.add(createScoreBoardLabel(match.getAwayTeam().getTeamName(), 50));
        scoreBoard.add(homeButtonScoreBoard);
        scoreBoard.add(statusLabel);
        scoreBoard.add(awayButtonScoreBoard);
    }

    // MODIFIES: this 
    // EFFECTS: Draws buttons to the frame
    @Override
    public void drawButtons() {
        drawButton(changeStateButton = new JButton("Start Match"), 200, 580, WIDTH - 400, 60);
        drawButton(removeButton = new JButton("Remove Match"), 200, 660, WIDTH - 400, 60);
        drawButton(viewMatchdayButton = new JButton("Return to Matchday Menu"), 200, 740, WIDTH - 400, 60);
    }

    // MODIFIES: this
    // EFFECTS: Determines the text of the buttons
    public void updateButtonText() {
        if (match.getStatus().equals("Future")) {
            changeStateButton.setText("Start Match");
            removeButton.setEnabled(true);
        } else if (match.getStatus().equals("Underway")) {
            changeStateButton.setText("End Match");
            removeButton.setEnabled(false);
            updateButtonState(true);
        } else {
            changeStateButton.setText("Reset Match");
            removeButton.setEnabled(false);
            updateButtonState(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: Set +,- button states
    public void updateButtonState(Boolean state) {
        plusOneHomeScoreButton.setEnabled(state);
        plusTenHomeScoreButton.setEnabled(state);
        minusOneHomeScoreButton.setEnabled(state);
        minusTenHomeScoreButton.setEnabled(state);
        plusOneAwayScoreButton.setEnabled(state);
        plusTenAwayScoreButton.setEnabled(state);
        minusOneAwayScoreButton.setEnabled(state);
        minusTenAwayScoreButton.setEnabled(state);
    }


    // MODIFIES: this
    // EFFECTS: Updates score for match and match menu
    public void updateMatch(int homePoints, int awayPoints) {
        match.addScore(homePoints, awayPoints);
        scoreLabel.setText(match.getHomeScore() + "-" + match.getAwayScore());
    }


    // MODIFIES: this
    // EFFECTS: Removes match from the matchday and matchday table
    public void removeMatch() {
        int index = 0;
        int removeIndex = 0;
        for (Match matchInList : matchday.getMatches()) {
            if (match.equals(matchInList)) {
                removeIndex = index;
            }
            index++;
        }
        matchday.removeMatch(removeIndex);
    }

    // MODIFIES: this
    // EFFECTS: Handles button inputs 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeStateButton) {
            determineStatus();
        } else if (e.getSource() == removeButton) {
            removeMatch();
            dispose();
            new ViewMatchdayPage(season, matchday);
            updateButtonText();
        } else if (e.getSource() == viewMatchdayButton) {
            dispose();
            new ViewMatchdayPage(season, matchday);
        } else {
            plusMinusActionPerformed(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles button inputs for +,- buttons
    public void plusMinusActionPerformed(ActionEvent e) {
        if (e.getSource() == plusOneHomeScoreButton) {
            updateMatch(1, 0);
        } else if (e.getSource() == plusTenHomeScoreButton) {
            updateMatch(10, 0);
        } else if (e.getSource() == minusOneHomeScoreButton) {
            updateMatch(-1, 0);
        } else if (e.getSource() == minusTenHomeScoreButton) {
            updateMatch(-10, 0);
        } else if (e.getSource() == plusOneAwayScoreButton) {
            updateMatch(0, 1);
        } else if (e.getSource() == plusTenAwayScoreButton) {
            updateMatch(0, 10);
        } else if (e.getSource() == minusOneAwayScoreButton) {
            updateMatch(0, -1);
        } else if (e.getSource() == minusTenAwayScoreButton) {
            updateMatch(0, -10);
        }
    }

    // MODIFIES: this
    // EFFECTS: Determines and changes the match status on the menu and in the match
    public void determineStatus() {
        if (match.getStatus().equals("Future")) {
            match.startMatch();
        } else if (match.getStatus().equals("Underway")) {
            match.endMatch();
        } else {
            match.resetMatch();
            scoreLabel.setText(match.getHomeScore() + "-" + match.getAwayScore());
        }
        statusLabel.setText("Status: " + match.getStatus());
        updateButtonText();
    }


}
