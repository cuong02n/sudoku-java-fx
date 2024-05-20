package com.cuong02n.sudokujavafx;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static com.cuong02n.sudokujavafx.Main.boardNow;
import static com.cuong02n.sudokujavafx.Main.currentSize;
import static com.cuong02n.sudokujavafx.SudokuUtils.squareIndex;

public class SudokuBoardController {
    public GridPane gridPane;
    public StackPane stackPane;
    TextField[][] textFields;
    Canvas canvas;

    @FXML
    public void initialize() {
        SudokuUtils.generateSudokuBoard(currentSize, Main.currentHardMode);

        drawSudokuBoard();
    }

    public void drawSudokuBoard() {
        textFields = new TextField[currentSize][currentSize];
        if (currentSize == 9) {
            canvas = new Canvas(450, 450);
            canvas.setMouseTransparent(true);
            drawSudoku9();
        }
    }

    public void drawSudoku9() {
        gridPane.getChildren().clear();

        int size = currentSize;
        double cellSize = 50.0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                textFields[i][j] = new TextField();
                textFields[i][j].setPrefSize(cellSize, cellSize);

                addTextEventListener(textFields[i][j]);

                textFields[i][j].setAlignment(Pos.CENTER);
                textFields[i][j].setFont(new Font("consolas", 25));

                gridPane.add(textFields[i][j], j, i);
                if (boardNow[i][j] != 0) {
                    setCellValue(i, j, boardNow[i][j]);
                }
                int finalI = i;
                int finalJ = j;

                textFields[i][j].focusedProperty().addListener(e -> {
                    SudokuBoardController.this.removeAllBgColor();
                    SudokuBoardController.this.setColorForSelectedCell(finalI, finalJ);
                });
            }
        }
        stackPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 450, 450);
        for (int i = 0; i <= size; i++) {
            if (i % 3 == 0) {
                // Vẽ các đường ngang lớn
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(5);
                gc.strokeLine(0, i * cellSize, size * cellSize, i * cellSize);

                // Vẽ các đường dọc lớn
                gc.strokeLine(i * cellSize, 0, i * cellSize, size * cellSize);
            }
        }

    }

    void addTextEventListener(TextField textField) {
        textField.addEventHandler(KeyEvent.ANY, keyEvent -> {
            String text = textField.getText();
            if (text.length() > 1) {
                String newText = text.substring(1).toUpperCase();
                textField.setText(newText);
                textField.positionCaret(1);
            }

            if (SudokuUtils.validSudokuBoard(boardNow)) {
                Main.showAlertEndgame();
            }
        });
    }

    void removeAllBgColor() {
        for (TextField[] textField : textFields) {
            for (TextField field : textField) {
                field.setStyle("-fx-background-color: #FFFFFF");
            }
        }
    }

    void setColorForSelectedCell(int i, int j) {
        // set color for horizontal and vertical
        for (int u = 0; u < currentSize; u++) {
            textFields[i][u].setStyle("-fx-background-color: #b5faec");
            textFields[u][j].setStyle("-fx-background-color: #b5faec");
        }

        for (int i1 = 0; i1 < currentSize; i1++) {
            for (int j1 = 0; j1 < currentSize; j1++) {
                // set color for square
                if (squareIndex[currentSize][i][j] == squareIndex[currentSize][i1][j1]) {
                    textFields[i1][j1].setStyle("-fx-background-color: #b5faec");
                }

            }
        }

        textFields[i][j].setStyle("-fx-background-color: #2c7b85");

        if (textFields[i][j].getText().isEmpty()) {
            return;
        }
        //set color for same value
        for (int i1 = 0; i1 < currentSize; i1++) {
            for (int j1 = 0; j1 < currentSize; j1++) {
                if (i1 == i && j1 == j) continue;
                if (boardNow[i][j] == boardNow[i1][j1]) {
                    textFields[i1][j1].setStyle("-fx-background-color: #6cc5b8");
                }
            }
        }
    }

    void removeWarningFalse(int i, int j) {

    }

    void setWarningFalse(int i, int j) {

    }

    public void setCellValue(int i, int j, int value) {
        if (value != 0)
            textFields[i][j].setText(SudokuUtils.getStrByInt(value));
    }

    public int getCellValue(int i, int j) {
        return SudokuUtils.getIntByStr(textFields[i][j].getText());
    }

}
