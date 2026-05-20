# Personal  Term Project - Sports League Manager 

## Project Introduction

The application I plan to design for the term project is a **Sports League Manager**. This application will allow users to run their own sports league season where they create a schedule, add an arbitrary number of *teams*, input results and keep track of the standings. The user will have the ability to make their own schedule by creating an arbitrary number of *Matchdays* which consist of a list of *Matches* that are to be played on that matchday. Throughout the season the user can input results to matches and view the *Standings* which update with each match and display statistics such as league position, number of wins, number of losses, winrate and point differential. At the end of the season the application will allow the user to either crown the league leader as champion or run a *Playoffs* if they so choose. A playoff cutoff position can be chosen and then the application can generate a playoff bracket comprised of the teams that have made the cutoff.   

The application is intended to be used by any users who either plan to run their own sports league or keep track of another league. I chose this project idea because I am a sports fan, which makes this a topic of interest to me. I believe this is an application which I could see myself using for my own personal purposes in the future. 

## User Stories:
- As a user, I want to be able to create a new team, specifiy it's team name and colour, and add the team to the season. 
- As a user, I want to be able to view the season standings, which displays a list of the teams in the season. 
- As a user, I want to able to filter the teams in the season standings by a statistic, for example league position or point differential.  
- As a user, I want to be able to create matchday, and add the matchday to the season. 
- As a user, I want to be able to create a match, specify a home team and an away team, and add the match to a matchday 
- As a user, I want to be able to select a matchday and view the list of matches on that matchday. 
- As a user, I want to able to select a match and set a score 
- As a user, I want to able to mark a match as underway or completed 
- As a user, when I select the option to quit from the main menu, I want to be given the option to save my season to file.
- As a user, when I start the application, I want to be given the option to load my season from file.


# Instructions for End User
- You can add a new team to the season by submitting a unique team name on the page reached by clicking the "Add New Team" button on the main menu.
- You can view the list of teams in a visual standings table on the page reached by the "View Team Standings" button on the main menu.
- You sort the standings page by 4 characteristics of each team: Record, points scored, points conceded, and point differential. 
- You can add new matchdays on the page reached by clicking the "View Matchday Schedule" button on the main menu.
- Once you have added atleast 1 matchday you can select it from the list and view the matchday by clicking the "Go to selected matchday" button.
- You can add new matches to a matchday by clicking the "Add new Match" button on the matchday page and then selecting 2 unique teams from the dropdown. 
- Once you have added atleast 1 match you can select it from the list and view the match by clicking the "Go to selected match" button.
- You can start, end, and reset the match by clicking the top button on the match menu page. 
- While the match is underway you can add and subtract points to each team but clicking the "+10", "+1", "-1", and "-10" buttons. 
- You can remove a match whose status is set to "future" via the remove match button. 

# Phase 4: Task 2
Event Log: 
- Added Team: Ravens
- Added Team: Bills
- Added Team: Steelers
- Added Team: Seahawks
- Added Team: Chiefs
- Added Team: Patriots
- Added Team: Broncos
- Added Team: 49ers
- Added New Matchday
- Added New Matchday
- Added Match to Matchday: Ravens vs Steelers
- Added Match to Matchday: Bills vs Chiefs
- Added Match to Matchday: 49ers vs Seahawks
- Added Match to Matchday: Patriots vs Broncos
- Started Match: Ravens vs Steelers
- Ended Match: Ravens vs Steelers
- Started Match: Bills vs Chiefs
- Ended Match: Bills vs Chiefs
- Started Match: 49ers vs Seahawks
- Ended Match: 49ers vs Seahawks
- Started Match: Patriots vs Broncos
- Ended Match: Patriots vs Broncos
- Reset Match: Patriots vs Broncos
- Started Match: Patriots vs Broncos
- Ended Match: Patriots vs Broncos

# Phase 4: Task 3
Reflecting on my design process for my application the biggest change I would have made is seperating the SeasonApp class into several other classes during the implementation of the console interface during phase 1 of the project. I would split the SeasonApp into several smaller classes with more distinct purposes in order to increase cohesion, as I find the SeasonApp class to be a very messy class that possesses too many different purposes all at once. I learnt this design lesson in my implementation of the GUI classes where I created the UI class relationships in a  structure where each class represents a distinct page of the application. I find this design structure to be far less convoluted compared to overinflating a single class with the entire UI system. Notably I believe this design structure is far easier to read and understand as each class has a clear and distinct purpose within the system. Moreover, this design also makes it easier to implement new features such as new application pages, since each new page is simply implemented as a new class which extends ApplicationPage class.   