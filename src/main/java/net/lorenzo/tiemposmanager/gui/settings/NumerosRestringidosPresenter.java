/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.settings;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.NumeroProhibido;
import net.lorenzo.tiemposmanager.service.NumeroProhibidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author home
 */
public class NumerosRestringidosPresenter implements Initializable{
    private static final Logger log = LoggerFactory.getLogger(NumerosRestringidosPresenter.class);
    
    @FXML private Node root;
    @FXML private VBox pnOne;
    @FXML private VBox pnTwo;
    @FXML private VBox pnThree;
    @FXML private VBox pnFour;
    @FXML private VBox pnFive;
    
    @Inject private SettingsPresenter settingsPresenter;
    @Inject private NumeroProhibidoService numeroProhibidoService;
    
    private List<CheckBox> listCheckBoxes;
    
    public Node getView(){
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("initializing");
        
        listCheckBoxes = new ArrayList<>();
        
        for(int i = 0; i<100; i++){
            CheckBox tmpNewCheck = new CheckBox(""+i);
            listCheckBoxes.add(tmpNewCheck);
            if(i < 20){
                pnOne.getChildren().add(tmpNewCheck);
            }
            if(i >= 20 && i < 40){
                pnTwo.getChildren().add(tmpNewCheck);
            }
            if(i >= 40 && i < 60){
                pnThree.getChildren().add(tmpNewCheck);
            }
            if(i >= 60 && i < 80){
                pnFour.getChildren().add(tmpNewCheck);
            }
            if(i >= 80 && i < 100){
                pnFive.getChildren().add(tmpNewCheck);
            }
        } 
    }
    
    public void loadExistingNumerosProhibidos(){
        listCheckBoxes.stream().forEach((tmpCheck) -> {
            tmpCheck.setSelected(false);
        });
        
        List<NumeroProhibido> tmpListNumeros = numeroProhibidoService.getListAll();
        tmpListNumeros.stream().forEach((tmpListNumero) -> {
            listCheckBoxes.get(tmpListNumero.getNumero()).setSelected(true);
        });
    }

    public void close(ActionEvent event) {
        log.info("Closing popup");
        settingsPresenter.closePopupNumeroRestringidos();
    }
    
    public void save(ActionEvent event) {
        log.info("Saving info");
        numeroProhibidoService.deleteAll();
        for (CheckBox tmpCheckNumeroProhibido : listCheckBoxes) {
            if(tmpCheckNumeroProhibido.isSelected()){
                NumeroProhibido tmpNew = new NumeroProhibido(null, new Integer(tmpCheckNumeroProhibido.getText()));
                numeroProhibidoService.save(tmpNew);
            }
        }
        settingsPresenter.inicializeSettings();
        settingsPresenter.closePopupNumeroRestringidos();
    }
}
