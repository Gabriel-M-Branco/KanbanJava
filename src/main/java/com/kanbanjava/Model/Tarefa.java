package com.kanbanjava.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarefa_seq")
    @SequenceGenerator(name = "tarefa_seq", sequenceName = "seq_tarefa_id", allocationSize = 1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Atribuicao> atribuicoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    @Schema(description = "ID do responsavel pela tarefa", example = "1")
    private Usuario responsavel;

    @ManyToOne
    @JoinColumn(name = "atribuido_por_id")
    @Schema(description = "ID de quem atribuiu a tarefa", example = "1")
    private Usuario atribuidoPor;

    @ManyToOne
    @JoinColumn(name = "quadro_id")
    @Schema(description = "Quadro ao qual a tarefa pertence", example = "1")
    private Quadro quadro;

    @ManyToOne
    @JoinColumn(name = "coluna_status_id")
    @Schema(description = "Coluna onde a tarefa está", example = "1")
    private ColunaStatus colunaStatus;

    @Schema(description = "Titulo da tarefa", example = "Consertar Front-End")
    private String titulo;
    @Schema(description = "Descricao da tarefa", example = "Verificar e consertar a função de atualizar a página")
    private String descricao;
    @Schema(description = "Data da criação da tarefa", example = "2025-01-26T10:30")
    private LocalDateTime dataCriacao;
    @Schema(description = "Data de início", example = "2025-01-26T11:30")
    private LocalDateTime dataInicio;
    @Schema(description = "Data prevista para o término da tarefa", example = "2025-01-26T13:30")
    private LocalDateTime dataTerminoPrevisao;
    @Schema(description = "Data real do término da tarefa", example = "2025-01-26T12:30")
    private LocalDateTime dataTerminoReal;

}