package com.cuong02n.sudokujavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static Stage stage;
    public static int currentSize;
    public static int currentHardMode;
    public static int[][] boardNow;

    public static int[][] solution;

    public static boolean[][] fixed;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sudoku-home-view.fxml"));
        Scene scene = new Scene(root, 500, 600);

        // Thêm file CSS vào scene
//        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());

        primaryStage.setTitle("Sudoku Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScreen(String fxml) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
            Scene scene = new Scene(root, 500, 600);
            stage.setScene(scene);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void gotoMain() {
        changeScreen("sudoku-home-view.fxml");
    }


    public static void showAlertEndgame(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(stage);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn đã thắng chế độ này, hãy chuyển sang phần tiếp theo nếu muốn "+"\n"+"Thời gian hoàn thành: "+text);

        gotoMain();
        alert.showAndWait();
    }

    public static void showWarning(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(stage);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("WARNING: " + msg);

        alert.showAndWait();
    }

    public static void yesNoAlert(String message, Runnable yes, Runnable no) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.showAndWait();
    }
}
