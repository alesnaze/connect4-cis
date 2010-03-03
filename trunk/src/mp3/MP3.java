/**
         * This class is responsible for playing the mp3 files
         * when we import it and call it's methods for a mouse response to make sounds
         *
 */

package mp3;

/**
 *
 * @author Salma
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;

public class MP3 {


     File filename;
     Player player;

    // constructor that takes the name of an MP3 file
    public MP3(File filename) {

        this.filename = filename;
    }

    
// close the mp3 file
   public void close() {

        if (player != null)
            
            player.close();
    }

    // play the MP3 file to the sound card
    public void play() {
        try {
            FileInputStream fis     = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {

            JOptionPane.showMessageDialog(null,"Problem playing file "+filename ,"Error", JOptionPane.ERROR_MESSAGE);

        }

        // run in new thread to play in background
        new Thread() {
            public void run() {

                try { player.play();

                }
                catch (Exception e) { 
                    
                     JOptionPane.showMessageDialog(null,"Problem playing file "+filename ,"Error", JOptionPane.ERROR_MESSAGE);
      

                }
            }
        }.start();




    }


    // test 
 /* public static void main(String[] args) {
        File filename = new File("src/sounds/app-15.mp3");
        Main mp3 = new Main(filename);
        mp3.play();
       

    }*/


}
