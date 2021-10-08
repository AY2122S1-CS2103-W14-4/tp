package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getTagSet("friends"),
                    getAttendanceList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends"),
                    getAttendanceList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getTagSet("neighbours"),
                    getAttendanceList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getTagSet("family"),
                    getAttendanceList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getTagSet("classmates"),
                    getAttendanceList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getTagSet("colleagues"),
                    getAttendanceList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }


    /**
     * Returns a attendance list containing the list of integers given.
     */
    public static Attendance getAttendanceList(Integer... integers) {
        return new Attendance(new ArrayList<>(Arrays.asList(integers)));
    }

}
