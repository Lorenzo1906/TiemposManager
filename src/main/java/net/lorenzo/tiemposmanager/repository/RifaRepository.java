/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.repository;

import java.util.Calendar;
import net.lorenzo.tiemposmanager.model.Rifa;
import net.lorenzo.tiemposmanager.model.TipoRifa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lorenzo
 */
@Repository
public interface RifaRepository extends PagingAndSortingRepository<Rifa,Long>, JpaSpecificationExecutor{
    Rifa findByFechaAndTipo(Calendar fecha, TipoRifa tipo);
}
