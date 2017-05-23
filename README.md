# TP1-GoL
Criação de Game of Life usando Scala e LibGDX.

_Observação: melhor visto no [GitHub](https://github.com/victoragcosta/TP1-GoL) ou usando um editor de texto com preview de MarkDown como [Atom](https://atom.io/)._

### Alunos
Aluno | Username | Matrícula
----- | -------- | ---------
Victor André Gris Costa | @victoragcosta | 16/0019311
Hugo Nascimento Fonseca | @Hugo-NF | 16/0008166
André Filipe Caldas Laranjeira | @AndreLaranjeira | 16/0023777

### Uso do Programa
* Existem basicamente 3 projetos: Template Method, Strategy e a implementação com a interface gráfica e afins.
* Instruções para o uso da implementação com LibGDX:
  * Funciona melhor com IntelliJ IDEA
  * Gere um jar contendo todos os módulos exceto GameRules e outro jar que contém a implementação de várias regras e contendo o arquivo Classes.txt. Coloque esse segundo jar na ClassPath do primeiro jar. (Já deixamos isso pronto na pasta classes na root do projeto gráfico)
  * Rode o jar
  * Os botões estão em Inglês e são autoexplicativos, mas da esquerda para direita:
    * Desfaz Geração;
    * Muda Mudança Automática de geração para pausa ou não;
    * Calcula Próxima Geração;
    * Refaz Geração;
    * Diminui Velocidade de mudança de geração;
    * Reestabelece velocidade de mudança de geração padrão;
    * Aumenta velocidade de mudança de geração;
    * Mata todas as células e limpa a tela;
    * Menu de mudança de regras;
    * Sai do programa;
  * Para fazer uma célula viver, clique com o botão esquerdo em qualquer lugar com células mortas. Para mudar a cor da célula, clique com o botão esquerdo em cima de uma célula viva. Para matar uma célula, clique com o botão direito em uma célula viva.
  * Há atalhos para os botões também:

Ação | Botão
---- | -----
Desfaz Geração | b
Play/Pause | Espaço
Próxima Geração | n
Refaz Geração | m
Diminui Velocidade | -
Reseta velocidade | 0
Aumenta velocidade | +
Limpa a tela | c
Ciclar pelas regras | r
Sair | esc

---

### Esquema do Programa
![Esquema do GoL](http://i63.tinypic.com/29ofhop.png)
As setas finas significam chamadas entre as classes e as grossas significam 'é pai de'.


No início do programa, GameView, DependencyInjector e GameController são instanciados como objects assim que instanciado DependencyInjector já possui as classes de regras e GameController já as pode acessar.

### Controller
* O GameController possui uma variável gameMode que mantém a referência da regra da GameEngine atual. Como GameController herda de Game (da LibGDX), ele implementa o método create que substitui o método start da implementação original, nesse método ele escolhe a tela a ser exibida como GameScreen, e o processador de entradas como GameInputHandler. Logo Após ele pede para começar a exibição de gerações na tela chamando o método update da GameView passando as células vivas e tamanho do tabuleiro de GameEngine (armazenado em gameMode).
* O GameController possui todos os métodos que são chamados por interação do usuário. Nesse caso o GameInputHandler gerencia todas as e ntradas, muitas vezes convertendo coordenadas da tela para as de tabuleiro, chamando métodos de GameController que modificam células, passam ou voltam a geração, mudam o estado da mudança de geração automática, a regra a ser utilizada e a velocidade de mudança de geração automática.
* Ao mudar de regra, GameController pode fazer ciclo pelas regras pegando de DependencyInjector, ou receber a instância da regra diretamente. Para receber diretamente, no menu de Regras cada botão possui uma instância de uma regra e a passa para GameController, gerenciado por um método, claro.

### View
* A GameView controla a tela e se utiliza de GameButton ou classes derivadas e uma classe LiveCell auxiliar simples, que só contem as coordenadas na tela e a Cell em si, para criar a interface gráfica.
* Como a responsabilidade de mudar a geração automaticamente ficou reservada a View, mais especificamente na GameScreen por conter o loop de render, ele possui métodos de controle de velocidade.
* O loop de render é chamado a todo frame ele:
  * Chama um método que pega da GameView as células que devem ser desenhadas e as desenha na tela;
  * Chama um método que percorre todos os botões da GameView e os desenha, além de decidir desenhar ou não o menu pop-up e seus botões;
  * Chama um método que desenha as letras dos botões usando a mesta distinção do quadrado;
  * Chama um método que decide se chama ou não a próxima geração, baseado no tempo passado e no estado pausado ou não.

### Engine
* Para a engine usamos o padrão Template. Ela se utiliza de Cells para montar o tabuleiro armazenado na classe Table que é basicamente um Array Bidimensional.
* Todas as classes de regra devem derivar de GameEngine e implementar shouldKeepAlive e shouldRevive, porém elas podem sobrescrever métodos de cor, mudando o funcionamento baseado na cor e também métodos de afterLife (A célula permanece na tela por um número de frames após morrer sendo desenhado e não pode ser trocada por células vivas até liberar o espaço).
