import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanelComponent extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 400;
    static final int DELAY = 75;
    static int generatedValue;
    ArrayList<Integer> UserEnteredSequence = new ArrayList<>();
    ArrayList<Integer> ComputerGeneratedSequence = new ArrayList<>();
    public int UserClickedButton;
    static boolean animate = false;
    static int LEVEL =1;
    static int index =0;
    static boolean RUNNING = false;
    JButton[] buttons;
    private JButton replayButton;
    Timer timer;
    GamePanelComponent(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);


        startGame();
        addButtons();
    }
    public void startGame(){
        RUNNING = true;
        timer = new Timer(DELAY, this);
        timer.start();
        generateSequence();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(RUNNING){



            g.setColor(Color.gray);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }



            // Draw rectangles with rounded corners
            int cornerRadius = 60; // Adjust the corner radius as needed

            // Red rectangle
            g.setColor(Color.red);
            g.fillRoundRect(40, 40, UNIT_SIZE-80, UNIT_SIZE-80, cornerRadius, cornerRadius);

            // Green rectangle
            g.setColor(Color.green);
            g.fillRoundRect(UNIT_SIZE+40, 40, UNIT_SIZE-80, UNIT_SIZE-80, cornerRadius, cornerRadius);

            // Blue rectangle
            g.setColor(Color.blue);
            g.fillRoundRect(40, UNIT_SIZE+40, UNIT_SIZE-80, UNIT_SIZE-80, cornerRadius, cornerRadius);

            // White rectangle
            g.setColor(Color.white);
            g.fillRoundRect(UNIT_SIZE+40, UNIT_SIZE+40, UNIT_SIZE-80, UNIT_SIZE-80, cornerRadius, cornerRadius);




            //animate box
            if (animate) {


                switch (generatedValue) {
                    case 1:
                        g.setColor(new Color(255, 255, 255, 178));
                        g.fillRect(0, 0, UNIT_SIZE, UNIT_SIZE);
                        break;
                    case 2:
                        g.setColor(new Color(255, 255, 255, 178));
                        g.fillRect(UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE);
                        break;
                    case 3:
                        g.setColor(new Color(255, 255, 255, 178));
                        g.fillRect(0, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        break;
                    case 4:
                        g.setColor(new Color(255, 255, 255, 178));
                        g.fillRect(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        break;


                }

                // Delay before clearing the animated box
                int animationDelay = 1000; // Adjust the delay as desired
                Timer clearTimer = new Timer(animationDelay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        animate = false;
                        repaint();
                    }
                });
                clearTimer.setRepeats(false);
                clearTimer.start();



            }
        }else{


            for(int i=0;i<4;i++) {
                remove(buttons[i]);
            }
            timer.stop();
            gameOver(g);
            repaint();
        }
    }

    private void removeReplayButton() {
        if (replayButton != null) {
            remove(replayButton);
            replayButton = null;
        }
    }


    public void gameOver(Graphics g ){
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over" , (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);


    }


    public void addButtons(){
        //Initiating Button
        buttons= new JButton[4];

        //Setting properties for button
        for(int i=0;i<4;i++){
            buttons[i] = new JButton();
            buttons[i].setContentAreaFilled(true);
            buttons[i].setBorderPainted(false);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(new ButtonClickAction());
        }
        //Setting button area
        buttons[0].setBounds(0, 0, UNIT_SIZE, UNIT_SIZE);
        buttons[1].setBounds(UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE);
        buttons[2].setBounds(0, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        buttons[3].setBounds(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);


        // Set layout manager to null
        this.setLayout(null);

        //add button to screen
        for (JButton button : buttons) {
            this.add(button);
        }
    }

    public void generateSequence(){
        generatedValue = (int) Math.floor(Math.random() * 4 + 1);
        System.out.println("Generated value: "+generatedValue);
        ComputerGeneratedSequence.add(generatedValue);
        animate = true;
        repaint();




    }

    public void checkSequence(int currentIndex){
        //debugging
        /*
        System.out.println("Current index: "+currentIndex);
        System.out.println("Current user index: "+UserEnteredSequence.get(currentIndex));
        System.out.println("Current computer index: "+ComputerGeneratedSequence.get(currentIndex));
        System.out.println("user sequence size: "+UserEnteredSequence.size() );
        System.out.println("computer sequence size: "+ComputerGeneratedSequence.size());
         */

        //check if same value as computer generated is entered
        if(UserEnteredSequence.get(currentIndex)==ComputerGeneratedSequence.get(currentIndex)){
            //check if the length of user seq and com seq is same
            if(UserEnteredSequence.size() == ComputerGeneratedSequence.size()){
                UserEnteredSequence.clear();
                System.out.println("You won! Next Level");
                index = -1 ;
                generateSequence();

            }

        }else{
            System.out.println("Wrong colour entered");
            RUNNING = false;
            playSound("/Users/xdzc0/IdeaProjects/Simon/src/lost.wav");
        }


    }





    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class ButtonClickAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(RUNNING){
                for(int i=0;i<4;i++){
                    if(e.getSource()==buttons[i]){
                        UserClickedButton = i;
                    }
                }
                playSound("/Users/xdzc0/IdeaProjects/Simon/src/click.wav");


                int buttonClickedNum =UserClickedButton+1;
                UserEnteredSequence.add(buttonClickedNum);
                checkSequence(index);
                index++;
            }

        }
    }





    //Sound Implementation
    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            DataLine.Info info = new DataLine.Info(Clip.class, audioIn.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

}
