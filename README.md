# TP1-GoL
Criação de Game of Life usando Scala e LibGDX.

#### Esquema do Programa
![Esquema do GoL](http://i67.tinypic.com/2a4o3nn.png)
As setas finas significam chamadas entre as classes e as grossas significam 'é pai de'. No início do programa, GameView, DependencyInjector e GameController são instanciados como objects assim que instanciado DependencyInjector já possui as classes de regras e GameController já as pode acessar. A GameView controla a tela e se utiliza de classes derivadas de GameButton para criar a interface gráfica e uma classe **LifeCell** auxiliar simples que só contem as coordenadas na tela e a **Cell** em si. Para a engine usamos o padrão Template. Ela se utiliza de **Cells** para montar o tabuleiro junto de uma classe **Table** que é basicamente um Array Bidimensional.
