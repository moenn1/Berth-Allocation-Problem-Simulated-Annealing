package Solution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static Solution.Main.*;


public class Data {

    void readData(String arq1, String arq2, int nB, int nS) {
        try {
            Scanner f1 = new Scanner(new File(arq1));
            nBerths = nB;
            nShips = nS;
            //delimiter is semicolon
            f1.useDelimiter(";");
            String line;
            inputMatrix = new int[nBerths][nShips];
            //read input from file and store in inputMatrix
            for (int i = 0; i < nBerths; i++) {
                for (int j = 0; j < nShips; j++) {
                    line = f1.next();
                    inputMatrix[i][j] = Integer.parseInt(line);
                }
            }
            //close f1
            f1.close();
            //open new file arq2 to read arrival time
            Scanner f2 = new Scanner(new File(arq2));
            //delimiter is semicolon
            f2.useDelimiter(";");
            arrivalTime = new int[nShips];
            //read arrival time from file and store in arrivalTime
            for (int i = 0; i < nShips; i++) {
                line = f2.next();
                arrivalTime[i] = Integer.parseInt(line);
            }
            System.out.println("Data read successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void printData() {
        System.out.println("nBerths = " + nBerths);
        System.out.println("nShips = " + nShips);
        System.out.println("Here is the matrix for the handling times: ");
        for (int i = 0; i < nBerths; i++) {
            for (int j = 0; j < nShips; j++) {
                System.out.print(inputMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Here is the matrix for the arrival times: ");
        for (int i = 0; i < nShips; i++) {
            System.out.print(arrivalTime[i] + " ");
        }
        System.out.println();
    }

    public static void printSolution(Solution s) {

        int countBerth = 0;
        int countShips = 0;
        System.out.println("< ----------------------------- Solution ---------------------------- >");
        System.out.println();

        for (int i = 0; i < nBerths; i++) {
            if (s.nbShipsOnBerth[i] != 0) {
                countBerth++;
            }
        }

        System.out.println("Number of berths used.........................: " + countBerth);
        System.out.println();

        for (int i = 0; i < nBerths; i++) {
            if (s.nbShipsOnBerth[i] != 0) {
                countShips += s.nbShipsOnBerth[i];
            }
        }

        System.out.println("Number of ships serviced..........................: " + countShips);
        System.out.println();

        System.out.println("Cost of solution.............................: " + s.cost);
        System.out.println();


        for (int i = 0; i < nBerths; i++) {
            System.out.println("Number of ships serviced at the berth " + (i + 1) + ": " + s.nbShipsOnBerth[i]);
        }
        System.out.println();

        //Print data into a file
        try {
            File file = new File("src\\Solution\\Solution.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("< ----------------------------- Solution ---------------------------- >");
            bw.newLine();
            bw.newLine();
            bw.write("Number of berths used.........................: " + countBerth);
            bw.newLine();
            bw.newLine();
            bw.write("Number of ships serviced..........................: " + countShips);
            bw.newLine();
            bw.newLine();
            bw.write("Cost of solution.............................: " + s.cost);
            bw.newLine();
            bw.newLine();
            for (int i = 0; i < nBerths; i++) {
                bw.write("Number of ships serviced at the berth " + (i + 1) + ": " + s.nbShipsOnBerth[i]);
                bw.newLine();
            }
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}