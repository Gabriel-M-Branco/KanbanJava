package com.kanbanjava.Model;

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

    @ElementCollection
    @CollectionTable(name = "quadro_status", joinColumns = @JoinColumn(name = "quadro_id"))
    @Column(name = "status")
    @Schema(description = "Lista de status do quadro", example = "[\"Aberto\", \"Em andamento\", \"Concluído\"]")
    private List<String> status;

    @OneToMany(mappedBy = "quadro")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Tarefa> tarefas = new ArrayList<>();

    @Schema(description = "Nome do quadro", example = "Quadro de Projetos")
    private String nome;

    @Schema(description = "Descrição do quadro", example = "Gerencia tarefas do time de projetos")
    private String descricao;

}