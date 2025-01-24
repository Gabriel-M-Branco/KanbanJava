package com.kanbanjava.Repository;

import com.kanbanjava.Model.Quadro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadroRepository extends JpaRepository<Quadro, Long> {
}