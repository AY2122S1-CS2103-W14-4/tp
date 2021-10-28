---
layout: page
title: Developer Guide
---
* Table of Contents

{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Format of this DG is based on [AB3 DG](https://se-education.org/addressbook-level3/DeveloperGuide.html)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.


**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `deleteStudent 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ListPanelPlaceholder`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `ListPanel` is in turn made up of other parts e.g. `StudentListPanel`, `TaskListPanel`, `GroupListPanel`.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands and to determine the type of object to display in the `ListPanelPlaceholder`.
* depends on some classes in the `Model` component, as it displays either a `Student`, `Group`, or `Task` object residing in the `Model`.

#### ListPanel Component

The `ListPanel` consists of either a `StudentListPanel`, `TaskListPanel` or `GroupListPanel`.

Each of these `ListPanels` consists of their own `Card` components which make up the final GUI.

The layout of these `ListPanels` are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)


### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddStudentCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a student).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("deleteStudent 1")` API call.

![Interactions Inside the Logic Component for the `deleteStudent 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteStudentCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddStudentCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddStudentCommandParser`, `DeleteStudentCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Student`, `Group` or `Task` objects (which are contained in a `UniqueStudentList`, `UniqueGroupList`, `UniqueTaskList` object respectively).
* stores the currently 'selected' objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Student` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Student` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### 


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

This product is for CS2103/T TAs who are:
* Familiar with command line and code
* Worried about using too many applications to manage his students' projects and grades.
* Able to tolerate a steep learning curve
* Disorganized, forgetful
* Busy with other school projects and modules


**Value proposition**:

TAs are required to access different platforms (LumiNUS, GitHub & CS2103/T website) and manage multiple groups and students.

This application aims to integrate different tools into a centralised platform that can improve a TA’s efficiency. It helps to ensure instructors complete all tasks on the relevant platforms by stipulated deadlines.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                                      | So that I can…​                                                     |
| -------- | ------------------- | ---------------------------------------------------- | ---------------------------------------------------------------------- |
| `* * *`  | user                | add a task with time                                 | be reminded of when certain tasks are due                              |
| `* * *`  | user                | delete tasks                                         | not be cluttered with completed/wrong tasks                            |
| `* * *`  | user                | mark students' attendance                            | keep track of who is present                                           |
| `* * *`  | user                | mark tasks as complete                               | know which tasks have already been done                                |
| `* * *`  | user                | view my todo list                                    | know what tasks I have to do                                           |
| `* * *`  | user                | view my student list                                 | know what students I have                                              |
| `* * *`  | user                | add a student contact                                | add my student into the list                                           |
| `* * *`  | user                | delete a student contact                             | remove my student if he quits the course                               |
| `* * *`  | user                | store my data in a file                              | easily export data from the application                                |
| `* * *`  | user                | add GitHub links for each student contact            | easily access their Github to check their progress                     |
| `* * *`  | user                | add GitHub links for each student group              | easily access their Github to check their progress                     |
| `* * *`  | user                | record participation during tutorials                | accurately award participation marks to students                       |
| `* * *`  | user                | add descriptions to the tasks                        | see the extra detail pertaining to the task                            |
| `* *`    | user                | set my current directory to either students or tasks | add students/tasks more easily                                         |
| `* *`    | user                | access the ‘help’ page with all available commands   | refer to instructions when I forget how to use tApp                    |
| `* *`    | user                | allocate students to different groups                | track their progress based on their groups                             |
| `* *`    | user                | import data from CSV files                           | get started with the app quickly                                       |
| `* *`    | user                | edit tasks                                           | correct any errors I made without deleting and creating a new task     |
| `* *`    | user                | search for a student                                 | quickly access all information related to the student                  |
| `* *`    | user                | search for a group                                   | quickly access all information related to the group                    |
| `* *`    | user                | purge all current students                           | start a new semester with new students                                 |
| `* *`    | user                | purge all current groups                             | start a new semester with new groups                                   |
| `* *`    | user                | purge all current tasks                              | get rid of all my tasks                                                |
| `* *`    | user                | purge all current data                               | get rid of sample data I used for exploring the app                    |
| `* *`    | user                | sort groups & students by their tP / iP progress     | see who needs help                                                     |
| `* *`    | expert user         | sort tasks by earliest deadline                      | know what tasks need to be completed urgently                          |
| `* *`    | user                | specify default tasks to add when adding students    | I do not need to manually add the same tasks                           |
| `* *`    | user                | create tags to be added to tasks                     | easily access the links related to the task                            |
| `* *`    | user                | tag students to specific tasks                       | keep track of students related to a task                               |
| `* *`    | user                | create events as a type of task                      | keep track of tasks that occur at a specified time                     |
| `* *`    | expert user         | view the changes I made to my todo list              | recover tasks that are accidentally deleted                            |
| `* *`    | user                | set automated reminders upon startup                 | not forget any task                                                    |
| `*`      | expert user         | create command line shortcuts to access tasks        | easily access data and save time                                       |
| `*`      | user                | set recurring tasks                                  | not create tasks that I have to complete regularly                     |
| `*`      | user                | customise the order of the menu                      | easily access the features I use most                                  |
| `*`      | user                | broadcasts task to a certain group                   | efficiently add new module wide tasks                                  |
| `*`      | user                | assign priority levels for tasks                     | what tasks require my earliest attention                               |
| `*`      | user                | string multiple commands into a single line          | manage my tasks more efficiently                                       |
| `*`      | user                | view both my students’ tasks and my own tasks        | be informed of the week’s progress                                     |
| `*`      | expert user         | create custom commands                               | make managing tasks more convenient, and more tailored to my needs     |
| `*`      | user                | filter the CS2103/T textbook                         | refresh my memory on concepts I forgot                                 |

### Use cases

(For all use cases below, the **System** is `tApp` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - View student list**

**MSS**

1.  User requests to list all students
2.  tApp displays the list of students

    Use case ends.

**Extensions**

* 1a. User is already in the student directory.

  Use case resumes at step 3.

* 2a. The list is empty.

  Use case ends.

**Use case: UC2 - Add a student**

**MSS**

1.  User requests to view students (UC1)
2.  tApp displays the list of students
3.  User requests to add a student contact
4.  tApp adds the student

    Use case ends.

**Extensions**

* 3a. No name is specified.

    * 3a1. tApp shows an error message.

      Use case ends.

**Use case: UC3 - Edit a student**

**MSS**

1.  User requests to view students (UC2)
2.  tApp displays the list of students
3.  User requests to delete a specific student in the list
4.  tApp deletes the student

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. tApp shows an error message.

      Use case resumes at step 2.

**Use case: UC4 - Delete a student**

**MSS**

1.  User requests to view students (UC1)
2.  tApp displays the list of students
3.  User requests to delete a specific student in the list
4.  tApp deletes the student

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. tApp shows an error message.

      Use case resumes at step 2.

**Use case: UC5 - Mark student as present**

**MSS**

1.  User requests to view students (UC1)
2.  tApp lists all students
3.  User requests to mark a specific student as present in the list
4.  tApp marks the student as present

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 2b. User requests to mark a specific student as absent in the list.

    * 2b1. tApp marks the student as absent.

      Use case ends.

* 3a. The given index is invalid.

    * 3a1. tApp shows an error message.

      Use case resumes at step 2.


**Use case: UC6 - Mark student as participated**

Similar to UC5, except the student's participation is marked instead of attendance.

**Use case: UC7 - Find student by name**

**MSS**

1.  User requests to find student whose name is John
2.  tApp displays all names containing 'john'

    Use case ends.

**Extensions**

* 2a. The student list is empty.

    * 2a1. tApp displays an empty list

      Use case ends.

**Use case: UC8 - Clear student list**

**MSS**

1.  User requests to view students (UC1)
2.  tApp displays the list of students
3.  User requests clear the student list
4.  tApp clears the student list

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

**Use case: UC9 - View group list**

**MSS**

1.  User requests to view the list of groups
2.  tApp displays all groups

    Use case ends.

**Extensions**

* 2a. The group list is empty.

    * 2a1. tApp displays an empty list

      Use case ends.

**Use case: UC10 - Add a group**

**MSS**

1.  User requests to create a group with the specified name
2.  tApp creates the group and stores it in the group list
3.  tApp displays all groups

    Use case ends.

**Extensions**

* 1a. The group name is empty.

    * 1a1. tApp displays an error message stating that the group name is invalid and to follow the correct format
    
    Use case ends.

* 1b. The group name is invalid.

    * 1b1. tApp displays an error message stating that the group name is invalid and to follow the correct format

    Use case ends.

**Use case: UC11 - Edit a group**

**MSS**

1.  User requests to view the list of groups
2.  tApp displays all groups
3.  User requests to edits a specific group from the list of groups
4.  tApp edits the group
5.  tApp displays all groups with the edited group

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. tApp shows an error message.

      Use case resumes at step 2.

**Use case: UC12 - Delete a group**

Similar to UC11 (Edit a group), except the group is deleted instead of edited.

**Use case: UC13 - Add student to group**

**MSS**

1.  User requests to view the list of students
2.  tApp displays all the students
3.  User requests to add a specific student from the list of students to a specific group from the list of groups
4.  tApp adds the student to the group

    Use case ends.

**Extensions**

* 3a. The group name is empty or invalid.

    * 3a1. tApp displays an error message stating that the group name is invalid and to follow the correct format

      Use case ends.

* 3b. The student index is empty or invalid.

    * 3b1. tApp displays an error message stating that the command format is invalid and to follow the correct format

      Use case ends.

* 3c. The group name is valid but the group does not exist.

    * 3c1. tApp displays an error message stating that the group does not exist

      Use case ends.

* 3d. The student index is valid but the student does not exist.

    * 3d1. tApp displays an error message stating that the student already has a group

      Use case ends.

**Use case: UC13 - Delete student from group**

Similar to UC12 (Add student to group), except we are deleting the student from the group, and group index and member list index is used instead of group name and student list index.

**Use case: UC14 - Find groups by name**

Similar to UC7 (Find student by name), except we are finding groups by group name.

**Use case: UC15 - Clear group list**

Similar to UC8 (Clear student list), except we are clearing the group list.

**Use case: UC16 - View task list**

**MSS**

1.  User requests to view the list of tasks
2.  tApp displays all tasks

    Use case ends.

**Extensions**

* 2a. The task list is empty.

    * 2a1. tApp displays message that there are no tasks

      Use case ends.

**Use case: UC17 - Add a todo task**

**MSS**

1.  User requests to create a todo task
2.  tApp creates the todo and stores it in the task list
3.  tApp displays the task list with the task that was just created

    Use case ends.

**Extensions**

* 1a. The task name is empty.

    * 1a1. tApp displays an error message stating that the task name is invalid

      Use case ends.

* 1b. The command format is incorrect.

    * 1b1. tApp displays an error message requesting the user to follow the correct format

  Use case ends.

**Use case: UC18 - Add a task with a specified deadliine**

**MSS**

1.  User requests to create a task with a specified deadline
2.  tApp creates the task and stores it in the task list
3.  tApp displays the task list with the task that was just created

    Use case ends.

**Extensions**

* 1a. The task name is empty.

    * 1a1. tApp displays an error message stating that the task name is invalid

      Use case ends.

* 1b. The format of the deadline is incorrect.

    * 1b1. tApp displays an error message requesting the user to follow the correct deadline format

      Use case ends.

**Use case: UC19 - Add a task with a specified event date**

Similar to UC18, except the deadline is an event date.

**Use case: UC20 - Edit a task**

**MSS**

1.  User requests to view the list of tasks
2.  tApp displays all the tasks currently in the list
3.  User requests to edit a specific task from the list of tasks
4.  tApp edits the task
5.  tApp displays the task list

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 2a1. tApp shows an error message.
      Use case resumes at step 2.

**Use case: UC21 - Delete a task**

Similar to UC20 (Edit a task), except the task is deleted instead of edited.

**Use case: UC22 - Mark a task as done**

Similar to UC20 (Edit a task), except the task is marked as done instead of edited.

**Use case: UC23 - Clear task list**

Similar to UC8 (Clear student list), except we are clearing the task list.

**Use case: UC24 - Clear all entries in tApp**

Similar to UC8 (Clear student list), except we are clearing the whole address book.


### Non-Functional Requirements
* Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
* Should be able to hold up to 1000 students without a noticeable sluggishness in performance for typical usage.
* A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
* Commands used should be intuitive, and should not exceed 80 characters.
* System should respond almost immediately upon entering a command.
* Error messages shown should inform the user of what is wrong and what the correct command syntax should be.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Student contact**: A student entry with the corresponding student’s name, tutorial attendance, and tutorial participation.
* **Task**: An entry with a textual description of a piece of work to do, and a time that specifies the date that piece of work should be completed by
* **Directory**: The list commands entered will be applied to (either student or task)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file.
       Expected: Shows the GUI with a set of sample students. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a student

1. Adding a student while all students are being shown

### Deleting a student

1. Deleting a student while all students are being shown

    1. Prerequisites: List all students using the `students` command. Multiple students in the list.

    1. Test case: `deleteStudent 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.

    1. Test case: `deleteStudent 0`<br>
       Expected: No student is deleted. Error details shown in the status message.

    1. Other incorrect delete commands to try: `deleteStudent`, `deleteStudent x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Editing a student

### Marking a student's attendance

1. Marking a student while all students are being shown

    1. Prerequisites: List all students using the `students` command. Multiple students in the list.

    1. Test case: `marka 1 w/1`<br>
       Expected: First contact is marked as present in the list. Status message shows details of his week 1 attendance.

    1. Test case: `marka 0 w/1`<br>
       Expected: No student is marked. Error details shown in the status message.

    1. Other incorrect mark attendance commands to try: `marka`, `marka x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. Marking a student while on another directory

    1. Prerequisites: Perform a `findStudent` command: e.g. `find David`.

    1. Test case: `marka 1 w/1`<br>
       Expected: First contact in the last filtered students list (David) is marked as present. Status message shows details of student's week 1 attendance. Updated students list is shown.

    1. Test case: `marka 1 w/1`<br>
       Expected: First contact in student list (Alex) is marked as present/absent depending on his last attendance status. Status message shows details of student's week 1 attendance. Updated students list is shown.

1. Marking multiple students

    1. Test case: `marka 1 2 3 w/1`<br>
       Expected: Students 1, 2 and 3 are marked as present in the list. Status message shows details of their week 1 attendance.

    1. Test case: `marka 1 2 3 w/1`<br>
       Expected: Students 1, 2 and 3 are marked as absent in the list. Status message shows details of their week 1 attendance.

### Marking a student's participation

1. Marking a student while all students are being shown

    1. Prerequisites: List all students using the `students` command. Multiple students in the list.

    1. Test case: `markp 1 w/1`<br>
       Expected: First contact is marked as participated in the list. Status message shows details of his week 1 participation.

    1. Test case: `markp 0 w/1`<br>
       Expected: No student is marked. Error details shown in the status message.

    1. Other incorrect mark participation commands to try: `markp`, `markp x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. Marking a student while on another directory

    1. Prerequisites: Perform a `findStudent` command: e.g. `find David`.

    1. Test case: `markp 1 w/1`<br>
       Expected: First contact in the last filtered students list (David) is marked as participated. Status message shows details of student's week 1 participation. Updated students list is shown.

    1. Test case: `markp 1 w/1`<br>
       Expected: First contact in student list (Alex) is marked as participated/not participated depending on his last participation status. Status message shows details of student's week 1 participation. Updated students list is shown.

1. Marking multiple students

    1. Test case: `markp 1 2 3 w/1`<br>
       Expected: Students 1, 2 and 3 are marked as participated in the list. Status message shows details of their week 1 participation.

    1. Test case: `markp 1 2 3 w/1`<br>
       Expected: Students 1, 2 and 3 are marked as not participated in the list. Status message shows details of their week 1 participation.

### Finding a student

1. Finding a student while all students are being shown

    1. Prerequisites: List all students using the `students` command. Multiple students in the list.

    1. Test case: `findStudent al`<br>
       Expected: Students whose name contains "al" will be shown (e.g. Alex). Status message shows how many students has been found.

    1. Test case: `findStudent !alex! ben123`<br>
       Expected: Students whose name contains "alex" OR "ben" will be shown (e.g. Alex, Ben). Special characters and numbers ignored. Status message shows how many students has been found.

    1. Other incorrect findStudent commands to try: `findStudent`, `findStudent !` <br>
       Expected: No student found. Error details shown in the status message.

1. Finding a student while in another directory

   Expected: Similar to previous.

### Clearing student list

1. Clearing the student list

    1. Test case: `clearStudents`<br>
       Expected: All students cleared from student list. Existing students in groups will also be cleared, leaving empty groups.

### Adding a group

### Deleting a group

### Editing a group

### Finding a group

1. Finding a group while all groups are being shown

    1. Prerequisites: List all groups using the `groups` command. Multiple groups in the list.

    1. Test case: `findGroup w14`<br>
       Expected: Groups whose name contains "w14" will be shown (e.g. W14-4). Case-insensitive. Status message shows how many groups has been found.

    1. Test case: `findGroup w14 w15-2`<br>
       Expected: Groups whose name contains "w14" OR "w15-2" will be shown (e.g. W14-2, W14-4, W15-2). Ignores all special characters except dashes. Case-insensitive. Status message shows how many groups has been found.

    1. Other incorrect findGroup commands to try: `findGroup`, `findGroup !` <br>
       Expected: No groups found. Error details shown in the status message.

1. Finding a group while in another directory

   Expected: Similar to previous.

### Clearing group list

1. Clearing the group list

    1. Test case: `clearGroups`<br>
       Expected: All groups cleared from group list. Existing students in groups will be removed from their group (students will have no group).

### Adding a todo

### Adding a deadline task

### Adding an event task

### Editing a task

### Deleting a task

### Marking a task as done

### Clearing task list

### Clearing all data from tApp

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_
    
--------------------------------------------------------------------------------------------------------------------

[comment]: <> (TODO)
## **Effort**
We highly recommend adding an appendix named Effort that evaluators can use to estimate the total project effort.
Keep it brief (~1 page)
Explain the difficulty level, challenges faced, effort required, and achievements of the project.
If a significant part (e.g., more than 5%) of the effort was saved through reuse, mention what you reused and how it affected the effort e.g., the feature X is implemented using library Foo -- our work on adapting Foo to our product is contained in class FooAdapter.java.
Use AB3 as a reference point e.g., you can explain that while AB3 deals with only one entity type, your project was harder because it deals with multiple entity types.
