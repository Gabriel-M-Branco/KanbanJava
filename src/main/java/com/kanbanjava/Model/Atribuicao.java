package com.kanbanjava.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Atribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atribuicao_seq")
    @SequenceGenerator(name = "atribuicao_seq", sequenceName = "seq_atribuicao_id", allocationSize = 1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    private Tarefa tarefa;

    @Schema(description = "Data da atribuicao", example = "2025-01-26T14:30")
    private LocalDateTime dataAtribuicao;

    @Schema(description = "Status da Atribuição", example = "EM ANDAMENTO")
    private String status;

    @Schema(description = "Nome do quadro", example = "Quadro de Projetos")
    private String prioridade;

}
