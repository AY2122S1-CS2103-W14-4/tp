package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_WEEK;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DUPLICATE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkStudentPartCommand;
import seedu.address.model.student.Participation;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MarkStudentPartCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MarkStudentPartCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MarkStudentPartCommandParserTest {

    private MarkStudentPartCommandParser parser = new MarkStudentPartCommandParser();

    @Test
    public void parse_validArgs_returnsMarkStudentPartCommand() {
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + VALID_WEEK;

        assertParseSuccess(parser, userInput,
                new MarkStudentPartCommand(Collections.singletonList(INDEX_FIRST_STUDENT), 1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkStudentPartCommand.MESSAGE_USAGE));

        assertParseFailure(parser, String.valueOf(Participation.LAST_WEEK_OF_SEM + 1),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkStudentPartCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 12",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkStudentPartCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkStudentPartCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 1 1 w/1", MESSAGE_INVALID_DUPLICATE_INDEX);

        assertParseFailure(parser, "1 w/", MESSAGE_EMPTY_WEEK);
    }
}
