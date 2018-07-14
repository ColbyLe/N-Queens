import java.util.PriorityQueue;

public class Node {
    private int[] state;
    private int h;

    public Node(int[] s) {
        state = new int[s.length];
        h = 0;

        for(int i=0; i<s.length; i++) {
            state[i] = s[i];
        }

        int diag = 0;
        int horiz = 0;

        // check for # of pairs of attacking queens
        // only checks in one direction, so no pairs are repeated
        for(int i=0; i<state.length; i++) {
            for(int j=i+1; j<state.length; j++) {
                // check horizontal alignment
                if((state[i] == state[j]) && horiz<2) horiz++;
                // if difference between vertical position = difference between horizontal position
                // diagonal alignment
                if((Math.abs(j-i) == Math.abs(state[j] - state[i])) && diag<2) diag++;
                // each queen can only be aligned with one queen horizontally and one diagonally
                if(horiz == 1 && diag == 1) break;
            }
        }
    }

    public int getH() {
        return h;
    }

    // store last node's h in driver function
    // if last node's h>=current h, or if h==0, stop search

    // method to generate new nodes and select a successor based on steepest-ascent hill climbing
    public Node getNextSteepest() {
        // priority queue to sort all successor nodes by # of pairs of attacking queens
        PriorityQueue<Node> next = new PriorityQueue<Node>(500, new NodeComparator());
        // for each queen, record current position, then find range of valid next moves
        for(int i=0; i<state.length; i++) {
            int pos = state[i];
            int min = pos - 8;
            int max = pos + 8;
            if(min<0) min = 0;
            if(max>state.length-1) max = state.length-1;
            // for each valid next move, generate new node and add to priority queue
            for(int j=min; j<=max; j++) {
                if(j==pos) continue;
                int[] sOut = copyState(state);
                sOut[i] = j;
                next.add(new Node(sOut));
            }
        }
        // return best option from successor nodes
        return next.poll();
    }

    // utility function to make deep copy of state
    public static int[] copyState(int[] s) {
        int[] newS = new int[s.length];
        for(int i=0; i<s.length; i++) {
            newS[i] = s[i];
        }
        return newS;
    }
}

