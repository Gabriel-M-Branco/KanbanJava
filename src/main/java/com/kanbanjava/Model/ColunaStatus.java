package com.kanbanjava.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Schema(description = "Representa uma coluna de status que contém tarefas.")
public class ColunaStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coluna_seq")
    @SequenceGenerator(name = "coluna_seq", sequenceName = "seq_coluna_id", allocationSize = 1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quadro_id")
    @JsonBackReference
    @Schema(description = "Quadro ao qual a coluna status pertence", example = "1")
    private Quadro quadro;

    @OneToMany(mappedBy = "colunaStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Tarefa> tarefas = new ArrayList<>();

    private String titulo;

}
