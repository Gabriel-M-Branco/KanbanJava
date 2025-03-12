package com.kanbanjava.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Schema(description = "Representa um quadro que contém tarefas e status.")
public class Quadro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quadro_seq")
    @SequenceGenerator(name = "quadro_seq", sequenceName = "seq_quadro_id", allocationSize = 1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Tarefa> tarefas = new ArrayList<>();

    @OneToMany(mappedBy = "quadro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<ColunaStatus> colunaStatus = new ArrayList<>();

    @Schema(description = "Nome do quadro", example = "Quadro de Projetos")
    private String nome;

    @Schema(description = "Descrição do quadro", example = "Gerencia tarefas do time de projetos")
    private String descricao;

}