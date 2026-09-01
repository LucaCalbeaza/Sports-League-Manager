# Sports League Manager

Sports League Manager is a Java desktop application for managing a sports league season. It lets you register teams, schedule matchdays, record and update match scores, and view live team standings. I made this application as part of an academic project for a software engineering and design course at the University of British Columbia. 


## Key Features

- **Team Management:** Add new teams to your league
- **Matchday Scheduling:** Create matchdays and add matches between registered teams
- **Live Match Tracking:** Start, update, and end matches, with scores recorded in real time
- **Team Standings:** View standings sorted by record, point differential, points for, or points against
- **Save / Load:** Save your current season onto a local JSON file, and load it back up later

## Building Requirements

- [Java Development Kit (JDK) 17 or later](https://www.oracle.com/ca-en/java/technologies/downloads/)
- [Apache Maven](https://maven.apache.org/download.cgi)

Check that Java & Maven are installed correctly by running:

```bash
java -version
mvn -version
```

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/LucaCalbeaza/Sports-League-Manager.git
   cd Sports-League-Manager
   ```

2. **Build the project**
   ```
   mvn clean package
   ```

3. **Run the application**

    ```bash
   java -jar target/sports-league-maker.jar
   ```
   **Note:** Run this command from the project root directory
   ***

   Alternatively, you open the project in an IDE (ex. VS Code with the Java extension) and run `src/main/ui/Main.java` directly.

## How to Use the App

1. Add teams to sports league through the "Add New Team" page. 
2. Create new matchdays on the "View Matchday Schedule" page.
3. Add matches to a matchday by visiting each matchday.
4. Start the matches and update the scores live. End the matches once they are finished.
5. View the updated team standings on the "View Team Standings" page.
6. If you want to return to the same season file save your season before closing the app.
