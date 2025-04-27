import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private final int BOARD_SIZE = 3;
    private JButton[][] buttons;
    private boolean playerX = true; // true for X, false for O
    private JLabel statusLabel;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setResizable(false);

        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 50));
                button.addActionListener(this);
                buttons[row][col] = button;
                panel.add(button);
            }
        }

        statusLabel = new JLabel("X's turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().equals("")) {
            return; // Cell already occupied
        }

        if (playerX) {
            clickedButton.setText("X");
            statusLabel.setText("O's turn");
        } else {
            clickedButton.setText("O");
            statusLabel.setText("X's turn");
        }

        playerX = !playerX; // Switch player

        if (checkWin()) {
            statusLabel.setText((playerX ? "O" : "X") + " wins!");
            disableButtons();
        } else if (checkDraw()) {
            statusLabel.setText("It's a draw!");
        }
    }

    private boolean checkWin() {
        String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = buttons[i][j].getText();
            }
        }

        // Check rows and columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals("")) {
                return true; // Row win
            }
            if (board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals("")) {
                return true; // Column win
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals("")) {
            return true; // Diagonal 1 win
        }
        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals("")) {
            return true; // Diagonal 2 win
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false; // Not a draw, still empty cells
                }
            }
        }
        return true; // All cells are occupied and no winner
    }

    private void disableButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
}
