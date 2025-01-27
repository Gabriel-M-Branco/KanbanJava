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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "seq_usuario_id", allocationSize = 1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToMany(mappedBy = "usuario")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Atribuicao> atribuicoes = new ArrayList<>();

    @OneToMany(mappedBy = "responsavel")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Tarefa> tarefasQueRecebeu = new ArrayList<>();

    @OneToMany(mappedBy = "atribuidoPor")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Tarefa> tarefasQueAtribuiu = new ArrayList<>();

    @Schema(description = "Nome do usuário", example = "José da Silva")
    private String nome;

    @Schema(description = "Email do usuário", example = "jose@silva.com")
    private String email;

}
