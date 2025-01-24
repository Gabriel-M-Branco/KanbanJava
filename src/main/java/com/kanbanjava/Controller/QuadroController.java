package com.kanbanjava.Controller;

import com.kanbanjava.Model.Quadro;
import com.kanbanjava.Repository.QuadroRepository;
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
@RequestMapping("/quadro")
@Tag(name = "Controller de Quadros")
public class QuadroController {

    private final QuadroRepository repository;

    public QuadroController(QuadroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista os Quadros", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quadros retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar os Quadros"),
    })
    public ResponseEntity<RespostaApi<List<Quadro>>> ListarQuadros() {
        List<Quadro> quadros = repository.findAll();

        if (quadros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhum quadro foi encontrado.", 404));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(quadros, "Quadros encontrados com sucesso", 200));
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista um Quadro pelo seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quadro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum quadro com esse ID foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao encontrar o Quadro"),
    })
    public ResponseEntity<RespostaApi<Quadro>> ListarQuadroPorId(@PathVariable("id") Long id) {
        Quadro quadro = repository.findById(id).orElse(null);

        if (quadro == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhum quadro com o ID " + id + " foi encontrado.", 404));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(quadro, "Quadro encontrado com sucesso", 200));
    }

    @PostMapping("/salvar")
    @Operation(summary = "Salva um Quadro", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quadro salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar o quadro"),
    })
    public ResponseEntity<RespostaApi<Quadro>> salvarQuadro(@RequestBody Quadro quadro) {
        Quadro quadroSalvo = repository.save(quadro);

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(quadroSalvo, "Quadro salvo com sucesso", 200));
    }

}