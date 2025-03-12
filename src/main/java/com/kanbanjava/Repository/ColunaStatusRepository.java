package com.kanbanjava.Repository;

import com.kanbanjava.Model.ColunaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColunaStatusRepository extends JpaRepository<ColunaStatus, Long> {

    @Query("SELECT u FROM ColunaStatus u WHERE u.quadro.id = :quadroId")
    List<ColunaStatus> buscarPorQuadro(@Param("quadroId") Long id);
}