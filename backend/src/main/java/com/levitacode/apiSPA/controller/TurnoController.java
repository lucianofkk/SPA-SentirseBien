package com.levitacode.apiSPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levitacode.apiSPA.Dto.TurnoDTO;
import com.levitacode.apiSPA.model.Turno;
import com.levitacode.apiSPA.service.TurnoService;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin("*")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public List<Turno> listarTurnos() {
        return turnoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Turno obtenerTurno(@PathVariable Long id) {
        return turnoService.obtenerPorId(id);
    }

    @PostMapping
public Turno crearTurno(@RequestBody TurnoDTO turnoDTO) {
    return turnoService.
    crearDesdeDTO(turnoDTO);
}

    @PutMapping("/{id}")
    public Turno actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) {
        return turnoService.actualizar(id, turno);
    }

    @DeleteMapping("/{id}")
    public void eliminarTurno(@PathVariable Long id) {
        turnoService.eliminar(id);
    }
}
