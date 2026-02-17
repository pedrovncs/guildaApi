package org.infnet.guildaApiTP1.repository;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.enums.EspecieEnum;
import org.infnet.guildaApiTP1.model.Aventureiro;
import org.infnet.guildaApiTP1.model.Companheiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AventureiroRepository {
    private final List<Aventureiro> registro = new ArrayList<>();
    private final Faker faker = new Faker();

    @PostConstruct
    public void initDB() {
        ClasseEnum[] classes = ClasseEnum.values();
        EspecieEnum[] especies = EspecieEnum.values();

        for (int i = 0; i < 100; i++) {
            ClasseEnum classeAleatoria = classes[faker.random().nextInt(classes.length)];

            Aventureiro aventureiro = new Aventureiro(
                    faker.name().fullName(),
                    classeAleatoria,
                    faker.number().numberBetween(1, 100)
            );

            if (faker.random().nextBoolean()) {
                EspecieEnum especieAleatoria = especies[faker.random().nextInt(especies.length)];
                Companheiro companheiro = new Companheiro();
                companheiro.setNome(faker.funnyName().name());
                companheiro.setEspecie(especieAleatoria);
                companheiro.setLealdade(faker.number().numberBetween(0, 100));

                aventureiro.setCompanheiro(companheiro);
            }

            registro.add(aventureiro);
        }
    }

    public void salvar(Aventureiro aventureiro) {
        registro.add(aventureiro);
    }

    public List<Aventureiro> buscarTodos() {
        return List.copyOf(registro);
    }

    public Optional<Aventureiro> buscarPorId(Long id) {
        return registro.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }
}
