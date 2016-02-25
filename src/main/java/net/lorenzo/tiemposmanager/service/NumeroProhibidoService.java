/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service;

import java.util.List;
import net.lorenzo.tiemposmanager.model.NumeroProhibido;

/**
 *
 * @author Lorenzo
 */
public interface NumeroProhibidoService {
    NumeroProhibido getNumeroProhibido(Long numeroId);
    NumeroProhibido findByNumero(Integer argNumero);
    List<NumeroProhibido> getListAll();
    void save(NumeroProhibido argNumeroProhibido);
    void deleteAll();
}
