package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Season;
import model.Team;

// Represents application's team standings frame 
public class AddTeamPage extends ApplicationPage {
    private JLabel titleTextLabel;
    private JTextField textField;
    private JButton mainMenuButton;
    private JButton submitTeamButton;
    private JPanel optionPanel;
    private JLabel optionPanelText;

    // EFFECTS: Constructs addTeam frame
    AddTeamPage(Season season) {
        super("Add Team", season);
        drawTitleText();
        drawTextField();
        drawButtons();
        drawConsoleText(consoleTextLabel = new JLabel(""), 200, 620, WIDTH - 400, 100);
    }

    // MODIFIES: this 
    // EFFECTS: Draws title text label to frame 
    private void drawTitleText() {
        titleTextLabel = new JLabel("Enter the name of the new Team (Minimum Length of 4 characters)");
        titleTextLabel.setBounds(200, 100, WIDTH - 400, 100);
        titleTextLabel.setHorizontalAlignment(JLabel.CENTER);
        titleTextLabel.setVerticalAlignment(JLabel.CENTER);
        titleTextLabel.setFont(new Font(null, Font.PLAIN, 30));
        add(titleTextLabel);
    }

    // MODIFIES: this 
    // EFFECTS: Draws title text label to frame 
    private void drawTextField() {
        textField = new JTextField("Enter Team Name Here");
        textField.setBounds(200, 400, WIDTH - 400, 60);
        textField.setHorizontalAlignment(JTextField.CENTER);
        add(textField);
    }

    // MODIFIES: this 
    // EFFECTS: Draws buttons to the frame
    @Override
    public void drawButtons() {
        drawButton(submitTeamButton = new JButton("Submit Name"), 200, 480, WIDTH - 400, 60);
        drawButton(mainMenuButton = new JButton("Return to Main Menu"), 200, 560, WIDTH - 400, 60);
    }

    // MODIFIES: this 
    // EFFECTS: Draws a confirmation popup 
    private int drawConfirmationPopup(String teamName) {
        optionPanel = new JPanel();
        optionPanel.setPreferredSize(new Dimension(400, 70));
        optionPanelText = new JLabel("Confirm new team with name : " + teamName);
        optionPanel.add(optionPanelText);
        return JOptionPane.showOptionDialog(null, optionPanel, "Confirm new Team", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    // EFFECTS: Handles button inputs 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton) {
            dispose();
            new MainMenuPage(season);
        } else if (e.getSource() == submitTeamButton) {
            String teamName = textField.getText().trim();
            Team fillerTeam = new Team(teamName);
            if (teamName.length() < 4) {
                consoleTextLabel.setText("Team name must be atleast 4 charcters long");
            } else if (season.getTeams().contains(fillerTeam)) {
                consoleTextLabel.setText("A team with that name already exists");
            } else {
                int confirmationChoice = drawConfirmationPopup(teamName);
                if (confirmationChoice == 0) {
                    consoleTextLabel.setText("Added new team with name: " + teamName);
                    season.addTeam(new Team(teamName));
                }
            }
        }
    }


}
