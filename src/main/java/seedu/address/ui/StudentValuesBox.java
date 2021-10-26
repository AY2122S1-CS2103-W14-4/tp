package seedu.address.ui;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.Participation;

public class StudentValuesBox extends VBox {

    public static final String ATTENDANCE_HEADER = "Attendance";
    public static final String PARTICIPATION_HEADER = "Participation";
    private static final String FXML = "/view/StudentValuesBox.fxml";

    @FXML
    private Label valueHeader;

    @FXML
    private GridPane valueArrayGrid;

    /**
     *
     */
    public StudentValuesBox(String header, Attendance attendance) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        valueHeader.setText(header);


        ObservableList<Node> children = valueArrayGrid.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (attendance.checkPresent(i) == 1) {
                children.get(i).getStyleClass().add("studentValuePositive");
            } else {
                children.get(i).getStyleClass().add("studentValueNegative");
            }
        }
    }

    /**
     *
     */
    public StudentValuesBox(String header, Participation participation) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(FXML));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        valueHeader.setText(header);

        ObservableList<Node> children = valueArrayGrid.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (participation.checkParticipated(i) == 1) {
                children.get(i).getStyleClass().add("studentValuePositive");
            } else {
                children.get(i).getStyleClass().add("studentValueNegative");
            }
        }
    }
}
