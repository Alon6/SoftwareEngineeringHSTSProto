package il.cshaifasweng.OCSFMediatorExample.client;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.Arrays;
import java.util.List;


public class TeacherAddQuestion implements Initializable {

    @FXML
    private Button another_q_btn;

    @FXML
    private TextField answer1_text_field;

    @FXML
    private TextField answer2_text_field;

    @FXML
    private TextField answer3_text_field;

    @FXML
    private TextField answer4_text_field;

    @FXML
    private Button courses_button;

    @FXML
    private ListView<String> courses_list_view;

    @FXML
    private Button go_back_button;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField question_text_field;

    @FXML
    private TextArea question_id_text_area;

    @FXML
    private Button save_q_btn;

    @FXML
    private TextArea selected_courses_text_area;

    @FXML
    private ChoiceBox<String> subjects_choice_box;

    @FXML
    private Label teacher_id;

    @FXML
    private Label teacher_name;

    @FXML
    void AnotherQuestionBtn(ActionEvent event) {

    }
    private static String msg;

    public static void receiveMessage(String message)
    {
        msg = message;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize subjects_choice_box
        SimpleClient.sendMessage("get all subjects");
        while (msg == null){}
        String[] subjects = msg.split("___");
        List<String> subjectList = Arrays.asList(subjects);
        subjects_choice_box.setItems((ObservableList<String>) subjectList);
        msg = null;

        // initialize courses_list_view
        SimpleClient.sendMessage("get all courses");
        while (msg == null){}
        String[] courses = msg.split("___");
        List<String> courseList = Arrays.asList(courses);
        courses_list_view.setItems((ObservableList<String>) courseList);
    }

    @FXML
    void coursesBtnPushed(ActionEvent event) {
        String textAreaString = "";
        List<String> selectedCourses = courses_list_view.getSelectionModel().getSelectedItems();
        for (Object item : selectedCourses)
        {
            textAreaString += String.format("%s%n",(String) item);
        }
        selected_courses_text_area.setText(textAreaString);
    }

    @FXML
    void initializeAns1TF(ActionEvent event) {

    }

    @FXML
    void initializeAns2TF(ActionEvent event) {

    }

    @FXML
    void initializeAns3TF(ActionEvent event) {

    }

    @FXML
    void initializeAns4TF(ActionEvent event) {

    }

    @FXML
    void initializeQuestionTF(ActionEvent event) {

    }

    @FXML
    void saveQuestionBtn(ActionEvent event) {

    }

    @FXML
    void viewLastPage(ActionEvent event) {

    }

}