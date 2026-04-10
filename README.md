```text
                  d8b 888      888                    d8888 8888888b. 8888888
                  Y8P 888      888                   d88888 888   Y88b  888
                      888      888                  d88P888 888    888  888
 .d88b.  888  888 888 888  .d88888  8888b.         d88P 888 888   d88P  888
d88P"88b 888  888 888 888 d88" 888     "88b       d88P  888 8888888P"   888
888  888 888  888 888 888 888  888 .d888888      d88P   888 888         888
Y88b 888 Y88b 888 888 888 Y88b 888 888  888     d8888888888 888         888
 "Y88888  "Y88888 888 888  "Y88888 "Y888888    d88P     888 888       8888888
     888
Y8b d88P
 "Y88P"
```
# Guilda API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=elasticsearch&logoColor=white)



API REST desenvolvida com Spring Boot para a disciplina Desenvolvimento de Serviços com Spring Boot do Bloco Engenharia de Softwares Escaláveis.

## Branches

- `main`: versão final (Assessment)
- `tp1`: código entregue no Teste de Performance 1
- `tp2`: código entregue no Teste de Performance 2
- `tp3-sem-cache`: código apenas com adição da questão 1 do Teste de Performance 3
- `tp3-com-cache`: código entregue no Teste de Performance 3 (completo)

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Redis
- Elasticsearch

## Funcionalidades principais

- Cadastro e consulta de usuários e papéis de acesso
- Cadastro, atualização e consulta de aventureiros
- Vinculação e remoção de companheiros
- Consulta de painel tático com missões dos últimos 15 dias
- Buscas textuais e agregações de produtos no Elasticsearch
- Cache com Redis para consultas do painel tático

## Estrutura do projeto

- `audit`: usuários, roles, permissões e organizações (schema audit)
- `aventura`: aventureiros, companheiros, missões e participações (schema aventura)
- `loja`: busca e agregações de produtos (`elasticsearch index guilda_loja`)
- `operacoes`: painel tático de missões (schema operacoes)
- `common`: tratamento de exceções, cache, scheduler e utilitários

## Pré-requisitos

- Java 21
- Docker e Docker Compose
- Maven Wrapper do projeto (`mvnw` ou `mvnw.cmd`)

## Como executar

1. Subir os serviços:

```bash
docker compose up -d
```

Alterar senha no container do PostgreSQL: usuário `postgres` para `password123`:

```sql
ALTER USER postgres WITH PASSWORD 'password123';
```

2. Iniciar a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A API sobe na porta padrão do Spring Boot: `http://localhost:8080`.

## Serviços utilizados

- PostgreSQL: `localhost:5432`
- Redis: `localhost:6379`
- Elasticsearch: `localhost:9200`

Toadas as configurações de conexão estão em: `src/main/resources/application.yaml`.

## Endpoints

### Audit

- `GET /usuarios/{id}`
- `POST /usuarios`
- `GET /roles/{id}`

### Aventura

- `POST /aventureiros`
- `PUT /aventureiros/{id}`
- `GET /aventureiros`
- `GET /aventureiros/{id}`
- `POST /aventureiros/{id}/desvincular`
- `POST /aventureiros/{id}/revincular`
- `PUT /aventureiros/{id}/companheiro`
- `DELETE /aventureiros/{id}/companheiro`

Filtros disponíveis em `GET /aventureiros`:

- `ativo`
- `classe`
- `nivelMinimo`
- `page`
- `size`

### Operações

- `GET /missoes/top15dias`

### Loja

#### Parte A - Buscas textuais

- Busca por nome do produto: `GET /produtos/busca/nome?termo=espada`
- Busca por descrição do produto: `GET /produtos/busca/descricao?termo=cura`
- Busca por frase exata: `GET /produtos/busca/frase?termo=cura superior`
- Busca fuzzy: `GET /produtos/busca/fuzzy?termo=espdaa`
- Busca em múltiplos campos: `GET /produtos/busca/multicampos?termo=dragao`

#### Parte B - Buscas com filtros

- Busca textual com filtro por categoria: `GET /produtos/busca/com-filtro?termo=pocao&categoria=pocoes`
- Busca por faixa de preço: `GET /produtos/busca/faixa-preco?min=50&max=300`
- Busca combinada por categoria, raridade e faixa de preço: `GET /produtos/busca/avancada?categoria=armas&raridade=raro&min=200&max=1000`

#### Parte C - Agregações

- Quantidade de produtos por categoria: `GET /produtos/agregacoes/por-categoria`
- Quantidade de produtos por raridade: `GET /produtos/agregacoes/por-raridade`
- Preço médio dos produtos: `GET /produtos/agregacoes/preco-medio`
- Faixas de preço: `GET /produtos/agregacoes/faixas-preco`

## Exemplo JSON Usuário POST

Criar usuário:

```http
POST /usuarios
Content-Type: application/json

{
  "nome": "pedro usuario",
  "email": "pedro@email.com",
  "senha": "senhasenha123",
  "organizacaoId": "2"
}
```

## Testes

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

## Observações

- O projeto usa cache Redis para o painel tático de missões.
- O cache renova todo dia às 3:33 através de um scheduler + TTL de 24 horas para garantir a limpeza em caso de falha no `evictCache()`.
- O ddl-auto está configurado com ``validate`` para não modificar a estrutura dos bancos já existente.
- O arquivo ``schema.sql`` é executado para criar as tabelas do schema aventura na imagem docker seguindo o que foi desenvolvido no Teste de Performanc 2.