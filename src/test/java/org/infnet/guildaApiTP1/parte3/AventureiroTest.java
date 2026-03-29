package org.infnet.guildaApiTP1.parte3;

import jakarta.persistence.EntityManager;
import org.infnet.guildaApiTP1.dto.AventureiroCompletoDTO;
import org.infnet.guildaApiTP1.dto.AventureiroDTO;
import org.infnet.guildaApiTP1.dto.CriarParticipacaoEmMissaoRequest;
import org.infnet.guildaApiTP1.enums.*;
import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;
import org.infnet.guildaApiTP1.model.audit_schema.Usuario;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Companheiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Missao;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.infnet.guildaApiTP1.service.AventureiroService;
import org.infnet.guildaApiTP1.service.ParticipacaoEmMissaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({AventureiroService.class, ParticipacaoEmMissaoService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AventureiroTest {

    @Autowired
    private AventureiroService aventureiroService;

    @Autowired
    private ParticipacaoEmMissaoService participacaoService;

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private EntityManager entityManager;

    private Organizacao org;
    private Usuario usuario;
    private Aventureiro aventureiroThorin;

    @BeforeEach
    void setup() {
        org = new Organizacao();
        org.setNome("ORG TESTE " + System.nanoTime());
        org.setAtivo(true);
        entityManager.persist(org);

        usuario = new Usuario();
        usuario.setNome("USUARIO TESTE");
        usuario.setEmail("user" + System.nanoTime() + "@teste.com");
        usuario.setSenhaHash("hash");
        usuario.setStatus(StatusUsuarioEnum.ATIVO);
        usuario.setOrganizacao(org);
        entityManager.persist(usuario);

        criarAventureiro("BILBO", ClasseEnum.LADINO, 15, true);
        criarAventureiro("BOROMIR", ClasseEnum.GUERREIRO, 20, true);
        criarAventureiro("BARD", ClasseEnum.ARQUEIRO, 33, true);
        aventureiroThorin = criarAventureiro("THORIN ESCUDO DE CARVALHO", ClasseEnum.GUERREIRO, 40, true);
        criarAventureiro("RADAGAST", ClasseEnum.MAGO, 48, true);
        criarAventureiro("SARUMAN", ClasseEnum.MAGO, 53, false);
        criarAventureiro("GANDALF", ClasseEnum.MAGO, 70, true);

        entityManager.flush();
        entityManager.clear();
    }

    private Aventureiro criarAventureiro(String nome, ClasseEnum classe, Integer nivel, Boolean ativo) {
        Aventureiro a = new Aventureiro();
        a.setNome(nome);
        a.setClasse(classe);
        a.setNivel(nivel);
        a.setAtivo(ativo);
        a.setOrganizacao(org);
        a.setUsuarioResponsavel(usuario);
        entityManager.persist(a);

        return a;
    }

    @Test
    @DisplayName("deve listar aventureiros com filtros de nivel, classe e status e ordenado por nivel")
    void deveListarAventureirosComFiltros() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nivel").descending());

        Page<AventureiroDTO> busca = aventureiroRepository.buscarComFiltros(true, ClasseEnum.MAGO, 30, pageable);

        assertEquals(2, busca.getTotalElements());
        assertEquals("GANDALF", busca.getContent().getFirst().nome());
    }

    @Test
    @DisplayName("deve listar apenas 2 aventureiros")
    void deveListarApenas2() {
        Pageable pageable = PageRequest.of(0, 2);

        Page<AventureiroDTO> busca = aventureiroRepository.buscarComFiltros(null, null, null, pageable);

        assertEquals(2, busca.getNumberOfElements());
    }

    @Test
    @DisplayName("deve listar ordenado por nome em ordem crescente")
    void deveListarOrdenadoPorNome() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());

        Page<AventureiroDTO> busca = aventureiroRepository.buscarComFiltros(null, null, null, pageable);

        assertEquals("BARD", busca.getContent().getFirst().nome());
        assertEquals(7, busca.getTotalElements());
    }

    @Test
    @DisplayName("deve buscar por nome parcial")
    void deveBuscarPorNomeParcial() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<AventureiroDTO> busca = aventureiroService.listarAventureirosPorNome("lbo", pageable);

        assertEquals("BILBO", busca.getContent().getFirst().nome());
    }

    @Test
    @DisplayName("deve buscar aventueiro thorin completo com companheiro e ultima missao")
    void deveBuscarThorinCompleto(){
        Companheiro comp = new Companheiro();
        comp.setNome("MINI SMAUG");
        comp.setEspecie(EspecieEnum.DRAGAO_MINIATURA);
        comp.setIndiceLealdade(1);
        aventureiroThorin.setCompanheiro(comp);
        entityManager.persist(comp);

        Missao m1 = new Missao();
        m1.setTitulo("ENCONTRAR UM LADINO");
        m1.setNivelDePerigo(NivelPerigoEnum.BAIXO);
        m1.setStatus(StatusMissaoEnum.PLANJEADA);
        m1.setOrganizacao(org);
        entityManager.persist(m1);

        Missao m2 = new Missao();
        m2.setTitulo("RETOMAR EREBOR");
        m2.setNivelDePerigo(NivelPerigoEnum.EXTREMO);
        m2.setStatus(StatusMissaoEnum.PLANJEADA);
        m2.setOrganizacao(org);
        entityManager.persist(m2);

        CriarParticipacaoEmMissaoRequest dto1 =
                new CriarParticipacaoEmMissaoRequest(
                        m1.getId(),
                        aventureiroThorin.getId(),
                        PapelNaMissaoEnum.LIDER,
                        0,
                        false);
        CriarParticipacaoEmMissaoRequest dto2 = new CriarParticipacaoEmMissaoRequest(
                m2.getId(),
                aventureiroThorin.getId(),
                PapelNaMissaoEnum.LIDER,
                100000,
                true);

        participacaoService.criarParticipacaoEmMissao(dto1);
        m1.setStatus(StatusMissaoEnum.CONCLUIDA);
        participacaoService.criarParticipacaoEmMissao(dto2);

        entityManager.flush();
        entityManager.clear();

        AventureiroCompletoDTO thorin = aventureiroService.buscarAventureiroCompleto(aventureiroThorin.getId());

        assertNotNull(thorin);
        assertEquals("THORIN ESCUDO DE CARVALHO", thorin.nome());
        assertEquals("MINI SMAUG", thorin.companheiro().nome());
        assertEquals(2, thorin.qtdTotalMissoes());
        assertEquals("RETOMAR EREBOR", thorin.ultimaMissao().titulo());
    }

    @Test
    @DisplayName("deve buscar aventueiro thorin completo sem companheiro")
    void deveBuscarThorinCompletoSemCompanheiro(){
        Missao m1 = new Missao();
        m1.setTitulo("ENCONTRAR UM LADINO");
        m1.setNivelDePerigo(NivelPerigoEnum.BAIXO);
        m1.setStatus(StatusMissaoEnum.PLANJEADA);
        m1.setOrganizacao(org);
        entityManager.persist(m1);

        Missao m2 = new Missao();
        m2.setTitulo("RETOMAR EREBOR");
        m2.setNivelDePerigo(NivelPerigoEnum.EXTREMO);
        m2.setStatus(StatusMissaoEnum.PLANJEADA);
        m2.setOrganizacao(org);
        entityManager.persist(m2);

        CriarParticipacaoEmMissaoRequest dto1 =
                new CriarParticipacaoEmMissaoRequest(
                        m1.getId(),
                        aventureiroThorin.getId(),
                        PapelNaMissaoEnum.LIDER,
                        0,
                        false);
        CriarParticipacaoEmMissaoRequest dto2 = new CriarParticipacaoEmMissaoRequest(
                m2.getId(),
                aventureiroThorin.getId(),
                PapelNaMissaoEnum.LIDER,
                100000,
                true);

        participacaoService.criarParticipacaoEmMissao(dto1);
        m1.setStatus(StatusMissaoEnum.CONCLUIDA);
        participacaoService.criarParticipacaoEmMissao(dto2);

        entityManager.flush();
        entityManager.clear();

        AventureiroCompletoDTO thorin = aventureiroService.buscarAventureiroCompleto(aventureiroThorin.getId());

        assertNotNull(thorin);
        assertEquals("THORIN ESCUDO DE CARVALHO", thorin.nome());
        assertNull(thorin.companheiro());
        assertEquals(2, thorin.qtdTotalMissoes());
        assertEquals("RETOMAR EREBOR", thorin.ultimaMissao().titulo());
    }

    @Test
    @DisplayName("deve buscar aventueiro thorin completo sem possuir nenhuma missao")
    void deveBuscarThorinCompletoSemMissao(){
        Companheiro comp = new Companheiro();
        comp.setNome("MINI SMAUG");
        comp.setEspecie(EspecieEnum.DRAGAO_MINIATURA);
        comp.setIndiceLealdade(1);
        aventureiroThorin.setCompanheiro(comp);
        entityManager.persist(comp);

        entityManager.flush();
        entityManager.clear();

        AventureiroCompletoDTO thorin = aventureiroService.buscarAventureiroCompleto(aventureiroThorin.getId());

        assertNotNull(thorin);
        assertEquals("THORIN ESCUDO DE CARVALHO", thorin.nome());
        assertEquals("MINI SMAUG", thorin.companheiro().nome());
        assertEquals(0, thorin.qtdTotalMissoes());
        assertNull(thorin.ultimaMissao());
    }
}
