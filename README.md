# SWD_Project
minimal working UX for a basic employee management system that views and edits an SQL Database. For my version of the code, I'll reference my Overall System Class Design I made last week:
![Class Diagram, Also available in the Dev Diagrams folder.](<Development Diagrams/ClassDiagram.pdf>)

## Functionality Requirements (MANDATORY)

- Generate Reports (A)
  1. Full-time Employee information with pay statement history
  2. Total Pay for the month by Job title
  3. Total Pay for the month by Division

- Database Functionality (B)
  1. Be able to change the employee table (aka add a column)
  2. Search for an employee using employee-specific information (name, SSN, empID) to show their information.
  3. Update an existing employee's data
  4. Update employees' salary for an increase of a particular percentage only for a salary amount range. (EX: 3.2% for salary range 58k - 105k).

## Programming Tasks

- IDK what EXACTLY she meant by 'programming tasks', but here are the elements of the programming that need to be tested, say the system is fully complete
  1. Main class funtionality
    - This class is where the Menu Options are displayed, and the user decides what feature they'd like to interact with in the system (viewing the database, editing the database, or generating a report). This class is also where the program receives needed input from the user to execute functions, and then imports those values into the InputHandler Class.
    - To complete this, we make a Menu for each table (Employee, Job_title, Division, PayStament), ask the user how they'd like to interact with that table, and request needed input if that's required. The user should also be able to go back to the previous menu or exit the system at any point.
    - **To Test:**  unit tests where each option is tried, and we ensure that the user input is correctly interpreted/saved to be processed in InputHandler.
  <br>

  2. InputHandler
   - This is the central class where all the actual functions/commands are taken from the user, and processed as Java commands to execute. **Before Making this Class**, we need to make class objects of the Table elements ((Employee, Job_title, Division, PayStament) reference the class diagram to this step.
