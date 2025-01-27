package com.kanbanjava.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "seq_item_id", allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "tarefa")
    private List<Atribuicao> atribuicoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @ManyToOne
    @JoinColumn(name = "atribuido_por_id")
    private Usuario atribuidoPor;

    @ManyToOne
    @JoinColumn(name = "quadro_id")
    private Quadro quadro;

    private String titulo;
    private String descricao;
    private String dataCriacao;
    private String dataInicio;
    private String dataTerminoPrevisao;
    private String dataTerminoReal;

}