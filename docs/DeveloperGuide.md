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

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

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

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

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

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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
| `* * *`  | user                | add different links for each student/group contact   | easily access their Github, email etc                                  |
| `* * *`  | user                | record participation during tutorials                | accurately award participation marks to students                       |
| `* * *`  | user                | add descriptions to the tasks                        | see the extra detail pertaining to the task                            |
| `* *`    | user                | set my current directory to either students or tasks | add students/tasks more easily                                         |
| `* *`    | user                | access the ‘help’ page with all available commands   | refer to instructions when I forget how to use tApp                    |
| `* *`    | user                | allocate students to different groups                | track their progress based on their groups                             |
| `* *`    | user                | import data from CSV files                           | get started with the app quickly                                       |
| `* *`    | user                | edit tasks                                           | correct any errors I made without deleting and creating a new task     |
| `* *`    | new user            | search for a student                                 | quickly access all information related to the student                  |
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


*{More to be added}*

### Use cases

(For all use cases below, the **System** is `tApp` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Set current directory**

**MSS**

1.  User requests to go back to the welcome page to choose either students or tasks directory again.
2.  tApp navigates into the welcome page and displays the exit confirm message.

    Use case ends.

**Extensions**

* 1a.  The user hasn’t entered either the student or task management directory.

  Use case ends.

**Use case: UC2 - View student list/Enter student directory**

**MSS**

1.  User requests to navigate to the student directory (UC1)
2.  tApp navigates into the student directory
3.  tApp displays the list of student contacts

    Use case ends.

**Extensions**

* 1a. User is already in the student directory.

  Use case resumes at step 3.

* 2a. The list is empty.

  Use case ends.

**Use case: UC3 - Add a student contact**

**MSS**

1.  User requests to enter student directory (UC1)
2.  tApp navigates to student directory
3.  User requests to add a student contact
4.  tApp adds the student

    Use case ends.

**Extensions**

* 3a. No name is specified.

    * 3a1. tApp shows an error message.

      Use case ends.

**Use case: UC4 - Mark student as present**

**MSS**

1.  User requests to view students (UC2)
2.  tApp shows a list of persons
3.  User requests to mark a specific person as present in the list
4.  tApp marks the person as present

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 2b. User requests to mark a specific person as absent in the list.

    * 2b1. tApp marks the person as absent.

      Use case ends.

* 3a. The given index is invalid.

    * 3a1. tApp shows an error message.

      Use case resumes at step 2.

**Use case: UC5 - Delete a student contact**

**MSS**

1.  User requests to view students (UC2)
2.  tApp shows a list of persons
3.  User requests to delete a specific student in the list
4.  tApp deletes the person

    Use case ends.

**Extensions**

* 2a. The student list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. tApp shows an error message.

      Use case resumes at step 2.


**Use case: UC6 - View task list**

**MSS**

1.  User requests to view the list of tasks
2.  tApp displays all the tasks currently in the list

    Use case ends.

**Extensions**

* 2a. The task list is empty.

    * 2a1. tApp displays message that there are no tasks

      Use case ends.

**Use case: UC7 - Add task with time**

**MSS**

1.  User requests to create a task with the specified deadline
2.  tApp creates the task and stores it in the task list
3.  tApp displays the task that was just created

    Use case ends.

**Extensions**

* 1a. The task list is empty.

    * 1a1. tApp displays an error message stating that the task name is invalid

      Use case ends.

* 1b. The task list is empty.

    * 1b1. tApp displays an error message requesting the user to follow the correct format

      Use case ends.

**Use case: UC8 - Delete a task**

**MSS**

1.  User requests to view their list of tasks
2.  tApp displays all the tasks currently in the list
3.  User requests to delete a specific task from the list of tasks
4.  tApp deletes the task

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 2a1. tApp shows an error message.

      Use case resumes at step 2.

**Use case: UC9 - Mark a task as done**

**MSS**

1.  User requests to view their list of tasks
2.  tApp displays all the tasks currently in the list
3.  User requests to mark a specific task as done
4.  tApp marks the task as done

    Use case ends.

**Extensions**

* 2a. The list is empty.
      Use case ends.

* 3a. The given index is invalid.

    * 3a1.  tApp shows an error message.

      Use case ends.

### Non-Functional Requirements
* Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
* Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
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

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
