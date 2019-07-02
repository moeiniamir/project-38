package view.fxmlControllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import view.Utility;
import view.WindowChanger;

import java.io.IOException;

public class AlertController {
    private static double yLayout = Screen.getPrimary().getVisualBounds().getHeight() * 100 / 540;
    public AnchorPane carrier;
    public Text text;
    public ImageView closeButton;
    public ImageView button;
    public ImageView glowButton;
    public Boolean result;
    private boolean haveAcceptButton;

    public synchronized void close(MouseEvent mouseEvent) {
        resetAlertInPosition();
        result = false;
        notify();
    }

    public void shineCloseButton(MouseEvent mouseEvent) {
        Platform.runLater(() -> closeButton.setOpacity(0.4));
    }

    public void resetCloseButton(MouseEvent mouseEvent) {
        Platform.runLater(() -> closeButton.setOpacity(0));
    }

    public synchronized void accept(MouseEvent mouseEvent) {
        if (haveAcceptButton) {
            resetAlertInPosition();
            result = true;
            notify();
        }
    }

    public void shineAcceptButton(MouseEvent mouseEvent) {
        if (haveAcceptButton) {
            Platform.runLater(() -> {
                glowButton.setOpacity(1);
                button.setOpacity(0);
            });
        }
    }

    public void resetAcceptButton(MouseEvent mouseEvent) {
        if (haveAcceptButton) {
            Platform.runLater(() -> {
                button.setOpacity(1);
                glowButton.setOpacity(0);
            });
        }
    }

    public synchronized static AlertController setAndShowAndGetResultByAnAlertController(String text, boolean haveAcceptButton) {
        FXMLLoader fxmlLoader = new FXMLLoader(AlertController.class.getResource("../fxmls/alert.fxml"));
        AnchorPane alertPane = null;
        try {
            alertPane = (AnchorPane) Utility.scale(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertController alertController = fxmlLoader.getController();
        alertController.text.setText(text);
        alertController.haveAcceptButton = haveAcceptButton;
        if (!haveAcceptButton) alertController.button.setOpacity(0);
        alertController.carrier.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight());
        WindowChanger.instance.addNewScene(alertPane);
        alertController.setAlertInPosition();
        return alertController;
    }

    private void setAlertInPosition() {
        new Thread(() -> {
            while (carrier.getLayoutY() > yLayout) {
                double newYLayout = carrier.getLayoutY() - 10 > yLayout ? carrier.getLayoutY() - 10: yLayout;
                Platform.runLater(() -> carrier.setLayoutY(newYLayout));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void resetAlertInPosition() {
        new Thread(() -> {
            synchronized (AlertController.class) {
                while (carrier.getLayoutY() < Screen.getPrimary().getVisualBounds().getHeight()) {
                    double newYLayout = carrier.getLayoutY() + 10 < Screen.getPrimary().getVisualBounds().getHeight() ?
                            carrier.getLayoutY() + 10: Screen.getPrimary().getVisualBounds().getHeight();
                    Platform.runLater(() -> carrier.setLayoutY(newYLayout));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                WindowChanger.instance.removeAdditionalScene();
            }
        }).start();
    }
}
