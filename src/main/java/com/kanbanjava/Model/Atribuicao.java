package com.kanbanjava.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Atribuicao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atribuicao_seq")
    @SequenceGenerator(name = "atribuicao_seq", sequenceName = "seq_atribuicao_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String dataAtribuicao;
    private String status;
    private String prioridade;

}
