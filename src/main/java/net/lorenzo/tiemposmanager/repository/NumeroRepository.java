/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.repository;

import java.util.List;
import net.lorenzo.tiemposmanager.model.Numero;
import net.lorenzo.tiemposmanager.model.Rifa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lorenzo
 */
@Repository
public interface NumeroRepository extends CrudRepository<Numero,Long>, JpaSpecificationExecutor{
    Numero findByNumeroAndRifa(Integer argNumero, Rifa argRifa);
    List<Numero> findByRifa(Rifa argRifa);
}
