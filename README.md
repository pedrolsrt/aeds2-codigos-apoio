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

## Compilar e executar no PowerShell (Java 8 ou superior)

Na raiz do repositório:

```powershell
cd .\pilhas
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 -source 8 -target 8 -d bin (Get-ChildItem .\src\*.java).FullName
java -cp bin App
```

Para testar Filas, volte à raiz (`cd ..`) e use os mesmos comandos após `cd .\filas`.

**Execute na pasta de cada projeto** para que `produtos.txt` seja encontrado.

## Guia de adaptação

Veja [`GUIA-RAPIDO.md`](GUIA-RAPIDO.md) para saber onde copiar a lógica e quais nomes preservar. Os arquivos Java principais têm explicações apenas antes dos imports; o restante é código contínuo.

## Validação

Os dois projetos foram compilados com `javac -source 8 -target 8` e submetidos a testes de pilha/fila, ordem, limites e filtros. A execução com Java 8 foi verificada por compilação para Java 8, não em uma JVM 8 real.

## Origem e licença

Os arquivos do sistema de comércio foram adaptados do template distribuído na disciplina de AEDs II. A licença original do template (CC BY 4.0) está incluída em [`LICENSE`](LICENSE). As implementações de pilha/fila e as adaptações do menu estão identificadas neste repositório.
