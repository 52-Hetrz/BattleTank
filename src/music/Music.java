package music;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class Music implements Runnable{
    AudioInputStream as;//音频文件在项目根目录的img文件夹下
    AudioFormat format ;
    DataLine.Info info;
    SourceDataLine sdl ;
    int nBytesRead = 0;

    public Music(String fileName){
        try {
            setFile(fileName);
        }catch(IOException|UnsupportedAudioFileException|LineUnavailableException e){
            e.printStackTrace();
        }
    }
    void play(){
        try {
            sdl.open(format);
            sdl.start();
            byte[] abData = new byte[512];
            while (nBytesRead != -1) {
                nBytesRead = as.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    sdl.write(abData, 0, nBytesRead);
            }
            sdl.drain();
            sdl.stop();
        }catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void musicClose() throws InterruptedException {
        nBytesRead=-1;
    }

    void setFile(String filePath) throws IOException, UnsupportedAudioFileException, LineUnavailableException { ;
        as = AudioSystem.getAudioInputStream(new File(filePath));
        format = as.getFormat();
        info = new DataLine.Info(SourceDataLine.class, format);
        sdl = (SourceDataLine) AudioSystem.getLine(info);
    }
    @Override
    public void run() {
        play();
    }


}
