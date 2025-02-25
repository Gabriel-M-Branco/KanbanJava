package com.kanbanjava.Controller;

import com.kanbanjava.Model.Atribuicao;
import com.kanbanjava.Repository.AtribuicaoRepository;
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
@RequestMapping("/atribuicao")
@Tag(name = "Controller de Atribuições")
public class AtribuicaoController {

    private final AtribuicaoRepository repository;

    public AtribuicaoController(AtribuicaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista as Atribuições", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atribuições retornadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar as Atribuições"),
    })
    public ResponseEntity<RespostaApi<List<Atribuicao>>> ListarAtribuicoes() {
        try {
            List<Atribuicao> atribuicoes = repository.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(atribuicoes, "Atribuições encontradas com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar as Atribuições", 500));
        }

    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista uma Atribuicao pelo seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atribuicao encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma atribuição com esse ID foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao encontrar a Atribuicao"),
    })
    public ResponseEntity<RespostaApi<Atribuicao>> ListarAtribuicaoPorId(@PathVariable("id") Long id) {
        try {
            Atribuicao atribuicao = repository.findById(id).orElse(null);

            if (atribuicao == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma atribuição com o ID " + id + " foi encontrado.", 404));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(atribuicao, "Atribuicao encontrada com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar a atribuição", 500));
        }
    }

    @PostMapping("/salvar")
    @Operation(summary = "Salva uma Atribuicao", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atribuicao salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar a atribuição"),
    })
    public ResponseEntity<RespostaApi<Atribuicao>> salvarAtribuicao(@RequestBody Atribuicao atribuicao) {
        try {
            Atribuicao atribuicaoSalva = repository.save(atribuicao);

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(atribuicaoSalva, "Atribuicao salva com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro desconhecido ao salvar a atribuição", 500));
        }
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza uma Atribuicao", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atribuicao atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Atribuicao não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar a atribuição"),
    })
    public ResponseEntity<RespostaApi<Atribuicao>> atualizarAtribuicao(@PathVariable("id") Long id, @RequestBody Atribuicao atribuicaoNova) {
        Atribuicao atribuicaoAntiga = repository.findById(id).orElse(null);

        if (atribuicaoAntiga == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma atribuição com o ID " + id + " foi encontrado.", 404));
        }

        atribuicaoAntiga.setDataAtribuicao(atribuicaoNova.getDataAtribuicao());
        atribuicaoAntiga.setStatus(atribuicaoNova.getStatus());
        atribuicaoAntiga.setPrioridade(atribuicaoNova.getPrioridade());

        repository.save(atribuicaoAntiga);

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(atribuicaoAntiga, "Atribuição atualizada com sucesso", 200));
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Exclui uma Atribuicao", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atribuicao excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Atribuicao não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir a atribuição"),
    })
    public ResponseEntity<RespostaApi<Atribuicao>> excluirAtribuicao(@PathVariable("id") Long id) {
        Atribuicao atribuicao = repository.findById(id).orElse(null);

        if (atribuicao == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhuma atribuição com o ID " + id + " foi encontrada.", 404));
        }

        try {
            repository.delete(atribuicao);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new RespostaApi<>(null, "Atribuicao excluída com sucesso", 204));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Atribuicao não foi excluida", 500));
        }
    }
}
