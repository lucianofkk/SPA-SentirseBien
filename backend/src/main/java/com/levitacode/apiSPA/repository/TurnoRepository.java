package com.levitacode.apiSPA.repository;

import com.levitacode.apiSPA.model.Turno;
import com.levitacode.apiSPA.model.EstadoTurno;
import scala.collection.immutable.List;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    // Los métodos CRUD básicos ya vienen incluidos con JpaRepository
    // (save, findById, findAll, delete, etc.)

    // Puedes agregar métodos personalizados según tus necesidades
    // Por ejemplo, para buscar turnos por profesional y fecha:
    List<Turno> findByProfesionalIdAndFecha(Integer profesionalId, LocalDate fecha);
    
    // O para buscar turnos por estado:
    List<Turno> findByEstado(EstadoTurno estado);
    
    // O para buscar turnos por profesional y estado:
    List<Turno> findByProfesionalIdAndEstado(Integer profesionalId, EstadoTurno estado);
    
    // O para buscar turnos por profesional:
    List<Turno> findByProfesionalId(Integer profesionalId);
    
    // O para buscar turnos por cliente:
    List<Turno> findByClienteId(Integer clienteId);
    
    // O para buscar turnos por cliente ordenados por fecha descendente:
    List<Turno> findByClienteIdOrderByFechaDesc(Long clienteId);
    
    // O para buscar turnos por profesional y cliente:
    List<Turno> findByProfesionalIdAndClienteId(Integer profesionalId, Integer clienteId);
}