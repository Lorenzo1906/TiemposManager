/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.NumeroProhibido;
import net.lorenzo.tiemposmanager.repository.NumeroProhibidoRepository;
import net.lorenzo.tiemposmanager.service.NumeroProhibidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lorenzo
 */
@Service
@Transactional(readOnly = false)
public class NumeroProhibidoServiceImpl implements NumeroProhibidoService{
    @Inject NumeroProhibidoRepository numeroProhibidoRepository;
    
    @Override
    public NumeroProhibido getNumeroProhibido(Long numeroId) {
        return numeroProhibidoRepository.findOne(numeroId);
    }
    
    @Override
    public void deleteAll(){
        numeroProhibidoRepository.deleteAll();
    }
    
    @Override
    public void save(NumeroProhibido argNumeroProhibido){
        numeroProhibidoRepository.save(argNumeroProhibido);
    }
    
    @Override
    public List<NumeroProhibido> getListAll(){
        List<NumeroProhibido> tmpListNumeros = new ArrayList<>();
        
        for (NumeroProhibido tmpNumeroProhibido : numeroProhibidoRepository.findAll()) {
            tmpListNumeros.add(tmpNumeroProhibido);
        }
        return tmpListNumeros;
    }

    @Override
    public NumeroProhibido findByNumero(Integer argNumero) {
        return numeroProhibidoRepository.findByNumero(argNumero);
    }
}
