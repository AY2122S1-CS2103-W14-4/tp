package seedu.address.model.task;

import java.time.LocalDate;
import java.util.Set;

import seedu.address.model.tag.Tag;

public class DeadlineTask extends Task {

    private TaskDate dueDate;

    /**
     * Constructs a {@code Task}.
     *
     * @param name A valid TaskName.
     * @param tags A valid Set of Tags.
     */
    public DeadlineTask(TaskName name, Set<Tag> tags, boolean isDone,
                        TaskDate dueDate, Description description, Priority priority) {
        super(name, tags, isDone, description, priority);
        this.dueDate = dueDate;
    }

    public TaskDate getDeadline() {
        return dueDate;
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeadlineTask)) {
            return false;
        }

        DeadlineTask otherTask = (DeadlineTask) other;
        return otherTask.getName().equals(getName())
                && otherTask.getDeadline().equals(getDeadline())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nTaskDate: ")
                .append(getDeadline().toString())
                .append("\nStatus: ")
                .append(getStatusString())
                .append("\nPriority: ")
                .append(getPriorityAsString());
        if (!this.getDescription().isEmpty()) {
            builder.append("\nDescription: ")
                    .append(getDescription());
        }
        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("\nTags: ");
            tags.forEach(builder::append);
        }

        return builder.toString();
    }

    @Override
    public LocalDate getDate() {
        return this.dueDate.getDeadline();
    }

    @Override
    public DeadlineTask clone() {
        DeadlineTask clone = (DeadlineTask) super.clone();
        clone.dueDate = dueDate;
        return clone;
    }
}
