/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager;

import java.io.IOException;
import java.util.Properties;
import javafx.fxml.FXMLLoader;
import javax.sql.DataSource;
import net.lorenzo.tiemposmanager.gui.main.MainPresenter;
import net.lorenzo.tiemposmanager.gui.reporte.ReportePresenter;
import net.lorenzo.tiemposmanager.gui.rifa.NumerosPresenter;
import net.lorenzo.tiemposmanager.gui.rifa.RifaPresenter;
import net.lorenzo.tiemposmanager.gui.settings.NumerosRestringidosPresenter;
import net.lorenzo.tiemposmanager.gui.settings.SettingsPresenter;
import net.lorenzo.tiemposmanager.gui.settings.TipoRifaPresenter;
import net.lorenzo.tiemposmanager.service.ConfigService;
import net.lorenzo.tiemposmanager.service.NumeroProhibidoService;
import net.lorenzo.tiemposmanager.service.NumeroService;
import net.lorenzo.tiemposmanager.service.RifaService;
import net.lorenzo.tiemposmanager.service.TipoRifaService;
import net.lorenzo.tiemposmanager.service.impl.ConfigServiceImpl;
import net.lorenzo.tiemposmanager.service.impl.NumeroProhibidoServiceImpl;
import net.lorenzo.tiemposmanager.service.impl.NumeroServiceImpl;
import net.lorenzo.tiemposmanager.service.impl.RifaServiceImpl;
import net.lorenzo.tiemposmanager.service.impl.TipoRifaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


/**
 *
 * @author Lorenzo
 */
@Configuration
@EnableJpaRepositories
//@EnableTransactionManagement
public class TiemposManagerAppFactory {
    @Bean
    public MainPresenter mainPresenter(){
        return loadPresenter("/fxml/MainScreen.fxml");
    }
    
    @Bean
    public SettingsPresenter settingsPresenter(){
        return loadPresenter("/fxml/SettingsScreen.fxml");
    }

    @Bean
    public NumerosRestringidosPresenter numerosRestringidosPresenter(){
        return loadPresenter("/fxml/NumerosRestringidosScreen.fxml");
    }
    
    @Bean
    public NumerosPresenter numerosPresenter(){
        return loadPresenter("/fxml/NumerosScreen.fxml");
    }
    
    @Bean
    public TipoRifaPresenter tipoRifaPresenter(){
        return loadPresenter("/fxml/TipoRifaScreen.fxml");
    }
    
    @Bean
    public RifaPresenter rifaPresenter(){
        return loadPresenter("/fxml/RifaScreen.fxml");
    }
    
    @Bean
    public ReportePresenter reportePresenter(){
        return loadPresenter("/fxml/ReportesScreen.fxml");
    }
    
    @Bean
    public NumeroService numeroService(){
        return new NumeroServiceImpl();
    }
    
    @Bean
    public RifaService rifaService(){
        return new RifaServiceImpl();
    }
    
    @Bean
    public TipoRifaService tipoRifaService(){
        return new TipoRifaServiceImpl();
    }
    
    @Bean
    public NumeroProhibidoService numeroProhibidoService(){
        return new NumeroProhibidoServiceImpl();
    }
    
    @Bean
    public ConfigService configService(){
        return new ConfigServiceImpl();
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource( this.restDataSource() );
        factoryBean.setPackagesToScan( new String[ ] { "net.lorenzo.tiemposmanager.model" } );
        factoryBean.setPersistenceUnitName("tiempos-manager-data");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
            {
                // JPA properties ...
            }
        };

        factoryBean.setJpaVendorAdapter( vendorAdapter );
        
        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.put("hibernate.hbm2ddl.auto", "update");
        factoryBean.setJpaProperties(props);

        return factoryBean;

    }

    @Bean
    public DataSource restDataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;

    }

    @Bean
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject() );

        return transactionManager;

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
