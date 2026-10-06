# Roteiro de validação antes da entrega
Execute com PostgreSQL e servidor ligados. Anote resultado e tire capturas reais.

| Caso | Ação | Resultado esperado |
|---|---|---|
| M01 | Criar marca sem nome | Campo obrigatório; nada gravado |
| M02 | Criar Hinode, HINODE e nome com espaços | Duplicidade bloqueada |
| M03 | Editar marca e desativar | Lista e estado atualizados |
| M04 | Excluir marca vinculada | Mensagem amigável; produto preservado |
| C01 | Criar raiz, filha e neta | Caminho completo e menus indentados |
| C02 | Colocar raiz como filha da neta | Operação bloqueada |
| C03 | Excluir categoria com filhos ou produtos | Operação bloqueada |
| C04 | Mover neta para outra raiz | Caminho atualizado ao atualizar lista |
| P01 | Cadastrar com preço zero ou estoque negativo | Operação bloqueada |
| P02 | Cadastrar sem marca/categoria | Obrigatórios informados |
| P03 | Enviar JPG, PNG e WEBP reais | Prévia antes de salvar e miniatura após salvar |
| P04 | Enviar executável renomeado e imagem >2 MB | Arquivo rejeitado |
| P05 | Alterar imagem e cancelar | Imagem anterior preservada |
| P06 | Trocar imagem, salvar e reiniciar app | Nova imagem persistida e antiga removida |
| P07 | Excluir produto com imagem | Produto e arquivo removidos |
| U01 | Cadastrar 12 registros; filtrar/ordenar/paginar | Tabela responde corretamente |
| U02 | Cancelar diálogo de exclusão | Registro preservado |
| U03 | Abrir no Moto G42 e computador | Formulário legível e tabela com rolagem |
| I01 | Derrubar banco e tentar salvar | Mensagem de erro; sem sucesso falso |
| I02 | Duas sessões tentam criar nome igual | Banco mantém apenas uma marca |
| I03 | SQL tenta inserir ciclo | Gatilho rejeita ciclo |

As assinaturas dos uploads e o tamanho são verificados; isso não equivale a antivírus nem validação integral do codec. O projeto é um módulo acadêmico local sem autenticação.
