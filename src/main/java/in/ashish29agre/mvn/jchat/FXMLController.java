package in.ashish29agre.mvn.jchat;

import in.ashish29agre.mvn.jchat.client.endpoint.Client;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class FXMLController implements Initializable, LogUpdater {

    @FXML
    private Label label;
    @FXML
    private TextArea chatContent;
    @FXML
    private TextField chatMsg;

    private boolean sessionStarted;
    private WebSocketContainer container;
    private Session session;

    private Client chatClient;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (!sessionStarted) {
            start();
        } else {
            try {
                String msg = chatMsg.getText();
                session.getBasicRemote().sendText(msg);
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    protected void start() {

        container = ContainerProvider.getWebSocketContainer();

        String uri = "ws://localhost:8080/SocketChat/chat";
        System.out.println("Connecting to " + uri);
        try {
            chatClient = new Client(this);
            session = container.connectToServer(chatClient, URI.create(uri));
            sessionStarted = true;
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateLog(String message) {
        chatContent.appendText(message);
    }

}
