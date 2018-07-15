import java.util.Random;
import java.util.Scanner;

public class Solver {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("n-Queen Solver\n");
        int g = 10000;
        long cTime;
        long[] sTime = new long[g];
        long[] aTime = new long[g];
        boolean[] resSteep = new boolean[g];
        boolean[] resAnneal = new boolean[g];

        System.out.print("Enter number of queens: ");
        int n = kb.nextInt();

        System.out.println("\nSteepest Ascent: \n");
        for(int i=0; i<g; i++) {
            cTime = System.nanoTime();
            //System.out.println();
            resSteep[i] = steepest(randomize(n));
            sTime[i] = timeElapsed(cTime);
        }

        System.out.println("\nSimulated Annealing: \n");
        for(int i=0; i<g; i++) {
            cTime = System.nanoTime();
            //System.out.println();
            resAnneal[i] = steepest(randomize(n));
            aTime[i] = timeElapsed(cTime);
        }

        System.out.println("\n\nResults from " + g + " tests:");
        System.out.println("\nSteepest Ascent: ");
        System.out.println("Success rate: " + successRate(resSteep) + "%");
        System.out.println("Average running time: " + avgTime(sTime) + " ns");

        System.out.println("\nSimulated Annealing: ");
        System.out.println("Success rate: " + successRate(resAnneal) + "%");
        System.out.println("Average running time: " + avgTime(aTime) + " ns");
    }

    public static boolean steepest(Node start) {
        Node s = start;
        int lastH;
        int counter = 0;
        do {
            // System.out.println("Trial " + counter);
            // counter++;
            if(s.getH() == 0) {
                Node.printState(s);
                Node.printBoard(s);
                System.out.println("Solved!\n");
                return true;
            }
            lastH = s.getH();
            //System.out.print(lastH);

            s = s.getNextSteepest();
            //System.out.println(", " + s.getH());
        } while(lastH>s.getH());
        //Node.printState(s);
        //Node.printBoard(s);
        return false;
    }

    public static boolean anneal(Node start) {
        Random rd = new Random();
        Node s = start;
        Node next;
        int t = 0;
        int dE;
        do {
            t++;
            if(s.getH() == 0) {
                Node.printState(s);
                Node.printBoard(s);
                System.out.println("Solved!\n");
                return true;
            }
            next = s.getNextRandom();
            dE = s.getH() - next.getH();
            if(dE>0) s=next;
            else {
                int e = rd.nextInt(t/25);
                if(e==1) s = next;
                else break;
            }
        } while(s.getH() != 0);
        return false;
    }

    public static Node randomize(int n) {
        Random rd = new Random();
        int[] s = new int[n];
        for(int i =0; i<s.length; i++) {
            s[i] = rd.nextInt(n);
        }
        Node ret = new Node(s);
        //Node.printState(ret);
        //System.out.println("h: " + ret.getH());
        //Node.printBoard(ret);
        return ret;
    }

    public static long timeElapsed(long start) {
        return (System.nanoTime() - start);
    }

    public static double successRate(boolean[] b) {
        double counter = 0;
        for(boolean i : b) {
            if(i) counter++;
        }
        return ((counter*100)/b.length);
    }

    public static long avgTime(long[] t) {
        long acc = 0;
        for(long i : t) {
            acc+=i;
        }
        return (acc/t.length);
    }
}
