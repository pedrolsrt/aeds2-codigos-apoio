# Guia rápido para a atividade de estruturas lineares

## 1. Identificar o tema

**Pilha = LIFO** (último a entrar, primeiro a sair). Abra `pilhas/src/Pilha.java` e `pilhas/src/App.java`.

**Fila = FIFO** (primeiro a entrar, primeiro a sair). Abra `filas/src/Fila.java` e `filas/src/App.java`.

## 2. Onde estão as soluções

| Expressão no enunciado | Arquivo / método |
| --- | --- |
| Empilhar pedido ao finalizar | `pilhas/src/App.java` → `finalizarPedido(Pedido)` |
| N elementos mais recentes / topo, sem alterar original | `pilhas/src/Pilha.java` → `subPilha(int)` |
| Produtos dos pedidos mais recentes | `pilhas/src/App.java` → `listarProdutosPedidosRecentes()` |
| Armazenar por ordem de chegada | `filas/src/App.java` → `finalizarPedido(Pedido)` |
| Média de atributo dos primeiros N | `filas/src/Fila.java` → `calcularValorMedio(Function<E, Double>, int)` |
| Filtrar os primeiros N por condição | `filas/src/Fila.java` → `filtrar(Predicate<E>, int)` |
| Média dos valores totais dos pedidos | `filas/src/App.java` → `exibirValorTotalMedio()` |
| Pedidos com valor acima do limite | `filas/src/App.java` → `exibirPedidosAcimaValor()` |
| Pedidos contendo produto específico | `filas/src/App.java` → `exibirPedidosComProduto()` |

## 3. Nomes importantes do template da professora

- `Pedido.valorFinal()` calcula o valor final do pedido; **não** `getValorTotal()`.
- `Pedido.existeNoPedido(Produto)` retorna um `ItemDePedido` ou `null`.
- `Pedido.getItensDoPedido()` retorna `ItemDePedido[]` com posições possivelmente `null`.
- `Produto` tem subclasses `ProdutoPerecivel` e `ProdutoNaoPerecivel`.
- `Pilha<E>` usa `Celula<E>` e oferece `vazia`, `empilhar`, `desempilhar`, `consultarTopo`, `subPilha`.
- `Fila<E>` neste material usa `Celula<E>` e oferece `vazia`, `enfileirar`, `desenfileirar`, `consultarPrimeiro`, `tamanho`, `calcularValorMedio`, `filtrar`.

## 4. Diferenças e cuidados

- `subPilha(N)` exige N elementos existentes: se N for maior, lança `IllegalArgumentException`, conforme comentário contratual do template da professora.
- `Fila.calcularValorMedio` e `Fila.filtrar` percorrem **somente os N primeiros elementos** da fila, sem retirar itens da fila original.
- Para a fila, o PDF não especifica o tratamento de N maior que o tamanho: neste exemplo são considerados todos os elementos disponíveis. Para N negativo, há exceção; média de zero elementos = `0.0`.
- Não confunda **N primeiros pedidos** (mais antigos na fila) com **N mais recentes** (mais novos na pilha).
- O projeto oficial da oficina de Filas não foi disponibilizado. Se a professora fornecer uma classe `Fila` diferente, compare as assinaturas antes de reutilizar.
- A atividade pode exigir comportamento adicional, outras entradas/saídas ou outra estrutura. Adapte ao enunciado real.

## 5. Checklist rápido

1. Estrutura: Pilha ou Fila?
2. Método exigido: assinatura exata?
3. Ordem: topo / chegada?
4. Quantidade: zero, negativa, maior que disponível?
5. A estrutura original deve permanecer intacta?
6. Dados: `Pedido`, `ItemDePedido`, `Produto`?
7. Menu e formato de saída?
8. Compilar e testar antes de entregar.

## 6. Exemplos para abrir antes da prova

- Pilha: `pilhas/src/ExemploPilha.java` (executar com `java -cp bin ExemploPilha`).
- Fila: `filas/src/ExemploFila.java` (executar com `java -cp bin ExemploFila`).
- Sistema completo: `pilhas/src/App.java` ou `filas/src/App.java`, com menu e `produtos.txt`.

**Limite do material:** não recebemos o enunciado da avaliação nem o template oficial da oficina de Filas. Os arquivos são bases de consulta e não garantem solução sem adaptações.
