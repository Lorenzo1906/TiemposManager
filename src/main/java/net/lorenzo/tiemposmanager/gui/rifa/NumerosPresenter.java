/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.rifa;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author home
 */
public class NumerosPresenter implements Initializable{
    private static final Logger log = LoggerFactory.getLogger(NumerosPresenter.class);
    
    @FXML private Node root;
    @FXML private VBox pnOne;
    @FXML private VBox pnTwo;
    @FXML private VBox pnThree;
    @FXML private VBox pnFour;
    @FXML private VBox pnFive;
    
    private RifaPresenter rifaPresenter;
    
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
    
    public void cleanNumeros(){
        listCheckBoxes.stream().forEach((tmpChk) -> {
            tmpChk.setSelected(false);
        });
    }

    public void close(ActionEvent event) {
        log.info("Closing popup");
        getRifaPresenter().closePopupNumeros();
    }
    
    public void save(ActionEvent event) {
        log.info("Saving info");

        String numero = "";
        
        numero = listCheckBoxes.stream().filter((tmpCheckNumeroProhibido) -> (tmpCheckNumeroProhibido.isSelected())).map((tmpCheckNumeroProhibido) -> tmpCheckNumeroProhibido.getText() + ", ").reduce(numero, String::concat);
        
        numero = numero.substring(0, numero.length() - 2);
        
        getRifaPresenter().setNumero(numero);
        getRifaPresenter().closePopupNumeros();
    }

    /**
     * @return the rifaPresenter
     */
    public RifaPresenter getRifaPresenter() {
        return rifaPresenter;
    }

    /**
     * @param rifaPresenter the rifaPresenter to set
     */
    public void setRifaPresenter(RifaPresenter rifaPresenter) {
        this.rifaPresenter = rifaPresenter;
    }
}
