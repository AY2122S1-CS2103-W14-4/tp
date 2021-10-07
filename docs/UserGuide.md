---
layout: page
title: User Guide
---

tApp is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI). If you are a TA that is looking for an app that integrates different tools into a centralised platform and tracks your TA tasks, tApp is the app for you.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `tApp.jar` from [here](https://github.com/AY2122S1-CS2103-W14-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your tApp.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`students`** : Lists all students.

   * **`add`**`Tom Lim` : Adds a student named `Tom Lim` to the student list.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add NAME`, `NAME` is a parameter which can be used as `add John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME s/STUDENT_NUMBER`, `s/STUDENT_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Lists all students: `students`

Shows a list of all students inputted into tApp together with their attendance and participation scores for tutorials.

Format: `students`

Examples:
* `students` returns a list of all students with their information and attendance scores

### Add a student : `add`

Adds a student to the student list.

Format: `add NAME`

Examples:
* `add Tom Lim` adds Tom Lim to the list and displays a confirmation output

### Navigation: `/back`

Go back to the welcome page to redecide.

Format: `/back`

* `/back` should be exactly entered to go back to the initial (welcome) page

Examples:
* `/back` displays the following output:
  You exited the current directory!

### Listing the task list : `tasks`

Displays all the tasks currently in the list.

Format: `tasks`

### Adding a task with a date: `add TASK_NAME /by DATE`

Adds a task with the given name and a specified deadline.

Format: `add TASK_NAME /by DATE`

* Adds a task with the specified `TASK_NAME`
* The task has the deadline `DATE`
* The deadline must be in the format `YYYY-MM-dd`

Examples:
* `add grade scripts /by 2021-09-23` creates the task "grade scripts", which is to be completed by the given date.
* `add tutorial preparation /by 2021-10-04` creates the task "tutorial preparation", which is to be completed by the given date.

### Marking a student as present: `mark`

Marks the specified student in the student list as present or absent.

Format: `mark INDEX /wWEEK NUMBER`

* Marks the person as present in the specified `INDEX`.
* The index must refer to the index number shown in the displayed students list.
* The index and week number must be a positive number: 1, 2, 3…
* If the student at the specified `INDEX` is marked as present, the command toggles the attendance to absent.

Examples:

* `students` followed by `mark 1 /w1` marks the 1st person in the students list as present in week 1.
* Another `mark 1 /w1` instance will mark the 1st person in the student list as absent in week 1.

Sample Usage:

`> mark 1 /w1`

    > Kho Tze Jit is marked as present for week 1!

`> mark 1 /w1`

    > Kho Tze Jit is marked as absent for week 1!

### Deleting a student contact: `delete INDEX`

Deletes the specified student from the student list.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index must refer to the index number shown in the displayed students list.
* The index and week number must be a positive number: 1, 2, 3…

Examples:

* `students` followed by `delete 1` removes the 1st person in the students list.

Sample Usage:

`> delete 1`

    > Kho Tze Jit is removed from the student list!

### Marking a task as done: : `done`

Mark the specified task from the list as done.

Format: `done INDEX`

* Mark the task as done at the specified `INDEX`.
* The index refers to the index number shown in the task list.
* The index must be a positive integer 1, 2, 3, …​

Examples:
* `tasks` followed by `done 2` marks the 2nd task in the task list as completed.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

tApp data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

[comment]: <> (TODO)
### Viewing help : `help` `[coming in v1.3]`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

[comment]: <> (TODO)
### Editing a person : `edit` `[coming in v1.3]`

Edits an existing person in the students list.

Format: `edit INDEX [n/NAME] [s/STUDENT_NUMBER] [e/EMAIL] [g/GITHUB_LINK] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 s/A0221111L e/johndoe@example.com` Edits the student number and email address of the 1st person to be `A0221111L` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

[comment]: <> (TODO)
### Locating persons by name: `find` `[coming in v1.3]`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

[comment]: <> (TODO)
### Clearing all entries from task list : `clear` `[coming in v1.3]`

Clears all entries from the task list.

Format: `clear`

### Editing the data file `[coming in v1.3]`

tApp data are saved as a JSON file `[JAR file location]/data/tApp.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, tApp will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

[comment]: <> (TODO)
## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous tApp home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**List Students** | `students`
**List Tasks** | `tasks`
**Add Student** | `add n/NAME s/STUDENT_NUMBER e/EMAIL g/GITHUB_LINK [t/TAG]…​` <br> e.g., `add n/James Ho s/A0221111L e/jamesho@example.com g/https://github.com/james t/W14-4`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [s/STUDENT_NUMBER] [e/EMAIL] [g/GITHUB_LINK] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Help** | `help`
