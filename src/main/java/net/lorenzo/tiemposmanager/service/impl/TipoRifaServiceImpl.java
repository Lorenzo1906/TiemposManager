/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.repository.TipoRifaRepository;
import net.lorenzo.tiemposmanager.service.TipoRifaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lorenzo
 */
@Service
@Transactional(readOnly = false)
public class TipoRifaServiceImpl implements TipoRifaService{
    @Inject private TipoRifaRepository tipoRifaRepository;

    @Override
    public TipoRifa getTipoRifa(Long tipoRifaId) {
        return tipoRifaRepository.findOne(tipoRifaId);
    }
    
    @Override
    public void saveTipoRifa(TipoRifa argTipoRifa){
        tipoRifaRepository.save(argTipoRifa);
    }
    
    @Override
    public void deleteTipoRifa(TipoRifa argTipoRifa){
        tipoRifaRepository.delete(argTipoRifa);
    }
    
    @Override
    public List<TipoRifa> getListAll(){
        List<TipoRifa> tmpList = new ArrayList<>();
        
        for (TipoRifa tmpTipo : tipoRifaRepository.findAll()) {
            tmpList.add(tmpTipo);
        }
        
        return tmpList;
    }
}
