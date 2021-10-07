package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonAdaptedPerson.java
import seedu.address.model.person.*;
=======
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
>>>>>>> 5d2e16017f6223caf7ddd4bfd545206c2ddb3b24:src/main/java/seedu/address/storage/JsonAdaptedStudent.java
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String email;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    //Attendance and participation added
    private Attendance attendance;
    private Participation participation;

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonAdaptedPerson.java
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("attendance") String attendance,
                             @JsonProperty("participation") String participation) {
=======
    public JsonAdaptedStudent(@JsonProperty("name") String name,
                              @JsonProperty("email") String email,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
>>>>>>> 5d2e16017f6223caf7ddd4bfd545206c2ddb3b24:src/main/java/seedu/address/storage/JsonAdaptedStudent.java
        this.name = name;
        this.email = email;
        this.attendance = new Attendance();
        this.participation = new Participation();
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        email = source.getEmail().value;
        attendance = source.getAttendance();
        participation = source.getParticipation();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> studentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            studentTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);


        final Set<Tag> modelTags = new HashSet<>(studentTags);
        return new Student(modelName, modelEmail, modelTags);
    }

}
