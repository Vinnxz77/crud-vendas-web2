# Alterações em relação à base do professor

- Preservados groupId, artifactId, Java 17, PrimeFaces 15.0.0, Faces 4.1.14, Weld 6.0.4 e arquivos de exemplos em br.edu.iftm.ppw2.aula1.
- Preservados a imagem institucional, recursos e template original; o template original recebeu links do catálogo. O módulo de vendas usa seu próprio template para formulários responsivos.
- Mantido o nome da persistence-unit `my_persistence_unit`; preenchido com Hibernate RESOURCE_LOCAL, entidades e validação de esquema. A base continha unidade vazia e nenhuma dependência Hibernate.
- Acrescentados Hibernate ORM 7.0.2, Hibernate Validator 9.0.1 e produtor CDI de Validator para execução no Tomcat.
- Jakarta EE API passou de milestone 11.0.0-M1 para 11.0.0. Lombok passou de 1.18.48 para 1.18.36 e PostgreSQL JDBC de 42.7.13 para 42.7.4, versões fixadas para reprodução. Não foi necessário modificar as entidades de exemplo com Lombok.
- web.xml recebeu configuração multipart, upload nativo e validação de cliente PrimeFaces 15. O Bean Validation também roda no serviço, independentemente do cliente.
- HelloServlet deixou de capturar todas as URLs *.png e passou a /exemplo-imagem-aula. O exemplo original lê uma imagem da máquina do professor; ele não é usado no módulo de vendas. A servlet nova /imagens/* lê uploads armazenados pelo projeto.
- O CRUD de usuários original é apenas exemplo de aula: não foi incluído no escopo da atividade e ainda utiliza seu banco JDBC original. A navegação inicial do catálogo mostra somente os módulos exigidos.
- Tomcat 11 foi escolhido porque a base já empacota Faces e Weld. Não é um WAR para instalar diretamente em servidor Jakarta EE completo, que já traz essas implementações.

- Compatibilidade CDI dos exemplos: GenericCrud/UsuarioLogic/UsuarioDAO receberam Serializable; UsuarioLogic recebeu @Dependent, e Connection ficou transient. Isso impede que os exemplos preservados interrompam a inicialização CDI do catálogo por dependência não resolvida ou escopo passivante inválido.
