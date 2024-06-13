package com.cuong02n.sudokujavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static com.cuong02n.sudokujavafx.Main.*;
import static com.cuong02n.sudokujavafx.SudokuUtils.getStrByInt;
import static com.cuong02n.sudokujavafx.SudokuUtils.squareIndex;

public class SudokuBoardController {
    public ImageView resetIcon;
    int hint = 5;
    public GridPane gridPane;
    public StackPane stackPane;
    public ImageView hintIcon;
    TextField[][] textFields;
    Canvas canvas;

    int currentI = 0;
    int currentJ = 0;

    long start;

    @FXML
    public void initialize() {
        start = System.currentTimeMillis();
        SudokuUtils.generateSudokuBoard(currentSize, Main.currentHardMode);
        setHintAction();
        setResetAction();
        drawSudokuBoard();
    }

    private void setResetAction() {
        resetIcon.setOnMouseClicked(e -> {
            for (int i = 0; i < currentSize; i++) {
                for (int j = 0; j < currentSize; j++) {
                    if (!fixed[i][j]) {
                        setCellValue(i, j, "0");
                    }
                }
            }
        });
    }

    private void setHintAction() {
        hintIcon.setOnMouseClicked(e -> {
            System.out.println("clicked");
            if (hint == 0) {
                Main.showWarning("Hết quyền trợ giúp");
                return;
            }
            if (fixed[currentI][currentJ]) {
                Main.showWarning("ô này đã chính xác rồi");
                return;
            }
            fixed[currentI][currentJ] = true;
            setCellValue(currentI, currentJ, getStrByInt(solution[currentI][currentJ]));
            textFields[currentI][currentJ].setEditable(false);
            addStyle(currentI, currentJ, "-fx-text-fill: #f2a2d5");
            addStyle(currentI, currentJ, "-fx-font-style: italic");
            hintIcon.setImage(new Image("/com/cuong02n/sudokujavafx/hint" + --hint + ".png"));
            System.out.println(currentI + " " + currentJ);
        });

    }

    public void drawSudokuBoard() {
        textFields = new TextField[currentSize][currentSize];
        double cellSize = Config.cellSizes[currentSize];
        canvas = new Canvas(currentSize * cellSize, currentSize * cellSize);
        canvas.setMouseTransparent(true);
        switch (currentSize) {
            case 4 -> drawLineSudoku4(cellSize);
            case 7 -> drawLineSudoku7(cellSize);
            case 9 -> drawLineSudoku9(cellSize);
            case 16 -> drawLineSudoku16(cellSize);
            case 18 -> drawLineSudoku18(cellSize);
        }
        drawTextField(cellSize);

        setValueForInitialTextField();

        addListenerForTextField();

        setDisableChangeForFixedCell();
    }


    private void drawTextField(double cellSize) {
        System.out.println("cell size: " + cellSize);
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                textFields[i][j] = new TextField();
                textFields[i][j].setPrefSize(cellSize, cellSize);
                textFields[i][j].setMaxSize(cellSize, cellSize);
                textFields[i][j].setMinSize(cellSize, cellSize);
                addTextEventListener(i, j);

                textFields[i][j].setAlignment(Pos.CENTER);
                textFields[i][j].setFont(new Font("consolas", Config.textSizes[currentSize]));

                gridPane.add(textFields[i][j], j, i);
            }
        }
    }

    private void setValueForInitialTextField() {
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                if (boardNow[i][j] != 0) {
                    setCellValue(i, j, SudokuUtils.getStrByInt(boardNow[i][j]));
                }
            }
        }
    }

    private void addListenerForTextField() {
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                addListenerForTextField(i, j);
            }
        }
    }

    private void addListenerForTextField(final int i, final int j) {
        textFields[i][j].focusedProperty().addListener(e -> {
            SudokuBoardController.this.removeAllBgColor();
            SudokuBoardController.this.setColorForSelectedCell(i, j);
        });
    }

    public void drawLineSudoku4(double cellSize) {
        stackPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, cellSize * currentSize, cellSize * currentSize);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        for (int i = 0; i <= currentSize; i++) {
            if (i % 2 == 0) {

                gc.strokeLine(0, i * cellSize, currentSize * cellSize, i * cellSize);

                gc.strokeLine(i * cellSize, 0, i * cellSize, currentSize * cellSize);
            }
        }
    }

    public void drawLineSudoku7(double cellSize) {
        stackPane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, cellSize * currentSize, cellSize * currentSize);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        for (int[] positions : Config.strokeThickLineSize7) {
            gc.strokeLine(positions[0] * cellSize, positions[1] * cellSize, positions[2] * cellSize, positions[3] * cellSize);
        }

    }

    public void drawLineSudoku9(double cellSize) {
        stackPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, cellSize * currentSize, cellSize * currentSize);
        for (int i = 0; i <= currentSize; i++) {
            if (i % 3 == 0) {
                // Vẽ các đường ngang lớn
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(5);
                gc.strokeLine(0, i * cellSize, currentSize * cellSize, i * cellSize);

                // Vẽ các đường dọc lớn
                gc.strokeLine(i * cellSize, 0, i * cellSize, currentSize * cellSize);
            }
        }
    }


    private void drawLineSudoku16(double cellSize) {
        stackPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, cellSize * currentSize, cellSize * currentSize);
        for (int i = 0; i <= currentSize; i++) {
            if (i % 4 == 0) {
                // Vẽ các đường ngang lớn
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(5);
                gc.strokeLine(0, i * cellSize, currentSize * cellSize, i * cellSize);

                // Vẽ các đường dọc lớn
                gc.strokeLine(i * cellSize, 0, i * cellSize, currentSize * cellSize);
            }
        }
    }

    private void drawLineSudoku18(double cellSize) {
        stackPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, cellSize * currentSize, cellSize * currentSize);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        for (int i = 0; i <= currentSize; i++) {
            if (i % 6 == 0) {
                // Vẽ các đường dọc lớn
                gc.strokeLine(i * cellSize, 0, i * cellSize, currentSize * cellSize);
            }
        }
        for (int j = 0; j <= currentSize; j++) {
            if (j % 3 == 0) {
                gc.strokeLine(0, j * cellSize, currentSize * cellSize, j * cellSize);
            }
        }
    }

    private void setDisableChangeForFixedCell() {
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                if (fixed[i][j]) {
                    textFields[i][j].setEditable(false);
                    addStyle(i, j, "-fx-text-fill: #f2a2d5");
                    addStyle(i, j, "-fx-font-style: italic");
                }
            }
        }
    }

    void addTextEventListener(int i, int j) {
        textFields[i][j].textProperty().addListener((observableValue, s, t1) -> {
            String text = observableValue.getValue().toUpperCase();
            if (text.isEmpty()) {
                return;
            }
            if (text.length() > 1) {
                String newText = text.substring(1);
                setCellValue(i, j, newText);
            } else {
                setCellValue(i, j, text);
            }
            if (SudokuUtils.validSudokuBoard(boardNow)) {

                Main.showAlertEndgame((System.currentTimeMillis() - start) / 1000 + " giây.");
            }
        });
        textFields[i][j].focusedProperty().addListener((observable, oldValue, newValue) -> {
            currentI = i;
            currentJ = j;
        });
    }

    void removeAllBgColor() {
        for (int i = 0; i < currentSize; i++) {
            for (int j = 0; j < currentSize; j++) {
                addStyle(i, j, "-fx-background-color: #FFFFFF");
            }
        }
    }

    void setColorForSelectedCell(int i, int j) {
        // set color for horizontal and vertical
        for (int u = 0; u < currentSize; u++) {
            addStyle(i, u, "-fx-background-color: #d8e6cc");
            addStyle(u, j, "-fx-background-color: #d8e6cc");
        }

        for (int i1 = 0; i1 < currentSize; i1++) {
            for (int j1 = 0; j1 < currentSize; j1++) {
                // set color for square
                if (squareIndex[currentSize][i][j] == squareIndex[currentSize][i1][j1]) {
                    addStyle(i1, j1, "-fx-background-color: #d8e6cc");
                }
            }
        }

        addStyle(i, j, "-fx-background-color: #b6db95 ");

        if (textFields[i][j].getText().isEmpty()) {
            return;
        }
        //set color for same value
        for (int i1 = 0; i1 < currentSize; i1++) {
            for (int j1 = 0; j1 < currentSize; j1++) {
                if (i1 == i && j1 == j) continue;
                if (boardNow[i][j] == boardNow[i1][j1]) {
                    addStyle(i1, j1, "-fx-background-color: #6cc5b8");
                }
            }
        }
    }


    public void setCellValue(int i, int j, String value) {
        if (!value.isEmpty()) {
            int valueInt = SudokuUtils.getIntByStr(value);
            if (valueInt == 0) {
                textFields[i][j].setText("");
                boardNow[i][j] = valueInt;
                return;
            }
            System.out.print("value int: " + valueInt + ", ");
            System.out.println("after change to string: " + getStrByInt(valueInt));
            textFields[i][j].setText(SudokuUtils.getStrByInt(valueInt));
            boardNow[i][j] = valueInt;
            textFields[i][j].positionCaret(1);
        }
    }

    private void addStyle(int i, int j, String style) {
        String oldStyle = textFields[i][j].getStyle();
        String newStyle = oldStyle + " " + style + "; ";
        textFields[i][j].setStyle(newStyle);
    }

    public void backToHome(ActionEvent actionEvent) {
        Main.gotoMain();
    }
}
