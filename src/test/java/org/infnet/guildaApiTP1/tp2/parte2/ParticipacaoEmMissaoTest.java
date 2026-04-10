package org.infnet.guildaApiTP1.tp2.parte2;

import org.infnet.guildaApiTP1.audit.enums.StatusUsuarioEnum;
import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import jakarta.persistence.EntityManager;
import org.infnet.guildaApiTP1.aventura.dto.request.CriarParticipacaoEmMissaoRequest;
import org.infnet.guildaApiTP1.common.exceptions.RegraDeMissaoException;
import org.infnet.guildaApiTP1.audit.model.Organizacao;
import org.infnet.guildaApiTP1.audit.model.Usuario;
import org.infnet.guildaApiTP1.aventura.model.Aventureiro;
import org.infnet.guildaApiTP1.aventura.model.Missao;
import org.infnet.guildaApiTP1.aventura.model.ParticipacaoEmMissao;
import org.infnet.guildaApiTP1.aventura.service.ParticipacaoEmMissaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ParticipacaoEmMissaoService.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParticipacaoEmMissaoTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParticipacaoEmMissaoService service;

    private Organizacao orgPadrao;
    private Usuario usuarioPadrao;
    private Missao missaoPadrao;
    private Aventureiro aventureiroPadrao;

    @BeforeEach
    void setup(){
        orgPadrao = new Organizacao();
        orgPadrao.setNome("ORGANIZACAO TESTE");
        orgPadrao.setAtivo(true);
        entityManager.persist(orgPadrao);

        usuarioPadrao = new Usuario();
        usuarioPadrao.setNome("USUARIO TESTE");
        usuarioPadrao.setEmail("TESTE@EMAIL.COM");
        usuarioPadrao.setSenhaHash("senhasenha123");
        usuarioPadrao.setStatus(StatusUsuarioEnum.ATIVO);
        usuarioPadrao.setOrganizacao(orgPadrao);
        entityManager.persist(usuarioPadrao);

        missaoPadrao = new Missao();
        missaoPadrao.setTitulo("MISAO TESTE");
        missaoPadrao.setNivelDePerigo(NivelPerigoEnum.MEDIO);
        missaoPadrao.setStatus(StatusMissaoEnum.PLANEJADA);
        missaoPadrao.setOrganizacao(orgPadrao);
        entityManager.persist(missaoPadrao);

        aventureiroPadrao = new Aventureiro();
        aventureiroPadrao.setNome("AVENTUREIRO TESTE");
        aventureiroPadrao.setClasse(ClasseEnum.LADINO);
        aventureiroPadrao.setNivel(44);
        aventureiroPadrao.setAtivo(true);
        aventureiroPadrao.setOrganizacao(orgPadrao);
        aventureiroPadrao.setUsuarioResponsavel(usuarioPadrao);
        entityManager.persist(aventureiroPadrao);

        entityManager.flush();
    }

    @Test
    @DisplayName(" deve falhar ao tentar criar uma participacao com aventureiro de uma organizacao diferente da missao")
    void deveFalharComAventureiroDeOutraOrg() {
        Organizacao outraOrg = new Organizacao();
        outraOrg.setNome("OUTRA ORGANIZACAO TESTE");
        outraOrg.setAtivo(true);
        entityManager.persist(outraOrg);

        Aventureiro aventureiro = new Aventureiro();
        aventureiroPadrao.setNome("AVENTUREIRO DE OUTRA ORG");
        aventureiroPadrao.setClasse(ClasseEnum.LADINO);
        aventureiroPadrao.setNivel(65);
        aventureiroPadrao.setAtivo(true);
        aventureiroPadrao.setOrganizacao(outraOrg);
        aventureiroPadrao.setUsuarioResponsavel(usuarioPadrao);

        entityManager.persist(aventureiroPadrao);

        entityManager.flush();

        CriarParticipacaoEmMissaoRequest dto = new CriarParticipacaoEmMissaoRequest(
                missaoPadrao.getId(),
                aventureiroPadrao.getId(),
                PapelNaMissaoEnum.LIDER,
                777,
                true
        );
        assertThrows(RegraDeMissaoException.class, () -> service.criarParticipacaoEmMissao(dto));
    }

    @Test
    @DisplayName("deve falhar ao tentar criar uma participacao com aventureiro com ativo = false")
    void deveFalharComAventureiroInativo() {
        aventureiroPadrao.setAtivo(false);
        entityManager.merge(aventureiroPadrao);
        entityManager.flush();

        CriarParticipacaoEmMissaoRequest dto = new CriarParticipacaoEmMissaoRequest(
                missaoPadrao.getId(),
                aventureiroPadrao.getId(),
                PapelNaMissaoEnum.LIDER,
                10,
                false
        );

        assertThrows(RegraDeMissaoException.class, () -> {
            service.criarParticipacaoEmMissao(dto);
        });
    }

    @Test
    @DisplayName("deve falhar com tentar criar uma participacao em uma missao que não está aceitando novos participantes")
    void deveFalharComMissaoQueNaoAceitaNovosParticipantes() {
        missaoPadrao.setStatus(StatusMissaoEnum.CONCLUIDA);
        entityManager.merge(missaoPadrao);
        entityManager.flush();

        CriarParticipacaoEmMissaoRequest dto = new CriarParticipacaoEmMissaoRequest(
                missaoPadrao.getId(),
                aventureiroPadrao.getId(),
                PapelNaMissaoEnum.LIDER,
                1000,
                false
        );

        assertThrows(RegraDeMissaoException.class, () -> {
            service.criarParticipacaoEmMissao(dto);
        });
    }

    @Test
    @DisplayName("deve criar participacao em missao com dados validos")
    void deveCriarParticipacaoEmMissaoComDadosValidos() {

        CriarParticipacaoEmMissaoRequest dto = new CriarParticipacaoEmMissaoRequest(
                missaoPadrao.getId(),
                aventureiroPadrao.getId(),
                PapelNaMissaoEnum.LIDER,
                999,
                true
        );

        ParticipacaoEmMissao participacaoCriada = service.criarParticipacaoEmMissao(dto);

        entityManager.flush();
        entityManager.clear();

        ParticipacaoEmMissao participacao = entityManager.find(ParticipacaoEmMissao.class, participacaoCriada.getId());

        assertNotNull(participacao.getId());
        assertEquals(missaoPadrao.getId(), participacao.getMissao().getId());
        assertEquals(aventureiroPadrao.getId(), participacao.getAventureiro().getId());
    }
}
