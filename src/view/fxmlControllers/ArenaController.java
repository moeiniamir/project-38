package view.fxmlControllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Game;
import view.visualminion.VisualMinion;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ArenaController implements Initializable {
    public static ArenaController ac;

    Game game;
    public GridPane grid;
    public Pane pane;
    VisualMinion[][] visualMinions;

    public void init(Game game) {
        this.game = game;
        visualMinions = new VisualMinion[5][9];
    }

    void put(int row, int col, String name) {
        Platform.runLater(() -> {
            VisualMinion vm = new VisualMinion(name);
            vm.view.relocate(getXFromIndexes(row, col), getYFromIndexes(row, col));

            pane.getChildren().add(vm.view);
            visualMinions[row][col] = vm;
        });
    }

    double getXFromIndexes(int row, int col) {
        Bounds bounds = grid.getCellBounds(col, row);
        return bounds.getMinX() + grid.getLayoutX() - bounds.getWidth() / 2;
    }

    double getYFromIndexes(int row, int col) {
        Bounds bounds = grid.getCellBounds(col, row);
        return bounds.getMinY() * Math.cos(Math.toRadians(grid.getRotate())) + bounds.getHeight() + 30;
    }

    void move(int sRow, int sCol, int tRow, int tCol) {
        Platform.runLater(() -> {
            VisualMinion vm = visualMinions[sRow][sCol];
            if ((tCol - sCol) * vm.view.getScaleX() < 0) {
                vm.view.setScaleX(-vm.view.getScaleX());
            }
            vm.run();
            Duration duration = Duration.millis((Math.abs(sRow - tRow) + Math.abs(sCol - tCol)) * 400);
            KeyValue xValue = new KeyValue(vm.view.xProperty(), getXFromIndexes(tRow, tCol) - vm.view.getLayoutX());
            KeyValue yValue = new KeyValue(vm.view.yProperty(), getYFromIndexes(tRow, tCol) - vm.view.getLayoutY());
            KeyFrame keyFrame = new KeyFrame(duration, xValue, yValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    vm.breathing();
                }
            }, (long) duration.toMillis());
        });
    }

    void attack(int sRow, int sCol, int tRow, int tCol) {
        Platform.runLater(() -> {
            VisualMinion vm = visualMinions[sRow][sCol];
            if ((tCol - sCol) * vm.view.getScaleX() < 0) {
                vm.view.setScaleX(-vm.view.getScaleX());
            }
            vm.attack();
        });
    }

    void cast(int row, int col) {
        Platform.runLater(() -> {
            visualMinions[row][col].cast();
        });
    }

    void kill(int row, int col) {
        Platform.runLater(() -> {
            visualMinions[row][col].death();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    visualMinions[row][col].view.setImage(null);
                    visualMinions[row][col] = null;
                }
            }, visualMinions[row][col].animation.realDuration);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ac = this;
    }
}