/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service.impl;

import java.util.List;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.Numero;
import net.lorenzo.tiemposmanager.model.Rifa;
import net.lorenzo.tiemposmanager.repository.NumeroRepository;
import net.lorenzo.tiemposmanager.service.NumeroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lorenzo
 */
@Service
@Transactional(readOnly = false)
public class NumeroServiceImpl implements NumeroService{
    @Inject private NumeroRepository numeroRepository;

    @Override
    public Numero getNumero(Long numeroId) {
        return numeroRepository.findOne(numeroId);
    }

    @Override
    public void saveNumero(Numero argNumero) {
        numeroRepository.save(argNumero);
    }

    @Override
    public Numero findByNumeroAndRifa(Integer argNumero, Rifa argRifa) {
        return numeroRepository.findByNumeroAndRifa(argNumero, argRifa);
    }

    @Override
    public List<Numero> findByRifa(Rifa argRifa) {
        return numeroRepository.findByRifa(argRifa);
    }
}
