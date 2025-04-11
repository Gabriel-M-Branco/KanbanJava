package com.kanbanjava.Repository;

import com.kanbanjava.Model.ColunaStatus;
import com.kanbanjava.Model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT u FROM Tarefa u WHERE u.colunaStatus.id = :colunaStatusId")
    List<Tarefa> listarPorColunaStatus(@Param("colunaStatusId") Long id);

}
