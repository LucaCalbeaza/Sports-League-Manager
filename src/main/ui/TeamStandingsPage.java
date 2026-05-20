package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Season;
import model.Team;

// Represents application's team standings frame 
public class TeamStandingsPage extends ApplicationPage {
    private JLabel titleTextLabel;
    private JButton sortRecordButton;
    private JButton sortPointDifferentialButton;
    private JButton sortPointsScoredButton;
    private JButton sortPointsConcededButton;
    private JButton mainMenuButton;

    private JTable teamStandings;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private Vector<Vector<Object>> teamData;
    private Vector<String> tableColoumns;


    // EFFECTS: Constructs team standings frame
    TeamStandingsPage(Season season) {
        super("Team Standings", season);
        tableColoumns = generateTableColoumns();
        teamData = generateTeamData();
        drawTitleText();
        drawTable();
        drawButtons();
        drawConsoleText(consoleTextLabel = new JLabel("Currently Sorting by Record"), 200, 720, WIDTH - 400, 100);
        sortByRecord();
        updateTable();
    }

    // MODIFIES: this 
    // EFFECTS: Draws title text label to frame 
    private void drawTitleText() {
        titleTextLabel = new JLabel("Team Standings");
        titleTextLabel.setBounds(200, 10, WIDTH - 400, 80);
        titleTextLabel.setHorizontalAlignment(JLabel.CENTER);
        titleTextLabel.setVerticalAlignment(JLabel.CENTER);
        titleTextLabel.setFont(new Font(null, Font.PLAIN, 30));
        add(titleTextLabel);
    }

    // MODIFIES: this
    // EFFECTS: Draws table to the frame following the table model
    public void drawTable() {
        tableModel = new DefaultTableModel(teamData, tableColoumns);
        teamStandings = new JTable(tableModel);
        teamStandings.setBounds(200, 100, WIDTH - 400, 350);
        teamStandings.setRowHeight(30);
        scrollPane = new JScrollPane(teamStandings);
        scrollPane.setBounds(200, 100, WIDTH - 400, 350);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableColoumns.size(); i++) {
            teamStandings.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: Draws table to the frame following the table model
    public void updateTable() {
        tableColoumns = generateTableColoumns();
        teamData = generateTeamData();
        tableModel.setDataVector(teamData, tableColoumns);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableColoumns.size(); i++) {
            teamStandings.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
    }


    // EFFECTS: Generates a vector composed of the table coloumn names
    private Vector<String> generateTableColoumns() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Position");
        columnNames.add("Team Name");
        columnNames.add("Record(W-L)");
        columnNames.add("Points For");
        columnNames.add("Points Against");
        columnNames.add("Point Differential");
        return columnNames;
    }

    // EFFECTS: Generates a vector composed of rows of data for each team
    private Vector<Vector<Object>> generateTeamData() {
        Vector<Vector<Object>> teamRowData = new Vector<>();
        int position = 1;
        for (Team team : season.getTeams()) {
            Vector<Object> row = new Vector<>();
            row.add(position++);
            row.add(team.getTeamName());
            row.add(team.getNumberOfWins() + "-" + team.getNumberOfLosses());
            row.add(team.getTotalPointsScored());
            row.add(team.getTotalPointsConceded());
            row.add(team.getPointDifferential());
            teamRowData.add(row);
        }

        return teamRowData;
    }
    
    // MODIFIES: this 
    // EFFECTS: Draws buttons to the frame
    @Override
    public void drawButtons() {
        drawButton(sortRecordButton = new JButton("Sort by Record"), 200, 500, (WIDTH - 450) / 2, 60);
        drawButton(sortPointDifferentialButton = new JButton("Sort by Point Differential"), 
                250 + (WIDTH - 450) / 2, 500, (WIDTH - 450) / 2, 60);
        drawButton(sortPointsScoredButton = new JButton("Sort by Points Scored"), 200, 580, (WIDTH - 450) / 2, 60);
        drawButton(sortPointsConcededButton = new JButton("Sort by Points Conceded"), 
                250 + (WIDTH - 450) / 2, 580, (WIDTH - 450) / 2, 60);
        drawButton(mainMenuButton = new JButton("Return to Main Menu"), 200, 660, WIDTH - 400, 60);
    }

    // EFFECTS: Handles button inputs 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton) {
            dispose();
            new MainMenuPage(season);
        } else if (e.getSource() == sortRecordButton) {
            sortByRecord();
        } else if (e.getSource() == sortPointDifferentialButton) {
            sortByPointsDifferential();
        } else if (e.getSource() == sortPointsScoredButton) {
            sortByPointsScored();
        } else if (e.getSource() == sortPointsConcededButton) {
            sortByPointsConceded();
        }
        updateTable();
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their winrate
    private void sortByRecord() {
        consoleTextLabel.setText("Currently Sorting by Record");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Double.compare(team2.getWinRate(), team1.getWinRate());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their points for  
    private void sortByPointsScored() {
        consoleTextLabel.setText("Currently Sorting by Points Scored");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getTotalPointsScored(), team1.getTotalPointsScored());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their points against
    private void sortByPointsConceded() {
        consoleTextLabel.setText("Currently Sorting by Points Conceded");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getTotalPointsConceded(), team1.getTotalPointsConceded());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their point differential
    private void sortByPointsDifferential() {
        consoleTextLabel.setText("Currently Sorting by Points Differential");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getPointDifferential(), team1.getPointDifferential());
            }
        });
    }


}
