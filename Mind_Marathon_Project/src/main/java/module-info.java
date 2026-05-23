module com.example.mind_marathon_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.mind_marathon_project to javafx.fxml;
    exports com.example.mind_marathon_project;
}