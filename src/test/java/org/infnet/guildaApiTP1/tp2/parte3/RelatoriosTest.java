package org.infnet.guildaApiTP1.tp2.parte3;

import org.infnet.guildaApiTP1.audit.enums.StatusUsuarioEnum;
import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import jakarta.persistence.EntityManager;
import org.infnet.guildaApiTP1.aventura.dto.request.CriarParticipacaoEmMissaoRequest;
import org.infnet.guildaApiTP1.aventura.dto.RelatorioMissaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.RelatorioParticipacaoDTO;
import org.infnet.guildaApiTP1.audit.model.Organizacao;
import org.infnet.guildaApiTP1.audit.model.Usuario;
import org.infnet.guildaApiTP1.aventura.model.Aventureiro;
import org.infnet.guildaApiTP1.aventura.model.Missao;
import org.infnet.guildaApiTP1.aventura.service.ParticipacaoEmMissaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ParticipacaoEmMissaoService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RelatoriosTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParticipacaoEmMissaoService participacaoService;

    Usuario usuario;
    Organizacao org;
    Aventureiro bilbo, thorin, gandalf, kili, bombur;
    Missao missao1, missao2, missao3, missao4;

    @BeforeEach
    void setup() {
        org = new Organizacao();
        org.setNome("ORG TESTE ");
        org.setAtivo(true);
        entityManager.persist(org);

        usuario = new Usuario();
        usuario.setNome("USUARIO TESTE");
        usuario.setEmail("TESTE@MEAIL.COM");
        usuario.setSenhaHash("senhasenh1234");
        usuario.setStatus(StatusUsuarioEnum.ATIVO);
        usuario.setOrganizacao(org);
        entityManager.persist(usuario);

        missao1 = new Missao();
        missao1.setTitulo("ENCONTRAR UM LADINO");
        missao1.setNivelDePerigo(NivelPerigoEnum.BAIXO);
        missao1.setStatus(StatusMissaoEnum.PLANEJADA);
        missao1.setDataInicio(ZonedDateTime.of(1937, 9, 21, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao1.setOrganizacao(org);
        entityManager.persist(missao1);

        missao2 = new Missao();
        missao2.setTitulo("MATAR SMAUG EM EREBOR");
        missao2.setNivelDePerigo(NivelPerigoEnum.EXTREMO);
        missao2.setStatus(StatusMissaoEnum.PLANEJADA);
        missao2.setDataInicio(ZonedDateTime.of(1937, 9, 30, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao2.setOrganizacao(org);
        entityManager.persist(missao2);

        missao3 = new Missao();
        missao3.setTitulo("DIVIDIR ESPOLIOS");
        missao3.setNivelDePerigo(NivelPerigoEnum.ALTO);
        missao3.setStatus(StatusMissaoEnum.PLANEJADA);
        missao3.setDataInicio(ZonedDateTime.of(1938, 9, 21, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao3.setDataTermino(ZonedDateTime.of(1938, 9, 23, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao3.setOrganizacao(org);
        entityManager.persist(missao3);

        missao4 = new Missao();
        missao4.setTitulo("RETORNAR AO CONDADO");
        missao4.setNivelDePerigo(NivelPerigoEnum.ALTO);
        missao4.setStatus(StatusMissaoEnum.PLANEJADA);
        missao4.setDataInicio(ZonedDateTime.of(1938, 9, 23, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao4.setDataTermino(ZonedDateTime.of(1938, 9, 30, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao4.setOrganizacao(org);
        entityManager.persist(missao4);

        bilbo = criarAventureiro("BILBO", ClasseEnum.LADINO, 15);
        inserirEmMissao(missao2.getId(), bilbo.getId(), PapelNaMissaoEnum.EXPLORADOR, 100, true);
        inserirEmMissao(missao3.getId(), bilbo.getId(), PapelNaMissaoEnum.EXPLORADOR, 10000, false);
        inserirEmMissao(missao4.getId(), bilbo.getId(), PapelNaMissaoEnum.LIDER, 1000, false);

        thorin = criarAventureiro("THORIN ESCUDO DE CARVALHO", ClasseEnum.GUERREIRO, 40);
        inserirEmMissao(missao1.getId(), thorin.getId(), PapelNaMissaoEnum.LIDER, 100, false);
        inserirEmMissao(missao2.getId(), thorin.getId(), PapelNaMissaoEnum.LIDER, 1000, true);
        inserirEmMissao(missao3.getId(), thorin.getId(), PapelNaMissaoEnum.LIDER, 1300000, true);

        gandalf = criarAventureiro("GANDALF", ClasseEnum.MAGO, 70);
        inserirEmMissao(missao1.getId(), gandalf.getId(), PapelNaMissaoEnum.SUPORTE, 1000, true);
        inserirEmMissao(missao2.getId(), gandalf.getId(), PapelNaMissaoEnum.SUPORTE, 100, false);
        inserirEmMissao(missao4.getId(), gandalf.getId(), PapelNaMissaoEnum.SUPORTE, 1000, true);

        kili = criarAventureiro("KILI", ClasseEnum.ARQUEIRO, 25);
        inserirEmMissao(missao2.getId(), kili.getId(), PapelNaMissaoEnum.ATAQUE, 1000, false);

        bombur = criarAventureiro("BOMBUR", ClasseEnum.GUERREIRO, 25);
        inserirEmMissao(missao2.getId(), bombur.getId(), PapelNaMissaoEnum.ATAQUE, 1000, false);

        entityManager.flush();
        entityManager.clear();
    }

    private Aventureiro criarAventureiro(String nome, ClasseEnum classe, Integer nivel) {
        Aventureiro a = new Aventureiro();
        a.setNome(nome);
        a.setClasse(classe);
        a.setNivel(nivel);
        a.setAtivo(true);
        a.setOrganizacao(org);
        a.setUsuarioResponsavel(usuario);
        entityManager.persist(a);

        return a;
    }

    private void inserirEmMissao(Long missaoId, Long aventId, PapelNaMissaoEnum papel, Integer recompensa, Boolean mvp) {
        CriarParticipacaoEmMissaoRequest dto = new CriarParticipacaoEmMissaoRequest(missaoId, aventId, papel, recompensa, mvp);
        participacaoService.criarParticipacaoEmMissao(dto);
    }

    @Test
    @DisplayName("deve gerar o ranking de aventureiros apenas am missoes concluidas")
    void deveGerarRankingApenasDeMissoesConcluidas() {
        missao1.setStatus(StatusMissaoEnum.CONCLUIDA);
        missao3.setStatus(StatusMissaoEnum.CONCLUIDA);
        missao4.setStatus(StatusMissaoEnum.CONCLUIDA);

        entityManager.merge(missao1);
        entityManager.merge(missao2);
        entityManager.merge(missao3);

        entityManager.flush();
        entityManager.clear();

        Page<RelatorioParticipacaoDTO> ranking = participacaoService.gerarRankingAventureiros(
                StatusMissaoEnum.CONCLUIDA,
                null,
                null);

        assertNotNull(ranking);
        assertEquals(3, ranking.getTotalElements());

        RelatorioParticipacaoDTO thorinRelatorio = ranking.getContent().getFirst();
        assertEquals("THORIN ESCUDO DE CARVALHO", thorinRelatorio.nome());
        assertEquals(1300100, thorinRelatorio.somaRecompensas());
        assertEquals(1, thorinRelatorio.totalDestaquesMvp());
    }

    @Test
    @DisplayName("deve gerar o ranking de aventuros apenas em missoes no ano 1938")
    void deveGerarRankingApenasEmMissaoNoAno1938() {
        Page<RelatorioParticipacaoDTO> ranking1938 = participacaoService.gerarRankingAventureiros(
                null,
                ZonedDateTime.of(1938, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().getZone()),
                ZonedDateTime.of(1938, 12, 31, 23, 59, 59, 999999999, ZonedDateTime.now().getZone())
        );

        assertNotNull(ranking1938);
        assertEquals(3, ranking1938.getTotalElements());

        assertDoesNotThrow(() -> ranking1938.getContent().stream()
                .filter(r -> r.nome().equals("THORIN ESCUDO DE CARVALHO"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Thorin não encontrado no ranking de 1938")));

        assertDoesNotThrow(() -> ranking1938.getContent().stream()
                .filter(r -> r.nome().equals("BILBO"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Bibo não encontrado no ranking de 1938")));

        assertDoesNotThrow(() -> ranking1938.getContent().stream()
                .filter(r -> r.nome().equals("GANDALF"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Gandalf não encontrado no ranking de 1938")));
    }

    @Test
    @DisplayName("deve gerar relatorios de missoes sem filtro de intervalo")
    void deveGerarRelatoriosDeMissaoSemFiltroIntervalo() {

        Page<RelatorioMissaoDTO> relatorios = participacaoService.gerarRelatorioMissao(
                null,
                null,
                PageRequest.of(0, 10));

        assertNotNull(relatorios);
        assertEquals(4, relatorios.getTotalElements());

        RelatorioMissaoDTO dividirEspoliosDTO = relatorios.getContent()
                .stream()
                .filter(r -> r.titulo().equals("DIVIDIR ESPOLIOS"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("missao DIVIDIR ESPOLIOS não encontrada"));

        assertEquals("DIVIDIR ESPOLIOS", dividirEspoliosDTO.titulo());
        assertEquals(2, dividirEspoliosDTO.totalParticipantes());
        assertEquals(1310000, dividirEspoliosDTO.totalRecompensas());
    }

    @Test
    @DisplayName("deve gerar relatorios de missoes sem participantes")
    void deveGerarRelatoriosDeMissaoSemParticipantes() {
        Missao missao5 = new Missao();
        missao5.setTitulo("FAZER ANEIS DE FUMAÇA");
        missao5.setNivelDePerigo(NivelPerigoEnum.BAIXO);
        missao5.setStatus(StatusMissaoEnum.EM_ANDAMENTO);
        missao5.setDataInicio(ZonedDateTime.of(1939, 1, 12, 14, 30, 0, 0, ZonedDateTime.now().getZone()));
        missao5.setOrganizacao(org);
        entityManager.persist(missao5);

        entityManager.flush();
        entityManager.clear();

        Page<RelatorioMissaoDTO> relatorios = participacaoService.gerarRelatorioMissao(
                ZonedDateTime.of(1939, 1, 11, 14, 30, 0, 0, ZonedDateTime.now().getZone()),
                ZonedDateTime.of(1939, 1, 12, 14, 31, 0, 0, ZonedDateTime.now().getZone()),
                PageRequest.of(0, 10));

        assertNotNull(relatorios);
        assertEquals(1, relatorios.getTotalElements());
        assertEquals("FAZER ANEIS DE FUMAÇA", relatorios.getContent().getFirst().titulo());
        assertEquals(0, relatorios.getContent().getFirst().totalParticipantes());
    }
}
