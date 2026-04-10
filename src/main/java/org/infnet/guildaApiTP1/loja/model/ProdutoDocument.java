package org.infnet.guildaApiTP1.loja.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "guilda_loja")
@Getter@Setter
public class ProdutoDocument {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String categoria;

    @Field(type = FieldType.Text, analyzer = "portuguese_custom")
    private String descricao;

    @Field(type = FieldType.Text, analyzer = "portuguese_custom")
    private String nome;

    @Field(type = FieldType.Float)
    private Float preco;

    @Field(type = FieldType.Keyword)
    private String raridade;
}
