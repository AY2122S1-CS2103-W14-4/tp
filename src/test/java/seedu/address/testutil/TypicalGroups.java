package seedu.address.testutil;

import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.DANIEL;
import static seedu.address.testutil.TypicalStudents.ELLE;
import static seedu.address.testutil.TypicalStudents.FIONA;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {

    public static final Group GROUP1 = new GroupBuilder().withName("w14-4").withMembers(BENSON, CARL, DANIEL).build();
    public static final Group GROUP2 = new GroupBuilder().withName("w15-5").withMembers(ELLE, FIONA).withRepo("tp")
            .withYear("AY20212022").withTags("nottApp").build();
    public static final Group GROUP3 = new GroupBuilder().withName("m16-6").withRepo("ip").withYear("AY20192020")
            .build();

    private TypicalGroups() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBookWithGroups() {
        AddressBook ab = new AddressBook();
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(GROUP1, GROUP2, GROUP3));
    }
}