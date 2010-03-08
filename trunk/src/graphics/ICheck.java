package graphics;

import javax.swing.*;

@SuppressWarnings("unused")
public class ICheck {

    static ImageIcon winRed, winGreen;

    // check the first empty place in the column
    public static int checkColumn(int xIndex, ImageIcon img, JLabel[][] jlbl) {
        int j = 0, i = 0;
        for (i = 1; i < 8; i++) {
            if (xIndex == i) {
                for (j = 5; j >= 0; j--) {
                    if (jlbl[j][xIndex - 1].getIcon() == img) {
                        return j;
                    }
                }
            }
        }
        return -1; // mean that the column is full
    }

    // check if the board is full
    public static boolean checkFull(int full) {
        if (full == 42) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkWin method is used for checking the board for a winner it uses
     * Three main variables, "r" for storing the value of the player (takes
     * red or white) "x" and "y" for the axis X and axis Y it returns "r"
     * */
    public static String checkwin(JLabel[][] arr, ImageIcon red, ImageIcon green) {
        winRed = new ImageIcon("src/images/red_win.png");
        winGreen = new ImageIcon("src/images/green_win.png");

        String winner = "none";
        winner = checkVertical(arr, red, green);
        if(winner == "none")
            winner = checkHorizontal(arr, red, green);
        if(winner == "none")
                winner = CheckDiagonalLeft(arr, red, green);
        if(winner == "none")
            winner = CheckDiagonalRight(arr, red, green);
        return winner;
    }

    public static String checkVertical(JLabel[][] arr, ImageIcon red, ImageIcon green) {

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 6; j++) {
                if (arr[i][j].getIcon() == red || arr[i][j].getIcon() == green) {
                    if (arr[i][j].getIcon() == red && arr[i + 1][j].getIcon() == red && arr[i + 2][j].getIcon() == red && arr[i + 3][j].getIcon() == red) {
                        arr[i][j].setIcon(winRed);
                        arr[i + 1][j].setIcon(winRed);
                        arr[i + 2][j].setIcon(winRed);
                        arr[i + 3][j].setIcon(winRed);
                        return "red";
                    } else if (arr[i][j].getIcon() == green && arr[i + 1][j].getIcon() == green && arr[i + 2][j].getIcon() == green && arr[i + 3][j].getIcon() == green) {
                        arr[i][j].setIcon(winGreen);
                        arr[i + 1][j].setIcon(winGreen);
                        arr[i + 2][j].setIcon(winGreen);
                        arr[i + 3][j].setIcon(winGreen);
                        return "green";
                    }
                }
            }
        }
        return "none";
    }

    public static String checkHorizontal(JLabel[][] arr, ImageIcon red, ImageIcon green) {
		String win = "none";
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= 3; j++) {
                if (arr[i][j].getIcon() == red || arr[i][j].getIcon() == green) {
                    if (arr[i][j].getIcon() == red && arr[i][j + 1].getIcon() == red && arr[i][j + 2].getIcon() == red && arr[i][j + 3].getIcon() == red) {
                        arr[i][j].setIcon(winRed);
                        arr[i][j + 1].setIcon(winRed);
                        arr[i][j + 2].setIcon(winRed);
                        arr[i][j + 3].setIcon(winRed);
                        return "red";

                    } else if (arr[i][j].getIcon() == green && arr[i][j + 1].getIcon() == green && arr[i][j + 2].getIcon() == green && arr[i][j + 3].getIcon() == green) {
                        arr[i][j].setIcon(winGreen);
                        arr[i][j + 1].setIcon(winGreen);
                        arr[i][j + 2].setIcon(winGreen);
                        arr[i][j + 3].setIcon(winGreen);
                        return "green";
                    }
                }
            }
        }
        return "none";
    }

    public static String CheckDiagonalLeft(JLabel[][] arr, ImageIcon red, ImageIcon green) {
        String win = "none";
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 3; j++) {
                if (arr[i][j].getIcon() == red || arr[i][j].getIcon() == green) {
                    if (arr[i][j].getIcon() == red && arr[i + 1][j + 1].getIcon() == red && arr[i + 2][j + 2].getIcon() == red && arr[i + 3][j + 3].getIcon() == red) {
                        arr[i][j].setIcon(winRed);
                        arr[i + 1][j + 1].setIcon(winRed);
                        arr[i + 2][j + 2].setIcon(winRed);
                        arr[i + 3][j + 3].setIcon(winRed);
                        return "red";

                    } else if (arr[i][j].getIcon() == green && arr[i + 1][j + 1].getIcon() == green && arr[i + 2][j + 2].getIcon() == green && arr[i + 3][j + 3].getIcon() == green) {
                        arr[i][j].setIcon(winGreen);
                        arr[i + 1][j + 1].setIcon(winGreen);
                        arr[i + 2][j + 2].setIcon(winGreen);
                        arr[i + 3][j + 3].setIcon(winGreen);
                        return "green";
                    }
                }
            }
        }
        return "none";

    }

    public static String CheckDiagonalRight(JLabel[][] arr, ImageIcon red, ImageIcon green) {
        String win = "none";
        for (int i = 3; i <= 5; i++) {
            for (int j = 0; j <= 3; j++) {
                if (arr[i][j].getIcon() == red || arr[i][j].getIcon() == green) {
                    if (arr[i][j].getIcon() == red && arr[i - 1][j + 1].getIcon() == red && arr[i - 2][j + 2].getIcon() == red && arr[i - 3][j + 3].getIcon() == red) {
                        arr[i][j].setIcon(winRed);
                        arr[i - 1][j + 1].setIcon(winRed);
                        arr[i - 2][j + 2].setIcon(winRed);
                        arr[i - 3][j + 3].setIcon(winRed);
                        return "red";

                    } else if (arr[i][j].getIcon() == green && arr[i - 1][j + 1].getIcon() == green && arr[i - 2][j + 2].getIcon() == green && arr[i - 3][j + 3].getIcon() == green) {
                        arr[i][j].setIcon(winGreen);
                        arr[i - 1][j + 1].setIcon(winGreen);
                        arr[i - 2][j + 2].setIcon(winGreen);
                        arr[i - 3][j + 3].setIcon(winGreen);
                        return "green";
                    }
                }
            }
        }
        return "none";

    }
}
