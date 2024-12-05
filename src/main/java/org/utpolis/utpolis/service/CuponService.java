/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utpolis.utpolis.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.utpolis.utpolis.entity.Cupon;
import org.utpolis.utpolis.repo.CuponRepo;

/**
 *
 * @author Crepa
 */

@Service
public class CuponService {

    @Autowired
    private CuponRepo cuponRepo;

    public Cupon insertar(Cupon cup) {
        return cuponRepo.save(cup);
    }

    public Cupon actualizar(Cupon cup) {
        return cuponRepo.save(cup);
    }

    public List<Cupon> listar() {
        return cuponRepo.findAll();
    }

    public void eliminar(Cupon cup) {
        cuponRepo.delete(cup);
    }

    public Cupon obtenerPorId(Integer idCupon) {
        return cuponRepo.findById(idCupon).orElse(null);
    }
}
