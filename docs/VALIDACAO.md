# Validação realizada em 06/10/2026

## Build e testes de unidade
Com Java 17 e Maven 3.9.9, `mvn clean verify` compilou o projeto, passou os testes e gerou `target/vendas.war`. Depois dos ajustes, `mvn verify` passou novamente com a versão final.

Seis testes JUnit passaram: caminho de três níveis; rejeição de pai próprio/descendente; detecção de árvore corrompida; rejeição de conteúdo/extensão disfarçados; limites de tamanho/arquivo vazio; bloqueio de travessia de diretórios.

## Servidor e interface
O WAR foi instalado no Apache Tomcat 11.0.2 com Faces 4.1.14, Weld 6.0.4, PrimeFaces 15.0.0, Hibernate ORM 7.0.2 e Hibernate Validator 9.0.1. Início, marcas, categorias e produtos responderam HTTP 200.

Os testes de navegador passaram nas operações registradas em `RESULTADOS-UI.txt`: cadastro, edição e exclusão de marcas; duplicidade normalizada; bloqueio de marca vinculada; cadastro de raiz/filha e caminho; seleção de pai filtrando descendentes; bloqueio de exclusão com filhos; mudança do pai; exclusão de categorias sem vínculos; edição do produto; upload PNG com prévia; entrega de miniatura com HTTP 200 e Content-Type image/png; cadastro e exclusão de produto.

As capturas em `docs/imagens` foram produzidas na aplicação real. A captura móvel usa uma viewport 412 × 915; não equivale a um teste em um aparelho Moto G42 físico.

## Banco usado para esta verificação
O ambiente de preparação não permitiu executar o processo PostgreSQL nativo com usuário próprio. Por isso os testes de integração usaram **PGlite 0.3.2 (PostgreSQL 17.4 em WebAssembly)** com pglite-socket 0.0.7, driver JDBC PostgreSQL e o mesmo DDL/DML. O Hibernate validou o esquema com sucesso. Nesse teste o pool tinha uma conexão e SSL estava desativado.

PGlite não reproduz todas as características de concorrência e instalação do servidor nativo. **Docker Compose, PostgreSQL 16 nativo, concorrência real, reinício de volumes e todos os formatos de imagem não foram verificados neste ambiente.** O upload real testado foi PNG; JPG e WEBP possuem validação por assinatura no código, e ainda devem ser testados com arquivos reais. Antes da entrega, execute `docker compose up --build` ou o roteiro sem Docker e os casos de `TESTES-MANUAIS.md` na sua máquina.

## Publicação
Os commits são locais. O repositório ainda precisa ser publicado/autorizado na conta do integrante e seu link enviado no AVA.
