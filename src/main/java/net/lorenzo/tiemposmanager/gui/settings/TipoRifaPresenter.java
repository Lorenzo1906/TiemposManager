/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.settings;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.service.TipoRifaService;
import org.controlsfx.control.NotificationPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author home
 */
public class TipoRifaPresenter {
    private static final Logger log = LoggerFactory.getLogger(TipoRifaPresenter.class);
    
    @FXML private Node root;
    @FXML private ListView lstView;
    @FXML private TextField txtNombre;
    
    @Inject private SettingsPresenter settingsPresenter;
    @Inject private TipoRifaService tipoRifaService;
    
    public Node getView(){
        return root;
    }
    
    public void close(ActionEvent event) {
        log.info("Closing popup");
        settingsPresenter.closePopupTipoRifa();
        settingsPresenter.inicializeSettings();
        ((NotificationPane)settingsPresenter.getView().getParent()).show("Reinicie la aplicacion");
    }
    
    public void addElement(ActionEvent event) {
        log.info("adding element");
        TipoRifa tmpNewRifa = new TipoRifa();
        tmpNewRifa.setNombre(txtNombre.getText());
        
        tipoRifaService.saveTipoRifa(tmpNewRifa);
        
        loadTiposRifa();
        txtNombre.setText("");
    }
    
    public void delete(ActionEvent event) {
        log.info("deleting element");
        HBoxCell tmpToDelete = (HBoxCell)lstView.getFocusModel().getFocusedItem();
        tipoRifaService.deleteTipoRifa(tmpToDelete.tipoRifa);
        
        loadTiposRifa();
    }
    
    public void loadTiposRifa(){
        log.info("loading tipos de rifa");
        List<TipoRifa> tmpListTipos = tipoRifaService.getListAll();
        List<HBoxCell> list = new ArrayList<>();
        
        tmpListTipos.stream().forEach((tmpTipo) -> {
            list.add(new HBoxCell(tmpTipo.getNombre(), tmpTipo));
        });

        ObservableList<HBoxCell> myObservableList = FXCollections.observableList(list);
        lstView.setItems(myObservableList);
    }
    
    class HBoxCell extends HBox {
        Label label = new Label();
        TipoRifa tipoRifa;

        HBoxCell(String labelText, TipoRifa tipoRifa) {
            super();

            this.tipoRifa = tipoRifa;

            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            this.getChildren().addAll(label);
        }
     }

}
