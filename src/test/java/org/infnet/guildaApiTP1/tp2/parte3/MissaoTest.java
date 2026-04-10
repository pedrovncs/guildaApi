package org.infnet.guildaApiTP1.tp2.parte3;

import org.infnet.guildaApiTP1.audit.enums.StatusUsuarioEnum;
import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import jakarta.persistence.EntityManager;
import org.infnet.guildaApiTP1.aventura.dto.request.CriarParticipacaoEmMissaoRequest;
import org.infnet.guildaApiTP1.aventura.dto.MissaoCompletaDTO;
import org.infnet.guildaApiTP1.aventura.dto.MissaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.ParticipanteDTO;
import org.infnet.guildaApiTP1.audit.model.Organizacao;
import org.infnet.guildaApiTP1.audit.model.Usuario;
import org.infnet.guildaApiTP1.aventura.model.Aventureiro;
import org.infnet.guildaApiTP1.aventura.model.Missao;
import org.infnet.guildaApiTP1.aventura.service.MissaoService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import({ParticipacaoEmMissaoService.class, MissaoService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MissaoTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParticipacaoEmMissaoService participacaoEmMissaoService;

    @Autowired
    private MissaoService missaoService;

    Organizacao org;
    Usuario usuario;
    Missao missao;

    Aventureiro bilbo, thorin, gandalf, kili, bombur;

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

        Missao missao2 = new Missao();
        missao2.setTitulo("ENCONTRAR UM LADINO");
        missao2.setNivelDePerigo(NivelPerigoEnum.BAIXO);
        missao2.setStatus(StatusMissaoEnum.PLANEJADA);
        missao2.setDataInicio(ZonedDateTime.of(1937, 9, 21, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao2.setOrganizacao(org);
        entityManager.persist(missao2);

        missao = new Missao();
        missao.setTitulo("RETOMAR EREBOR");
        missao.setNivelDePerigo(NivelPerigoEnum.EXTREMO);
        missao.setStatus(StatusMissaoEnum.PLANEJADA);
        missao.setDataInicio(ZonedDateTime.of(1937, 9, 30, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao.setOrganizacao(org);
        entityManager.persist(missao);

        Missao missao3 = new Missao();
        missao3.setTitulo("DIVIDIR ESPOLIOS");
        missao3.setNivelDePerigo(NivelPerigoEnum.ALTO);
        missao3.setStatus(StatusMissaoEnum.CONCLUIDA);
        missao3.setDataInicio(ZonedDateTime.of(1938, 9, 21, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao3.setDataTermino(ZonedDateTime.of(1938, 9, 23, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao3.setOrganizacao(org);
        entityManager.persist(missao3);

        Missao missao4 = new Missao();
        missao4.setTitulo("RETORNAR AO CONDADO");
        missao4.setNivelDePerigo(NivelPerigoEnum.ALTO);
        missao4.setStatus(StatusMissaoEnum.CONCLUIDA);
        missao4.setDataInicio(ZonedDateTime.of(1938, 9, 23, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao4.setDataTermino(ZonedDateTime.of(1938, 9, 30, 0, 0, 0, 0, ZonedDateTime.now().getZone()));
        missao4.setOrganizacao(org);
        entityManager.persist(missao4);

        bilbo = criarAventureiro("BILBO", ClasseEnum.LADINO, 15, true);
        thorin = criarAventureiro("THORIN ESCUDO DE CARVALHO", ClasseEnum.GUERREIRO, 40, true);
        gandalf = criarAventureiro("GANDALF", ClasseEnum.MAGO, 70, true);
        kili = criarAventureiro("KILI", ClasseEnum.ARQUEIRO, 25, true);
        bombur = criarAventureiro("BOMBUR", ClasseEnum.GUERREIRO, 25, true);

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

    private void inserirEmMissao(Long missaoId, Long aventId, PapelNaMissaoEnum papel, Integer recompensa, Boolean mvp){
        CriarParticipacaoEmMissaoRequest dto = new CriarParticipacaoEmMissaoRequest(missaoId, aventId, papel, recompensa, mvp);
        participacaoEmMissaoService.criarParticipacaoEmMissao(dto);
    }

    @Test
    @DisplayName("deve buscar missoes: CONCLUIDAS, nivelPerigo ALTO, em set de 1938, ordenado por dataTermino")
    void deveBuscarMissoesComFiltros(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("dataTermino").descending());

        Page<MissaoDTO> busca = missaoService.buscarMissaoComFiltros(
                StatusMissaoEnum.CONCLUIDA,
                NivelPerigoEnum.ALTO,
                ZonedDateTime.of(1938, 9, 1, 0, 0, 0,
                        0, ZonedDateTime.now().getZone()),
                ZonedDateTime.of(1938, 9, 30, 0, 0, 0, 0,
                        ZonedDateTime.now().getZone()),
                pageable
        );

        assertNotNull(busca);
        assertEquals(2, busca.getTotalElements());
        assertEquals("RETORNAR AO CONDADO", busca.getContent().getFirst().titulo());
    }

    @Test
    @DisplayName("deve listar missoes sem filtros e ordenado por titulo")
    void deveListarMissoesSemFiltro(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("titulo").ascending());

        Page<MissaoDTO> busca = missaoService.buscarMissaoComFiltros(null, null, null, null, pageable);

        assertNotNull(busca);
        assertEquals(4, busca.getNumberOfElements());
        assertEquals("DIVIDIR ESPOLIOS", busca.getContent().getFirst().titulo());
    }

    @Test
    @DisplayName("deve exibir os dados completos da missao com participantes")
    void deveExibirMissaoCompletaComParticipantes(){
        inserirEmMissao(missao.getId(), bilbo.getId(), PapelNaMissaoEnum.EXPLORADOR, 10000, true);
        inserirEmMissao(missao.getId(), thorin.getId(), PapelNaMissaoEnum.LIDER, 1900000, true);
        inserirEmMissao(missao.getId(), gandalf.getId(), PapelNaMissaoEnum.SUPORTE, 0, true);
        inserirEmMissao(missao.getId(), kili.getId(), PapelNaMissaoEnum.ATAQUE, 10000, false);
        inserirEmMissao(missao.getId(), bombur.getId(), PapelNaMissaoEnum.DEFESA, 10000, false);

        entityManager.flush();
        entityManager.clear();

        MissaoCompletaDTO questErebor = missaoService.buscarMissaoCompleta(missao.getId());

        assertNotNull(questErebor);
        assertEquals(missao.getTitulo(), questErebor.titulo());
        assertEquals(5, questErebor.participantes().size());

        ParticipanteDTO thorinDTO = questErebor.participantes()
                .stream()
                .filter(p -> p.id().equals(thorin.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(thorinDTO);
        assertEquals(1900000, thorinDTO.recompensaEmOuro());
        assertEquals(true, thorinDTO.destaqueMvp());

    }

    @Test
    @DisplayName("deve exibir os dados completos da missao sem participantes")
    void deveExibirMissaoCompletaSemParticipantes(){
        MissaoCompletaDTO questErebor = missaoService.buscarMissaoCompleta(missao.getId());

        assertNotNull(questErebor);
        assertEquals(missao.getTitulo(), questErebor.titulo());
        assertEquals(missao.getStatus(), questErebor.status());
        assertEquals(missao.getNivelDePerigo(), questErebor.nivelPerigo());
        assertEquals(0, questErebor.participantes().size());
    }
}
