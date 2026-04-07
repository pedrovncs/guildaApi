package org.infnet.guildaApiTP1.tp2.parte1;

import jakarta.persistence.EntityManager;
import org.infnet.guildaApiTP1.enums.StatusUsuarioEnum;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.audit_schema.*;
import org.infnet.guildaApiTP1.model.audit_schema.keys.UserRoleId;
import org.infnet.guildaApiTP1.repository.RoleRepository;
import org.infnet.guildaApiTP1.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Parte1JpaTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EntityManager entityManager;

    private Organizacao org;
    private Usuario usuario;

    @BeforeEach
    void setupOrgUsuario(){
        org = new Organizacao();
        org.setNome("GUILDA TESTE ");
        org.setAtivo(true);
        entityManager.persist(org);

        usuario = new Usuario();
        usuario.setNome("PEDRO TESTE");
        usuario.setEmail("PEDRO_TESTE@ENAIL.COM");
        usuario.setSenhaHash("SENHASENHA123");
        usuario.setStatus(StatusUsuarioEnum.ATIVO);
        usuario.setOrganizacao(org);
        entityManager.persist(usuario);
    }

    @Test
    @DisplayName("deve salvar usuario com organizacao ja existente")
    void deveSalvarUsuarioComOrganizacaoExistente() {
        entityManager.flush();
        entityManager.clear();

        Usuario usuarioBuscado = entityManager.find(Usuario.class, usuario.getId());

        assertNotNull(usuarioBuscado);
        assertNotNull(usuarioBuscado.getOrganizacao());
        assertEquals(org.getId(), usuarioBuscado.getOrganizacao().getId());
        assertEquals("PEDRO TESTE", usuarioBuscado.getNome());
    }

    @Test
    @DisplayName("deve carregar usuario com roles")
    void deveCarregarUsuarioComRoles() {
        Role role = new Role();
        role.setNome("TESTER");
        role.setDescricao("TESTADOR DA ORGANIZACAO");
        role.setOrganizacao(org);

        entityManager.persist(role);

        UserRole userRole = new UserRole();
        userRole.setUserRoleId(new UserRoleId());
        userRole.setUsuario(usuario);
        userRole.setRole(role);
        userRole.setGrantedAt(ZonedDateTime.now());

        entityManager.persist(userRole);
        entityManager.flush();
        entityManager.clear();

        Usuario usuarioCarregado = usuarioRepository.findWithRolesById(usuario.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));

        assertNotNull(usuarioCarregado);
        assertNotNull(usuarioCarregado.getRoles());
        assertEquals(1, usuarioCarregado.getRoles().size());

        UserRole vinculo = usuarioCarregado.getRoles().iterator().next();
        assertNotNull(vinculo.getRole());
        assertEquals(role.getId(), vinculo.getRole().getId());
        assertEquals("PEDRO TESTE", usuarioCarregado.getNome());
    }

    @Test
    @DisplayName("deve carregar role com permissions")
    void deveCarregarRoleComPermissions() {
        Permission permissao1 = new Permission();
        permissao1.setCode("TESTAR SISTEMA 2");
        permissao1.setDescricao("Permite ler usuario");
        entityManager.persist(permissao1);

        Permission permissao2 = new Permission();
        permissao2.setCode("TESTER_TEST1");
        permissao2.setDescricao("TESTAR SISTEMA 1");
        entityManager.persist(permissao2);

        Role role = new Role();
        role.setNome("TESTER_TEST2");
        role.setDescricao("TESTAR SISTEMA 2");
        role.setOrganizacao(org);
        role.setPermissions(Set.of(permissao1, permissao2));
        entityManager.persist(role);

        entityManager.flush();
        entityManager.clear();

        Role roleCarregada = roleRepository.findWithPermissionsById(role.getId())
                .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));

        assertNotNull(roleCarregada);
        assertNotNull(roleCarregada.getPermissions());
        assertEquals(2, roleCarregada.getPermissions().size());
    }
}
