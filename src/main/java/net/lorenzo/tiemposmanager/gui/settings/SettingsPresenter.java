/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.settings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.Config;
import net.lorenzo.tiemposmanager.model.NumeroProhibido;
import net.lorenzo.tiemposmanager.model.TipoConfig;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.service.ConfigService;
import net.lorenzo.tiemposmanager.service.NumeroProhibidoService;
import net.lorenzo.tiemposmanager.service.TipoRifaService;
import static net.lorenzo.tiemposmanager.utils.Utils.generatePopup;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenzo
 */
public class SettingsPresenter implements Initializable{
    private static final Logger log = LoggerFactory.getLogger(SettingsPresenter.class);
    
    @FXML private Node root;
    @FXML private Label lblNumeroRestringidos;
    @FXML private Label lblTipoRifas;
    @FXML private Button btnNumRestringidos;
    @FXML private Button btnTipoRifas;
    @FXML private TextField tfLimite;
    @FXML private TextArea txaHeader;
    @FXML private TextArea txaFooter;
    private PopOver popupNumerosRestringidos;
    private PopOver popupTipoRifa;
    
    @Inject private NumerosRestringidosPresenter numerosRestringidosPresenter;
    @Inject private TipoRifaPresenter tipoRifaPresenter;
    @Inject private NumeroProhibidoService numeroProhibidoService;
    @Inject private TipoRifaService tipoRifaService;
    @Inject private ConfigService configService;
    
    Config configLimite;
    Config configHeader;
    Config configFooter;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
     
    public void inicializeSettings(){
        getNumerosProhibidos();
        getLimiteNumerosProhibidos();
        getTiposRifas();
        getHeaderAndFooter();
    }
    
    private void getNumerosProhibidos(){
        log.info("Loading numeros prohibidos");
        List<NumeroProhibido> tmpListNumerosProhibidos = numeroProhibidoService.getListAll();
        String tmpResult = "";
        
        tmpResult = tmpListNumerosProhibidos.stream().map((tmpNumeroProhibido) -> tmpNumeroProhibido.getNumero() + ", ").reduce(tmpResult, String::concat);  
        
        if(tmpListNumerosProhibidos.size() > 0){
            tmpResult = tmpResult.substring(0, tmpResult.length()-2);
        }
        
        lblNumeroRestringidos.setText(tmpResult);
    }
    
    private void getLimiteNumerosProhibidos(){
        log.info("Loading limite");
        configLimite = configService.findByTipoConfig(TipoConfig.LIMITE_NUMERO_PROHIBIDO);
        if(configLimite == null){
            configLimite = new Config(null, TipoConfig.LIMITE_NUMERO_PROHIBIDO, "0.0");
        }
        tfLimite.setText(configLimite.getValue());
    }
    
    private void getTiposRifas(){
        log.info("Loading tipos rifas");
        List<TipoRifa> tmpListTipoRifas = tipoRifaService.getListAll();
        String tmpResult = "";
        
        tmpResult = tmpListTipoRifas.stream().map((tmpTipoRifa) -> tmpTipoRifa.getNombre() + ", ").reduce(tmpResult, String::concat);
        
        if(tmpListTipoRifas.size() > 0){
            tmpResult = tmpResult.substring(0, tmpResult.length()-2);
        }
        
        lblTipoRifas.setText(tmpResult);
    }
    
    private void getHeaderAndFooter(){
        log.info("Loading footer and header");
        configFooter = configService.findByTipoConfig(TipoConfig.FOOTER);
        configHeader = configService.findByTipoConfig(TipoConfig.HEADER);
        
        if(configFooter == null){
            configFooter = new Config(null, TipoConfig.FOOTER, "FOOTER");
        }
        
        if(configHeader == null){
            configHeader = new Config(null, TipoConfig.HEADER, "HEADER");
        }
        
        txaFooter.setText(configFooter.getValue());
        txaHeader.setText(configHeader.getValue());
    }
    
    public void closePopupNumeroRestringidos(){
        log.info("Closing popup");
        popupNumerosRestringidos.hide();
        btnNumRestringidos.setDisable(false);
    }
    
    public void closePopupTipoRifa(){
        log.info("Closing popup");
        popupTipoRifa.hide();
        btnTipoRifas.setDisable(false);
    }
    
    public void add(ActionEvent event){
        log.info("Opening popup numeros");
        
        popupNumerosRestringidos = new PopOver();
        generatePopup(popupNumerosRestringidos, btnNumRestringidos, numerosRestringidosPresenter.getView());
        
        numerosRestringidosPresenter.loadExistingNumerosProhibidos();
        btnNumRestringidos.setDisable(true);
    }
    
    public void openPopupTipoIniciativa(ActionEvent event){
        log.info("Opening tipo rifa");
        
        popupTipoRifa = new PopOver();
        generatePopup(popupTipoRifa, btnTipoRifas, tipoRifaPresenter.getView());

        tipoRifaPresenter.loadTiposRifa();
        btnTipoRifas.setDisable(true);
    }
    
    public void setLimite(ActionEvent event){
        log.info("Setting limite");
        
        try{
            Double tmpNewLimite = new Double(tfLimite.getText());
            configLimite.setValue(tmpNewLimite.toString());
            
            configService.save(configLimite);
            ((NotificationPane)getView().getParent()).show("Limite establecido correctamente");
        }catch(NumberFormatException ex){
            log.error(ex.getLocalizedMessage());
            ((NotificationPane)getView().getParent()).show("Solo ingrese numeros en el limite");
        }
    }
    
    public void setHeader(ActionEvent event){
        log.info("Setting header");
        configHeader.setValue(txaHeader.getText());
        configService.save(configHeader);
        
        ((NotificationPane)getView().getParent()).show("Encabezado guardado correctamente");
    }

    public void setFooter(ActionEvent event){
        log.info("Setting footer");
        configFooter.setValue(txaFooter.getText());
        configService.save(configFooter);
        
        ((NotificationPane)getView().getParent()).show("Pie de pagina guardada correctamente");
    }
    
    public Node getView(){
        return root;
    }
}
