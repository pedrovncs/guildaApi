package org.infnet.guildaApiTP1.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.*;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.audit_schema.Usuario;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Companheiro;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.infnet.guildaApiTP1.repository.ParticipacaoEmMissaoRepository;
import org.infnet.guildaApiTP1.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepository aventureiroRepository;

    private final ParticipacaoEmMissaoRepository participacaoEmMissaoRepository;

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public AventureiroDTO criarAventureiro(CriarAventureiroRequest dto) {
        Usuario usuarioResponsavel = usuarioRepository.findById(dto.usuarioResponsavelId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário responsável não encontrado"));

        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setNome(dto.nome());
        aventureiro.setClasse(dto.classe());
        aventureiro.setNivel(dto.nivel());
        aventureiro.setAtivo(true);
        aventureiro.setCompanheiro(null);
        aventureiro.setUsuarioResponsavel(usuarioResponsavel);
        aventureiro.setOrganizacao(usuarioResponsavel.getOrganizacao());

        Aventureiro salvo = aventureiroRepository.save(aventureiro);

        return new AventureiroDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getClasse(),
                salvo.getNivel(),
                salvo.isAtivo()
        );
    }

    @Transactional
    public AventureiroDTO atualizarAventureiro(Long id, AtualizarAventureiroRequest dto) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        aventureiro.setNome(dto.nome());
        aventureiro.setClasse(dto.classe());
        aventureiro.setNivel(dto.nivel());

        Aventureiro salvo = aventureiroRepository.save(aventureiro);

        return new AventureiroDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getClasse(),
                salvo.getNivel(),
                salvo.isAtivo()
        );
    }

    @Transactional
    public void encerrarVinculo(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        aventureiro.setAtivo(false);
        aventureiroRepository.save(aventureiro);
    }

    @Transactional
    public void renovarVinculo(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        aventureiro.setAtivo(true);
        aventureiroRepository.save(aventureiro);
    }

    @Transactional
    public void definirCompanheiro(Long id, CriarCompanheiroRequest dto) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        Companheiro companheiro = new Companheiro();
        companheiro.setNome(dto.nome());
        companheiro.setEspecie(dto.especie());
        companheiro.setIndiceLealdade(dto.lealdade());

        aventureiro.setCompanheiro(companheiro);
        aventureiroRepository.save(aventureiro);
    }

    @Transactional
    public void removerCompanheiro(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));
        aventureiro.setCompanheiro(null);
    }

    public Page<AventureiroDTO> listarAventureirosComFiltros(Boolean ativo, ClasseEnum classe, Integer nivelMinimo, Pageable pageable) {
        return aventureiroRepository.buscarComFiltros(ativo, classe, nivelMinimo, pageable);
    }

    public Page<AventureiroDTO> listarAventureirosPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(a -> new AventureiroDTO(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo()
                ));
    }

    public AventureiroCompletoDTO buscarAventureiroCompleto(Long id) {
        Aventureiro aventureiro = aventureiroRepository.findAventureiroComCompanheiroEOrganizacaoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        OrganizacaoDTO organizacao = new OrganizacaoDTO(
                aventureiro.getOrganizacao().getId(),
                aventureiro.getOrganizacao().getNome()
        );

        CompanheiroDTO companheiro = aventureiro.getCompanheiro() != null
                ? new CompanheiroDTO(
                aventureiro.getCompanheiro().getNome(),
                aventureiro.getCompanheiro().getEspecie(),
                aventureiro.getCompanheiro().getIndiceLealdade()
        )
                : null;

        UltimaMissaoDTO ultimaMissao = participacaoEmMissaoRepository
                .buscarUltimaMissao(aventureiro.getId(), PageRequest.of(0, 1))
                .stream().findFirst()
                .orElse(null);

        Integer qtdTotalMissoes = participacaoEmMissaoRepository.countByAventureiroId(aventureiro.getId());

        return new AventureiroCompletoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo(),
                organizacao,
                companheiro,
                qtdTotalMissoes,
                ultimaMissao
        );
    }


}
