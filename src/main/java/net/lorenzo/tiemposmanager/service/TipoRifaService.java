/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service;

import java.util.List;
import net.lorenzo.tiemposmanager.model.TipoRifa;

/**
 *
 * @author Lorenzo
 */
public interface TipoRifaService {
    TipoRifa getTipoRifa(Long tipoRifaId);
    List<TipoRifa> getListAll();
    void deleteTipoRifa(TipoRifa argTipoRifa);
    void saveTipoRifa(TipoRifa argTipoRifa);
}
