package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.NameContainsGroupKeywordsPredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.task.NameContainsTestKeywordsPredicate;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TASK_NAME_STUDY = "Study";
    public static final String VALID_TASK_NAME_PLAY = "Play";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_STUDENTNUMBER_AMY = "A1112223B";
    public static final String VALID_STUDENTNUMBER_BOB = "C1102923T";
    public static final String VALID_USERNAME_AMY = "ai_mee";
    public static final String VALID_USERNAME_BOB = "bob-the-builder";
    public static final String VALID_REPONAME_AMY = "aimee.repo";
    public static final String VALID_REPONAME_BOB = "bob_repo";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_DESCRIPTION_FUN = "fun";
    public static final String VALID_DESCRIPTION_BORING = "boring";



    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TASKNAME_DESC_STUDY = " " + PREFIX_NAME + VALID_TASK_NAME_STUDY;
    public static final String TASKNAME_DESC_PLAY = " " + PREFIX_NAME + VALID_TASK_NAME_PLAY;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DESCRIPTION_DESC_FUN = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_FUN;
    public static final String DESCRIPTION_DESC_BORING = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BORING;
    public static final String STUDENTNUMBER_DESC_AMY = " " + PREFIX_STUDENTNUMBER + VALID_STUDENTNUMBER_AMY;
    public static final String STUDENTNUMBER_DESC_BOB = " " + PREFIX_STUDENTNUMBER + VALID_STUDENTNUMBER_BOB;
    public static final String USERNAME_DESC_AMY = " " + PREFIX_USERNAME + VALID_USERNAME_AMY;
    public static final String USERNAME_DESC_BOB = " " + PREFIX_USERNAME + VALID_USERNAME_BOB;
    public static final String REPONAME_DESC_AMY = " " + PREFIX_REPO + VALID_REPONAME_AMY;
    public static final String REPONAME_DESC_BOB = " " + PREFIX_REPO + VALID_REPONAME_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String VALID_WEEK = " " + PREFIX_WEEK + 1;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "&"; // '&' not allowed in desc
    public static final String INVALID_STUDENTNUMBER_DESC = " " + PREFIX_STUDENTNUMBER + "A1233222";
    // missing final character
    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USERNAME + "a@my"; // '@' not allowed in username
    public static final String INVALID_REPONAME_DESC = " " + PREFIX_REPO + "boB!"; // '!' not allowed in reponame
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditStudentDescriptor DESC_AMY;
    public static final EditStudentCommand.EditStudentDescriptor DESC_BOB;

    public static final EditTaskCommand.EditTaskDescriptor DESC_STUDY;
    public static final EditTaskCommand.EditTaskDescriptor DESC_PLAY;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withStudentNumber(VALID_STUDENTNUMBER_AMY).withUserName(VALID_USERNAME_AMY)
                .withRepoName(VALID_REPONAME_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withStudentNumber(VALID_STUDENTNUMBER_BOB).withUserName(VALID_USERNAME_BOB)
                .withRepoName(VALID_REPONAME_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_STUDY = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_STUDY).withTags(VALID_TAG_FRIEND).build();
        DESC_PLAY = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_PLAY).withTags(VALID_TAG_HUSBAND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertTaskCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().taskName.split("\\s+");
        model.updateFilteredTaskList(new NameContainsTestKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the groups at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showGroupAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGroupList().size());

        Group group = model.getFilteredGroupList().get(targetIndex.getZeroBased());
        final String[] splitName = group.getName().name.split("\\s+");
        model.updateFilteredGroupList(new NameContainsGroupKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredGroupList().size());
    }

}
