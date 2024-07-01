import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp {
    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JLabel timerLabel;
    private JButton nextButton;
    
    private String[][] questions = {
        {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid"},
        {"Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Saturn"},
        {"Who wrote 'Hamlet'?", "Charles Dickens", "J.K. Rowling", "William Shakespeare", "Mark Twain"}
    };
    
    private String[] correctAnswers = {"Paris", "Mars", "William Shakespeare"};
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLimit = 10; // seconds for each question
    private int timeLeft;
    private Timer timer;
    
    public QuizApp() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        
        timerLabel = new JLabel("Time left: " + timeLimit + " seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        
        frame.setLayout(new BorderLayout());
        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(optionsPanel, BorderLayout.CENTER);
        frame.add(timerLabel, BorderLayout.SOUTH);
        frame.add(nextButton, BorderLayout.PAGE_END);
        
        loadQuestion();
        
        frame.setVisible(true);
    }
    
    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            String[] questionData = questions[currentQuestionIndex];
            questionLabel.setText(questionData[0]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(questionData[i + 1]);
            }
            optionsGroup.clearSelection();
            timeLeft = timeLimit;
            startTimer();
        } else {
            showResult();
        }
    }
    
    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timerLabel.setText("Time left: " + --timeLeft + " seconds");
                } else {
                    timer.stop();
                    checkAnswer();
                }
            }
        });
        timer.start();
    }
    
    private void checkAnswer() {
        timer.stop();
        String selectedAnswer = null;
        for (JRadioButton button : optionButtons) {
            if (button.isSelected()) {
                selectedAnswer = button.getText();
                break;
            }
        }
        if (selectedAnswer != null && selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
            score++;
        }
        currentQuestionIndex++;
        loadQuestion();
    }
    
    private void showResult() {
        StringBuilder resultText = new StringBuilder("Your score: " + score + "/" + questions.length + "\n\n");
        for (int i = 0; i < questions.length; i++) {
            resultText.append("Q").append(i + 1).append(": ").append(questions[i][0]).append("\n");
            resultText.append("Correct answer: ").append(correctAnswers[i]).append("\n\n");
        }
        JOptionPane.showMessageDialog(frame, resultText.toString(), "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp();
            }
        });
    }
}
