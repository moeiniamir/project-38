package client.controller;

import client.net.ClientSession;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import view.Loader;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;

public class ClientInit extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
        ClientSession.connect();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Loader.loadAll();

        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        mainStage.show();

        WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);

//        {//arena
//            Account account = new Account("test", "test");
//            account.getCollection().deselectMainDeck(Deck.getAllDecks().get("level1"));
//            Game game = Level.getAvailableLevels().get("1").getLevelGame(account);
//            ArenaController.ac.init(game);
//            game.initialiseGameFields();//
//            WindowChanger.instance.setMainParent(LoadedScenes.arena);
//        }

    }
}