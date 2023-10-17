package com.example.beerfactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Handler {
    public void openNewScene(String window, Node node)
    {
        node.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public boolean isBad(String str)
    {
        if(str.contains("a")||str.contains("b")||str.contains("c")||str.contains("d")||str.contains("e")||str.contains("f")||str.contains("g")||str.contains("h")||str.contains("i")||str.contains("j")||str.contains("k")||str.contains("l")||str.contains("m")||str.contains("n")||str.contains("o")||str.contains("p")||str.contains("q")||str.contains("r")||str.contains("s")||str.contains("t"))
        {
            return true;
        }
        else if(Integer.valueOf(str) < 0)
        {
            return false;
        }
        else {
            return false;
        }
    }
    public boolean snilsIsShortOrLong(String str)
    {
        if(str.length() != 8)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
