/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service;

import java.util.List;
import net.lorenzo.tiemposmanager.model.Numero;
import net.lorenzo.tiemposmanager.model.Rifa;

/**
 *
 * @author Lorenzo
 */
public interface NumeroService {
    Numero getNumero(Long numeroId);
    Numero findByNumeroAndRifa(Integer argNumero, Rifa argRifa);
    List<Numero> findByRifa(Rifa argRifa);
    void saveNumero(Numero argNumero);
}
