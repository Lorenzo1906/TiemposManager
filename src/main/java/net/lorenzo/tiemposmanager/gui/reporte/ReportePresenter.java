/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.reporte;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.swing.JFrame;
import net.lorenzo.tiemposmanager.model.Numero;
import net.lorenzo.tiemposmanager.model.Rifa;
import net.lorenzo.tiemposmanager.model.TipoConfig;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.service.ConfigService;
import net.lorenzo.tiemposmanager.service.NumeroService;
import net.lorenzo.tiemposmanager.service.RifaService;
import net.lorenzo.tiemposmanager.service.TipoRifaService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenzo
 */
public class ReportePresenter {
    private static final Logger log = LoggerFactory.getLogger(ReportePresenter.class);
    
    @FXML private Parent root;
    @FXML private ComboBox cbTiposRifa;
    @FXML private DatePicker dpFecha;
    @FXML private TableView tvData;
    @FXML private TableColumn colNum;
    @FXML private TableColumn colMon;
    
    @Inject TipoRifaService tipoRifaService;
    @Inject RifaService rifaService;
    @Inject NumeroService numeroService;
    @Inject ConfigService configService;
    
    private Rifa rifa;
    List<Numero> listNumeros;
    
    public void inicializeComboBox(){
        log.info("initializing combo");
        
        List<TipoRifa> tmpListTiposRifa = tipoRifaService.getListAll();
        cbTiposRifa.getItems().addAll(tmpListTiposRifa);
    }
    
    public void onBuscar(ActionEvent event){
        log.info("Searching data");
        
        Calendar newDate = new GregorianCalendar(dpFecha.getValue().getYear(), dpFecha.getValue().getMonthValue()-1, dpFecha.getValue().getDayOfMonth());
        
        TipoRifa tmpTipoRifa = (TipoRifa)cbTiposRifa.getValue();
        
        rifa = rifaService.findByFechaAndTipo(newDate, tmpTipoRifa);
        
        setDataRifa(rifa);
    }
    
    private void setDataRifa(Rifa argRifa){
        log.info("Setting data");
        
        listNumeros = numeroService.findByRifa(argRifa);
        
        colNum.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colMon.setCellValueFactory(new PropertyValueFactory<>("monto"));

        ObservableList<Numero> data = FXCollections.observableArrayList();
        data.addAll(listNumeros);
        tvData.setItems(data);
    }
    
    public void onPrint(ActionEvent event){
        log.info("Printing report");
        
        try {
            Map<String, Object> parameters = new HashMap<>();
            
            parameters.put("RIFA", rifa.getId().toString());
            parameters.put("NOMBRE", configService.findByTipoConfig(TipoConfig.HEADER).getValue());
            
            InputStream reportStream = this.getClass().getResourceAsStream("/reportes/Numeros.jrxml");
            JasperDesign jd = JRXmlLoader.load(reportStream);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr,parameters, new JRBeanCollectionDataSource(listNumeros));
            JasperViewer.viewReport(jp,false );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Parent getView(){
        return root;
    }
}
