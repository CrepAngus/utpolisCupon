/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utpolis.utpolis.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.utpolis.utpolis.entity.Cupon;

/**
 *
 * @author Crepa
 */

@Repository
public interface CuponRepo extends JpaRepository<Cupon, Integer> {
    
}