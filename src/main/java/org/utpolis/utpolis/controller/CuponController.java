/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utpolis.utpolis.controller;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.utpolis.utpolis.entity.Cupon;
import org.utpolis.utpolis.service.CuponService;

/**
 *
 * @author Crepa
 */


@RestController
@RequestMapping("/api")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    // Otener todos los cupones
    @GetMapping("/getAllCupon")
    public List<Cupon> listarCupones() {
        return cuponService.listar();
    }

    // Insertar un cupón
    @PostMapping("/insertarCupon")
    public Cupon insertarCupon(
            @RequestParam String codigoCupon,
            @RequestParam String descripcion,
            @RequestParam BigDecimal descuento,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaVencimiento,
            @RequestParam String condiciones,
            @RequestParam Integer estado) {

        Cupon cupon = new Cupon();
        cupon.setCodigoCupon(codigoCupon);
        cupon.setDescripcion(descripcion);
        cupon.setDescuento(descuento);
        cupon.setFechaInicio(fechaInicio);
        cupon.setFechaVencimiento(fechaVencimiento);
        cupon.setCondiciones(condiciones);
        cupon.setEstado(estado);
        cupon.setFechaCreacion(LocalDateTime.now());

        return cuponService.insertar(cupon);
    }

    // Actualizar un cupón
    @PutMapping("/actualizarCupon")
    public Cupon actualizarCupon(
            @RequestParam Integer idCupon,
            @RequestParam String codigoCupon,
            @RequestParam String descripcion,
            @RequestParam BigDecimal descuento,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaVencimiento,
            @RequestParam String condiciones,
            @RequestParam Integer estado) {

        Cupon cupon = cuponService.obtenerPorId(idCupon);
        if (cupon != null) {
            cupon.setCodigoCupon(codigoCupon);
            cupon.setDescripcion(descripcion);
            cupon.setDescuento(descuento);
            cupon.setFechaInicio(fechaInicio);
            cupon.setFechaVencimiento(fechaVencimiento);
            cupon.setCondiciones(condiciones);
            cupon.setEstado(estado);
            cupon.setFechaModificacion(LocalDateTime.now());
            return cuponService.actualizar(cupon);
        } else {
            throw new RuntimeException("Cupón no encontrado con ID: " + idCupon);
        }
    }

    // Endpoint para eliminar un cupón
    @DeleteMapping("/eliminarCupon")
    public String eliminarCupon(@RequestParam Integer idCupon) {
        Cupon cupon = cuponService.obtenerPorId(idCupon);
        if (cupon != null) {
            cuponService.eliminar(cupon);
            return "Cupón eliminado exitosamente.";
        } else {
            return "Cupón no encontrado con ID: " + idCupon;
        }
    }

    // Endpoint para verificar un cupón
    @GetMapping("/verificarCupon")
public boolean verificarCupon(@RequestParam String codigoCupon) {
    System.out.println("Parametro recibido: " + codigoCupon);
    List<Cupon> cupones = cuponService.listar();
    return cupones.stream().anyMatch(c -> c.getCodigoCupon().equals(codigoCupon) && c.getEstado() == 1);
}


    // Endpoint para aplicar un cupón
    @PostMapping("/aplicarCupon")
    public String aplicarCupon(@RequestParam String codigoCupon, @RequestParam BigDecimal totalCompra) {
        List<Cupon> cupones = cuponService.listar();
        Cupon cupon = cupones.stream()
                .filter(c -> c.getCodigoCupon().equals(codigoCupon) && c.getEstado() == 1)
                .findFirst()
                .orElse(null);

        if (cupon != null && !cupon.getFechaVencimiento().isBefore(LocalDate.now())) {
            BigDecimal descuentoAplicado = totalCompra.multiply(cupon.getDescuento().divide(new BigDecimal(100)));
            BigDecimal totalFinal = totalCompra.subtract(descuentoAplicado);
            return "Cupón aplicado. Total con descuento: " + totalFinal;
        } else {
            return "Cupón inválido o vencido.";
        }
    }
}
