package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.DisplayType.GROUPS;
import static seedu.address.model.Model.DisplayType.STUDENTS;
import static seedu.address.model.Model.DisplayType.TASKS;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.commons.RepoName;
import seedu.address.model.group.Group;
import seedu.address.model.group.LinkYear;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Group> filteredGroups;
    private DisplayType displayType;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        displayType = STUDENTS;
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.addressBook.getStudentList());
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    public DisplayType getDisplayType() {
        return displayType;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    public void clearTasks() {
        this.addressBook.clearAllTask();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return addressBook.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        addressBook.removeStudent(target);
        if (!(target.getGroupName().isNull())) {
            List<Group> groupList = getFilteredGroupList();
            Group group = groupList.stream()
                                          .filter(g -> g.getName().equals(target.getGroupName()))
                                          .findAny()
                                          .orElse(null);
            Group updatedGroup = group;
            updatedGroup.getMembers().removeMember(target);
            addressBook.setGroup(group, updatedGroup);
        }
    }

    @Override
    public void addStudent(Student student) {
        addressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        if (!(target.getGroupName().isNull())) {
            List<Group> groupList = getFilteredGroupList();
            Group updatedGroup = groupList.stream()
                    .filter(g -> g.getName().equals(target.getGroupName()))
                    .findAny()
                    .orElse(null);
            updatedGroup.getMembers().updateMember(target, editedStudent);
        }

        addressBook.setStudent(target, editedStudent);
    }

    @Override
    public void markStudentAttendance(Student target, int week) {
        requireAllNonNull(target);
        Student newPerson = target;

        newPerson.getAttendance().toggleAttendance(week);
        addressBook.setStudent(target, newPerson);
    }

    @Override
    public String getStudentAttendance(Student target, int week) {
        requireAllNonNull(target);
        return target.getAttendance().checkPresent(week) == 1 ? "present" : "absent";
    }

    @Override
    public void markStudentParticipation(Student target, int week) {
        requireAllNonNull(target);
        Student newPerson = target;

        newPerson.getParticipation().toggleParticipation(week);
        addressBook.setStudent(target, newPerson);
    }

    @Override
    public String getStudentParticipation(Student target, int week) {
        requireAllNonNull(target);
        return target.getParticipation().checkPresent(week) == 1 ? "participated" : "not participated";
    }

    /**
     * Adds a student group.
     *
     * @param student student to add to group
     * @param group student group to add student into
     */
    public void addStudentGroup(Student student, Group group) {
        requireAllNonNull(student, group);
        Group newGroup = group;
        Student updatedStudent = new Student(student.getName(), student.getEmail(), student.getStudentNumber(),
                student.getUserName(), student.getRepoName(), student.getTags(), student.getAttendance(),
                student.getParticipation(), group.getName());
        newGroup.getMembers().addMember(updatedStudent);
        addressBook.setStudent(student, updatedStudent);
        addressBook.setGroup(group, newGroup);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    /**
     * Checks if a task exists
     *
     * @param task task to check
     * @return true if task exists, false otherwise
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return addressBook.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addTask(Task student) {
        addressBook.addTask(student);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        addressBook.setTask(target, editedTask);
    }

    @Override
    public void completeTask(Task target) {
        requireAllNonNull(target, target);
        TaskName name = target.getName();
        Set<Tag> tags = target.getTags();
        Task newTask = new Task(name, tags, true);
        newTask.markTaskComplete();
        addressBook.setTask(target, newTask);
    }

    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return addressBook.hasGroup(group);
    }

    @Override
    public void deleteGroup(Group target) {
        addressBook.removeGroup(target);
    }

    @Override
    public void addGroup(Group group) {
        addressBook.addGroup(group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    @Override
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        addressBook.setGroup(target, editedGroup);
    }

    @Override
    public void addGithubGroup(LinkYear year, RepoName repoName, Group group) {
        requireAllNonNull(year, repoName, group);
        Group newGroup = new Group(group.getName(), group.getMembers(), year, repoName, group.getTags());
        addressBook.setGroup(group, newGroup);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        displayType = STUDENTS;
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        displayType = TASKS;
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //=========== Filtered Group List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        displayType = GROUPS;
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }
}
