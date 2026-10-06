INSERT INTO tb_marca (nome,descricao,ativo) VALUES ('Hinode','Perfumes e cosméticos',true),('Paris Elysées','Perfumaria',true),('Marca antiga','Exemplo de marca inativa',false);
INSERT INTO tb_categoria(nome,categoria_id) VALUES ('Perfumaria',NULL);
INSERT INTO tb_categoria(nome,categoria_id) SELECT 'Masculinos',id FROM tb_categoria WHERE nome='Perfumaria';
INSERT INTO tb_categoria(nome,categoria_id) SELECT 'Femininos',id FROM tb_categoria WHERE nome='Perfumaria';
INSERT INTO tb_categoria(nome,categoria_id) SELECT 'Amadeirados',id FROM tb_categoria WHERE nome='Masculinos';
INSERT INTO tb_produto(nome,descricao,preco,quantidade_estoque,marca_id,categoria_id) SELECT 'Perfume de demonstração','Dados fictícios para testar o CRUD.',169.90,12,m.id,c.id FROM tb_marca m CROSS JOIN tb_categoria c WHERE m.nome='Hinode' AND c.nome='Amadeirados';
