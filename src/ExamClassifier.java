import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ExamClassifier extends JFrame {
    private final JTextField txtMath;
    private final JTextField txtScience;
    private final JTextField txtEnglish;
    private final JTextField txtLearningRate;
    private final JTextField txtEpochs;
    private final JTextField txtGoal;
    private final JLabel lblResult;
    private final JLabel lblAccuracy;
    private final JLabel lblPerformance;
    private double w1;
    private double w2;
    private double w3;
    private double bias;
    double learningRate;
    int epochs;
    int goal;
    private int tp, tn, fp, fn;

    public ExamClassifier() {
        setTitle("Exam Score Classifier");
        setSize(380, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblMath = new JLabel("Math:");
        txtMath = new JTextField();
        JLabel lblScience = new JLabel("Science:");
        txtScience = new JTextField();
        JLabel lblEnglish = new JLabel("English:");
        txtEnglish = new JTextField();
        JLabel lblLearningRate = new JLabel("Learning Rate:");
        txtLearningRate = new JTextField("0.01");
        JLabel lblEpochs = new JLabel("Epochs:");
        txtEpochs = new JTextField("1000");
        JLabel lblGoal = new JLabel("Goal:");
        txtGoal = new JTextField("60");

        inputPanel.add(lblMath);
        inputPanel.add(txtMath);
        inputPanel.add(lblScience);
        inputPanel.add(txtScience);
        inputPanel.add(lblEnglish);
        inputPanel.add(txtEnglish);
        inputPanel.add(lblLearningRate);
        inputPanel.add(txtLearningRate);
        inputPanel.add(lblEpochs);
        inputPanel.add(txtEpochs);
        inputPanel.add(lblGoal);
        inputPanel.add(txtGoal);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnTrain = new JButton("Train");
        JButton btnTest = new JButton("Test");
        JButton userInput = new JButton("User Input");
        JButton btnShowConfusionMatrix = new JButton("Confusion Matrix");


        buttonPanel.add(btnTrain);
        buttonPanel.add(btnTest);
        buttonPanel.add(userInput);
        buttonPanel.add(btnShowConfusionMatrix);

        JPanel resultPanel = new JPanel(new BorderLayout());
        lblResult = new JLabel("Result: N/A");
        lblAccuracy = new JLabel("Accuracy: N/A");
        lblPerformance = new JLabel("Performance: N/A");
        lblResult.setFont(new Font("Arial", Font.BOLD, 14));
        lblAccuracy.setFont(new Font("Arial", Font.BOLD, 14));
        lblPerformance.setFont(new Font("Arial", Font.BOLD, 14));
        resultPanel.add(lblResult, BorderLayout.NORTH);
        resultPanel.add(lblAccuracy, BorderLayout.CENTER);
        resultPanel.add(lblPerformance, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(resultPanel, BorderLayout.NORTH);

        btnTrain.addActionListener(e -> {
            try {
                trainModel();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(ExamClassifier.this,
                        "Please ensure that the field is filled correctly.",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTest.addActionListener(e -> {
            try {
                testModel();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(ExamClassifier.this,
                        "Please ensure that the field is filled correctly.",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        userInput.addActionListener(e -> {
            try {
                userInput(new Exam(Integer.parseInt(txtMath.getText()),
                        Integer.parseInt(txtScience.getText()),
                        Integer.parseInt(txtEnglish.getText()),
                        Double.parseDouble(txtGoal.getText())));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(ExamClassifier.this,
                        "Please ensure that the field is filled correctly.",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            }
        });




        btnShowConfusionMatrix.addActionListener(e -> showConfusionMatrix());

        getContentPane().setBackground(new Color(240, 240, 240));
        inputPanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(new Color(240, 240, 240));
        resultPanel.setBackground(new Color(240, 240, 240));

        btnTrain.setBackground(new Color(0, 153, 0));
        btnTest.setBackground(new Color(255, 102, 102));
        userInput.setBackground(new Color(15, 144, 236));
        btnShowConfusionMatrix.setBackground(new Color(51, 102, 255));
        btnTrain.setForeground(Color.WHITE);
        btnTest.setForeground(Color.WHITE);
        userInput.setForeground(Color.WHITE);
        btnShowConfusionMatrix.setForeground(Color.WHITE);
    }

    private void trainModel() {
        try {
            learningRate = Double.parseDouble(txtLearningRate.getText());
            epochs = Integer.parseInt(txtEpochs.getText());
            goal = Integer.parseInt(txtGoal.getText());

            if (goal < 0 || goal > 100) throw new Exception();

        } catch (Exception exception) {
            JOptionPane.showMessageDialog(ExamClassifier.this,
                    "Please ensure that the field is filled correctly.",
                    "Error Message",
                    JOptionPane.ERROR_MESSAGE);
        }

        ArrayList<Exam> trainingInputs = new ArrayList<>();

        trainingInputs.add(new Exam(43, 40, 27));
        trainingInputs.add(new Exam(61, 45, 4));
        trainingInputs.add(new Exam(67, 5, 54));
        trainingInputs.add(new Exam(31, 12, 49));
        trainingInputs.add(new Exam(94, 55, 89));
        trainingInputs.add(new Exam(89, 18, 78));
        trainingInputs.add(new Exam(22, 41, 43));
        trainingInputs.add(new Exam(41, 0, 82));
        trainingInputs.add(new Exam(100, 42, 62));
        trainingInputs.add(new Exam(12, 10, 11));
        trainingInputs.add(new Exam(100, 24, 92));
        trainingInputs.add(new Exam(17, 37, 41));
        trainingInputs.add(new Exam(77, 79, 38));
        trainingInputs.add(new Exam(80, 33, 85));
        trainingInputs.add(new Exam(29, 51, 77));
        trainingInputs.add(new Exam(50, 72, 24));
        trainingInputs.add(new Exam(100, 84, 27));
        trainingInputs.add(new Exam(43, 32, 81));
        trainingInputs.add(new Exam(72, 89, 5));
        trainingInputs.add(new Exam(28, 7, 64));
        trainingInputs.add(new Exam(22, 36, 27));
        trainingInputs.add(new Exam(47, 26, 19));
        trainingInputs.add(new Exam(77, 100, 92));
        trainingInputs.add(new Exam(70, 35, 98));
        trainingInputs.add(new Exam(5, 78, 95));
        trainingInputs.add(new Exam(78, 6, 87));
        trainingInputs.add(new Exam(37, 17, 1));
        trainingInputs.add(new Exam(77, 95, 66));
        trainingInputs.add(new Exam(65, 18, 71));
        trainingInputs.add(new Exam(85, 42, 95));
        trainingInputs.add(new Exam(34, 8, 25));
        trainingInputs.add(new Exam(43, 26, 2));
        trainingInputs.add(new Exam(39, 55, 27));
        trainingInputs.add(new Exam(36, 1, 15));
        trainingInputs.add(new Exam(3, 55, 20));
        trainingInputs.add(new Exam(28, 58, 89));
        trainingInputs.add(new Exam(41, 89, 12));
        trainingInputs.add(new Exam(89, 20, 2));
        trainingInputs.add(new Exam(70, 14, 56));
        trainingInputs.add(new Exam(9, 34, 54));
        trainingInputs.add(new Exam(14, 27, 95));
        trainingInputs.add(new Exam(19, 82, 63));
        trainingInputs.add(new Exam(93, 90, 96));
        trainingInputs.add(new Exam(22, 12, 73));
        trainingInputs.add(new Exam(34, 77, 58));
        trainingInputs.add(new Exam(7, 21, 22));
        trainingInputs.add(new Exam(28, 99, 8));
        trainingInputs.add(new Exam(71, 9, 47));
        trainingInputs.add(new Exam(8, 79, 1));
        trainingInputs.add(new Exam(88, 1, 78));
        trainingInputs.add(new Exam(10, 68, 87));
        trainingInputs.add(new Exam(54, 98, 78));
        trainingInputs.add(new Exam(41, 51, 6));
        trainingInputs.add(new Exam(98, 82, 58));
        trainingInputs.add(new Exam(52, 60, 73));
        trainingInputs.add(new Exam(73, 17, 96));
        trainingInputs.add(new Exam(73, 87, 49));
        trainingInputs.add(new Exam(61, 100, 36));
        trainingInputs.add(new Exam(97, 75, 32));
        trainingInputs.add(new Exam(49, 100, 57));
        trainingInputs.add(new Exam(74, 92, 55));
        trainingInputs.add(new Exam(91, 41, 54));
        trainingInputs.add(new Exam(98, 55, 57));
        trainingInputs.add(new Exam(78, 36, 85));
        trainingInputs.add(new Exam(46, 64, 88));
        trainingInputs.add(new Exam(27, 95, 97));
        trainingInputs.add(new Exam(59, 68, 55));
        trainingInputs.add(new Exam(68, 97, 94));
        trainingInputs.add(new Exam(71, 66, 86));
        trainingInputs.add(new Exam(96, 75, 73));
        trainingInputs.add(new Exam(87, 34, 76));
        trainingInputs.add(new Exam(80, 60, 87));
        trainingInputs.add(new Exam(55, 95, 72));
        trainingInputs.add(new Exam(79, 57, 59));
        trainingInputs.add(new Exam(93, 87, 71));
        trainingInputs.add(new Exam(97, 29, 82));
        trainingInputs.add(new Exam(65, 88, 29));
        trainingInputs.add(new Exam(99, 95, 66));
        trainingInputs.add(new Exam(39, 61, 85));
        trainingInputs.add(new Exam(63, 36, 90));

        for (Exam trainingInput : trainingInputs) {
            if (trainingInput.getMath() + trainingInput.getScience() + trainingInput.getEnglish() >= goal * 3) {
                trainingInput.setPassOrFail(1);
            } else {
                trainingInput.setPassOrFail(0);
            }
        }

        perceptron(trainingInputs, learningRate, epochs);

        JOptionPane.showMessageDialog(ExamClassifier.this,
                "Model trained successfully.",
                "Successful Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void testModel() {
        ArrayList<Exam> testingInputs = new ArrayList<>();

        testingInputs.add(new Exam(1, 0, 29));
        testingInputs.add(new Exam(21, 1, 48));
        testingInputs.add(new Exam(49, 63, 22));
        testingInputs.add(new Exam(51, 37, 86));
        testingInputs.add(new Exam(20, 8, 0));
        testingInputs.add(new Exam(11, 47, 55));
        testingInputs.add(new Exam(52, 12, 81));
        testingInputs.add(new Exam(95, 40, 14));
        testingInputs.add(new Exam(1, 16, 93));
        testingInputs.add(new Exam(38, 64, 83));
        testingInputs.add(new Exam(72, 19, 39));
        testingInputs.add(new Exam(99, 25, 89));
        testingInputs.add(new Exam(64, 85, 52));
        testingInputs.add(new Exam(92, 52, 53));
        testingInputs.add(new Exam(52, 81, 50));
        testingInputs.add(new Exam(71, 30, 93));
        testingInputs.add(new Exam(99, 71, 19));
        testingInputs.add(new Exam(7, 100, 100));
        testingInputs.add(new Exam(51, 71, 60));
        testingInputs.add(new Exam(84, 24, 90));

        for (Exam testingInput : testingInputs) {
            if (testingInput.getMath() + testingInput.getScience() + testingInput.getEnglish() >= goal * 3) {
                testingInput.setPassOrFail(1);
            } else {
                testingInput.setPassOrFail(0);
            }
        }

        tp = 0; // True Positive
        tn = 0; // True Negative
        fp = 0; // False Positive
        fn = 0; // False Negative

        double bigX;
        double ya;
        double sumOfCorrectData = 0;
        double error;

        for (Exam testingInput : testingInputs) {
            bigX = w1 * testingInput.getMath() + w2 * testingInput.getScience() + w3 * testingInput.getEnglish() + bias;
            ya = (bigX >= 0) ? 1 : 0;
            error = testingInput.getPassOrFail() - ya;
            if (error == 0) sumOfCorrectData++;
            if (ya == 1 && testingInput.getPassOrFail() == 1) tp++;
            else if (ya == 0 && testingInput.getPassOrFail() == 0) tn++;
            else if (ya == 1 && testingInput.getPassOrFail() == 0) fp++;
            else if (ya == 0 && testingInput.getPassOrFail() == 1) fn++;

        }

        JOptionPane.showMessageDialog(this,
                "Confusion Matrix:\n\n" +
                        "True Positive (TP): " + tp + "\n" +
                        "True Negative (TN): " + tn + "\n" +
                        "False Positive (FP): " + fp + "\n" +
                        "False Negative (FN): " + fn,
                "Confusion Matrix",
                JOptionPane.INFORMATION_MESSAGE);


        double accuracy = sumOfCorrectData / testingInputs.size();
        lblAccuracy.setText("Accuracy: " + accuracy);
    }

    private void showConfusionMatrix() {
        String[] columnNames = {"", "Predicted Pass", "Predicted Fail"};
        Object[][] data = {
                {"Actual Pass", tp, fn},
                {"Actual Fail", fp, tn}
        };

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(table), "Confusion Matrix", JOptionPane.PLAIN_MESSAGE);
    }

    private void userInput(Exam exam) {
        double bigXForUserInputData = w1 * exam.getMath() + w2 * exam.getScience() + w3 * exam.getEnglish() + bias;
        String result = (bigXForUserInputData >= 0) ? "Result: Passed" : "Result: Failed";
        lblResult.setText(result);
    }



private void perceptron(ArrayList<Exam> exams, double learningRate, int epochs) {
        Random random = new Random();
        w1 = random.nextDouble() - 0.5;
        w2 = random.nextDouble() - 0.5;
        w3 = random.nextDouble() - 0.5;
        bias = goal;
        double ya;
        double bigX;
        double error;
        double totalError = 0;

        for (int i = 0; i < epochs; i++) {
            for (Exam exam : exams) {
                bigX = w1 * exam.getMath() + w2 * exam.getScience() + w3 * exam.getEnglish() + bias;
                ya = (bigX >= 0) ? 1 : 0;
                error = exam.getPassOrFail() - ya;
                totalError += Math.pow(error, 2);
                w1 = w1 + learningRate * exam.getMath() * error;
                w2 = w2 + learningRate * exam.getScience() * error;
                w3 = w3 + learningRate * exam.getEnglish() * error;
                bias = bias + learningRate * goal * error;
                System.out.println("w1: " + w1 + ", w2: " + w2 + ", w3: " + w3 + ", bias: " + bias);
            }
        }

        double performance = totalError / epochs;
        lblResult.setText("Result: N/A");
        lblAccuracy.setText("Accuracy: N/A");
        lblPerformance.setText("Performance: " + performance);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExamClassifier().setVisible(true));
    }
}