package view.fxmlControllers;

import controller.Main;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GamePreviewButtonController {
    public ImageView buttonImageView;
    public Label buttonText;
    private String fatherSceneName, nextSceneName;

    public void doClickEvents(MouseEvent mouseEvent) {
        LoadingGamePreviewScenes.selectedButtonsText.add(buttonText.getText());
        LoadingGamePreviewScenes.sceneControllers.get(nextSceneName).setPreviewSceneName(fatherSceneName);
        WindowChanger.instance.setNewScene(LoadingGamePreviewScenes.starterScenes.get(nextSceneName));
        LoadingGamePreviewScenes.starterControllers.get(nextSceneName).run();
    }

    public void shineButton(MouseEvent mouseEvent) {
        buttonImageView.setEffect(new Glow(0.5));
    }

    public void resetButton(MouseEvent mouseEvent) {
        buttonImageView.setEffect(null);
    }


    public void setFields(String text,String fatherSceneName, String nextSceneName) {
        buttonText.setText(text);
        this.fatherSceneName = fatherSceneName;
        this.nextSceneName = nextSceneName;
    }
}
