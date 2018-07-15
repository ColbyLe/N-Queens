import java.util.Random;

public class Solver {
    public static void main(String[] args) {
        System.out.println("22-Queen Solver");
        int g = 200;
        long cTime;
        long[] sTime = new long[g];
        long[] aTime = new long[g];
        boolean[] resSteep = new boolean[g];
        boolean[] resAnneal = new boolean[g];

        for(int i=0; i<g; i++) {
            cTime = System.nanoTime();
            System.out.println();
            resSteep[i] = steepest(randomize(22));
            sTime[i] = timeElapsed(cTime);
        }

        for(int i=0; i<g; i++) {
            cTime = System.nanoTime();
            System.out.println();
            resAnneal[i] = steepest(randomize(22));
            aTime[i] = timeElapsed(cTime);
        }
        System.out.println("Success rate: " + successRate(resSteep) + "%");
        System.out.println("Average running time: " + avgTime(sTime) + " ns");
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
                System.out.println("Solved!");
                return true;
            }
            lastH = s.getH();
            System.out.print(lastH);

            s = s.getNextSteepest();
            System.out.println(", " + s.getH());
        } while(lastH>s.getH());
        Node.printState(s);
        Node.printBoard(s);
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
                System.out.println("Solved!");
                return true;
            }
            next = s.getNextRandom();
            dE = next.getH() - s.getH();
            if(dE>0) s=next;
            else {
                int e = rd.nextInt(t);
                if(e==0) s = next;
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

    public static float successRate(boolean[] b) {
        int counter = 0;
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
