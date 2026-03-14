package org.infnet.guildaApiTP1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.UsuarioComRolesDTO;
import org.infnet.guildaApiTP1.dto.CriarUsuarioRequest;
import org.infnet.guildaApiTP1.dto.UsuarioDTO;
import org.infnet.guildaApiTP1.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioComRolesDTO> buscar(@PathVariable Long id) {
        UsuarioComRolesDTO usuario = usuarioService.buscarPorIdComRoles(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> inserir(@Valid @RequestBody CriarUsuarioRequest dto) {
        UsuarioDTO usuarioCriado = usuarioService.criar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCriado);

    }
}
