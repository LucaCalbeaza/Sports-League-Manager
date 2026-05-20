package ui;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Season;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) throws Exception {
        new MainMenuPage(new Season());
    }
}
