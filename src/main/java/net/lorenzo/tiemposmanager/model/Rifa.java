/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;

import static javax.persistence.TemporalType.DATE;
/**
 *
 * @author Lorenzo
 */
@Entity
public class Rifa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
    @Temporal(DATE)
    private Calendar  fecha;
    
    @ManyToOne
    private TipoRifa tipo;
    
    @OneToMany(mappedBy = "rifa", cascade = CascadeType.ALL)
    private List<Numero> numeros;

    public Rifa() {
    }

    public Rifa(Long id, Calendar fecha, TipoRifa tipo, List<Numero> numeros) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.numeros = numeros;
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
     * @return the fecha
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tipo
     */
    public TipoRifa getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoRifa tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the numeros
     */
    public List<Numero> getNumeros() {
        return numeros;
    }

    /**
     * @param numeros the numeros to set
     */
    public void setNumeros(List<Numero> numeros) {
        this.numeros = numeros;
    }
}
