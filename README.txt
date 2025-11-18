Sistema de Impressão — Java + DLL da Impressora Elgin

Este projeto é um programa em Java que permite controlar uma impressora
térmica da marca Elgin usando uma DLL oficial. O sistema funciona
através de um menu simples no terminal, onde o usuário escolhe o que
deseja imprimir ou executar.

O programa permite:

-   Abrir e fechar conexão com a impressora
-   Imprimir texto
-   Imprimir QR Code
-   Imprimir código de barras
-   Imprimir arquivos XML SAT e XML de cancelamento
-   Abrir a gaveta da impressora
-   Emitir sinais sonoros
-   Avançar papel
-   Realizar corte automático após cada impressão

1. Requisitos

Para executar este programa você precisa:

-   Java instalado
-   A DLL da impressora (E1_Impressora01.dll)
-   Os arquivos XML SAT, se quiser testar essa impressão
-   Uma impressora Elgin compatível

A DLL deve estar no caminho utilizado no código. Caso deseje colocar em
outro diretório, basta alterar o caminho dentro da função Native.load().

2. Como executar o programa

1.  Abra o projeto em qualquer IDE Java ou execute pelo terminal.
2.  O menu será exibido automaticamente.
3.  Antes de realizar qualquer impressão, escolha a opção de configurar
    a conexão.
4.  Depois abra a conexão.
5.  Selecione qualquer função do menu para executar as operações.

3. Como configurar a conexão

Ao escolher a opção: 1 - Configurar Conexão O programa vai pedir:

-   Tipo
-   Modelo
-   Conexão
-   Parâmetro

Esses valores dependem da sua impressora e da forma como ela está
conectada (USB, rede, serial etc.). Caso esteja usando USB, por exemplo,
basta inserir o modelo e deixar os demais valores padrão.

Depois disso, use: 2 - Abrir Conexão Se a conexão abrir com sucesso,
você poderá usar o restante das funções.

4. Opções do menu

O programa apresenta as seguintes opções:

1.  Configurar Conexão
2.  Abrir Conexão
3.  Impressão de Texto
4.  Impressão de QR Code
5.  Impressão de Código de Barras
6.  Impressão de XML SAT
7.  Impressão de XML de Cancelamento SAT
8.  Abrir Gaveta Elgin
9.  Abrir Gaveta (modo genérico)
10. Emitir Sinal Sonoro
11. Fechar Conexão e Sair

A cada impressão realizada, o sistema automaticamente manda a impressora
efetuar o corte do papel.

5. Arquivos XML

Para as opções de impressão SAT (6 e 7), os arquivos XML precisam
existir nos caminhos definidos dentro do código. Caso queira usar seus
próprios arquivos, basta alterar o caminho nas funções:

-   impressaoXMLSAT()
-   ImprimeXMLCancelamentoSAT()

6. Estrutura interna (explicação simples)

O programa usa a tecnologia JNA, que permite que o Java execute funções
que estão presentes dentro da DLL da impressora. Cada função da DLL foi
mapeada para um método Java, como por exemplo:

-   ImpressaoTexto()
-   ImpressaoQRCode()
-   Corte()
-   AbreGaveta()

Essas funções retornam um número:

-   0 significa que funcionou
-   Qualquer outro número indica erro

O programa checa essa resposta e mostra no terminal se a operação deu
certo ou não.

7. Finalizando o programa

Para encerrar o uso:

1.  Escolha a opção 0
2.  A conexão será fechada
3.  O programa encerra logo em seguida

Sempre feche a conexão antes de desligar a impressora.

----------------------------------------------------------------------

Este sistema foi desenvolvido para servir como ferramenta de testes e
aprendizado sobre integração Java com impressoras Elgin via DLL. O menu
é simples e permite que qualquer usuário realize impressões básicas e
comandos essenciais da impressora.