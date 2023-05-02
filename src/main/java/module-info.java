module com.example.footbalteam {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.footbalteam to javafx.fxml;
    exports com.example.footbalteam;
}