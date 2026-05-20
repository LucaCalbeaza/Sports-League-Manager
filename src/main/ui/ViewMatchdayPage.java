package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.Match;
import model.Matchday;
import model.Season;
import model.Team;

// Represents application's team standings frame 
public class ViewMatchdayPage extends ApplicationPage implements ListSelectionListener {
    private Matchday matchday;
    private Match selectedMatch;

    private JLabel titleTextLabel;
    private JButton viewMatchButton;
    private JButton addNewMatchButton;
    private JButton viewMatchdaysButton;
    private JButton mainMenuButton;
    
    private JTable matchTable;
    private DefaultTableModel tableModel;
    private TableColumnModel columnModel;
    private JScrollPane scrollPane;
    private Vector<Vector<Object>> matchData;
    private Vector<String> tableColoumns;
    
    private JPanel optionPanel;
    private JLabel optionPanelText;
    private JLabel homeTeamText;
    private JLabel awayTeamText;
    private JComboBox<String> homeTeamDropDown;
    private JComboBox<String> awayTeamDropDown;

    // EFFECTS: Constructs matchday frame
    ViewMatchdayPage(Season season, Matchday matchday) {
        super("Match Schedule", season);
        this.matchday = matchday;
        tableColoumns = generateTableColoumns();
        matchData = generateMatchData();
        drawTitleText();
        drawTable();
        drawButtons();
    }

    // EFFECTS: Generates a vector composed of the table coloumn names
    private Vector<String> generateTableColoumns() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Match");
        columnNames.add("Status");
        return columnNames;
    }

    // EFFECTS: Generates a vector composes of the data for each match
    private Vector<Vector<Object>> generateMatchData() {
        Vector<Vector<Object>> matchRowData = new Vector<>();
        for (Match match : matchday.getMatches()) {
            Vector<Object> row = new Vector<>();
            row.add(match.getHomeTeam().getTeamName() + " " + match.getHomeScore() 
                    + "-" + match.getAwayScore() + " " + match.getAwayTeam().getTeamName());
            row.add(match.getStatus());
            matchRowData.add(row);
        }

        return matchRowData;
    }


    // MODIFIES: this 
    // EFFECTS: Draws title text label to frame 
    private void drawTitleText() {
        titleTextLabel = new JLabel("Match Schedule");
        titleTextLabel.setBounds(200, 10, WIDTH - 400, 80);
        titleTextLabel.setHorizontalAlignment(JLabel.CENTER);
        titleTextLabel.setVerticalAlignment(JLabel.CENTER);
        titleTextLabel.setFont(new Font(null, Font.PLAIN, 30));
        add(titleTextLabel);
    }

     // MODIFIES: this 
    // EFFECTS: Draws match table to the frame 
    public void drawTable() {
        tableModel = new DefaultTableModel(matchData, tableColoumns);
        matchTable = new JTable(tableModel);
        columnModel = matchTable.getColumnModel();
        matchTable.setBounds(200, 100, WIDTH - 400, 350);
        matchTable.setRowHeight(30);
        columnModel.getColumn(0).setPreferredWidth((900));
        columnModel.getColumn(1).setPreferredWidth((300));
        matchTable.getSelectionModel().addListSelectionListener(this);
        scrollPane = new JScrollPane(matchTable);
        scrollPane.setBounds(200, 100, WIDTH - 400, 350);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableColoumns.size(); i++) {
            matchTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        add(scrollPane);
    }


    // MODIFIES: this 
    // EFFECTS: Draws buttons to the frame
    @Override
    public void drawButtons() {
        drawButton(viewMatchButton = new JButton("Go to Selected Match"), 200, 500, WIDTH - 400, 60);
        viewMatchButton.setEnabled(false);
        drawButton(addNewMatchButton = new JButton("Add New Match"), 200, 580, WIDTH - 400, 60);
        drawButton(viewMatchdaysButton = new JButton("Return to Matchdays Menu"), 200, 660, WIDTH - 400, 60);
        drawButton(mainMenuButton = new JButton("Return to Main Menu"), 200, 740, WIDTH - 400, 60);
    }

    // MODIFIES: this 
    // EFFECTS: Draws team selection popup  
    private int drawTeamSelectionPopup() {
        optionPanel = new JPanel();
        optionPanel.setLayout(null);
        Dimension panelDimension = new Dimension(250, 200);
        optionPanel.setPreferredSize(panelDimension);
        optionPanelText = new JLabel("Select a Home Team and Away Team", SwingConstants.CENTER);
        optionPanelText.setBounds(0,0, panelDimension.width, 50);
        homeTeamText = new JLabel("Home Team");
        homeTeamText.setBounds(0,50, panelDimension.width / 2, 50);
        awayTeamText = new JLabel("Away Team");
        awayTeamText.setBounds(0,100, panelDimension.width / 2, 50);
        homeTeamDropDown = new JComboBox<>(availableTeams());
        homeTeamDropDown.setBounds(125, 55, panelDimension.width / 2, 40);
        awayTeamDropDown = new JComboBox<>(availableTeams());
        awayTeamDropDown.setBounds(125, 105, panelDimension.width / 2, 40);
        optionPanel.add(optionPanelText);
        optionPanel.add(homeTeamText);
        optionPanel.add(awayTeamText);
        optionPanel.add(homeTeamDropDown);
        optionPanel.add(awayTeamDropDown);
        return JOptionPane.showOptionDialog(null, optionPanel, "Create new Match", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    // MODIFIES: this 
    // EFFECTS: Draws error popup to frame 
    private void drawErrorPopup() {
        JOptionPane.showMessageDialog(null, 
                "Error: Cannot set home team and away team as same team", "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: Creates list of available team names to display in team dropdown menu
    private Vector<String> availableTeams() {
        Vector<String> availableTeamsList = new Vector<>();
        for (Team team : season.getTeams()) {
            if (!matchday.getPlayingTeams().contains(team)) {
                availableTeamsList.add(team.getTeamName());
            }
        }

        return availableTeamsList;
    }

    // MODIFIES: this
    // EFFECTS: Adds new match to matchday and matchTable
    private void addNewMatch() {
        Team fillerHomeTeam = new Team((String)homeTeamDropDown.getSelectedItem());
        Team fillerAwayTeam = new Team((String)awayTeamDropDown.getSelectedItem());
        Team homeTeam = null;
        Team awayTeam = null;
        for (Team team : season.getTeams()) {
            if (team.equals(fillerHomeTeam)) {
                homeTeam = team;
            } else if (team.equals(fillerAwayTeam)) {
                awayTeam = team;
            }
        }
        if (!(homeTeam == null) && !(awayTeam == null)) {
            matchday.addMatch(homeTeam, awayTeam);
            Match newMatch = matchday.getMatches().get(matchday.getMatches().size() - 1);
            Vector<Object> row = new Vector<>();
            row.add(newMatch.getHomeTeam().getTeamName() + " " + newMatch.getHomeScore() + "-"
                    + newMatch.getAwayScore() + " " + newMatch.getAwayTeam().getTeamName());
            row.add(newMatch.getStatus());
            tableModel.addRow(row);
        } else {
            drawErrorPopup();
        }
    }

    // EFFECTS: Handles button inputs 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewMatchButton) {
            dispose();
            new ViewMatchPage(season, matchday, selectedMatch);
        } else if (e.getSource() == addNewMatchButton) {
            int confirmationChoice = drawTeamSelectionPopup();
            if (confirmationChoice == 0) {
                if (!(homeTeamDropDown.getSelectedItem() == null) && !(homeTeamDropDown.getSelectedItem() == null)) {
                    addNewMatch();
                }
            }
        } else if (e.getSource() == viewMatchdaysButton) {
            dispose();
            new ViewMatchdaysPage(season);
        } else if (e.getSource() == mainMenuButton) {
            dispose();
            new MainMenuPage(season);
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles list selection events
    @Override
    public void valueChanged(ListSelectionEvent e) {
        selectedMatch = matchday.getMatches().get(matchTable.getSelectedRow());
        viewMatchButton.setEnabled(true);
    }


}
