package org.infnet.guildaApiTP1.service;

import org.infnet.guildaApiTP1.dto.AventureiroDTO;
import org.infnet.guildaApiTP1.dto.BuscaResponse;
import org.infnet.guildaApiTP1.dto.CompanheiroDTO;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Companheiro;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AventureiroService {

    private final AventureiroRepository repository;

    public AventureiroService(AventureiroRepository repository) {
        this.repository = repository;
    }

    public Aventureiro registrar(AventureiroDTO aventDTO) {
        Aventureiro aventureiro = new Aventureiro(
                aventDTO.nome(),
                aventDTO.classe(),
                aventDTO.nivel()
        );
        repository.salvar(aventureiro);
        return aventureiro;
    }

    public BuscaResponse<Aventureiro> buscarTodos(ClasseEnum classe, Boolean ativo, Integer nivelMinimo, int pagina, int tamanho){
        List<Aventureiro> listaFiltrada = repository.buscarTodos().stream()
                .filter(a -> classe == null || a.getClasse() == classe)
                .filter(a -> ativo == null || a.isAtivo() == ativo)
                .filter(a -> nivelMinimo == null || a.getNivel() >= nivelMinimo)
                .sorted(Comparator.comparing(Aventureiro::getId))
                .toList();

        int totalItens = listaFiltrada.size();
        int totalPaginas = (int) Math.ceil((double) totalItens / tamanho);
        int inicio = pagina *  tamanho;

        List<Aventureiro> conteudodaBusca = listaFiltrada.stream()
                .skip(inicio)
                .limit(tamanho)
                .toList();
        return new BuscaResponse<Aventureiro>(conteudodaBusca, totalItens, totalPaginas, inicio);
    }

    public Aventureiro buscarPorId(Long id){
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não foi encontrado com esse id!"));
    }

    public Aventureiro atualizar(Long id, AventureiroDTO aventDTO){
        Aventureiro aventureiro = buscarPorId(id);

        aventureiro.setNome(aventDTO.nome());
        aventureiro.setClasse(aventDTO.classe());
        aventureiro.setNivel(aventDTO.nivel());

        repository.salvar(aventureiro);

        return aventureiro;
    }

    public void atualizarStatus(Long id, Boolean ativo){
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.setAtivo(ativo);
        repository.salvar(aventureiro);
    }

    public void definirCompanheiro(Long aventureiroId, CompanheiroDTO compDTO){
        Aventureiro aventureiro = buscarPorId(aventureiroId);

        Companheiro companheiro = new Companheiro(
                compDTO.nome(),
                compDTO.especie(),
                compDTO.lealdade()
        );

        aventureiro.setCompanheiro(companheiro);
        repository.salvar(aventureiro);
    }

    public void removerCompanheiro(Long id){
        Aventureiro aventureiro = buscarPorId(id);
        aventureiro.setCompanheiro(null);
        repository.salvar(aventureiro);
    }
}

