# TP1-GoL
Criação de Game of Life usando Scala e LibGDX.

### Alunos
* Victor André Gris Costa ( @victoragcosta )  16/0019311
* Hugo ( @HugoNF )
* André Laranjeira ( @andrelaranjeira )

# Todo:
- [ ] Escrever como usar o programa
- [x] Escrever funcionamento do Controller
- [ ] Escrever funcionamento da View
- [ ] Escrever funcionamento da Engine

### Uso do Programa
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

### Engine
* Para a engine usamos o padrão Template. Ela se utiliza de Cells para montar o tabuleiro armazenado na classe Table que é basicamente um Array Bidimensional.
