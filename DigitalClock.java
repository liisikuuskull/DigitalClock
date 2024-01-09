import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalClock extends JFrame {

    private JLabel clockLabel;
    

    public DigitalClock() {
        
        setTitle("Digital Clock");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        

        
        add(clockLabel);

        // Set up the timer to update the clock every second
        Timer timer = new Timer(1000, e -> updateClock());
        timer.start();
    }

    

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = sdf.format(new Date());

        
        clockLabel.setText(formattedTime);

        // Play sound at a specific time
        if (formattedTime.endsWith(":00:00")) {
            playHourlySound();
        }
    }

    private void playHourlySound() {
        String soundFile = "hourly_sound.wav"; // Change the file format to WAV
        File file = new File(soundFile);

        if (!file.exists()) {
            System.err.println("Error: Sound file not found");
            return;
        }

        
    System.out.println("Playing sound...");

    try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                clip.close();
                System.out.println("Sound stopped.");
            }
        });

        clip.start();
        System.out.println("Sound started.");
    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
        e.printStackTrace();
    }

}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DigitalClock digitalClock = new DigitalClock();
            digitalClock.setVisible(true);
        });
    }
}

