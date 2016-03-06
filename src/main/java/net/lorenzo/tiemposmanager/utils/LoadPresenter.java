package net.lorenzo.tiemposmanager.utils;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoadPresenter {

    private static LoadPresenter loadPresenter;

    public static LoadPresenter getInstance() {
        if (loadPresenter == null) {
           loadPresenter = new LoadPresenter();
        }

        return loadPresenter;
    }

    public <T> T load(String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("bundles.bundle", new Locale("es", "ES")));
            loader.load(getClass().getResourceAsStream(fxmlFile));
            return (T) loader.getController();
        }
        catch (IOException e){
            throw new RuntimeException(String.format("Unable to load FXML file '%s'", fxmlFile), e);
        }
    }
}
