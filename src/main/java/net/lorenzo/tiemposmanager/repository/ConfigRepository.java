/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.repository;

import net.lorenzo.tiemposmanager.model.Config;
import net.lorenzo.tiemposmanager.model.TipoConfig;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author home
 */
public interface ConfigRepository extends CrudRepository<Config,Long>, JpaSpecificationExecutor{
    Config findByTipoConfig(TipoConfig argTipoConfig);
}
