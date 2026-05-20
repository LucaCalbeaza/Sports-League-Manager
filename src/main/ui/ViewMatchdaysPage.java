package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Matchday;
import model.Season;

// Represents application's team standings frame 
public class ViewMatchdaysPage extends ApplicationPage implements ListSelectionListener {
    private JLabel titleTextLabel;
    private JButton mainMenuButton;
    private JButton viewMatchdayButton;
    private JButton addMatchdayButton;

    private JList<String> matchdayList;
    private JScrollPane scrollPane;
    private DefaultListModel<String> model;
    private JPanel optionPanel;
    private JLabel optionPanelText;
    private Matchday selectedMatchday;

    // EFFECTS: Constructs matchdays schedule frame
    ViewMatchdaysPage(Season season) {
        super("Matchday Schedule", season);
        model = new DefaultListModel<>();
        selectedMatchday = null;
        optionPanel = null;
        optionPanelText = null;
        drawTitleText();
        generateMatchdayList();
        drawMatchdayList();
        drawButtons();
    }

    // MODIFIES: this 
    // EFFECTS: Draws title text label to frame 
    private void drawTitleText() {
        titleTextLabel = new JLabel("Matchday Schedule");
        titleTextLabel.setBounds(200, 10, WIDTH - 400, 80);
        titleTextLabel.setHorizontalAlignment(JLabel.CENTER);
        titleTextLabel.setVerticalAlignment(JLabel.CENTER);
        titleTextLabel.setFont(new Font(null, Font.PLAIN, 30));
        add(titleTextLabel);
    }

    // MODIFIES: this
    // EFFECTS: generates a list of matchday names and adds the list to model
    private void generateMatchdayList() {
        for (Matchday md : season.getMatchdays()) {
            int index = season.getMatchdays().indexOf(md) + 1;
            model.addElement("Matchday " + index);
        }
    }

    // MODIFIES: this 
    // EFFECTS: Draws list of matchdays to frame 
    private void drawMatchdayList() {
        matchdayList = new JList<>(model);
        matchdayList.setBounds(200, 100, WIDTH - 400, 350);
        matchdayList.setFixedCellHeight(50);
        matchdayList.setFont(new Font(null, Font.PLAIN, 20));
        matchdayList.addListSelectionListener(this);
        scrollPane = new JScrollPane(matchdayList);
        scrollPane.setBounds(200, 100, WIDTH - 400, 350);

        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)matchdayList.getCellRenderer();  
        renderer.setHorizontalAlignment(JLabel.CENTER);  

        add(scrollPane);
    }

    // MODIFIES: this 
    // EFFECTS: Draws buttons to the frame
    @Override
    public void drawButtons() {
        drawButton(viewMatchdayButton = new JButton("Go to Selected Matchday"), 200, 500, WIDTH - 400, 60);
        viewMatchdayButton.setEnabled(false);
        drawButton(addMatchdayButton = new JButton("Add New Matchday"), 200, 580, WIDTH - 400, 60);
        drawButton(mainMenuButton = new JButton("Return to Main Menu"), 200, 660, WIDTH - 400, 60);
    }

    
    // MODIFIES: this 
    // EFFECTS: Draws a confirmation panel
    private int drawConfirmationPopup() {
        optionPanel = new JPanel();
        optionPanel.setPreferredSize(new Dimension(100, 70));
        optionPanelText = new JLabel("Confirm the creation of a new Matchday?");
        optionPanel.add(optionPanelText);
        return JOptionPane.showOptionDialog(null, optionPanel, "Confirm new Matchday", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
    }

    // EFFECTS: Handles button inputs 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenuButton) {
            dispose();
            new MainMenuPage(season);
        } else if (e.getSource() == viewMatchdayButton) {
            dispose();
            new ViewMatchdayPage(season, selectedMatchday);
        } else if (e.getSource() == addMatchdayButton) {
            int confirmationChoice = drawConfirmationPopup();
            if (confirmationChoice == 0) {
                season.addMatchday(new Matchday());
                model.addElement("Matchday " + season.getMatchdays().size());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles list selection events
    @Override
    public void valueChanged(ListSelectionEvent e) {
        selectedMatchday = season.getMatchdays().get(matchdayList.getSelectedIndex());
        viewMatchdayButton.setEnabled(true);
    }
}
