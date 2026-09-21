package com.projeto3.business;

import com.projeto3.business.converter.UsuarioConverter;
import com.projeto3.business.dto.EnderecoDTO;
import com.projeto3.business.dto.TelefoneDTO;
import com.projeto3.business.dto.usuarioDTO;
import com.projeto3.entity.Endereco;
import com.projeto3.entity.Telefone;
import com.projeto3.entity.Usuario;
import com.projeto3.exceptions.ConflictException;
import com.projeto3.repository.EnderecoRepository;
import com.projeto3.repository.ResourceNotFoundException;
import com.projeto3.repository.TelefoneRepository;
import com.projeto3.repository.UsuarioRepository;
import com.projeto3.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    @Override
    public UserDetails loadUserByUsername(@org.springframework.lang.NonNull String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }

    public usuarioDTO salvaUsuario(usuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        boolean existe = verificaEmailExistente(email);
        if (existe) {
            throw new ConflictException("Email já Cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public usuarioDTO buscarusuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                            () -> new ResourceNotFoundException("Email não encontrado: " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email nao encontrado" + email);
        }
    }
    public void deletausuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    // MÉTODO ATUALIZAR ORGANIZADO E CORRIGIDO
    public usuarioDTO atualizaDadosUsuario(String token, usuarioDTO dto) {
        // 1. Extrai o e-mail do usuário logado diretamente de dentro do Token JWT
        String emailToken = jwtUtil.extrairEmailToken(token.substring(7));

        // 2. Busca os dados atuais desse usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(emailToken).orElseThrow(() ->
                new ResourceNotFoundException("Usuário logado não encontrado no banco de dados."));

        // 3. Mescla os novos dados vindos da requisição (DTO) com a entidade existente do banco
        Usuario usuarioAtualizado = usuarioConverter.updateUsuario(dto, usuarioEntity);

        // 4. Se uma nova senha válida foi enviada no DTO, faz a criptografia usando a variável correta
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            usuarioAtualizado.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));
        }

        // 5. Salva a entidade atualizada de volta no banco de dados
        Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);

        // 6. Converte a entidade salva e a retorna como DTO para o Controller
        return usuarioConverter.paraUsuarioDTO(usuarioSalvo);
    }
    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(()->
                new ResourceNotFoundException(" Id não encontrado" + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);



        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }
    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {
        Telefone entity = telefoneRepository.findById(idTelefone) .orElseThrow(()->
            new ResourceNotFoundException(" Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
