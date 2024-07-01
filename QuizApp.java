package Task4;
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
        {"Which of the following is not a Java feature ?" , "A) Object-oriented" , "B) Use of pointers", "C) Platform independent",
        "D) Dynamic and Extensible"} , 
        {"Which data type is used to create a variable that should store text?" , "A) String" , "B) myString" , "C) txt" , "D) str"},
        {"What is the size of an int variable in Java?" , "A) 8-bit", "B) 16-bit" , "C) 32-bit" , "D) 64-bit"}, 
        {"Which method must be implemented by all threads?" , "A) start()" , "B) stop()" , "C) run()" , "D) execute()"},
        {"What is the default value of a local variable in Java?" , "A) 0" , "B) Null" , "C) Undefined" , "D) Depends on the type"}
    };

    private String[] correctAnswers = { "B) Use of pointers" , "A) String" , "C) 32-bit" , "C) run()" , "C) Undefined"};

    private int score = 0;
    private int timeLeft;
    private int timeLimit = 30;
    private int currentQuestionIndex=0;
    private Timer timer;

    public QuizApp(){
        frame = new JFrame("Quiz Application ");
        frame.setSize(400,200);

        questionLabel = new JLabel("",SwingConstants.CENTER);
        questionLabel.setFont(new Font("Helvetica" , Font.PLAIN , 22));

        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        JPanel panel = new JPanel(new GridLayout(5,1));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            panel.add(optionButtons[i]);            
        }

        timerLabel = new JLabel("Time Left : " + timeLeft + "seconds " + SwingConstants.CENTER);
        timerLabel.setFont(new Font("Helvetica" , Font.PLAIN , 22));

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    checkAnswer();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(questionLabel , BorderLayout.NORTH);
        frame.add(panel , BorderLayout.CENTER);
        frame.add(timerLabel ,BorderLayout.EAST);
        frame.add(nextButton , BorderLayout.PAGE_END);
        
        loadQuestion();
        frame.setVisible(true);

    }

    private void loadQuestion (){
        if (currentQuestionIndex < questions.length){
            String[] questionData = questions[currentQuestionIndex];
            questionLabel.setText(questionData[0]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(questionData[i+1]);
            }
            optionsGroup.clearSelection();
            timeLeft = timeLimit;
            startTimer();
        }else{
            showResult();
        }
    }

    private void startTimer(){
        timer = new Timer( 1000, new ActionListener(){
            public void actionPerformed( ActionEvent e){
                if(timeLeft > 0 ){
                    timerLabel.setText("Time Left "+ --timeLeft+" seconds ");
                }else{
                    timer.stop();
                    checkAnswer();
                }
            }
        });
        timer.start();
    }

    private void checkAnswer(){
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
