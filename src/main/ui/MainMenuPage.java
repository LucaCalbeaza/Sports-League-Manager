package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

import model.Season;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents application's Main Menu frame 
public class MainMenuPage extends ApplicationPage {
    private static final String JSON_STORE = "./data/season.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JLabel titleTextLabel;
    
    private JButton addTeamButton;
    private JButton teamStandingsButton;
    private JButton viewMatchdaysButton;
    private JButton saveSeasonButton;
    private JButton loadSeasonButton;
    

    // EFFECTS: Constructs main menu frame
    MainMenuPage(Season season) {
        super("Main Menu", season);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        drawTitleText();
        drawConsoleText(consoleTextLabel = new JLabel(""), 200, 720, WIDTH - 400, 100);
        drawButtons();
    }

    // MODIFIES: this 
    // EFFECTS: Draws  title text label to frame 
    private void drawTitleText() {
        titleTextLabel = new JLabel("League Season Manager");
        titleTextLabel.setBounds(200, 100, WIDTH - 400, 100);
        titleTextLabel.setHorizontalAlignment(JLabel.CENTER);
        titleTextLabel.setVerticalAlignment(JLabel.CENTER);
        titleTextLabel.setFont(new Font(null, Font.PLAIN, 60));
        add(titleTextLabel);
    }

    // MODIFIES: this 
    // EFFECTS: Draws buttons to the frame
    @Override
    public void drawButtons() {
        drawButton(addTeamButton = new JButton("Add New Team"), 200, 340, WIDTH - 400, 60);
        drawButton(teamStandingsButton = new JButton("View Team Standings"), 200, 420, WIDTH - 400, 60);
        drawButton(viewMatchdaysButton = new JButton("View Matchday Schedule"), 200, 500, WIDTH - 400, 60);
        drawButton(saveSeasonButton = new JButton("Save Season"), 200, 580, WIDTH - 400, 60);
        drawButton(loadSeasonButton = new JButton("Load Season"), 200, 660, WIDTH - 400, 60);
    }

    // EFFECTS: Handles button inputs 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTeamButton) {
            dispose();
            new AddTeamPage(season);
        } else if (e.getSource() == teamStandingsButton) {
            if (season.getTeams().size() != 0) {
                dispose();
                new TeamStandingsPage(season);
            } else {
                consoleTextLabel.setText("No teams currently in league, try adding some teams first."); 
            }
        } else if (e.getSource() == viewMatchdaysButton) {
            dispose();
            new ViewMatchdaysPage(season);
        } else if (e.getSource() == saveSeasonButton) {
            saveSeason();
        } else if (e.getSource() == loadSeasonButton) {
            loadSeason();
        }
    }

    // EFFECTS: saves the season to file
    private void saveSeason() {
        try {
            jsonWriter.open();
            jsonWriter.writeSeason(season);
            jsonWriter.close();
            consoleTextLabel.setText("Saved Season to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            consoleTextLabel.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads season from file
    private void loadSeason() {
        try {
            season = jsonReader.read();
            consoleTextLabel.setText("Loaded Season save file from " + JSON_STORE);
        } catch (IOException e) {
            consoleTextLabel.setText("Unable to read from file: " + JSON_STORE);
        }
    }
}
