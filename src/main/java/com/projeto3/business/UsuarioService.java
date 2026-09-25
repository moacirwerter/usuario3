package com.projeto3.business;

import com.projeto3.business.converter.UsuarioConverter;
import com.projeto3.business.dto.EnderecoDTO;
import com.projeto3.business.dto.TelefoneDTO;
import com.projeto3.business.dto.UsuarioDTO;
import com.projeto3.entity.Endereco;
import com.projeto3.entity.Telefone;
import com.projeto3.entity.Usuario;
import com.projeto3.exceptions.ConflictException;
import com.projeto3.repository.EnderecoRepository;
import com.projeto3.repository.ResourceNotFoundException;
import com.projeto3.repository.TelefoneRepository;
import com.projeto3.repository.UsuarioRepository;
import com.projeto3.security.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioConverter usuarioConverter,
                          @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
                          JwtUtil jwtUtil,
                          EnderecoRepository enderecoRepository,
                          TelefoneRepository telefoneRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioConverter = usuarioConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.enderecoRepository = enderecoRepository;
        this.telefoneRepository = telefoneRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        String emailTratado = email != null ? email.trim() : "";
        Usuario usuario = usuarioRepository.findByEmail(emailTratado)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + emailTratado));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        String emailTratado = email != null ? email.trim() : "";
        if (verificaEmailExistente(emailTratado)) {
            throw new ConflictException("Email já Cadastrado: " + emailTratado);
        }
    }

    public boolean verificaEmailExistente(String email) {
        String emailTratado = email != null ? email.trim() : "";
        return usuarioRepository.existsByEmail(emailTratado);
    }

    public UsuarioDTO buscarUsuarioDtoPorEmail(String email) {
        String emailTratado = email != null ? email.trim() : "";
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.findByEmail(emailTratado)
                        .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + emailTratado))
        );
    }

    public Usuario buscarUsuarioEntityPorEmail(String email) {
        String emailTratado = email != null ? email.trim() : "";
        return usuarioRepository.findByEmail(emailTratado)
                .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + emailTratado));
    }

    public void deletaUsuarioPorEmail(String email) {
        String emailTratado = email != null ? email.trim() : "";
        usuarioRepository.deleteByEmail(emailTratado);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String emailToken = jwtUtil.extrairEmailToken(token.substring(7));
        String emailTratado = emailToken != null ? emailToken.trim() : "";

        Usuario usuarioEntity = usuarioRepository.findByEmail(emailTratado)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário logado não encontrado no banco de dados."));

        Usuario usuarioAtualizado = usuarioConverter.updateUsuario(dto, usuarioEntity);

        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            usuarioAtualizado.setSenha(bCryptPasswordEncoder.encode(dto.getSenha()));
        }

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioAtualizado));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        Endereco entity = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    // CORRIGIDO: Removido o fragmento ("/telefone") que quebrava a sintaxe do Java
    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto) {
        Telefone entity = telefoneRepository.findById(idTelefone)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        String emailTratado = email != null ? email.trim() : "";

        Usuario usuario = usuarioRepository.findByEmail(emailTratado)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não localizado: " + emailTratado));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        String emailTratado = email != null ? email.trim() : "";

        Usuario usuario = usuarioRepository.findByEmail(emailTratado)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não localizado: " + emailTratado));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
