package com.kanbanjava.Repository;

import com.kanbanjava.Model.Atribuicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtribuicaoRepository extends JpaRepository<Atribuicao, Long> {
}