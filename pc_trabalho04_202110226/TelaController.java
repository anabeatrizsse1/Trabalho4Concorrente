
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TelaController implements Initializable {
  public Semaphore mutex; // variavel do Semaforo
  public Semaphore[] arraysemaforo;
  public int[] estado;
  public int x = 5;
  Filosofo filosofo0, filosofo1, filosofo2, filosofo3, filosofo4;

  public ImageView[] garfos = new ImageView[x];
  @FXML
  public ImageView f0, f1, f2, f3, f4;// instanciando os filosofos
  @FXML
  public ImageView g0, g1, g2, g3, g4; // instanciando os garfos
  @FXML
  Slider c0, c1, c2, c3, c4, p0, p1, p2, p3, p4; // sliders de comer e pensar
  @FXML
  public ImageView pen0, pen1, pen2, pen3, pen4; // balao de pensamento
  @FXML
  public ImageView fome0, fome1, fome2, fome3, fome4; // balao de fome
  @FXML
  public ImageView comendo0, comendo1, comendo2, comendo3, comendo4; // balao de comendo
  @FXML
  public CheckBox checkBOX;
  @FXML
  public CheckBox music;// checkbox para a musica

  @FXML
  private ImageView botaomusic;

  /*
   * ***************************************************************
   * Metodo: botaoIniciar
   * Funcao: inicia as threads com paramentros passados para comecar a execucao
   * Parametros: sem parametro
   * Retorno: void
   */
  @FXML
  public void botaoIniciar() { // botao para iniciar o jantar
    filosofo0 = new Filosofo(this);
    filosofo1 = new Filosofo(this);
    filosofo2 = new Filosofo(this);
    filosofo3 = new Filosofo(this);
    filosofo4 = new Filosofo(this);
    filosofo0.setControlador(this);
    filosofo0.setGarfos(g4, g0);
    filosofo0.setDaemon(true);
    filosofo0.setId(0);
    filosofo0.start();
    filosofo1.setControlador(this);
    filosofo1.setGarfos(g0, g1);
    filosofo1.setDaemon(true);
    filosofo1.setId(1);
    filosofo1.start();
    filosofo2.setControlador(this);
    filosofo2.setGarfos(g1, g2);
    filosofo2.setDaemon(true);
    filosofo2.setId(2);
    filosofo2.start();
    filosofo3.setControlador(this);
    filosofo3.setGarfos(g2, g3);
    filosofo3.setDaemon(true);
    filosofo3.setId(3);
    filosofo3.start();
    filosofo4.setControlador(this);
    filosofo4.setGarfos(g3, g4);
    filosofo4.setDaemon(true);
    filosofo4.setId(4);
    filosofo4.start();
  }

  /*
   * ***************************************************************
   * Metodo: reiniciar
   * Funcao: reinicia as threads do inicio
   * Parametros: sem parametro
   * Retorno: void
   */
  public void reiniciar() throws InterruptedException {
    filosofo0.pararThread();
    filosofo1.pararThread();
    filosofo2.pararThread();
    filosofo3.pararThread();
    filosofo4.pararThread();
    filosofo0.devolveGarfos();
    filosofo1.devolveGarfos();
    filosofo2.devolveGarfos();
    filosofo3.devolveGarfos();
    filosofo4.devolveGarfos();
    botaoIniciar();
  }

  public TelaController() {
    f0 = new ImageView();
    f1 = new ImageView();
    f2 = new ImageView();
    f3 = new ImageView();
    f4 = new ImageView();
    g0 = new ImageView();
    g1 = new ImageView();
    g2 = new ImageView();
    g3 = new ImageView();
    g4 = new ImageView();
    garfos[0] = g0;
    garfos[1] = g1;
    garfos[2] = g2;
    garfos[3] = g3;
    garfos[4] = g4;
    p1 = new Slider();
    p2 = new Slider();
    p3 = new Slider();
    p4 = new Slider();
    c0 = new Slider();
    c1 = new Slider();
    c2 = new Slider();
    c3 = new Slider();
    c4 = new Slider();

    mutex = new Semaphore(1);

    // instanciando abaixo os arrays de estado e semaforo
    arraysemaforo = new Semaphore[x];
    estado = new int[x];
    for (int i = 0; i < x; i++) {
      arraysemaforo[i] = new Semaphore(0);
      estado[i] = 0;
    }
  }

  /*
   * ***************************************************************
   * Metodo: verificarEsq
   * Funcao:verifica qual garfo da esquerda de acordo o valor passado
   * Parametros: int i( o indice passado como o valor a ser veficado para
   * esquerda)
   * Retorno: retorna o indice da esquerda
   */
  public int verificarEsq(int i) {
    if (i + 1 < x) { // 3+1=4 4<x(5)
      return i + 1;// 3+1=4
    } else {
      return 0;
    }
  }

  /*
   * ***************************************************************
   * Metodo: verificarDir
   * Funcao:verifica qual garfo da direita de acordo o valor passado
   * Parametros: int i( o indice passado como o valor a ser veficado para direita)
   * Retorno: retorna o indice da direita
   */
  public int verificarDir(int i) {
    if (i - 1 >= 0) { // 3-1=2 2>0
      return i - 1; // 2
    } else {
      return x - 1; // x=5; 5-1 = 4;
    }

  }

  /*
   * ***************************************************************
   * Metodo: colocarGarfos
   * Funcao: deixa os garfos visiveis quando o metodo é chamado
   * Parametros: variavel f do tipo Filosofo
   * Retorno: void
   */
  public void colocaGarfos(Filosofo f) {
    f.garfoDir.setVisible(true);
    f.garfoEsq.setVisible(true);
  }

  /*
   * ***************************************************************
   * Metodo: pausarF4
   * Funcao: pausa a thread do filosofo 4
   * Parametros: sem parametro
   * Retorno: void
   */
  @FXML
  public void pausarF4() {
    filosofo4.setPausar();
  }

  /*
   * ***************************************************************
   * Metodo: pausarF3
   * Funcao: pausa a thread do filosofo 3
   * Parametros: sem parametro
   * Retorno: void
   */
  @FXML
  public void pausarF3() {
    filosofo3.setPausar();
  }

  /*
   * ***************************************************************
   * Metodo: pausarF2
   * Funcao: pausa a thread do filosofo 2
   * Parametros: sem parametro
   * Retorno: void
   */
  @FXML
  public void pausarF2() {
    filosofo2.setPausar();
  }

  /*
   * ***************************************************************
   * Metodo: pausarF1
   * Funcao: pausa a thread do filosofo 1
   * Parametros: sem parametro
   * Retorno: void
   */
  @FXML
  public void pausarF1() {
    filosofo1.setPausar();
  }

  /*
   * ***************************************************************
   * Metodo: pausarF0
   * Funcao: pausa a thread do filosofo 0
   * Parametros: sem parametro
   * Retorno: void
   */
  @FXML
  public void pausarF0() {
    filosofo0.setPausar();
  }

  /*
   * ***************************************************************
   * Metodo: pensar
   * Funcao: muda a imagem do filosofo para 'pensando' e ajusta da velocidade,
   * do slider para o usuario decidir sua velocidade de pensamento
   * e hiberna a Thread de acordo o
   * tempo de pensamento desse filosofo
   * Parametros: variavel f do tipo Filosofo
   * Retorno: void
   */
  public void pensar(Filosofo f) {
    int valor = 0;
    switch (f.getid()) {
      case 0:
        f0.setImage(new Image("view/pedrinhopensando.png"));
        valor = (int) p0.getValue();
        break;
      case 1:
        f1.setImage(new Image("view/narizinhopensando.png"));
        valor = (int) p1.getValue();

        break;
      case 2:
        f2.setImage(new Image("view/sacipensando.png"));
        valor = (int) p2.getValue();

        break;
      case 3:
        f3.setImage(new Image("view/emiliapensando.png"));
        valor = (int) p3.getValue();

        break;
      case 4:
        f4.setImage(new Image("view/porcopensando.png"));
        valor = (int) p4.getValue();
        break;
      default:
        break;

    }
    // Define o tempo de pensamento do filósofo
    f.setTempoPensando(valor);
    // Hiberna a Thread de acordo com o tempo de pensamento do filósofo
    try {
      f.sleep(f.getTPensando());
    } catch (InterruptedException e) {
      // Se houver uma exceção, nada acontece
    }
  }

  /*
   * ***************************************************************
   * Metodo: comer
   * Funcao: muda a imagem do filosofo para 'comendo' e ajusta da velocidade,
   * do slider para o usuario decidir sua velocidade de que ele come
   * e hiberna a Thread de acordo o tempo de comer desse filosofo
   * Parametros: variavel f do tipo Filosofo
   * Retorno: void
   */
  public void comer(Filosofo f) {
    int valor = 0;
    switch (f.getid()) {
      case 0:
        f0.setImage(new Image("view/pedrinhocomendo.png"));
        valor = (int) c0.getValue();
        break;
      case 1:
        f1.setImage(new Image("view/narizinhocomendo.png"));
        valor = (int) c1.getValue();

        break;
      case 2:
        f2.setImage(new Image("view/sacicomendo.png"));
        valor = (int) c2.getValue();

        break;
      case 3:
        f3.setImage(new Image("view/emiliacomendo.png"));
        valor = (int) c3.getValue();

        break;
      case 4:
        f4.setImage(new Image("view/porcocomendo.png"));
        valor = (int) c4.getValue();
        break;
      default:
        break;

    }
    // Define o tempo de comer do filósofo
    f.setTempoComendo(valor);

    // Esconde os garfos utilizados pelo filósofo
    f.garfoDir.setVisible(false);
    f.garfoEsq.setVisible(false);

    // Hiberna a Thread do filósofo de acordo com o tempo de comer
    try {
      f.sleep(f.getTComendo());
    } catch (InterruptedException e) {
    }
  }

  /*
   * ***************************************************************
   * Metodo: fome
   * Funcao: muda a imagem do filosofo para 'com fome' e ajusta da velocidade,
   * do slider para o usuario decidir sua velocidade de quando estar com fome
   * e hiberna a Thread de acordo o
   * tempo de estado de fome desse filosofo
   * Parametros: variavel f do tipo Filosofo
   * Retorno: void
   */
  public void fome(int i) {
    switch (i) {
      case 0:
        f0.setImage(new Image("view/pedrinhocomfome.png"));
        break;
      case 1:
        f1.setImage(new Image("view/narizinhocomfome.png"));
        break;
      case 2:
        f2.setImage(new Image("view/sacicomfome.png"));
        break;
      case 3:
        f3.setImage(new Image("view/emiliacomfome.png"));
        break;
      case 4:
        f4.setImage(new Image("view/porcocomfome.png"));
        break;
      default:
        break;
    }

  }// fim do metodo fome

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: Inicializa a tela
   * Parametros: URL location, ResourceBundle resources
   * Retorno: void
   */
  public void initialize(URL location, ResourceBundle resources) {
    CaixaDeMusica.play("musica.wav"); // Executa a música
    music.setSelected(true); // Define a seleção padrão do checkbox "Música"
  }

  /*
   * ***************************************************************
   * Metodo: music
   * Funcao: Habilita/desabilita a reprodução da música de fundo
   * Parametros:
   * event: Evento de seleção/deseleção do checkbox
   * Retorno:
   * void
   */
  @FXML // id do checkbox
  public void music(ActionEvent event) {
    // Verifica se o checkbox está selecionado
    if (music.isSelected()) {
      // Toca a música usando a classe CaixaDeMusica
      CaixaDeMusica.play("musica.wav");
    } else {
      // Pausa a música usando a classe CaixaDeMusica
      CaixaDeMusica.pause();
    }
  }
  /*
   * ***************************************************************
   * Metodo: buttonPaused
   * Funcao: Pausa/retoma a música de fundo ao clicar no botão
   * Parametros:
   * mE: Evento de clique do mouse no botão
   * Retorno:
   * void
   */
  @FXML
  public void buttonPaused(MouseEvent mE) {
    // Verifica se o botão que disparou o evento é o botaomusic
    if (mE.getSource().equals(botaomusic)) {
      System.out.println("[pausei]");
      // Inverte o estado do checkbox music
      if (music.isSelected()) {
        music.setSelected(false);
      } else {
        music.setSelected(true);
      }

    }
  }

/****************************************************************
Metodo: pausar
Funcao: Pausar a música quando o checkbox é desmarcado
Parametros: event - evento que ocorreu
Retorno: void
****************************************************************/
@FXML // Anotação que indica que o método é um evento de OnAction no SceneBuilder
public void pausar(ActionEvent event) {
if (event.getSource().equals(music)) { // Verifica se o evento ocorreu no checkbox "music"
CaixaDeMusica.pause(); // Pausa a música
} // fim do if
}
// fim do método pausar
  }
