package com.kanbanjava.Repository;

import com.kanbanjava.Model.ColunaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColunaStatusRepository extends JpaRepository<ColunaStatus, Long> {
}

