/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service;

import net.lorenzo.tiemposmanager.model.Config;
import net.lorenzo.tiemposmanager.model.TipoConfig;

/**
 *
 * @author home
 */
public interface ConfigService {
    Config findByTipoConfig(TipoConfig argTipoConfig);
    void save(Config argConfig);
}
