import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Quiz extends JFrame {
    private JTextArea questionArea;
    private JTextField answerField;
    private JButton submitButton;
    private JLabel countLabel;
    private BufferedReader questionReader;
    private BufferedReader answerReader;
    private String currentQuestion;
    private String currentAnswer;
    private int countCorrectAnswers = 0;

    public Quiz() {
        setTitle("Quiz");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        questionArea = new JTextArea(2, 30);
        answerField = new JTextField(30);
        submitButton = new JButton("submit");
        countLabel = new JLabel("Correct answers: " + countCorrectAnswers);

        JPanel questionPanel = new JPanel();
        questionPanel.add(new JLabel("Question: "));
        questionPanel.add(questionArea);

        JPanel answerPanel = new JPanel();
        answerPanel.add(new JLabel("Your answer: "));
        answerPanel.add(answerField);
        answerPanel.add(submitButton);

        JPanel countPanel = new JPanel();
        countPanel.add(countLabel);

        getContentPane().setLayout(new GridLayout(3, 1));
        getContentPane().add(questionPanel);
        getContentPane().add(answerPanel);
        getContentPane().add(countPanel);

        try {
            questionReader = new BufferedReader(new FileReader("questions.txt"));
            answerReader = new BufferedReader(new FileReader("answers.txt"));
        } catch(IOException e) {
            JOptionPane.showMessageDialog(Quiz.this, "Error loading questions and answers: " + e.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        submitButton.addActionListener(new SubmitButtonListener());
        loadQuestion();
    }

    private void loadQuestion() {
        try{
            String question = questionReader.readLine();
            String answer = answerReader.readLine();

            if (question != null && answer != null) {
                currentQuestion = question;
                currentAnswer = answer;

                questionArea.setText(currentQuestion);
                answerField.setText("");
                answerField.requestFocus();
            } else {
                questionReader.close();
                answerReader.close();
                JOptionPane.showMessageDialog(this, "End of the game! You answered " + countCorrectAnswers + " questions correctly.");
                System.exit(0);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading next question: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userAnswer = answerField.getText().trim();

            if (!userAnswer.equalsIgnoreCase(currentAnswer))
                JOptionPane.showMessageDialog(Quiz.this, "Incorrect answer!");
            else {
                JOptionPane.showMessageDialog(Quiz.this, "Correct answer!");
                countCorrectAnswers++;
                countLabel.setText("Correct Answers: " + countCorrectAnswers);
            }
            loadQuestion();
        }
    }
}
