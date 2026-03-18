package org.infnet.guildaApiTP1.parte2;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.infnet.guildaApiTP1.enums.*;
import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;
import org.infnet.guildaApiTP1.model.audit_schema.Usuario;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Companheiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Missao;
import org.infnet.guildaApiTP1.model.aventura_schema.ParticipacaoEmMissao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Parte2Tests {

    @Autowired
    private EntityManager entityManager;

    private Organizacao org;
    private Usuario usuario;
    private Missao missao;
    private Aventureiro aventureiro;

    @BeforeEach
    void setup(){
        org = new Organizacao();
        org.setNome("GUILDA TESTE ");
        org.setAtivo(true);
        entityManager.persist(org);

        usuario = new Usuario();
        usuario.setNome("USUARIO TESTE");
        usuario.setEmail("USUARIO_TESTE@EMAIL.COM");
        usuario.setSenhaHash("123SENHA123");
        usuario.setStatus(StatusUsuarioEnum.ATIVO);
        usuario.setOrganizacao(org);
        entityManager.persist(usuario);

        aventureiro = new Aventureiro();
        aventureiro.setNome("AVENTUREIRO TESTE");
        aventureiro.setClasse(ClasseEnum.MAGO);
        aventureiro.setNivel(12);
        aventureiro.setAtivo(true);
        aventureiro.setOrganizacao(org);
        aventureiro.setUsuarioResponsavel(usuario);
        entityManager.persist(aventureiro);

        missao = new Missao();
        missao.setTitulo("TESTAR CÓDIGO");
        missao.setNivelDePerigo(NivelPerigoEnum.EXTREMO);
        missao.setStatus(StatusMissaoEnum.EM_ANDAMENTO);
        missao.setOrganizacao(org);

        entityManager.persist(missao);

        entityManager.flush();
    }

    @Test
    @DisplayName("deve adicionar um novo aventureiro com um companheiro")
    void deveAdicionarAventureiroComCompanheiro(){
        Companheiro companheiro = new Companheiro();
        companheiro.setNome("LOBO DE TESTE");
        companheiro.setEspecie(EspecieEnum.LOBO);
        companheiro.setIndiceLealdade(42);

        Aventureiro aventureiro = new Aventureiro();
        aventureiro.setNome("AVENTUREIRO COM COMPANHEIRO");
        aventureiro.setClasse(ClasseEnum.GUERREIRO);
        aventureiro.setNivel(11);
        aventureiro.setAtivo(true);
        aventureiro.setOrganizacao(org);
        aventureiro.setUsuarioResponsavel(usuario);
        aventureiro.setCompanheiro(companheiro);

        entityManager.persist(aventureiro);

        entityManager.flush();
        entityManager.clear();

        Aventureiro salvo = entityManager.find(Aventureiro.class, aventureiro.getId());

        assertNotNull(salvo);
        assertNotNull(salvo.getCompanheiro());
        assertEquals("LOBO DE TESTE", salvo.getCompanheiro().getNome());

    }

    @Test
    @DisplayName("deve adicionar uma nova missao com organizacao")
    void devePersistirMissaoAssociadaAOrganizacao() {
        Missao missao = new Missao();
        missao.setTitulo("TESTAR MISSAO");
        missao.setNivelDePerigo(NivelPerigoEnum.BAIXO);
        missao.setStatus(StatusMissaoEnum.PLANJEADA);
        missao.setOrganizacao(org);

        entityManager.persist(missao);
        entityManager.flush();
        entityManager.clear();

        Missao salva = entityManager.find(Missao.class, missao.getId());

        assertNotNull(salva);
        assertEquals("TESTAR MISSAO", salva.getTitulo());
        assertEquals(org.getId(), salva.getOrganizacao().getId());
    }

    @Test
    @DisplayName("deve adicionar uma nova participacao com missao e aventureiro associados")
    void devePersistirParticipacaoEmMissao() {
        ParticipacaoEmMissao participacao = new ParticipacaoEmMissao();
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapelNaMissao(PapelNaMissaoEnum.LIDER);
        participacao.setRecompensaEmOuro(100);
        participacao.setDestaqueMvp(true);

        entityManager.persist(participacao);

        entityManager.flush();
        entityManager.clear();

        ParticipacaoEmMissao salva = entityManager.find(ParticipacaoEmMissao.class, participacao.getId());

        assertNotNull(salva);
        assertEquals(missao.getId(), salva.getMissao().getId());
        assertEquals(aventureiro.getId(), salva.getAventureiro().getId());
    }

    @Test
    @DisplayName("deve falhar ao tentar adicionar uma participacao duplicada para o mesmo aventureiro e missao")
    void deveFalharComParticipacaoDuplicada(){
        ParticipacaoEmMissao p = new ParticipacaoEmMissao();
        p.setMissao(missao);
        p.setAventureiro(aventureiro);
        p.setPapelNaMissao(PapelNaMissaoEnum.SUPORTE);
        p.setRecompensaEmOuro(50);
        p.setDestaqueMvp(false);
        entityManager.persist(p);

        ParticipacaoEmMissao pRepetida = new ParticipacaoEmMissao();
        pRepetida.setMissao(missao);
        pRepetida.setAventureiro(aventureiro);
        pRepetida.setPapelNaMissao(PapelNaMissaoEnum.SUPORTE);
        pRepetida.setRecompensaEmOuro(70);
        pRepetida.setDestaqueMvp(false);

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(pRepetida);
            entityManager.flush();
        });
    }
}
