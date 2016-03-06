/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.gui.rifa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import net.lorenzo.tiemposmanager.model.Config;
import net.lorenzo.tiemposmanager.model.Numero;
import net.lorenzo.tiemposmanager.model.NumeroProhibido;
import net.lorenzo.tiemposmanager.model.Rifa;
import net.lorenzo.tiemposmanager.model.TipoConfig;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.service.ConfigService;
import net.lorenzo.tiemposmanager.service.NumeroProhibidoService;
import net.lorenzo.tiemposmanager.service.NumeroService;
import net.lorenzo.tiemposmanager.service.RifaService;
import net.lorenzo.tiemposmanager.utils.Impresora;
import net.lorenzo.tiemposmanager.utils.PrintableLine;
import static net.lorenzo.tiemposmanager.utils.Utils.generatePopup;
import static net.lorenzo.tiemposmanager.utils.Utils.getText;

import net.lorenzo.tiemposmanager.utils.Utils;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenzo
 */
public class RifaPresenter {
    private static final Logger log = LoggerFactory.getLogger(RifaPresenter.class);
    
    @FXML private Parent root;
    @FXML private Label lblTipoRifa;
    @FXML private Label lblCurrentNumero;
    @FXML private Label lblFecha;
    @FXML private Button btnAddNumber;
    @FXML private TextField txtMontoNumero;
    @FXML private TextField txtName;
    @FXML private ListView lstView;
    
    private NumerosPresenter numerosPresenter;
    
    private RifaService rifaService;
    private NumeroService numeroService;
    private ConfigService configService;
    private NumeroProhibidoService numeroProhibidoService;
    
    private TipoRifa tipoRifa;
    private Rifa rifa;
    private String numero;
    private PopOver popNumeros;

    public void loadRifa() throws ParseException{
        log.info("Updating rifa");
        
        Calendar newDate = Calendar.getInstance();
        newDate.set(Calendar.HOUR_OF_DAY, 0);
        newDate.set(Calendar.MINUTE,0);
        newDate.set(Calendar.SECOND,0);
        newDate.set(Calendar.MILLISECOND,0);
        
        Rifa tmpRifa = getRifaService().findByFechaAndTipo(newDate, tipoRifa);
        
        if(tmpRifa == null){
            generateNewRifa(newDate);
        }else{
            rifa = tmpRifa;
        }
        
        setHeader();
    }
    
    private void setHeader(){

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String tmpFechaString = format1.format(rifa.getFecha().getTime());

        lblFecha.setText(tmpFechaString);
        lblTipoRifa.setText(tipoRifa.getNombre());
    }
    
    public void selectNumero(ActionEvent event){
        log.info("Opening popup numeros");
        
        popNumeros = new PopOver();
        generatePopup(popNumeros, btnAddNumber, getNumerosPresenter().getView());
        getNumerosPresenter().setRifaPresenter(this);
        getNumerosPresenter().cleanNumeros();
        
        btnAddNumber.setDisable(true);
    }
    
    public void closePopupNumeros(){
        log.info("Closing popup");
        
        lblCurrentNumero.setText(getNumero());
        popNumeros.hide();
        btnAddNumber.setDisable(false);
    }
    
    private void generateNewRifa(Calendar argToday){
        log.info("Generating new rifa");
        
        rifa = new Rifa();
        rifa.setFecha(argToday);
        rifa.setTipo(tipoRifa);

        getRifaService().saveRifa(rifa);

        for (int i = 0; i < 100; i++) {
            Numero tmpNewNumero = new Numero(null, i, rifa, 0d);
            getNumeroService().saveNumero(tmpNewNumero);
        }
    }
    
    public void annadirNumero(ActionEvent event){
        log.info("Adding numero");
        
        StringTokenizer tmpTokenizer = new StringTokenizer(lblCurrentNumero.getText(), ", ");
        Double tmpMonto = Double.parseDouble(txtMontoNumero.getText());
        
        while (tmpTokenizer.hasMoreElements()) {
            String nextElement = (String) tmpTokenizer.nextElement();
            Integer tmpNumero = Integer.parseInt(nextElement);
            
            if(validateNumero(tmpNumero, tmpMonto)){
                lstView.getItems().add(new HBoxCell(tmpNumero.toString(), tmpMonto.toString()));
            }else{
                ((NotificationPane)getView().getParent()).show(Utils.getText("restricted.number.exception"));
            }
        }
        
        lblCurrentNumero.setText("");
        txtMontoNumero.setText("");
        
        setNumero("");
    }
    
    private boolean validateNumero(Integer argNumero, Double argMonto){
        boolean tmpResult = false;
        
        NumeroProhibido tmpNumeroProhibido = numeroProhibidoService.findByNumero(argNumero);
        if(tmpNumeroProhibido == null){
            tmpResult = true;
        }else{
            Config tmpConfigMontoLimite = configService.findByTipoConfig(TipoConfig.LIMITE_NUMERO_PROHIBIDO);
            Numero tmpNumero = numeroService.findByNumeroAndRifa(argNumero, rifa);
            Double tmpMontoLimite = new Double(tmpConfigMontoLimite.getValue());
            if((tmpNumero.getMonto()+argMonto) <= tmpMontoLimite ){
                tmpResult = true;
            }
        }
        
        return tmpResult;
    }
    
    public void eliminarNumero(ActionEvent event) {
        int indexSelect = lstView.getSelectionModel().getSelectedIndex();
        lstView.getItems().remove(indexSelect);
        ((NotificationPane)getView().getParent()).show(getText("raffle.number.removed"));
    }
    
    public void print(ActionEvent event){
        log.info("Printing numero");

        if(txtName.getText().equals("")){
            ((NotificationPane)getView().getParent()).show(getText("raffle.number.add.name"));
            return;
        }
        
        List<PrintableLine> tmpLines = new ArrayList<>();
        
        tmpLines.addAll(formatHeader());
        tmpLines.add(formatRifaHeader());
        tmpLines.add(formatName());
        tmpLines.add(new PrintableLine("___________________________"));
        tmpLines.addAll(addNumbersPrint());
        tmpLines.add(new PrintableLine("___________________________"));
        tmpLines.addAll(formatFooter());

        Impresora impresora = new Impresora();
        impresora.setUp(tmpLines);
        
        updateNumeros();
        cleanData();
    }
    
    private void updateNumeros(){
        log.info("Updating numeros");
        
        for (Object tmpCell : lstView.getItems()) {
            HBoxCell tmpCellBox = (HBoxCell) tmpCell;
            Integer tmpNumero = Integer.parseInt(tmpCellBox.labelNumero.getText());
            Double tmpMonto = Double.parseDouble(tmpCellBox.labelCantidad.getText());
            
            updateNumero(tmpNumero, tmpMonto);
        }
        
        ((NotificationPane)getView().getParent()).show(getText("raffle.number.saved.data"));
    }
    
    private void updateNumero(Integer argNumero, Double argMonto){
        Numero tmpNumero = numeroService.findByNumeroAndRifa(argNumero, rifa);
        tmpNumero.setMonto(tmpNumero.getMonto() + argMonto);
        
        numeroService.saveNumero(tmpNumero);
    }
    
    private List<PrintableLine> formatHeader(){
        log.info("Formating header");
        
        Config tmpConfigHeader = configService.findByTipoConfig(TipoConfig.HEADER);
        List<PrintableLine> tmpLines = new ArrayList<>();
        
        StringTokenizer tmpTokenizer = new StringTokenizer(tmpConfigHeader.getValue(), "\n");
        while(tmpTokenizer.hasMoreTokens()){
            tmpLines.add(new PrintableLine(tmpTokenizer.nextToken()));
        }
        
        return tmpLines;
    }
    
   private List<PrintableLine> formatFooter(){
        log.info("Formating footer");
       
        Config tmpConfigFooter = configService.findByTipoConfig(TipoConfig.FOOTER);
        List<PrintableLine> tmpLines = new ArrayList<>();
       
        StringTokenizer tmpTokenizer = new StringTokenizer(tmpConfigFooter.getValue(), "\n");
        while(tmpTokenizer.hasMoreTokens()){
            tmpLines.add(new PrintableLine(tmpTokenizer.nextToken()));
        }
        
        return tmpLines;
   }
   
   private PrintableLine formatRifaHeader(){
       String tmpNameTipoRifa = tipoRifa.getNombre();
       SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
       String tmpFechaString = format1.format(rifa.getFecha().getTime());
       PrintableLine tmpPrintableLine = new  PrintableLine(tmpNameTipoRifa, tmpFechaString);
       
       return tmpPrintableLine;
   }
   
   private PrintableLine formatName(){
       String tmpName = txtName.getText();
       PrintableLine tmpPrintableLine = new  PrintableLine("Nombre:", tmpName);
       
       return tmpPrintableLine;
   }
    
    private List<PrintableLine> addNumbersPrint(){
        List<PrintableLine> tmpLines = new ArrayList<>();
        Double tmpTotal = 0d;
        
        for (Object tmpCell : lstView.getItems()) {
            HBoxCell tmpCellBox = (HBoxCell) tmpCell;
            PrintableLine tmpLine = new PrintableLine(tmpCellBox.labelNumero.getText()+"x", tmpCellBox.labelCantidad.getText());
            tmpTotal += Double.parseDouble(tmpCellBox.labelCantidad.getText());
            tmpLines.add(tmpLine);
        }
        
        tmpLines.add(new PrintableLine("Total:",tmpTotal.toString()));
        
        return tmpLines;
    }
    
    private void cleanData(){
        txtName.setText("");
        lstView.getItems().remove(0, lstView.getItems().size());
    }
    
    public Parent getView(){
        return root;
    }

    /**
     * @return the tipoRifa
     */
    public TipoRifa getTipoRifa() {
        return tipoRifa;
    }

    /**
     * @param tipoRifa the tipoRifa to set
     */
    public void setTipoRifa(TipoRifa tipoRifa) {
        this.tipoRifa = tipoRifa;
        lblTipoRifa.setText(tipoRifa.getNombre());
    }

    /**
     * @return the rifa
     */
    public Rifa getRifa() {
        return rifa;
    }

    /**
     * @param rifa the rifa to set
     */
    public void setRifa(Rifa rifa) {
        this.rifa = rifa;
    }

    /**
     * @return the rifaService
     */
    public RifaService getRifaService() {
        return rifaService;
    }

    /**
     * @param rifaService the rifaService to set
     */
    public void setRifaService(RifaService rifaService) {
        this.rifaService = rifaService;
    }

    /**
     * @return the numeroService
     */
    public NumeroService getNumeroService() {
        return numeroService;
    }

    /**
     * @param numeroService the numeroService to set
     */
    public void setNumeroService(NumeroService numeroService) {
        this.numeroService = numeroService;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        if(numero == null){
            numero = "";
        }
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the numerosPresenter
     */
    public NumerosPresenter getNumerosPresenter() {
        return numerosPresenter;
    }

    /**
     * @param numerosPresenter the numerosPresenter to set
     */
    public void setNumerosPresenter(NumerosPresenter numerosPresenter) {
        this.numerosPresenter = numerosPresenter;
    }

    /**
     * @return the ConfigService
     */
    public ConfigService getConfigService() {
        return configService;
    }

    /**
     * @param ConfigService the ConfigService to set
     */
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * @return the numeroProhibidoService
     */
    public NumeroProhibidoService getNumeroProhibidoService() {
        return numeroProhibidoService;
    }

    /**
     * @param numeroProhibidoService the numeroProhibidoService to set
     */
    public void setNumeroProhibidoService(NumeroProhibidoService numeroProhibidoService) {
        this.numeroProhibidoService = numeroProhibidoService;
    }
    
    private class HBoxCell extends HBox {
        Label labelNumero = new Label();
        Label labelCantidad = new Label();

        HBoxCell(String labelNumeroText, String labelCantidadText) {
            super();

            labelNumero.setText(labelNumeroText);
            labelNumero.setMaxWidth(Double.MAX_VALUE);
            labelCantidad.setText(labelCantidadText);
            labelCantidad.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(labelNumero, Priority.ALWAYS);

            this.getChildren().addAll(labelNumero, labelCantidad);
        }
    }
}
