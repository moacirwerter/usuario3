package com.projeto3.usuario3.controller;

import com.projeto3.business.dto.usuarioDTO;
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
import java.util.Map; // IMPORTAÇÃO ADICIONADA

import com.projeto3.entity.Usuario;
import com.projeto3.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<usuarioDTO> salvaUsuario(@RequestBody usuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }


    // ENDPOINT DE LOGIN ATUALIZADO (Evita o erro 400 de mapeamento do DTO)
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
            return ResponseEntity.ok(jwt);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro na autenticação: Credenciais inválidas! Detalhe: " + e.getMessage());
        }
    }

    // ENDPOINT DE ATUALIZAÇÃO DO USUÁRIO
    @PutMapping
    public ResponseEntity<?> atualizaDadosUsuario(
            @RequestHeader("Authorization") String token,
            @RequestBody usuarioDTO usuarioDTO) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente ou malformatado.");
            }

            usuarioDTO usuarioAtualizado = usuarioService.atualizaDadosUsuario(token, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar: " + e.getMessage());
        }
    }

    // ENDPOINT DE ATUALIZAÇÃO DE ENDEREÇO
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(
            @RequestBody EnderecoDTO dto,
            @RequestParam("id") long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    // ENDPOINT DE ATUALIZAÇÃO DE TELEFONE
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(
            @RequestBody TelefoneDTO dto,
            @RequestParam("id") long id) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));

    @PostMapping("/login")
    public String login(@RequestBody usuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()
                )
        );

        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarusuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deleteUsuarioPorEmail(email);
        return ResponseEntity.ok().build();

    }
}

