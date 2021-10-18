package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkTaskDoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MarkTaskDoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkTaskDoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkTaskDoneCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskDoneCommand.MESSAGE_USAGE), pe);
        }
    }
}
