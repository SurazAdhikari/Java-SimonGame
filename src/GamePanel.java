import javax.sound.midi.Sequence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Random;
import java.util.Stack;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 400;
    static final int DELAY = 5000;
    boolean start = false;
    Random random = new Random();
    Timer timer;
    static int LEVEL = 1;
    final int arr[] = new int[30];
    static int newNum;
    JButton[] buttons;
    private int buttonIndex;
    public int ans[] = new int[50];
    public boolean animate = false;
    public int var;

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        addButtons();
        StartGame();

    }

    public void StartGame() {
        start = true;
        timer = new Timer(DELAY, this);
        timer.start();
        Sequence();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        g.setColor(Color.white);
    }


    public void draw(Graphics g) {
        if (start) {
            g.setColor(Color.gray);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }


            /*g.setColor(Color.red);
            g.fillRect(0, 0, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.green);
            g.fillRect(UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.blue);
            g.fillRect(0, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.WHITE);
            g.fillRect(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);*/


            if (animate) {
                switch (newNum) {
                    case 1:
                        g.setColor(new Color(0, 0, 0, 178));
                        g.fillRect(0, 0, UNIT_SIZE, UNIT_SIZE);
                        break;
                    case 2:
                        g.setColor(new Color(0, 0, 0, 178));
                        g.fillRect(UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE);
                        break;
                    case 3:
                        g.setColor(new Color(0, 0, 0, 178));
                        g.fillRect(0, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        break;
                    case 4:
                        g.setColor(new Color(0, 0, 0, 178));
                        g.fillRect(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                        break;
                }
                animate = false;
            }


        } else {
            gameOver(g);
        }
    }

    public void Sequence() {
        int lowerBound = 1;
        int upperBound = 4;
        //newNum = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        newNum = (int) Math.floor(Math.random() * 4 + 1);
        System.out.println("New generated number is :" + newNum);
        arr[LEVEL - 1] = newNum;
        System.out.println("VALUE OF LEVEL IN SEQUENCE" + LEVEL);
        animate = true;

    }


    public void addButtons() {
        buttons = new JButton[4];


        // Create buttons and set their properties
        buttons[0] = new JButton();
        buttons[0].setBounds(0, 0, UNIT_SIZE, UNIT_SIZE);
        buttons[0].setContentAreaFilled(true);
        buttons[0].setBorderPainted(false);
        buttons[0].setFocusPainted(false);
        buttons[0].setBackground(Color.RED);
        buttons[0].addActionListener(new ButtonClickAction());

        buttons[1] = new JButton();
        buttons[1].setBounds(UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE);
        buttons[1].setContentAreaFilled(true);
        buttons[1].setBorderPainted(false);
        buttons[1].setFocusPainted(false);
        buttons[1].setBackground(Color.GREEN);
        buttons[1].addActionListener(new ButtonClickAction());

        buttons[2] = new JButton();
        buttons[2].setBounds(0, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        buttons[2].setContentAreaFilled(true);
        buttons[2].setBorderPainted(false);
        buttons[2].setFocusPainted(false);
        buttons[2].setBackground(Color.BLUE);
        buttons[2].addActionListener(new ButtonClickAction());

        buttons[3] = new JButton();
        buttons[3].setBounds(UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
        buttons[3].setContentAreaFilled(true);
        buttons[3].setBorderPainted(false);
        buttons[3].setFocusPainted(false);
        buttons[3].setBackground(Color.WHITE);
        buttons[3].addActionListener(new ButtonClickAction());


        // Set layout manager to null
        this.setLayout(null);

        //add button to screen
        for (JButton button : buttons) {
            this.add(button);
        }
    }


    public void gameOver(Graphics g) {
        System.out.println("Game over");

    }

    public void checkSequence(int num) {
        // write a code here to compare each element of ans with values currently pushing in stack and display some message if the value dont match the sequence od arr


        //boolean isEqual = true;
        //for(int i=0;i<LEVEL;i++) {
        //  if ((ans[i]) != arr[i]) {
        //    isEqual = false;
        // }
        if (ans[num] == arr[num]) {
            //if (!isEqual) {
            if (arr.length == ans.length) {
                System.out.println("Correct sequence!");
                Sequence();
                LEVEL++;
            } else {
                System.out.println("Incorrect sequence!");
            }
        }

    }


            //ans = null;








    @Override
    public void actionPerformed(ActionEvent e) {
        if(start){
            //checkSequence();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
    }
    class ButtonClickAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int buttonIndex = -1;

            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i] == button) {
                    buttonIndex = i;
                    break;
                }
            }

            if (buttonIndex != -1) {
                int var = buttonIndex+1;
                ans[LEVEL-1] = var; // Store the button index in the ans[] array
                System.out.println("Button clicked : "+ var);
                checkSequence((ans.length)-1);


            }
        }
    }
}
