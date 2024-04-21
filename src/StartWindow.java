import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartWindow extends JFrame {
    public StartWindow() {
        super("Dice Duel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Welcome to Two Player Dice!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Create a panel for the dice-like square
        JPanel dicePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw white square background with rounded edges
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(250, 50, 300, 300, 50, 50); // Moved closer to the top

                // Draw black square border with rounded edges
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawRoundRect(250, 50, 300, 300, 50, 50); // Moved closer to the top

                // Calculate circle positions
                int circleSize = 100;
                int paddingX = (300 - 2 * circleSize) / 3;
                int paddingY = (300 - 2 * circleSize) / 3;

                // Draw black circles
                g2d.setColor(Color.BLACK);
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        int x = 250 + paddingX * (i + 1) + circleSize * i;
                        int y = 50 + paddingY * (j + 1) + circleSize * j; // Moved closer to the top
                        g2d.fillOval(x, y, circleSize, circleSize);
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 700);
            }
        };
        mainPanel.add(dicePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(Color.RED); // Change button color to red
        startButton.setForeground(Color.WHITE);
        startButton.setToolTipText("Click to start the game");
        startButton.addActionListener((ActionEvent event) -> {
            showInstructions();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(200, 60));
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setBackground(Color.RED); // Change button color to red
        exitButton.setForeground(Color.WHITE);
        exitButton.setToolTipText("Click to exit the game");
        exitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton); // Added exit button

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void showInstructions() {
        this.setVisible(false);
        InstructionWindow instructionWindow = new InstructionWindow();
        instructionWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartWindow().setVisible(true);
        });
    }
}

class InstructionWindow extends JFrame {
    public InstructionWindow() {
        super("Instructions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JTextArea instructionText = new JTextArea
                ("Instructions:\n" +
                        "1. MAX Of Two Players Can Participate!\n" +
                        "2. Pick Your Side! (Either Left or Right).\n" +
                        "3. Roll The Dice & The Player With The Higher Roll Wins The Round.");

        instructionText.setEditable(false);
        instructionText.setOpaque(false);
        instructionText.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionText.setForeground(Color.BLACK);
        instructionText.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton continueButton = new JButton("Continue");
        continueButton.setPreferredSize(new Dimension(200, 60));
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        continueButton.setBackground(Color.RED); // Change button color to red
        continueButton.setForeground(Color.WHITE);
        continueButton.setToolTipText("Click to continue to the game");
        continueButton.addActionListener((ActionEvent event) -> {
            new RollingDiceGui().setVisible(true);
            dispose();
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 60));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBackground(Color.RED); // Change button color to red
        backButton.setForeground(Color.WHITE);
        backButton.setToolTipText("Click to go back to the start screen");
        backButton.addActionListener((ActionEvent event) -> {
            this.setVisible(false);
            StartWindow startWindow = new StartWindow();
            startWindow.setVisible(true);
        });

        buttonPanel.add(continueButton);
        buttonPanel.add(backButton);

        mainPanel.add(instructionText, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}

