import java.util.*;
import java.io.*;
import javax.swing.*; // Biblioteca do Swing para interfaces gráficas
import java.awt.event.*;
import javax.sound.sampled.*;


// https://developer.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
public class Player{

	AudioFormat format;
	AudioInputStream inputStream;
	SourceDataLine line;

	public static void main(String[] args) {
		new Player();
	}

	public Player(){
		try{
			File file = new File("1-welcome.wav");
			inputStream = AudioSystem.getAudioInputStream(file);
			format = inputStream.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine) AudioSystem.getLine(info);
			new PlayThread().start();

		}
		catch(Exception e){

		}
	}

	class PlayThread extends Thread{
		byte tempBuffer[] = new byte[10000];

		public void run(){
			try{
				line.open(format);
				line.start();

				int cnt;
				while((cnt = inputStream.read(tempBuffer,0,tempBuffer.length)) != 1){
					if(cnt >0){
						line.write(tempBuffer,0,cnt);
					}
				}


			}
			catch(Exception e){

			}
		}	
	}

}

