/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Lorenzo
 */
@Entity
public class TipoRifa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    
    @OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL)
    private List<Rifa> rifas;

    public TipoRifa() {
    }

    public TipoRifa(Long id, String nombre, List<Rifa> rifas) {
        this.id = id;
        this.nombre = nombre;
        this.rifas = rifas;
    }
    
    @Override
    public String toString(){
        return nombre;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the rifas
     */
    public List<Rifa> getRifas() {
        return rifas;
    }

    /**
     * @param rifas the rifas to set
     */
    public void setRifas(List<Rifa> rifas) {
        this.rifas = rifas;
    }
}
