package com.kanbanjava.Controller;

import com.kanbanjava.Model.ColunaStatus;
import com.kanbanjava.Repository.ColunaStatusRepository;
import com.kanbanjava.Response.RespostaApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coluna-status")
@Tag(name = "Controller de Colunas de Status")
public class ColunaStatusController {

    private final ColunaStatusRepository repository;

    public ColunaStatusController(ColunaStatusRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista as Colunas de Status", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coluna de Status retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar as Colunas de Status"),
    })
    public ResponseEntity<RespostaApi<List<ColunaStatus>>> ListarColunaStatus() {
        try {
            List<ColunaStatus> colunasStatus = repository.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(colunasStatus, "Coluna de Status encontrados com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar as Colunas de Status", 500));
        }

    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista uma Coluna de Status pelo seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coluna de Status encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma Coluna de Status com esse ID foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao encontrar a Coluna de Status"),
    })
    public ResponseEntity<RespostaApi<ColunaStatus>> ListarColunaStatus(@PathVariable("id") Long id) {
        try {
            ColunaStatus colunaStatus = repository.findById(id).orElse(null);

            if (colunaStatus == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma Coluna de Status com o ID " + id + " foi encontrado.", 404));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(colunaStatus, "Coluna Status encontrada com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar a Coluna Status", 500));
        }
    }

    @PostMapping("/salvar")
    @Operation(summary = "Salva uma Coluna de Status", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coluna de Status salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar a Coluna de Status"),
    })
    public ResponseEntity<RespostaApi<ColunaStatus>> salvarColunaStatus(@RequestBody ColunaStatus colunaStatus) {
        try {
            ColunaStatus colunaStatusSalva = repository.save(colunaStatus);

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(colunaStatusSalva, "Coluna de Status salva com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro desconhecido ao salvar a Coluna de Status", 500));
        }
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza uma Coluna de Status", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coluna de Status atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Coluna de Status não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar a Coluna de Status"),
    })
    public ResponseEntity<RespostaApi<ColunaStatus>> atualizarColunaStatus(@PathVariable("id") Long id, @RequestBody ColunaStatus colunaStatusNova) {
        ColunaStatus colunaStatusAntiga = repository.findById(id).orElse(null);

        if (colunaStatusAntiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma Coluna de Status com o ID " + id + " foi encontrado.", 404));
        }

        colunaStatusAntiga.setTitulo(colunaStatusNova.getTitulo());
        repository.save(colunaStatusAntiga);

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(colunaStatusAntiga, "Quadro atualizado com sucesso", 200));
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Exclui uma Coluna de Status", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coluna de Status excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Coluna de Status não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir a Coluna de Status"),
    })
    public ResponseEntity<RespostaApi<ColunaStatus>> excluirColunaStatus(@PathVariable("id") Long id) {
        ColunaStatus colunaStatus = repository.findById(id).orElse(null);

        if (colunaStatus == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma Coluna de Status com o ID " + id + " foi encontrado.", 404));
        }

        try {
            repository.delete(colunaStatus);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new RespostaApi<>(null, "Coluna de Status excluída com sucesso", 204));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Coluna de Status não foi excluido", 500));
        }
    }

}