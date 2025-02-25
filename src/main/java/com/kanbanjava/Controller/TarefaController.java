package com.kanbanjava.Controller;

import com.kanbanjava.Model.Tarefa;
import com.kanbanjava.Repository.TarefaRepository;
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
@RequestMapping("/tarefa")
@Tag(name = "Controller de Tarefas")
public class TarefaController {

    private final TarefaRepository repository;

    public TarefaController(TarefaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista as Tarefas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar as Tarefas"),
    })
    public ResponseEntity<RespostaApi<List<Tarefa>>> ListarTarefas() {
        try {
            List<Tarefa> tarefas = repository.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(tarefas, "Tarefas encontradas com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar as tarefas", 500));
        }

    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista uma Tarefa pelo seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa com esse ID foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao encontrar a Tarefa"),
    })
    public ResponseEntity<RespostaApi<Tarefa>> ListarTarefaPorId(@PathVariable("id") Long id) {
        try {
            Tarefa tarefa = repository.findById(id).orElse(null);

            if (tarefa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma tarefa com o ID " + id + " foi encontrado.", 404));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(tarefa, "Tarefa encontrada com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar a tarefa", 500));
        }
    }

    @PostMapping("/salvar")
    @Operation(summary = "Salva uma Tarefa", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar a tarefa"),
    })
    public ResponseEntity<RespostaApi<Tarefa>> salvarTarefa(@RequestBody Tarefa tarefa) {
        try {
            Tarefa tarefaSalva = repository.save(tarefa);

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(tarefaSalva, "Tarefa salva com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro desconhecido ao salvar a tarefa", 500));
        }
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza uma Tarefa", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Tarefa não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar a tarefa"),
    })
    public ResponseEntity<RespostaApi<Tarefa>> atualizarTarefa(@PathVariable("id") Long id, @RequestBody Tarefa tarefaNova) {
        Tarefa tarefaAntiga = repository.findById(id).orElse(null);

        if (tarefaAntiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma tarefa com o ID " + id + " foi encontrado.", 404));
        }

        tarefaAntiga.setTitulo(tarefaNova.getTitulo());
        tarefaAntiga.setDescricao(tarefaNova.getDescricao());
        tarefaAntiga.setDataCriacao(tarefaNova.getDataCriacao());
        tarefaAntiga.setDataInicio(tarefaNova.getDataInicio());
        tarefaAntiga.setDataTerminoPrevisao(tarefaNova.getDataTerminoPrevisao());
        tarefaAntiga.setDataTerminoReal(tarefaNova.getDataTerminoReal());

        repository.save(tarefaAntiga);

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(tarefaAntiga, "Tarefa atualizada com sucesso", 200));
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Exclui uma Tarefa", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Tarefa não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir a tarefa"),
    })
    public ResponseEntity<RespostaApi<Tarefa>> excluirTarefa(@PathVariable("id") Long id) {
        Tarefa tarefa = repository.findById(id).orElse(null);

        if (tarefa == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma tarefa com o ID " + id + " foi encontrada.", 404));
        }

        try {
            repository.delete(tarefa);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new RespostaApi<>(null, "Tarefa excluída com sucesso", 204));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Tarefa não foi excluida", 500));
        }
    }
}