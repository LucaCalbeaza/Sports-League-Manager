package ui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Event;
import model.EventLog;
import model.Season;

// Represents the base template for the all the frames in the application
// Referenced from the Alarm System Project
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
// Referenced from the Following StackOverFlow Thread
// https://stackoverflow.com/questions/16295942/java-swing-adding-action-listener-for-exit-on-close
public abstract class ApplicationPage extends JFrame implements ActionListener {
    protected static final int WIDTH = 1600;
    protected static final int HEIGHT = 900; 
    protected Season season;
    protected JLabel consoleTextLabel;

    // EFFECTS: Sets up frame with dimensions and settings
    ApplicationPage(String pageTitle, Season season) {
        this.season = season;
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        centreOnScreen();
        setTitle(pageTitle);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
    }

    // MODIFIES: this 
    // EFFECTS: Draws button to frame with given position and dimensions 
    protected void drawButton(JButton button, int posX, int posY, int width, int height) {
        button.setBounds(posX, posY, width, height);
        button.setFocusable(false);
        button.addActionListener(this);
        add(button);
    }

    // MODIFIES: this 
    // EFFECTS: Draws console text label to frame 
    protected void drawConsoleText(JLabel label, int posX, int posY, int width, int height) {
        label.setBounds(posX, posY, width, height);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font(null, Font.PLAIN, 20));
        add(label);
    }

    /**
	 * Helper to centre main application window on desktop
	 */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }



    public abstract void drawButtons();

    public abstract void actionPerformed(ActionEvent e);

    public void printLog(EventLog el) {
        System.out.println("Event Log: ");
        for (Event next : el) {
            System.out.println(next.getDescription());
        }
    }
}
