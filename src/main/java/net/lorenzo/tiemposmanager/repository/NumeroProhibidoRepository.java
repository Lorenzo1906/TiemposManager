/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.lorenzo.tiemposmanager.repository;

import net.lorenzo.tiemposmanager.model.NumeroProhibido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lorenzo
 */
@Repository
public interface NumeroProhibidoRepository extends CrudRepository<NumeroProhibido, Long>{
    NumeroProhibido findByNumero(Integer argNumero);
}
