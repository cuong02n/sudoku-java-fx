package com.cuong02n.sudokujavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;

public class SudokuHomeController {

    @FXML
    private Button playButton;

    @FXML
    private Button highscoreButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        // Thiết lập hành động cho nút Exit
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });

        playButton.setOnAction(e -> {
            Main.changeScreen("pick-difficulty-view.fxml","css/difficulty.css");
        });

        // Bạn có thể thêm các hành động cho các nút khác ở đây
    }
}
