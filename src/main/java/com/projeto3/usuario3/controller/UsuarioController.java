package com.projeto3.usuario3.controller;

import com.projeto3.business.dto.usuarioDTO; // Mantido padrão com 'u' minúsculo
import com.projeto3.business.UsuarioService;
import com.projeto3.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    // ENDPOINT DE LOGIN (Retorna o Token JWT)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody usuarioDTO usuarioDTO) {
        try {
            // Autentica o usuário com o e-mail e a senha digitados no Postman
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
            );
        } catch (Exception e) {
            // Captura o erro se a senha estiver errada ou se o usuário não existir
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro na autenticação: Credenciais inválidas! Detalhe: " + e.getMessage());
        }

        // Busca os dados do usuário para colocar dentro do token
        final UserDetails userDetails = usuarioService.loadUserByUsername(usuarioDTO.getEmail());

        // Extrai o username (String) do userDetails para gerar o token
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Devolve o token na resposta do Postman
        return ResponseEntity.ok(jwt);
    }

    // ENDPOINT DE ATUALIZAÇÃO (Validando o token JWT enviado no Header)
    @PutMapping
    public ResponseEntity<?> atualizaDadosUsuario(
            @RequestHeader("Authorization") String token,
            @RequestBody usuarioDTO usuarioDTO) {
        try {
            // Valida se o cabeçalho veio preenchido corretamente antes de processar
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente ou malformatado.");
            }

            // Executa a atualização chamando a lógica do Service
            usuarioDTO usuarioAtualizado = usuarioService.atualizaDadosUsuario(token, usuarioDTO);
            return ResponseEntity.ok(usuarioAtualizado);

        } catch (Exception e) {
            // Captura falhas se o usuário não existir ou se houver erro ao ler o token
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar: " + e.getMessage());
        }
    }
}
