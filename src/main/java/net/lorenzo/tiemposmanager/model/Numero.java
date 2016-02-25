/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Lorenzo
 */
@Entity
public class Numero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer numero;
    
    @ManyToOne
    private Rifa rifa;
    
    private Double monto;

    public Numero() {
    }

    public Numero(Long id, Integer numero, Rifa rifa, Double monto) {
        this.id = id;
        this.numero = numero;
        this.rifa = rifa;
        this.monto = monto;
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
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
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
     * @return the monto
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
