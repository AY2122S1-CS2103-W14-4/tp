---
layout: page
title: Raveen's Project Portfolio Page
---

### Project: tApp
tApp is a desktop app for managing tutorial groups and personal tasks, optimized for use via a Command Line Interface 
(CLI). It has a GUI created with JavaFX. It is written in Java, and has about 15 kLoC.

Given below are my contributions to the project.

* **Code contributed:** [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=raveen)

* **New Feature**: Added priority levels to each of the tasks. (Pull requests [\#124](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/124))
    * What it does: Allows users to specify each new task they add with a priority level.
    * Justification: tA, who are the target users, have many tasks to do. Being very busy, it helps for them to be able
      to see tasks that are urgent and requires immediate attention. 
    * Highlights: This enhancement is implemented in a way that makes this command very flexible. It is designed in a way to 
      accept flexible commands, such as partial commands and are not case-sensitive. Also, to make it convenient, if users
      do not specify any priority level, the task will be marked low by default.  

* **New Feature**: Refactored the Task Model. (Pull requests [\#115](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/115))
    * What it does: The add task command now accepts three types of tasks: Todos, Deadlines & Events.
    * Justification: There are primarily three types of tasks for one to consider, tasks that happen on a date, Events,
      tasks that needs to be completed by a date, Deadlines, and tasks that are not associated with a date.
    * Highlights: 

* **New Feature**: Implemented the Done Task Command. (Pull requests [\#85](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/85))
    * What it does: Marks a specified task as completed.
    * Justification: Tasks needs are meant for the users to do. And it is necessary to reflect the status of the task
      in the app.
    * Highlights: Command is a toggle. User is able to undo the mark task as done command using the same command. 

* **Project management**:
    * Managed releases [`v1.2.1`](https://github.com/AY2122S1-CS2103-W14-4/tp/releases/tag/v1.2.1) - 
      [`v1.3`](https://github.com/AY2122S1-CS2103-W14-4/tp/releases/tag/v1.3) on GitHub

* **Enhancements to existing features**:
    * Implemented clear all tasks command, by drawing inspiration from clear address book command.
    * Wrote additional tests for existing features to increase coverage from 61% to 65% 
      (Pull requests [\#136](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/136))

* **Documentation**:
    * User Guide:
        * Added relevant documentation for following tasks featuresL: [\#147](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/147), 
          [\#136](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/136)
            * `doneTask`, `deleteTask`, `editTask`, `deadline`, `event`, `todo`, `tasks`
    * Developer Guide:
        * Added implementation details of the `AddTodoTaskCommand` feature.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#241](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/241), 
      [\#152](https://github.com/AY2122S1-CS2103-W14-4/tp/pull/152),
