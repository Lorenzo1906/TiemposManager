/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service.impl;

import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.Config;
import net.lorenzo.tiemposmanager.model.TipoConfig;
import net.lorenzo.tiemposmanager.repository.ConfigRepository;
import net.lorenzo.tiemposmanager.service.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author home
 */
@Service
@Transactional(readOnly = false)
public class ConfigServiceImpl implements ConfigService{

    @Inject private ConfigRepository configRepository;
    
    @Override
    public Config findByTipoConfig(TipoConfig argTipoConfig) {
        return configRepository.findByTipoConfig(argTipoConfig);
    }
    
    @Override
    public void save(Config argConfig){
        configRepository.save(argConfig);
    }
    
}
