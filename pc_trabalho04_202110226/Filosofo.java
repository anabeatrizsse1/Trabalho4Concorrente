
import javafx.scene.image.ImageView;

public class Filosofo extends Thread {
  public ImageView garfoDir, garfoEsq;
  private TelaController controle;
  private int pensar = 0, fome = 1, comer = 2;
  private int id;
  private int tComendo = 3000, tPensando = 3000;
  private boolean pausar = false;
  private int estado = 0;

  /*
   * ***************************************************************
   * Metodo: Filosofo
   * Funcao: construtor para o controlador da tela
   * Parametros: variavel controle do tipo TelaController
   * Retorno: sem retorno
   */

  public Filosofo(TelaController controle) {
    this.controle = controle;
  }

  /*
   * ***************************************************************
   * Metodo: pegarGarfos()
   * Funcao: implementa a lógica de um filósofo tentando pegar um garfo para comer
   * Parametros: sem parametro
   * Retorno: void
   */

  public void pegarGarfo() throws InterruptedException {
    // Adquire o mutex para acessar as variáveis compartilhadas
    controle.mutex.acquire();
    // Define o estado do filósofo como fome
    controle.estado[id] = fome;
    // Chama o método fome para atualizar o estado dos vizinhos
    controle.fome(id);
    // Testa se o filósofo pode comer com os garfos disponíveis
    testa(id);
    // Libera o mutex para permitir que outros filósofos acessem as variáveis
    // compartilhadas
    controle.mutex.release();
    // Aguarda até que os garfos estejam disponíveis para serem pegos
    controle.arraysemaforo[id].acquire();

  }
/*
   ***************************************************************
   * Método: getTComendo()
   * Função: o tempo de comer de um filósofo
   * Parâmetros: nenhum
   * Retorno: retorna o tempo de comer do filosofo
   */
  public int getTComendo() {
    return tComendo;
  }
/*
   ***************************************************************
   * Método: getTPensando()
   * Função: *o tempo de pensando de um filósofo
   * Parâmetros: nenhum
   * Retorno: retorna o tempo de pensar do filosofo
   */
  public int getTPensando() {
    return tPensando;
  }

  /*
   * ***************************************************************
   * Metodo: devolveGarfos()
   * Funcao: implementa a lógica de um filósofo devolvendo os garfos após terminar
   * de comer
   * Parametros: nenhum
   * Retorno: nenhum
   */
  public void devolveGarfos() throws InterruptedException {
    // Adquire o mutex para acessar as variáveis compartilhadas
    controle.mutex.acquire();
    // Define o estado do filósofo como pensar
    controle.estado[id] = pensar;
    // Coloca os garfos de volta na mesa
    controle.colocaGarfos(this);
    // Testa se os vizinhos podem comer com os garfos disponíveis
    testa(controle.verificarEsq(id));
    testa(controle.verificarDir(id));
    // Libera o mutex para permitir que outros filósofos acessem as variáveis
    // compartilhadas
    controle.mutex.release();
  }

  /*
   *************************************************************** 
   * Metodo: testa()
   * Funcao: implementa a lógica de um filósofo testando se pode pegar os garfos
   * para comer
   * Parametros: um inteiro i que representa o ID do filósofo
   * Retorno: nenhum
   */
  public void testa(int i) throws InterruptedException {
    // Verifica se o filósofo está com fome e se seus vizinhos não estão comendo
    if (controle.estado[i] == fome && controle.estado[controle.verificarEsq(i)] != comer
        && controle.estado[controle.verificarDir(i)] != comer) {
      // Define o estado do filósofo como comer
      controle.estado[i] = comer;
      // Libera o semáforo correspondente ao filósofo, permitindo que ele pegue os
      // garfos e comece a comer
      controle.arraysemaforo[i].release();
    }
  }

  /*
   ***************************************************************
   * Método: setGarfos()
   * Função: Configura as imagens dos garfos (esquerdo e direito) para um filósofo
   * Parâmetros: um objeto ImageView para o garfo esquerdo e outro para o garfo
   * direito
   * Retorno: nenhum
   ****************************************************************/
  public void setGarfos(ImageView garfoEsq, ImageView garfoDir) {
    // Configura as imagens dos garfos para o filósofo atual
    this.garfoDir = garfoDir;
    this.garfoEsq = garfoEsq;
  }

  /*
   * ***************************************************************
   * Método: setId()
   * Função: Define o ID de um filósofo
   * Parâmetros: um inteiro que representa o ID do filósofo
   * Retorno: nenhum
   */
  public void setId(int id) {
    // Define o ID do filósofo atual
    this.id = id;
  }

  /*
   ***************************************************************
   * Método: getid()
   * Função: Retorna o ID de um filósofo
   * Parâmetros: nenhum
   * Retorno: um inteiro que representa o ID do filósofo
   */
  public int getid() {
    // Retorna o ID do filósofo atual
    return id;
  }

  /*
   * ***************************************************************
   * Método: setControlador
   * Função: Recebe uma instância do controlador da tela e associa ao objeto atual
   * Parâmetros: TelaController controle - instância do controlador da tela
   * Retorno: nenhum
   ****************************************************************/
  public void setControlador(TelaController controle) {
    this.controle = controle;
  }

  /*
   * ***************************************************************
   * Método: setTempoComendo
   * Função: Recebe o tempo que o filósofo deve ficar comendo e converte de
   * segundos para milissegundos
   * Parâmetros: int tempoComendo - tempo em segundos
   * Retorno: nenhum
   */
  public void setTempoComendo(int tempoComendo) {
    this.tComendo = tempoComendo * 1000;
  }

  /*
   * ***************************************************************
   * Método: setTempoPensando
   * Função: Recebe o tempo que o filósofo deve ficar pensando e converte de
   * segundos para milissegundos
   * Parâmetros: int tempoPensando - tempo em segundos
   * Retorno: nenhum
   */
  public void setTempoPensando(int tempoPensando) {
    this.tPensando = tempoPensando * 1000;
  }

  /*
   * ***************************************************************
   * Método: setPausar
   * Função: Inverte o estado da variável pausar (se estiver true, passa para
   * false e vice-versa)
   * Parâmetros: nenhum
   * Retorno: nenhum
   */
  public void setPausar() {
    this.pausar = !pausar;
  }

  /****************************************************************
   * Método: pararThread()
   * Função: Para a execução da thread atual, zerando os tempos de comer e pensar
   * e alterando o estado para -1
   * Parâmetros: nenhum
   * Retorno: nenhum
   ****************************************************************/
  public void pararThread() {
    tComendo = 0;
    tPensando = 0;
    estado = -1;
  }
  
  /*
   * ***************************************************************
   * Metodo: run()
   * Funcao: executa o comportamento do filosofo
   * Parametros:
   * Retorno:
   *************************************************************** */
  public void run() {
    // Enquanto o estado for maior ou igual a 0, executa o comportamento do filósofo
    while (estado >= 0) {
      try {
        // Enquanto a thread estiver pausada, imprime "pausado"
        while (pausar) {
          System.out.println("thread pausada");
        }
        // Se o estado do filósofo for 0, ele está pensando
        if (estado == 0) {
          controle.pensar(this);
        }
        // Se o estado do filósofo for 1, ele está com fome e tenta pegar os garfos
        if (estado == 1) {
          pegarGarfo();
        }
        // Se o estado do filósofo for 2, ele está comendo
        if (estado == 2) {
          controle.comer(this);
        }
        // Se o estado do filósofo for 3, ele acabou de comer e devolve os garfos
        if (estado == 3) {
          estado = -1;
          devolveGarfos();
        }
        // Incrementa o estado do filósofo
        estado++;
      } catch (InterruptedException e) {
      }
    }//fim do while
  }// fim do run
}// fim da thread