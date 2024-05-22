package com.cuong02n.sudokujavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class PickDifficultyController {
    public Button easyButton;
    public Button mediumButton;
    public Button hardButton;
    public Button impossibleButton;
    public Slider easySlider;

    @FXML
    public void initialize() {
        easyButton.setOnAction(e -> {
            Main.currentSize = (int) easySlider.getValue();
            Main.currentHardMode = 0;
            Main.changeScreen("sudoku-board-view.fxml");
        });
        mediumButton.setOnAction(e -> {
            Main.currentSize = (int) easySlider.getValue();
            Main.currentHardMode = 1;
            Main.changeScreen("sudoku-board-view.fxml");
        });
        mediumButton.setOnAction(e -> {
            Main.currentSize = (int) easySlider.getValue();
            Main.currentHardMode = 2;
            Main.changeScreen("sudoku-board-view.fxml");
        });
        mediumButton.setOnAction(e -> {
            Main.currentSize = (int) easySlider.getValue();
            Main.currentHardMode = 3;
            Main.changeScreen("sudoku-board-view.fxml");
        });
    }

}
