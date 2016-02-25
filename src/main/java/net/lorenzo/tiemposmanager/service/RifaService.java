/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service;

import java.util.Calendar;
import net.lorenzo.tiemposmanager.model.Rifa;
import net.lorenzo.tiemposmanager.model.TipoRifa;

/**
 *
 * @author Lorenzo
 */
public interface RifaService {
    Rifa getRifa(Long rifaId);
    Rifa findByFechaAndTipo(Calendar fecha, TipoRifa tipo);
    Iterable<Rifa> findByFechaDesc();
    void saveRifa(Rifa rifa);
}
