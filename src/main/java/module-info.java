module partalex.homework_one {
    requires javafx.controls;
    requires javafx.fxml;


    opens partalex.homework_one to javafx.fxml;
    exports partalex.homework_one;
}