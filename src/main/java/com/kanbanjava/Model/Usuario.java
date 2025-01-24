package com.kanbanjava.Model;

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
    private Long id;

    @OneToMany(mappedBy = "usuario")
    private List<Atribuicao> atribuicoes = new ArrayList<>();

    @OneToMany(mappedBy = "responsavel")
    private List<Task> tarefasQueRecebeu = new ArrayList<>();

    @OneToMany(mappedBy = "atribuidoPor")
    private List<Task> tarefasQueAtribuiu = new ArrayList<>();

    private String nome;
    private String email;

}
