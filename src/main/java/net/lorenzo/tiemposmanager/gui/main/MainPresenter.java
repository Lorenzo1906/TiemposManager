/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.gui.reporte.ReportePresenter;
import net.lorenzo.tiemposmanager.gui.rifa.RifaPresenter;
import net.lorenzo.tiemposmanager.gui.settings.SettingsPresenter;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.service.ConfigService;
import net.lorenzo.tiemposmanager.service.NumeroProhibidoService;
import net.lorenzo.tiemposmanager.service.NumeroService;
import net.lorenzo.tiemposmanager.service.RifaService;
import net.lorenzo.tiemposmanager.service.TipoRifaService;
import org.controlsfx.control.NotificationPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenzo
 */
public class MainPresenter {
    private static final Logger log = LoggerFactory.getLogger(MainPresenter.class);
    
    final static String AJUSTES_NAME = "Ajustes";
    final static String REPORTES_NAME = "Reportes";
    
    @FXML private Parent root;
    @FXML private TabPane tabPane;

    @Inject private SettingsPresenter settingsPresenter;
    @Inject private ReportePresenter reportePresenter;
    @Inject private TipoRifaService tipoRifaService;
    @Inject private RifaService rifaService;
    @Inject private NumeroService numeroService;
    @Inject private ConfigService configService;
    @Inject private NumeroProhibidoService numeroProhibidoService;

    public Parent getView(){
        return root;
    }

    public void setTabs(){
        log.info("Setting tabs");
                
        generateTabsTiposRifa();
        
        Tab tmpTabReporte = new Tab(REPORTES_NAME);
        tmpTabReporte.setContent(generatePaneReporte());
        tabPane.getTabs().add(tmpTabReporte);
        
        Tab tmpTabSettings = new Tab(AJUSTES_NAME);
        tmpTabSettings.setContent(generatePaneSettings());
        tabPane.getTabs().add(tmpTabSettings);
    }
    
    private void generateTabsTiposRifa(){
        log.info("Generating tabs tipos de rifa");
        
        List<TipoRifa> listTiposRifas = tipoRifaService.getListAll();
        
        listTiposRifas.stream().map((tmpTipoRifa) -> {
            Tab tmpNewTab = new Tab(tmpTipoRifa.getNombre());
            tmpNewTab.setContent(generatePaneRifa(tmpTipoRifa));
            return tmpNewTab;
        }).forEach((tmpNewTab) -> {
            tabPane.getTabs().add(tmpNewTab);
        });
    }
    
    private NotificationPane generatePaneRifa(TipoRifa argTipoRifa){
        NotificationPane notificationPane = generateBasicNotificationPane();
        
        try {
            log.info("Generating tab Rifa");
            
            RifaPresenter controller =  loadPresenter("/fxml/RifaScreen.fxml");
            controller.setTipoRifa(argTipoRifa);
            controller.setRifaService(rifaService);
            controller.setNumeroService(numeroService);
            controller.setConfigService(configService);
            controller.setNumeroProhibidoService(numeroProhibidoService);
            controller.setNumerosPresenter(loadPresenter("/fxml/NumerosScreen.fxml"));
            controller.loadRifa();
            
            
            notificationPane.setContent(controller.getView());
            
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return notificationPane;
    }
    
    private NotificationPane generatePaneReporte(){
        log.info("Generating tab reporte");
        
        NotificationPane notificationPane = generateBasicNotificationPane();
        notificationPane.setContent(reportePresenter.getView());
        
        return notificationPane;
    }
    
    private NotificationPane generatePaneSettings(){
        log.info("Generating tab reporte");
        
        NotificationPane notificationPane = generateBasicNotificationPane();
        notificationPane.setContent(settingsPresenter.getView());
        settingsPresenter.inicializeSettings();
        
        return notificationPane;
    }
    
    private NotificationPane generateBasicNotificationPane(){
        log.info("Generating tab Basic");
        
        NotificationPane notificationPane = new NotificationPane();
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
        notificationPane.setShowFromTop(false);
        
        return notificationPane;
    }
    
    private <T> T loadPresenter(String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.load(getClass().getResourceAsStream(fxmlFile));
            return (T) loader.getController();
        }
        catch (IOException e){
            throw new RuntimeException(String.format("Unable to load FXML file '%s'", fxmlFile), e);
        }
    }
}
