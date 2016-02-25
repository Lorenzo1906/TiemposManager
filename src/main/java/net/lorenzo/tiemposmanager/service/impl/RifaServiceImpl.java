/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.service.impl;

import java.util.Calendar;
import javax.inject.Inject;
import net.lorenzo.tiemposmanager.model.Rifa;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import net.lorenzo.tiemposmanager.repository.RifaRepository;
import net.lorenzo.tiemposmanager.service.RifaService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lorenzo
 */
@Service
@Transactional(readOnly = false)
public class RifaServiceImpl implements RifaService{
    @Inject private RifaRepository rifaRepository;

    @Override
    public Rifa getRifa(Long rifaId) {
        return rifaRepository.findOne(rifaId);
    }

    @Override
    public Iterable<Rifa> findByFechaDesc() {
        return rifaRepository.findAll(new Sort(Sort.Direction.DESC, "fecha"));
    }

    @Override
    public void saveRifa(Rifa rifa) {
        rifaRepository.save(rifa);
    }

    @Override
    public Rifa findByFechaAndTipo(Calendar fecha, TipoRifa tipo) {
        return rifaRepository.findByFechaAndTipo(fecha, tipo);
    }
}
