package org.infnet.guildaApiTP1.audit.controller;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.audit.dto.RoleComPermissionsDTO;
import org.infnet.guildaApiTP1.audit.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService service;

    @GetMapping("/{id}")
    public ResponseEntity<RoleComPermissionsDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorIdComPermissions(id));
    }
}