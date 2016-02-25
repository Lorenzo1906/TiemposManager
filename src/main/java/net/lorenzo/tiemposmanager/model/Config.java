/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author home
 */
@Entity
public class Config implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TipoConfig tipoConfig;
    
    private String value;

    public Config() {
    }

    public Config(Long id, TipoConfig tipoConfig, String value) {
        this.id = id;
        this.tipoConfig = tipoConfig;
        this.value = value;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the tipoConfig
     */
    public TipoConfig getTipoConfig() {
        return tipoConfig;
    }

    /**
     * @param tipoConfig the tipoConfig to set
     */
    public void setTipoConfig(TipoConfig tipoConfig) {
        this.tipoConfig = tipoConfig;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    } 
}
