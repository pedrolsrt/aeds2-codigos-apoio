# AEDS II - Códigos de apoio: Pilhas e Filas

Material de consulta em Java, separado do repositório da atividade já realizada.

## Escolha o projeto

| Tema | Pasta | Base | Métodos para localizar |
| --- | --- | --- | --- |
| Pilhas (LIFO) | `pilhas/` | Template da professora adaptado | `finalizarPedido`, `subPilha`, `listarProdutosPedidosRecentes` |
| Filas (FIFO) | `filas/` | Sistema de comércio da professora + classe `Fila` construída a partir do PDF | `enfileirar`, `calcularValorMedio`, `filtrar`, `exibirValorTotalMedio`, `exibirPedidosAcimaValor`, `exibirPedidosComProduto` |

**Importante:** só recebemos o código-base oficial de **Pilhas**. O código de **Filas** segue os requisitos do PDF, mas a classe `Fila` foi implementada como um modelo próprio, não como cópia de um template oficial de Filas. A avaliação pode ter requisitos diferentes; não há garantia de código pronto para qualquer questão.

## Estrutura

Cada pasta contém seu próprio `src/` com `App.java`, `Celula.java`, `Produto.java`, `ProdutoPerecivel.java`, `ProdutoNaoPerecivel.java`, `Pedido.java`, `ItemDePedido.java`, e a estrutura `Pilha.java` ou `Fila.java`, além de `produtos.txt`.

**Não compile as duas pastas juntas**, porque elas têm classes com os mesmos nomes.

## Exemplos prontos e verificáveis

- `pilhas/src/ExemploPilha.java`: demonstra LIFO, `subPilha(2)`, preservação da pilha original, erro por quantidade maior que o disponível e um cenário com `Pedido` e `Produto`.
- `filas/src/ExemploFila.java`: demonstra FIFO, `calcularValorMedio`, `filtrar`, preservação da fila e exemplos com `Pedido` e `Produto`.
- `pilhas/src/App.java` e `filas/src/App.java`: programas completos com menu e dados de `produtos.txt`.

Os exemplos são classes executáveis independentes dentro de cada projeto; não é necessário alterar a atividade já entregue.

Cada projeto inclui o `produtos.txt` original (7.750 produtos) e um `produtos-exemplo.txt` com seis produtos, incluindo perecíveis. Para testar o menu sem listar milhares de itens, execute `java -cp bin App produtos-exemplo.txt`. Sem argumento, `App` continua usando `produtos.txt`, preservando o comportamento original.

## Compilar e executar no PowerShell (Java 8 ou superior)

Na raiz do repositório:

```powershell
cd .\pilhas
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 -source 8 -target 8 -d bin (Get-ChildItem .\src\*.java).FullName
java -cp bin App produtos-exemplo.txt
```

Para executar os exemplos de consulta (na pasta do projeto correspondente):

```powershell
java -cp bin ExemploPilha
```

Ou, na pasta `filas`:

```powershell
java -cp bin ExemploFila
```

Para testar Filas, volte à raiz (`cd ..`) e use os mesmos comandos após `cd .\filas`.

**Execute na pasta de cada projeto** para que os arquivos de produtos sejam encontrados. Use `java -cp bin App` para executar com o arquivo original da professora.

## Guia de adaptação

Veja [`GUIA-RAPIDO.md`](GUIA-RAPIDO.md) para saber onde copiar a lógica e quais nomes preservar. Os arquivos Java principais têm explicações apenas antes dos imports; o restante é código contínuo.

## Validação

Os dois projetos foram compilados com `javac --release 8` e os exemplos executáveis passaram em verificações de pilha/fila, ordem, limites, filtros e integração com `Pedido`. O carregamento dos seis produtos de teste também foi conferido. A compatibilidade com Java 8 foi verificada por compilação com `--release 8`, não por execução em uma JVM 8 real. Os menus pressupõem entradas numéricas válidas, assim como o template original.

## Origem e licença

Os arquivos do sistema de comércio foram adaptados do template distribuído na disciplina de AEDs II. A licença original do template (CC BY 4.0) está incluída em [`LICENSE`](LICENSE). As implementações de pilha/fila e as adaptações do menu estão identificadas neste repositório.
