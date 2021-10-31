package seedu.address.model.student;

import java.util.ArrayList;

/**
 * Represents a Student's tutorial participation in tApp.
 * Guarantees: immutable;
 */
public class Participation {

    public static final String MESSAGE_CONSTRAINTS =
            "The week parameter should contain one positive integer between %1$s and %2$s!";

    public static final int FIRST_WEEK_OF_SEM = 3;
    public static final int LAST_WEEK_OF_SEM = 12;

    public final ArrayList<Integer> participationList;

    /**
     * Constructs an {@code Participation}.
     */
    public Participation() {
        participationList = new ArrayList<>();
        for (int i = FIRST_WEEK_OF_SEM; i <= LAST_WEEK_OF_SEM; i++) {
            participationList.add(0);
        }
    }

    /**
     * Constructs an existing {@code Participation}.
     */
    public Participation(ArrayList<Integer> participationList) {
        this.participationList = participationList;
    }

    /**
     * Toggles participation from 0 to 1, or 1 to 0
     * for the {@code week}.
     *
     * @param week participation for the week
     */
    public void toggleParticipation(int week) {
        int isPresent = participationList.get(week);
        if (isPresent == 1) {
            participationList.set(week, 0);
        } else {
            participationList.set(week, 1);
        }
    }

    /**
     * Checks if a student has participated during the specified {@code week}.
     *
     * @param week week to be checked.
     * @return int specifying if student has participated.
     */
    public int checkParticipated(int week) {
        return participationList.get(week);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Participation // instanceof handles nulls
                && participationList.equals(((Participation) other).participationList)); // state check
    }

    @Override
    public int hashCode() {
        return participationList.hashCode();
    }
}
