import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/* ***************************************************************
* Autor:Ana Beatriz Silva e Silva 
* Matricula: 202110226
* Inicio: 01/05/2023
* Ultima alteracao: 13/05/2023
* Nome: Jantar dos Filosofos  
 com Threads
* Funcao: Esse programa mostra a concorrencia entre filosofos em pegar
* garfos que estao na mesa. Ao comerem a comida, eles precisam de dois garfos. 
*************************************************************** */

public class Principal extends Application {

  public static Parent root;
  public static Stage stage = new Stage();

  @Override
  public void start(Stage stage) throws Exception {
    TelaController tela = new TelaController();
    root = FXMLLoader.load(getClass().getResource("/view/sitio.fxml"));
    Scene scene = new Scene(root);
    Principal.stage.setTitle("sitio");
    Principal.stage.setScene(scene);
    Principal.stage.setResizable(false);
    Principal.stage.sizeToScene();
    Principal.stage.centerOnScreen();
    Principal.stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
