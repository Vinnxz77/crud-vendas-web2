# Como explicar o projeto

“Este projeto implementa o catálogo inicial de um sistema de vendas. Podemos cadastrar marcas, organizar categorias em vários níveis e cadastrar produtos com preço, estoque e imagem.”

## Demonstração sugerida
1. Cadastre uma marca e tente repetir seu nome para demonstrar a validação.
2. Crie uma categoria raiz, uma filha e uma neta. Mostre o caminho e a indentação do menu.
3. Cadastre um produto ligado à marca e à categoria. Selecione uma imagem, veja a prévia e salve.
4. Edite preço e estoque, filtre a tabela e mostre a confirmação de exclusão.
5. Tente excluir a marca ou categoria que o produto utiliza: a operação é bloqueada para preservar os vínculos.

## Responsabilidade das camadas
- Entidades: representam os dados e relacionamentos que viram tabelas no PostgreSQL.
- Repository: executa consultas JPQL e usa EntityManager para acessar o banco.
- Service: verifica regras, coordena transações e controla o armazenamento de arquivos.
- Controller: recebe ações das telas JSF e envia mensagens ao usuário.
- Converter: transforma o ID selecionado no menu no objeto Marca ou Categoria correspondente.

## Perguntas comuns
**Por que BigDecimal?** Para representar preços com precisão decimal; double pode introduzir diferenças de arredondamento binário.

**Como funciona a categoria recursiva?** Cada categoria pode ter outra categoria como pai. Sem pai, ela é uma raiz. O código sobe pelos pais para montar o caminho e impedir ciclos.

**O que é ManyToOne?** Muitos produtos podem usar a mesma marca ou categoria. Muitas categorias filhas podem compartilhar o mesmo pai.

**O que fica no banco da imagem?** O nome seguro do arquivo. Os bytes ficam fora do WAR, em uma pasta persistente. Uma servlet entrega a imagem ao navegador.

**O que é uma transação?** Um grupo de operações no banco que é confirmado quando termina com sucesso, ou revertido quando ocorre erro. Nesta aplicação as transações são RESOURCE_LOCAL, gerenciadas explicitamente; não são transações JTA do servidor.

**Por que bloquear exclusões?** Excluir um registro que outros utilizam deixaria referências inválidas. O banco protege as chaves estrangeiras e o serviço informa o problema com uma mensagem amigável.

**O que foi aproveitado da base?** Maven, Java 17, Faces, PrimeFaces, Weld, a identificação do projeto e os exemplos de aula. O catálogo foi acrescentado em br.edu.vendas, como pede o enunciado.
