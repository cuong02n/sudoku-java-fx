module com.cuong02n.sudokujavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.management;
    requires java.desktop;

    opens com.cuong02n.sudokujavafx to javafx.fxml;
    exports com.cuong02n.sudokujavafx;
}