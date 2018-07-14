import java.util.Random;

public class Solver {
    public static void main(String[] args) {
        System.out.println("22-Queen Solver");
        int g = 200;
        long sTime;
        long[] tTime = new long[g];
        boolean[] res = new boolean[g];
        for(int i=0; i<g; i++) {
            sTime = System.nanoTime();
            System.out.println();
            res[i] = steepest(randomize(22));
            tTime[i] = timeElapsed(sTime);
        }
        System.out.println("Success rate: " + successRate(res) + "%");
        System.out.println("Average running time: " + avgTime(tTime) + " ns");
    }

    public static boolean steepest(Node start) {
        Node s = start;
        int lastH;
        int counter = 0;
        do {
            // System.out.println("Trial " + counter);
            // counter++;
            lastH = s.getH();
            System.out.print(lastH);
            s = s.getNextSteepest();
            System.out.println(", " + s.getH());
            if(s.getH() == 0) {
                Node.printState(s);
                Node.printBoard(s);
                System.out.println("Solved!");
                return true;
            }
        } while(lastH>s.getH());
        Node.printState(s);
        Node.printBoard(s);
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
