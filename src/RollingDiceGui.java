import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RollingDiceGui extends JFrame {
    private int leftScore = 0;
    private int rightScore = 0;
    private JLabel leftScoreLabel;
    private JLabel rightScoreLabel;
    private JLabel winnerLabel;

    public RollingDiceGui() {
        super("Rolling Double Dice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700)); // Increased width for scoreboard
        setResizable(false);
        setLocationRelativeTo(null);

        addGuiComponents();
        pack(); // Pack after adding components to ensure proper layout
    }

    private void addGuiComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel dicePanel = new JPanel(null);

        // Banner
        JLabel bannerImg = ImgService.loadImage("/resources/banner.png");
        bannerImg.setBounds(45, 25, 600, 100);
        dicePanel.add(bannerImg);

        // Dices
        JLabel diceOneImg = ImgService.loadImage("/resources/dice1.png");
        diceOneImg.setBounds(100, 200, 200, 200);
        dicePanel.add(diceOneImg);

        JLabel diceTwoImg = ImgService.loadImage("/resources/dice1.png");
        diceTwoImg.setBounds(390, 200, 200, 200);
        dicePanel.add(diceTwoImg);

        // Winner Label
        winnerLabel = new JLabel(" ");
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setForeground(Color.BLACK); // Color coding
        winnerLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Reduced font size
        winnerLabel.setBounds(250, 430, 200, 50); // Adjusted position
        dicePanel.add(winnerLabel);

        // Roll Dices
        Random rand = new Random();
        JButton rollButton = new JButton("Roll!");
        rollButton.setBounds(250, 480, 200, 50); // Adjusted position
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rollButton.setEnabled(false);

                // roll for 3 seconds
                long startTime = System.currentTimeMillis();
                Thread rollThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long endTime = System.currentTimeMillis();
                        try {
                            int diceOne = 0;
                            int diceTwo = 0;
                            while ((endTime - startTime) / 1000F < 3) {
                                // roll dice
                                diceOne = rand.nextInt(6) + 1;
                                diceTwo = rand.nextInt(6) + 1;

                                // update dice images
                                ImgService.updateImage(diceOneImg, "/resources/dice" + diceOne + ".png");
                                ImgService.updateImage(diceTwoImg, "/resources/dice" + diceTwo + ".png");

                                repaint();
                                revalidate();

                                // sleep thread
                                Thread.sleep(68);

                                endTime = System.currentTimeMillis();
                            }

                            // Update scores
                            if (diceOne > diceTwo) {
                                leftScore++;
                            } else if (diceTwo > diceOne) {
                                rightScore++;
                            }

                            updateScoreboard();
                            updateWinnerLabel();

                            rollButton.setEnabled(true);
                        } catch (InterruptedException e) {
                            System.out.println("Threading Error: " + e);
                        }
                    }
                });
                rollThread.start();
            }
        });
        rollButton.setBackground(Color.RED);
        rollButton.setForeground(Color.WHITE);
        dicePanel.add(rollButton);

        mainPanel.add(dicePanel, BorderLayout.CENTER);

        // Scoreboard
        JPanel scoreboardPanel = new JPanel(new GridLayout(1, 2));
        leftScoreLabel = new JLabel("Left Score: " + leftScore);
        leftScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftScoreLabel.setForeground(Color.BLUE); // Color coding
        rightScoreLabel = new JLabel("Right Score: " + rightScore);
        rightScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightScoreLabel.setForeground(Color.RED); // Color coding
        scoreboardPanel.add(leftScoreLabel);
        scoreboardPanel.add(rightScoreLabel);
        mainPanel.add(scoreboardPanel, BorderLayout.NORTH);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        mainPanel.add(exitButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void updateScoreboard() {
        leftScoreLabel.setText("Left Score: " + leftScore);
        rightScoreLabel.setText("Right Score: " + rightScore);
    }

    private void updateWinnerLabel() {
        if (leftScore > rightScore) {
            winnerLabel.setText("Left Player is winning!");
        } else if (rightScore > leftScore) {
            winnerLabel.setText("Right Player is winning!");
        } else {
            winnerLabel.setText("It's a tie!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RollingDiceGui().setVisible(true);
            }
        });
    }
}

