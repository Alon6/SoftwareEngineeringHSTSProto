package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Pupil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherCheckExam implements Initializable {
    @FXML
    private Button check_another_exam_button;

    @FXML
    private ChoiceBox<String> course_choice_box;

    @FXML
    private Button go_back_button;

    @FXML
    private TextField grade;

    @FXML
    private TextField note_to_student;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button save_btn;

    @FXML
    private ChoiceBox<String> student_choice_box;

    @FXML
    private ChoiceBox<String> subjects_choice_box;

    @FXML
    private Label teacher_id1;

    @FXML
    private Label teacher_id11;

    @FXML
    private Label teacher_name;

    @FXML
    private ChoiceBox<String> exam_choice_box;

    @FXML
    private ListView<String> correct_questions_listview;

    @FXML
    private ListView<String> wrong_questions_listview;

    private String subject_name;

    private String course_name;

    private String exam_name;

    private String[] details_for_each_student;

    private int pupil_index;

    private String[] out_string;

    private String prev_id;

    private Boolean[] used;

    private static String msg;

    public static void receiveMessage(String message)
    {
        msg = message;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //EventBus.getDefault().register(this);
        // initialize subjects_choice_box
        teacher_name.setText(SimpleClient.name);
        subjects_choice_box.setOnAction(this::addSubject);
        course_choice_box.setOnAction(this::addCourse);
        exam_choice_box.setOnAction(this::addExam);
        student_choice_box.setOnAction(this::addPupil);
//        question_choice_box.setOnAction(this::addQuestion);
        SimpleClient.sendMessage("gET all SUbjects" + SimpleClient.name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after first while loop");
        String[] subjects = msg.split("___");
        List<String> subjectList = Arrays.asList(subjects);

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList = FXCollections.observableArrayList(subjectList);
        subjects_choice_box.setItems(observableList);
        msg = null;
        this.pupil_index = -1;
/**/
//        // initialize courses_list_view
//        SimpleClient.sendMessage("get all courses" + SimpleClient.name);
//        while (msg == null){
//            System.out.print("");
//        }
//        System.out.println("after second while loop");
//        String[] courses = msg.split("___");
//        List<String> courseList = Arrays.asList(courses);
//
//        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
//        ObservableList<String> observableList1 = FXCollections.observableArrayList(courseList);
//        courses_list_view.setItems(observableList1);
//        msg = null;

    }

    @FXML
    void initializeGradeTF(ActionEvent event) {

    }

    @FXML
    void initializeNote_to_studentTF(ActionEvent event) {

    }

    @FXML
    void saveBtn(ActionEvent event) {
        String[] split_students_name = this.student_choice_box.getValue().split(" ");
        this.out_string[this.pupil_index] = split_students_name[split_students_name.length - 1] + "```" + this.grade.getText() + "```" + this.note_to_student.getText();
        String description_string = exam_name.split("@")[1];
        for (String pup: this.out_string)
        {
            description_string += ("___" + pup);
        }
        SimpleClient.sendMessage("SaVe UPdAted GRADes" + description_string);
    }

    @FXML
    void viewLastPage(ActionEvent event) {

    }

    @FXML
    void check_another_examBtn(ActionEvent event) {

    }

    public void addSubject(ActionEvent event)
    {
        this.subject_name = subjects_choice_box.getValue();
        // initialize courses_list_view
        SimpleClient.sendMessage("geT all Courses" + this.subject_name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        String[] courses = msg.split("___");
        List<String> courseList = Arrays.asList(courses);

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList1 = FXCollections.observableArrayList(courseList);
        course_choice_box.setItems(observableList1);
        msg = null;
        /*SimpleClient.sendMessage("get all QUestions" + this.subject_name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        String[] questions = msg.split("___");
        this.questionList = Arrays.asList(questions);
        List<String> real_questions = new ArrayList<String>();
        for (String q : questionList)
        {
            real_questions.add(q.split("---")[0]);
        }

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList = FXCollections.observableArrayList(real_questions);
        question_choice_box.setItems(observableList);
        msg = null;*/
    }

    public void addCourse(ActionEvent event)
    {
        this.course_name = course_choice_box.getValue();
        // initialize courses_list_view
        SimpleClient.sendMessage("get all REaDy Exams" + this.course_name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        String[] ready_exams = msg.split("~~~");
        List<String> examList = Arrays.asList(ready_exams);

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList1 = FXCollections.observableArrayList(examList);
        exam_choice_box.setItems(observableList1);
        msg = null;
        /*SimpleClient.sendMessage("get all QUestions" + this.subject_name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        String[] questions = msg.split("___");
        this.questionList = Arrays.asList(questions);
        List<String> real_questions = new ArrayList<String>();
        for (String q : questionList)
        {
            real_questions.add(q.split("---")[0]);
        }

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList = FXCollections.observableArrayList(real_questions);
        question_choice_box.setItems(observableList);
        msg = null;*/
    }
    public void addExam(ActionEvent event)
    {
        this.exam_name = exam_choice_box.getValue();
        // initialize courses_list_view
        SimpleClient.sendMessage("get all details relevant to ReadyEXAm" + this.exam_name.split("@")[1]);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        this.details_for_each_student = msg.split("~~~");
        String[] pupils = msg.split("~~~");
        this.out_string = new String[pupils.length];
        this.used = new Boolean[pupils.length];
        Arrays.fill(this.used, false);
        for (int i=0; i < pupils.length; i++)
        {
            pupils[i] = this.details_for_each_student[i].split("___")[0];
        }
        List<String> pupilList = Arrays.asList(pupils);
        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList1 = FXCollections.observableArrayList(pupilList);
        student_choice_box.setItems(observableList1);
        msg = null;

        /*SimpleClient.sendMessage("get all QUestions" + this.subject_name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        String[] questions = msg.split("___");
        this.questionList = Arrays.asList(questions);
        List<String> real_questions = new ArrayList<String>();
        for (String q : questionList)
        {
            real_questions.add(q.split("---")[0]);
        }

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList = FXCollections.observableArrayList(real_questions);
        question_choice_box.setItems(observableList);
        msg = null;*/
    }

    public void addPupil(ActionEvent event)
    {
//        this.exam_name = exam_choice_box.getValue();
//        // initialize courses_list_view
//        SimpleClient.sendMessage("get all details relevant to ReadyEXAm" + this.exam_name.split("@")[1]);
//        while (msg == null){
//            System.out.print("");
//        }
//        System.out.println("after second while loop");
//        this.details_for_each_student = msg.split("~~~");
//        String[] pupils = msg.split("~~~");
//        for (int i=0; i < pupils.length; i++)
//        {
//            pupils[i] = this.details_for_each_student[i].split("___")[0];
//        }
//        List<String> pupilList = Arrays.asList(pupils);
//
//        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
//        ObservableList<String> observableList1 = FXCollections.observableArrayList(pupilList);
//        student_choice_box.setItems(observableList1);
//        msg = null;
        if (this.pupil_index != -1)
        {
//            String[] split_students_name = this.student_choice_box.getValue().split(" ");
            this.out_string[this.pupil_index] = this.prev_id + "```" + this.grade.getText() + "```" + this.note_to_student.getText();
        }
        this.pupil_index = student_choice_box.getSelectionModel().getSelectedIndex();
//        System.out.println(this.pupil_index);
        if (!this.used[this.pupil_index]){
            this.grade.setText(this.details_for_each_student[pupil_index].split("___")[1]);
            this.note_to_student.setText(this.details_for_each_student[pupil_index].split("___")[2]);
        }
        else
        {
            this.grade.setText(this.out_string[this.pupil_index].split("```")[1]);
            this.note_to_student.setText(this.out_string[this.pupil_index].split("```")[2]);
        }
        //////////////
        String[] split_students_name = this.student_choice_box.getValue().split(" ");
        this.prev_id = split_students_name[split_students_name.length - 1];
        this.used[this.pupil_index] = true;

        /******************/
        String[] correct_questions = this.details_for_each_student[this.pupil_index].split("___")[3].split("§§§")[0].split("```");
        for (int i=0; i < correct_questions.length; i++)
        {
            correct_questions[i] = correct_questions[i].split("---")[0] + " " + correct_questions[i].split("---")[1] + " points";
        }
        List<String> correct_questionsList = Arrays.asList(correct_questions);

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList12 = FXCollections.observableArrayList(correct_questionsList);
        this.correct_questions_listview.setItems(observableList12);
        String[] wrong_questions = this.details_for_each_student[this.pupil_index].split("___")[3].split("§§§")[1].split("```");
        for (int i=0; i < wrong_questions.length; i++)
        {
            wrong_questions[i] = wrong_questions[i].split("---")[0] + " " + wrong_questions[i].split("---")[1] + " points";
        }
        List<String> wrong_questionsList = Arrays.asList(wrong_questions);

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList21 = FXCollections.observableArrayList(wrong_questionsList);
        this.wrong_questions_listview.setItems(observableList21);
        /******************/
        /*SimpleClient.sendMessage("get all QUestions" + this.subject_name);
        while (msg == null){
            System.out.print("");
        }
        System.out.println("after second while loop");
        String[] questions = msg.split("___");
        this.questionList = Arrays.asList(questions);
        List<String> real_questions = new ArrayList<String>();
        for (String q : questionList)
        {
            real_questions.add(q.split("---")[0]);
        }

        // Create a new ObservableList and pass the arrayArrayList as an argument to the FXCollections.observableArrayList() method
        ObservableList<String> observableList = FXCollections.observableArrayList(real_questions);
        question_choice_box.setItems(observableList);
        msg = null;*/
    }
}