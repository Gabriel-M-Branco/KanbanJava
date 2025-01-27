package com.kanbanjava.Controller;

import com.kanbanjava.Model.Usuario;
import com.kanbanjava.Repository.UsuarioRepository;
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
@RequestMapping("/usuario")
@Tag(name = "Controller de Usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista os Usuarios", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios retornados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao listar os Usuarios"),
    })
    public ResponseEntity<RespostaApi<List<Usuario>>> ListarUsuarios() {
        try {
            List<Usuario> usuarios = repository.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(usuarios, "Usuarios encontrados com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar os usuarios", 500));
        }
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista um Usuario pelo seu ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuario com esse ID foi encontrado"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao encontrar o Usuario"),
    })
    public ResponseEntity<RespostaApi<Usuario>> ListarUsuarioPorId(@PathVariable("id") Long id) {
        try {
            Usuario usuario = repository.findById(id).orElse(null);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhum usuario com o ID " + id + " foi encontrado.", 404));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(usuario, "Usuario encontrado com sucesso", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro ao tentar listar o usuario", 500));
        }
    }

    @PostMapping("/salvar")
    @Operation(summary = "Salva um Usuario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar o usuario"),
    })
    public ResponseEntity<RespostaApi<Usuario>> salvarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = repository.save(usuario);

            return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(usuarioSalvo, "Usuario salvo com sucesso", 200));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Erro desconhecido ao salvar o usuario", 500));
        }
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualiza um Usuario", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar o usuario"),
    })
    public ResponseEntity<RespostaApi<Usuario>> atualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuarioNovo) {
        Usuario usuarioAntigo = repository.findById(id).orElse(null);

        if (usuarioAntigo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhum usuario com o ID " + id + " foi encontrado.", 404));
        }

        usuarioAntigo.setEmail(usuarioNovo.getEmail());
        usuarioAntigo.setNome(usuarioNovo.getNome());
        repository.save(usuarioAntigo);

        return ResponseEntity.status(HttpStatus.OK).body(new RespostaApi<>(usuarioAntigo, "Usuario atualizado com sucesso", 200));
    }

    @DeleteMapping("/excluir/{id}")
    @Operation(summary = "Exclui um Usuario", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir o usuario"),
    })
    public ResponseEntity<RespostaApi<Usuario>> excluirUsuario(@PathVariable("id") Long id) {
        Usuario usuario = repository.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaApi<>(null, "Nenhum usuario com o ID " + id + " foi encontrado.", 404));
        }

        try {
            repository.delete(usuario);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new RespostaApi<>(null, "Usuario excluído com sucesso", 204));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaApi<>(null, "Usuario não foi excluido", 500));
        }
    }
}
