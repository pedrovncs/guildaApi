package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.*;
import org.infnet.guildaApiTP1.enums.StatusUsuarioEnum;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;
import org.infnet.guildaApiTP1.model.audit_schema.Usuario;
import org.infnet.guildaApiTP1.repository.OrganizacaoRepository;
import org.infnet.guildaApiTP1.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final OrganizacaoRepository organizacaoRepository;

    public UsuarioComRolesDTO buscarPorIdComRoles(Long userId) {
        Usuario usuario = usuarioRepository.findWithRolesById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        List<RoleDTO> usuarioRoles = usuario.getRoles().stream()
                .map(r ->
                        new RoleDTO(
                                r.getRole().getNome(),
                                r.getRole().getDescricao()
                        )).toList();

        Organizacao org = usuario.getOrganizacao();

        return new UsuarioComRolesDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getStatus(),
                new OrganizacaoDTO(
                        org.getId(),
                        org.getNome()
                ),
                usuarioRoles

        );
    }

    @Transactional
    public UsuarioDTO criar(CriarUsuarioRequest dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setStatus(StatusUsuarioEnum.ATIVO);
        usuario.setSenhaHash(String.valueOf(dto.senha().hashCode()));

        Organizacao org = organizacaoRepository.findById(dto.organizacaoId())
                .orElseThrow(() -> new IllegalArgumentException("Organização não encontrada"));

        usuario.setOrganizacao(org);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioDTO(
                usuarioSalvo.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getStatus(),
                new OrganizacaoDTO(
                        org.getId(),
                        org.getNome()
                )
    );
    }
}
