package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TASK_NAME = "Do CS2103 tP";
    public static final String DEFAULT_TASK_DATE = "2021-10-10";

    private TaskName taskName;
    private TaskDate taskTaskDate;
    private Set<Tag> tags;
    private boolean isDone;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public TaskBuilder() {
        this.taskName = new TaskName(DEFAULT_TASK_NAME);
        this.taskTaskDate = new TaskDate(DEFAULT_TASK_DATE);
        this.tags = new HashSet<>();
        this.isDone = false;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        this.taskName = taskToCopy.getName();
//        this.taskTaskDueDate = taskToCopy.getDeadline();
        this.tags = new HashSet<>(taskToCopy.getTags());
        this.isDone = taskToCopy.checkIsDone();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.taskName = new TaskName(name);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String date) {
        this.taskTaskDate = new TaskDate(date);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Task build() {
        return new Task(taskName, tags, false);
    }

}
