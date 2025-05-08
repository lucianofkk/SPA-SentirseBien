package com.spa.SentirseBien.repository;

import com.spa.SentirseBien.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositroy extends JpaRepository<Cliente, Long> {

}