package com.projeto3.usuario3.controller;

import com.projeto3.business.dto.UsuarioDTO;
import com.projeto3.business.dto.EnderecoDTO;
import com.projeto3.business.dto.TelefoneDTO;
import com.projeto3.business.UsuarioService;
import com.projeto3.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String email = loginRequest.get("email");
            String senha = loginRequest.get("senha");

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, senha)
            );

            final UserDetails userDetails = usuarioService.loadUserByUsername(email);
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok("Bearer " + jwt);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro na autenticação: Credenciais inválidas! Detalhe: " + e.getMessage());
        }
    }

    // CORRIGIDO: Corrigido o nome do Header para "Authorization"
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(
            @RequestBody EnderecoDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    // CORRIGIDO: Corrigido o nome do Header para "Authorization"
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(
            @RequestBody TelefoneDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }

    @PutMapping
    public ResponseEntity<?> atualizaDadosUsuario(
            @RequestHeader("Authorization") String token,
            @RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente ou malformatado.");
            }

            UsuarioDTO usuarioAtualizado = usuarioService.atualizaDadosUsuario(token, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(
            @RequestBody EnderecoDTO dto,
            @RequestParam("id") long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(
            @RequestBody TelefoneDTO dto,
            @RequestParam("id") long id) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioDtoPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
}
