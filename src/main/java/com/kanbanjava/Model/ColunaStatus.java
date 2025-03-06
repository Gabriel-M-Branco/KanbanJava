package com.kanbanjava.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Schema(description = "Representa um quadro que contém tarefas e status.")
public class ColunaStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quadro_seq")
    @SequenceGenerator(name = "quadro_seq", sequenceName = "seq_quadro_id", allocationSize = 1)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
}
