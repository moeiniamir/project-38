package view.fxmlControllers.menus;

import client.net.Message;
import client.net.Encoder;
import client.net.ClientSession;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {

    public AnchorPane mainPane;
    public TextField username;
    public TextField password;
    public TextField again;
    public Pane alertWindow;
    public Label alert;
    private boolean shouldClose = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void register() {
        String userNameString = username.getText();
        String passwordString = password.getText();
        String againPasswordString = again.getText();
        if (userNameString.isEmpty() || passwordString.isEmpty() || againPasswordString.isEmpty()) {
            alertWindow.toFront();
            alert.setText("Please fill up your fields");
            return;
        }

        Encoder.sendMessage(Message.createAccount);
        Encoder.sendString(userNameString);
        Encoder.sendString(passwordString);
        Encoder.sendString(againPasswordString);

        String result = null;
        try {
            result = ClientSession.dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result.equals("There's an account with this name")) {
            alertWindow.toFront();
            alert.setText("There's an account with this name");
        }
        if (result.equals("Your passwords aren't same")) {
            alertWindow.toFront();
            alert.setText("Your passwords aren't same");
        }
        if (result.equals("Account created successfully")) {
            alertWindow.toFront();
            alert.setText("Account created successfully");
            shouldClose = true;
        }
    }

    public void back() {
        WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);
        username.clear();
        password.clear();
        again.clear();
    }

    public void ok() {
        alertWindow.toBack();
        if (shouldClose) {
            WindowChanger.instance.setMainParent(LoadedScenes.registerMenu);
            shouldClose = false;
            username.clear();
            password.clear();
            again.clear();
        }
    }
}