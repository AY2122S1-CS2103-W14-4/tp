package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class ToDoTaskCard extends UiPart<Region> {

    private static final String FXML = "TodoTaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private Label taskType;
    @FXML
    private Label priorityLabel;

    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public ToDoTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        name.setText(task.getName().toString());
        status.setText(task.getStatusString());
        description.setText(task.getDescription());
        priorityLabel.setText(task.getPriorityAsString());
        taskType.setText("TODO");

        task.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ToDoTaskCard)) {
            return false;
        }

        // state check
        ToDoTaskCard card = (ToDoTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
