package seedu.address.model.group;

import java.util.ArrayList;

import seedu.address.model.student.Student;

public class Members {

    public static final String MESSAGE_CONSTRAINTS = "Weeks should be between 1 and %1$s";

    public final ArrayList<Student> studentList;

    /**
     * Constructs a {@code Members}.
     */
    public Members() {
        studentList = new ArrayList<>();
    }

    /**
     * Constructs a existing {@code Members}.
     */
    public Members(ArrayList<Student> studentList) {
        this.studentList = new ArrayList<>();
        this.studentList.addAll(studentList);
    }

    public void addMember(Student student) {
        this.studentList.add(student);
    }

    public void removeMember(Student student) {
        this.studentList.removeIf(s -> s.isSameStudent(student));
    }

    public void removeAllMembers() {
        this.studentList.clear();
    }

    /**
     * Updates a group member when edited
     * @param target group member to be edited
     * @param student edited group member
     */
    public void updateMember(Student target, Student student) {
        removeMember(target);
        addMember(student);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Members // instanceof handles nulls
                && studentList.equals(((Members) other).studentList)); // state check
    }

    @Override
    public String toString() {
        if (studentList.isEmpty()) {
            return "No members";
        }
        StringBuilder sb = new StringBuilder();
        for (Student m : studentList) {
            sb.append(m.getName().fullName).append(", ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return studentList.hashCode();
    }

}
