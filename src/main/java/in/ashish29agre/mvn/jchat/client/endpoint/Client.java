/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ashish29agre.mvn.jchat.client.endpoint;

import in.ashish29agre.mvn.jchat.FXMLController;
import in.ashish29agre.mvn.jchat.LogUpdater;
import java.io.IOException;
import javafx.scene.control.TextArea;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 *
 * @author AshishRAgre
 */
@ClientEndpoint
public class Client {

    private FXMLController controller;

    public Client(FXMLController controller) {
        this.controller = controller;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
        try {
            session.getBasicRemote().sendText("Connected to server...");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
        if (message != null || message != "") {
            controller.updateLog("\n" + message);
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

}
