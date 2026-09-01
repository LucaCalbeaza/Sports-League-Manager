package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import model.Match;
import model.Matchday;
import model.Season;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;


// A sports league manager application
public class SeasonApp {
    private static final String JSON_STORE = "./data/season.json";
    private Season season;
    private boolean applicationIsOn;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // Creates an instance of the season console application 
    public SeasonApp() {
        runSeason();
    }

    // MODIFIES: this
    // EFFECTS: Processes user inputs while the application is running 
    private void runSeason() {
        String input = null;
        init();

        while (applicationIsOn) {
            displayMainMenu();
            input = scanner.next();
            input = input.toLowerCase().trim();
            processMainMenuInput(input);
        }

        System.out.println("\nClosing Sports League Manager"); 

    }

    // MODIFIES: this
    // EFFECTS: Initializes the season with default values 
    private void init() {
        season = new Season();
        applicationIsOn = true;
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\r?\n|\r");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: Displays the main menu to the user with listed options 
    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        printDivider();
        System.out.println("Select from:");
        System.out.println("\ta -> Add new Team");
        System.out.println("\tv -> View Teams");
        System.out.println("\tt -> View Matchday Schedule");
        System.out.println("\ts -> Save Season");
        System.out.println("\tl -> Load Season");
        System.out.println("\tq -> Quit Application");
    }

    // MODIFIES: this
    // EFFECTS: Processes main menu inputs from the user 
    private void processMainMenuInput(String input) {
        if (input.equals("a")) {
            addNewTeam();
        } else if (input.equals("v")) {
            System.out.println("View Teams");
            viewTeams();
        } else if (input.equals("t")) {
            viewSchedule();
        } else if (input.equals("s")) {
            saveSeason();
        } else if (input.equals("l")) {
            loadSeason();
        } else if (input.equals("q")) {
            applicationIsOn = false;
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds a new team to the list of teams in season
    private void addNewTeam() {
        System.out.println("\nSelect a Team Name (Minimum Length of 5 characters):");
        String teamName = scanner.next().trim(); 

        if (teamName.length() >= 5) {
            System.out.println("\nConfirm that you want to add new team with name: " + teamName);
            System.out.println("\ty -> Yes: Confirm new team");
            System.out.println("\tother -> No: return to main menu");
            String confirmation = scanner.next(); 
            confirmation.toLowerCase();
            if (confirmation.equals("y")) {
                System.out.println("\nAdded new team with name: " + teamName);
                season.getTeams().add(new Team(teamName));
            } else {
                System.out.println("\nTeam not confirmed, returning to main menu");
            }
        } else {
            System.out.println("\nInvalid Input, returning to main menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays list of teams with given options
    private void viewTeams() {
        Boolean stayOnPage = true;
        if (season.getTeams().size() != 0) {
            while (stayOnPage) {
                displayStandings();
                System.out.println("\nSelect from:");
                System.out.println("\tr -> Sort by Record");
                System.out.println("\td -> Sort by Point Differential");
                System.out.println("\tf -> Sort by Points For");
                System.out.println("\ta -> Sort by Points Against");
                System.out.println("\tm -> Return to Main Menu");
                String selection = scanner.next().trim();
                selection = selection.toLowerCase();
                stayOnPage = processTeamMenuInput(selection);
            }
        } else {
            System.out.println("\nNo teams currently in league, try adding some teams first.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays the list of matchdays with given options
    private void viewSchedule() {
        Boolean scheduleEmpty = true;
        Boolean stayOnPage = true;
        while (stayOnPage) {
            if (season.getMatchdays().size() != 0) {
                displayMatchdaySchedule();
                scheduleEmpty = false;
                System.out.println("\nSelect from:");
                System.out.println("\ts -> Select a matchday");
            } else {
                System.out.println("\nNo Matchdays currently on the schedule. Choose From:");
            }
            System.out.println("\ta -> Add a Matchday");
            System.out.println("\tm -> Return to Main Menu");
            String selection = scanner.next().trim();
            selection = selection.toLowerCase();
            stayOnPage = processMatchdayScheduleMenuInput(selection, scheduleEmpty);
        }
        
    }

    // EFFECTS: Displays team standings in the order of their current sorting  
    private void displayStandings() {
        System.out.println("\n\nTeam Standings:");
        System.out.printf("%-22s%-22s%-22s%-22s%-22s%-22s\n", 
                        "Position","Team","Record(W-L)","Points For","Points Against", "Point Differential");
        int position = 0;
        for (Team team : season.getTeams()) {
            position++;
            String name = team.getTeamName();
            String record = team.getNumberOfWins() + "-" + team.getNumberOfLosses();
            int pf = team.getTotalPointsScored();
            int pa = team.getTotalPointsConceded();
            int pd = team.getPointDifferential();
            System.out.printf("%-22s%-22s%-22s%-22s%-22s%-22s\n",position, name, record, pf, pa, pd);
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes team menu inputs from the user 
    private boolean processTeamMenuInput(String input) {
        if (input.equals("r")) {
            sortByRecord();
        } else if (input.equals("d")) {
            sortByPointsDifferential();
        } else if (input.equals("f")) {
            sortByPointsFor();
        } else if (input.equals("a")) {
            sortByPointsAgainst();
        } else if (input.equals("m")) {
            System.out.println("\nReturning to Main Menu");
            return false;
        } else {
            System.out.println("\nSelection not valid...");
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their winrate
    private void sortByRecord() {
        System.out.println("Sorted by Record");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Double.compare(team2.getWinRate(), team1.getWinRate());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their points for  
    private void sortByPointsFor() {
        System.out.println("Sorted by Points For");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getTotalPointsScored(), team1.getTotalPointsScored());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their points against
    private void sortByPointsAgainst() {
        System.out.println("Sorted by Points Against");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getTotalPointsConceded(), team1.getTotalPointsConceded());
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sorts list of teams by their point differential
    private void sortByPointsDifferential() {
        System.out.println("Sorted by Point Differential");
        Collections.sort(season.getTeams(), new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return Integer.compare(team2.getPointDifferential(), team1.getPointDifferential());
            }
        });
    }

    // EFFECTS: Displays matchdays in their order  
    private void displayMatchdaySchedule() {
        System.out.println("\nMatchday Schedule:");
        int position = 0;
        for (int i = 0; i < season.getMatchdays().size(); i++) {
            position++;
            System.out.println("Matchday " + position);
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes matchdays menu inputs from the user 
    private boolean processMatchdayScheduleMenuInput(String input, Boolean matchdayEmpty) {
        if (input.equals("a")) {
            addNewMatchday();
        } else if (input.equals("s") && !matchdayEmpty) {
            System.out.println("\nChoose Matchday Number:");
            int matchdaySelection = scanner.nextInt();
            return processMatchdaySelection(matchdaySelection);
            
        } else if (input.equals("m")) {
            System.out.println("\nReturning to Main Menu");
            return false;
        } else {
            System.out.println("\nSelection not valid...");
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Adds a new matchday to the list of matchdays
    private void addNewMatchday() {
        System.out.println("\nConfirm that you want to add a new Matchday");
        System.out.println("\ty -> Yes: Confirm new matchday");
        System.out.println("\tother -> No: return to main menu");
        String confirmation = scanner.next().trim(); 
        if (confirmation.equals("y")) {
            System.out.println("\nAdded New Matchday");
            season.getMatchdays().add(new Matchday());
        } else {
            System.out.println("\nMatchday not confirmed, returning to Matchday menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes matchday selection input
    private boolean processMatchdaySelection(int input) {
        if (0 < input && input <= season.getMatchdays().size()) {
            viewMatchday(season.getMatchdays().get(input - 1));
            return false;
        } else {
            System.out.println("\nMatchday with that number does not exist");
            return true;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: Displays a list of matches within a matchday
    private void viewMatchday(Matchday matchday) {
        Boolean matchdayEmpty = true;
        Boolean stayOnPage = true;
        while (stayOnPage) {
            if (matchday.getMatches().size() != 0) {
                displayMatches(matchday);
                matchdayEmpty = false;
                System.out.println("\nSelect from:");
                System.out.println("\ts -> Select a Match");
            } else {
                System.out.println("\nNo Matches currently on this matchday. Select From:");
            }
            System.out.println("\ta -> Add a Match");
            System.out.println("\tm -> Return to Main Menu");
            String selection = scanner.next().trim();
            selection = selection.toLowerCase();
            stayOnPage = processMatchdayMenuInput(selection, matchdayEmpty, matchday);
        }
    }

    // EFFECTS: Displays a list of matches on a matchday  
    private void displayMatches(Matchday matchday) {
        System.out.println("\nMatch Schedule:");
        System.out.printf("%-22s%-22s%-22s%-22s%-22s%-22s\n",
                        "Match Number","Home Team", "Home Score", "Away Score", "Away Team","Status");
        int position = 0;
        for (Match match : matchday.getMatches()) {
            position++;
            String homeTeam = match.getHomeTeam().getTeamName();
            String awayTeam = match.getAwayTeam().getTeamName();
            int homeScore = match.getHomeScore();
            int awayScore = match.getAwayScore();
            String status = match.getStatus();
            System.out.printf("%-22s%-22s%-22s%-22s%-22s%-22s\n", 
                            position, homeTeam, homeScore, awayScore, awayTeam, status);
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes matchdays menu inputs from the user 
    private boolean processMatchdayMenuInput(String input, Boolean matchdayEmpty, Matchday matchday) {
        if (input.equals("a")) {
            addNewMatch(matchday);
        } else if (input.equals("s") && !matchdayEmpty) {
            System.out.println("\nChoose Match Number:");
            int matchSelection = scanner.nextInt();
            processMatchSelection(matchSelection, matchday);
            
        } else if (input.equals("m")) {
            System.out.println("\nReturning to Main Menu");
            return false;
        } else {
            System.out.println("\nSelection not valid...");
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Adds a new match to the matchday
    private void addNewMatch(Matchday matchday) {
        System.out.println("\nEnter Home Team name (case specific):");
        String homeTeamInput = scanner.next().trim(); 
        System.out.println("\nEnter Away Team name (case specific):");
        String awayTeamInput = scanner.next().trim();  
        processMatchTeamInputs(homeTeamInput, awayTeamInput, matchday); 
    }

    // MODIFIES: this
    // EFFECTS: Processes team inputs for new match
    private void processMatchTeamInputs(String homeTeamInput, String awayTeamInput, Matchday matchday) {
        Team homeTeam = null;
        Team awayTeam = null;
        for (Team team : season.getTeams()) {
            if (team.getTeamName().equals(homeTeamInput) && !matchday.getPlayingTeams().contains(team)) {
                homeTeam = team;
            } else if (team.getTeamName().equals(awayTeamInput) && !matchday.getPlayingTeams().contains(team)) {
                awayTeam = team;
            }
        }
        if (!(homeTeam == null) && !(awayTeam == null)) {
            matchday.addMatch(homeTeam, awayTeam);
            System.out.println("\nNew Match added");
        } else {
            System.out.println("\nCould not add match, either the given teams do not" 
                                + " exist or they are already playing on this matchday.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes match selection input
    private void processMatchSelection(int input, Matchday matchday) {
        if (0 < input && input <= matchday.getMatches().size()) {
            viewMatch(matchday.getMatches().get(input - 1), matchday);
        } else {
            System.out.println("\nMatch with that number does not exist");
            
        }
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to view and edit a match 
    private void viewMatch(Match match, Matchday matchday) {
        Boolean stayOnPage = true;
        while (stayOnPage) {
            System.out.println("\nSelected Match:");
            System.out.printf("%-22s%-22s%-22s%-22s%-22s\n",
                            "Home Team", "Home Score", "Away Score", "Away Team","Status");
            System.out.printf("%-22s%-22s%-22s%-22s%-22s\n",
                            match.getHomeTeam().getTeamName(), match.getHomeScore(), match.getAwayScore(),
                            match.getAwayTeam().getTeamName(), match.getStatus());
            System.out.println("\nSelect from:");
            if (match.getStatus().equals("Future")) {
                System.out.println("\ts -> Start Match");
                System.out.println("\tr -> Remove Match");
            } else if (match.getStatus().equals("Underway")) {
                System.out.println("\tu -> Update Match Score");
                System.out.println("\te -> End Match");
            } else {
                System.out.println("\tt -> Reset Match");
            }
            System.out.println("\td -> Return to Matchday Menu");
            String selection = scanner.next().trim();
            selection = selection.toLowerCase();
            stayOnPage = processMatchMenuInput(selection, match, matchday);
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes match menu inputs from the user 
    private boolean processMatchMenuInput(String input, Match match, Matchday matchday) {
        if (input.equals("s")) {
            match.startMatch();
            System.out.println("\nStarted Match");
        } else if (input.equals("r")) {
            removeMatch(match, matchday);
            return false;
        } else if (input.equals("u")) {
            updateScore(match);
        } else if (input.equals("e")) {
            match.endMatch();
            System.out.println("\n Ended Match");
        } else if (input.equals("t")) {
            match.resetMatch();
            System.out.println("\n Match has been reset");
        } else if (input.equals("d")) {
            System.out.println("\n Returning to Matchday Menu");
            return false;
        } else {
            System.out.println("\nSelection not valid...");
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to remove a match from matchday
    private void removeMatch(Match match, Matchday matchday) {
        int index = 0;
        int removeIndex = 0;
        for (Match matchInList : matchday.getMatches()) {
            if (match.equals(matchInList)) {
                removeIndex = index;
            }
            index++;
        }
        matchday.removeMatch(removeIndex);
        System.out.println("\nRemoved Match");
    }

    // MODIFIES: this
    // EFFECTS: Allows the user to update score of match 
    private void updateScore(Match match) {
        System.out.println("\nEnter amount of points to add to Home Team Score");
        int homeTeamInput = scanner.nextInt(); 
        System.out.println("\nEnter amount of points to add to Away Team Score:");
        int awayTeamInput = scanner.nextInt(); 
        match.addScore(homeTeamInput, awayTeamInput);
    }


    // EFFECTS: Prints out a line of dashes 
    private void printDivider() {
        System.out.println("--------------------------------------------------------------");
    }

    // EFFECTS: saves the season to file
    private void saveSeason() {
        try {
            jsonWriter.open();
            jsonWriter.writeSeason(season);
            jsonWriter.close();
            System.out.println("Saved Season to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads season from file
    private void loadSeason() {
        try {
            season = jsonReader.read();
            System.out.println("Loaded Season save file from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
