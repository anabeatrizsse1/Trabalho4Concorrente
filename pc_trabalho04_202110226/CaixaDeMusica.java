import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public abstract class CaixaDeMusica {

  private static final String DIRETORIO = "./view/music/";
  private static Clip clip;

  public static void play(String fileName) {
    try {

      File audio = new File(CaixaDeMusica.class.getClassLoader().getResource(DIRETORIO + fileName).getFile());

      if (audio.exists()) {
        System.out.println("[Audio encontrado com sucesso!]");

        AudioInputStream inptStream = AudioSystem.getAudioInputStream(audio);

        clip = AudioSystem.getClip();
        clip.open(inptStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
      } else {
        System.out.println("[Musica nao Encontrada :(]");
      }

    } catch (Exception e) {
      System.out.println("[Exececao encontrada]\n- Pilha: ");
      e.printStackTrace();

    }
  }

  /********************************************************************
   * Metodo: pause
   * Funcao: Se o audio estiver tocando ele podera ser pausado, se nao,
   */
  public static void pause() {
    if (clip != null) {
      if (clip.isRunning()) {
        clip.stop();
      } else {
        clip.start();
      }
    }
  }

}
