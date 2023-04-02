package Solution;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static Solution.Main.arrivalTime;

public class Main {
    public static int nBerths, nShips;
    public static int[][] inputMatrix;

    public static int[] arrivalTime;

    public static Solution s;

    Solution initSolution(Solution s)
    {
        int j;
        Random r = new Random();

        for (int i = 0; i < nShips; i++)
        {
            do
            {
                j = r.nextInt(nBerths);
            }
            while (inputMatrix[j][i] == 0);

            s.MAT[j][s.nbShipsOnBerth[j]] = i;
            s.nbShipsOnBerth[j]++;
        }
        for (int i = 0; i < nBerths; i++)
        {
            bubbleSort(s.MAT[i], s.nbShipsOnBerth[i]);
        }
        for (int i=0; i<nBerths; i++)
        {
            for (int k=0; k<s.nbShipsOnBerth[i]; k++)
            {
                //if(s.MAT[i][k]!=0)
                System.out.print(s.MAT[i][k] + " ");
            }
        }
        System.out.println();
        s.costFunction();
        System.out.println("The cost of this solution is: "+ s.cost);

        return s;
    }



    void genRandomSol(Solution s)
    {
        int pship, bship, berthN, ship;


        do
            bship = (int) (Math.random() * nBerths);
        while (s.nbShipsOnBerth[bship] == 0);

        pship = (int) (Math.random() * s.nbShipsOnBerth[bship]);

        ship = s.MAT[bship][pship];

        do
            berthN = (int) (Math.random() * nBerths);
        while (inputMatrix[berthN][ship] == 0 || bship == berthN);


        for (int j = pship; j < s.nbShipsOnBerth[bship]; j++)
        {
            s.MAT[bship][j] = s.MAT[bship][j + 1];
        }
        s.nbShipsOnBerth[bship]--;

        s.MAT[berthN][s.nbShipsOnBerth[berthN]] = ship;
        s.nbShipsOnBerth[berthN]++;

        bubbleSort(s.MAT[berthN], s.nbShipsOnBerth[berthN]);

        s.costFunction();
    }

    void bubbleSort(int row[], int qtd)
    {
        int flag, aux;
        flag = 1;

        while (flag == 1)
        {
            flag = 0;
            for (int j = 0; j < qtd - 1; j++)
            {

                if (arrivalTime[row[j]] > arrivalTime[row[j + 1]])
                {
                    flag = 1;
                    aux = row[j];
                    row[j] = row[j + 1];
                    row[j + 1] = aux;
                }
            }
        }
    }

    void simAnnealing(int N) {
        s = new Solution();
        System.out.println("Le solution initial (Generation 0): ");
        s = initSolution(s);
        s.costFunction();
        int T = N-1, T0 = 1000, T1 = 0, i = 0, j = 0;
        double r = 0;
        double delta = 0;
        double p = 0;
        int n = 0;
        int count = 2;
        Solution s1 = new Solution();
        System.out.print("Generation 1: ");
        Solution.copySolution(s1, s);

        //s.copySolution(s1, s);
        while (T > T1)
        {
            n = 0;
            while (n < 100)
            {

                genRandomSol(s1);
                s.costFunction();
                //s1.costFunction();
                delta = s1.cost - s.cost;
                if (delta < 0)
                {
                    Solution.copySolution(s, s1);
                }
                else
                {
                    r = Math.random();
                    p = Math.exp(-delta / T);
                    if (r < p)
                    {
                        Solution.copySolution(s, s1);
                    }
                }
                n++;
            }
            T--;
            System.out.print("Generation " + (count) + ": ");
            for (int l=0; l<nBerths; l++)
            {
                for (int k=0; k<s.nbShipsOnBerth[l]; k++)
                {
                    //if(s.MAT[l][k]!=0)
                    System.out.print(s.MAT[l][k] + " ");
                }
            }
            System.out.println();
            System.out.println("The cost of this solution is: " + s.cost);
            count++;
        }
        Data.printSolution(s);
    }

    public static void main(String[] args)  {
        int nbShips, nbBerths;
        String fileDir1, fileDir2;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of ships: ");
        nbShips = scan.nextInt();
        System.out.println("Enter the number of berths: ");
        nbBerths = scan.nextInt();
        //System.out.println("Enter the file directory: ");
        fileDir2 = "src\\Solution\\datesarrivee.csv";
        fileDir1 = "src\\Solution\\dureedeservice.csv";
        Main main = new Main();
        Data data = new Data();
        data.readData(fileDir1, fileDir2, nbBerths, nbShips);
        data.printData();
        System.out.println();
        System.out.println("Enter the number of iterations: ");
        int N = scan.nextInt();
        main.simAnnealing(N);
    }
}




class Solution{
    int MAT[][];
    int nbShipsOnBerth[];

    int cost;

    public Solution(){
        MAT = new int[Main.nBerths][Main.nShips];
        nbShipsOnBerth = new int[Main.nBerths];
        cost = 0;
    }


    public static void copySolution(Solution s1, Solution s2) {

        for (int i = 0; i < Main.nBerths; i++) {
            for (int j = 0; j < Main.nShips; j++) {
                s1.MAT[i][j] = s2.MAT[i][j];
            }
        }
        for (int i = 0; i < Main.nBerths; i++) {
            s1.nbShipsOnBerth[i] = s2.nbShipsOnBerth[i];
        }
        s1.cost = s2.cost;
    }


    public void costFunction() {
        int cost = 0;
        //calculate cost function
        for (int i = 0; i < Main.nBerths; i++) {
            for (int j = 0; j < nbShipsOnBerth[i]; j++) {
                cost += Main.inputMatrix[i][MAT[i][j]];
            }
        }

        this.cost = cost;
        //System.out.println(cost);
    }


    public Solution swap(int i, int j, Solution s2) {
        int aux;
        for (int k = 0; k < Main.nShips; k++) {
            aux = s2.MAT[i][k];
            s2.MAT[i][k] = s2.MAT[j][k];
            s2.MAT[j][k] = aux;
        }
        return s2;
    }


}