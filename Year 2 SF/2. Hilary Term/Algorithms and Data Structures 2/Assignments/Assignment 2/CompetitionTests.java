// * @author John Wesley Kommala

// import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompetitionTests {
    public static final int a = 50;
    public static final int b = 60;
    public static final int c = 75;

    @Test
    public void testDijkstraConstructor() {
        CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b, c);
        assertEquals(-1, competitionDijkstra.timeRequiredforCompetition());
        
        competitionDijkstra = new CompetitionDijkstra("doesn't exist", a, b, c);
        assertEquals(-1, competitionDijkstra.timeRequiredforCompetition());
        
        competitionDijkstra = new CompetitionDijkstra("", a, b, c);
        assertEquals(-1, competitionDijkstra.timeRequiredforCompetition());
    }

    @Test
    public void testFWConstructor() {
        CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(null, a, b, c);
        assertEquals(-1, competitionFloydWarshall.timeRequiredforCompetition());
        
        competitionFloydWarshall = new CompetitionFloydWarshall("doesn't exist", a, b, c);
        assertEquals(-1, competitionFloydWarshall.timeRequiredforCompetition());
        
        competitionFloydWarshall = new CompetitionFloydWarshall("", a, b, c);
        assertEquals(-1, competitionFloydWarshall.timeRequiredforCompetition());

    }

    @Test
    public void testInputABCD_Dijkstra() {
        CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b, c);

        competitionDijkstra = new CompetitionDijkstra("input-A.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-A.txt",-1, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-B.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-B.txt",10000, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-C.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-C.txt",-1, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-D.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-D.txt",38, competitionDijkstra.timeRequiredforCompetition());
    }

    // @Test
    // public void testInputEFGH_Dijkstra() {
    //     CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b, c);

    //     competitionDijkstra = new CompetitionDijkstra("input-E.txt", a, b, c);
    //     assertEquals("Testing Dijkstra for input-E.txt",28, competitionDijkstra.timeRequiredforCompetition());

    //     competitionDijkstra = new CompetitionDijkstra("input-F.txt", a, b, c);
    //     assertEquals("Testing Dijkstra for input-F.txt",-1, competitionDijkstra.timeRequiredforCompetition());
        
    //     competitionDijkstra = new CompetitionDijkstra("input-G.txt", a, b, c);
    //     assertEquals("Testing Dijkstra for input-G.txt",-1, competitionDijkstra.timeRequiredforCompetition());

    //     competitionDijkstra = new CompetitionDijkstra("input-H.txt", a, b, c);
    //     assertEquals("Testing Dijkstra for input-H.txt",-1, competitionDijkstra.timeRequiredforCompetition());
    // }

    @Test
    public void testInputIJKL_Dijkstra() {
        CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b, c);

        competitionDijkstra = new CompetitionDijkstra("input-I.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-I.txt",240, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-J.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-J.txt",-1, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-K.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-K.txt",320, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-L.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-L.txt",160, competitionDijkstra.timeRequiredforCompetition());
    }

    @Test
    public void testInputMNT_Dijkstra() {
        CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b, c);

        competitionDijkstra = new CompetitionDijkstra("input-M.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-M.txt",300, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("input-N.txt", a, b, c);
        assertEquals("Testing Dijkstra for input-N.txt",160, competitionDijkstra.timeRequiredforCompetition());

        competitionDijkstra = new CompetitionDijkstra("tinyEWD.txt", a, b, c);
        assertEquals("Testing Dijkstra for tinyEWD.txt",38, competitionDijkstra.timeRequiredforCompetition());
    }

    @Test
    public void testInputABCD_FloydWarshall() {
        CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(null, a, b, c);

        competitionFloydWarshall = new CompetitionFloydWarshall("input-A.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-A.txt",-1, competitionFloydWarshall.timeRequiredforCompetition());

        competitionFloydWarshall = new CompetitionFloydWarshall("input-B.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-B.txt",10000, competitionFloydWarshall.timeRequiredforCompetition());

        competitionFloydWarshall = new CompetitionFloydWarshall("input-C.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-C.txt",-1, competitionFloydWarshall.timeRequiredforCompetition());

        competitionFloydWarshall = new CompetitionFloydWarshall("input-D.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-D.txt",38, competitionFloydWarshall.timeRequiredforCompetition());
    }

    // @Test
    // public void testInputEFGH_FloydWarshall() {
    //     CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(null, a, b, c);

    //     competitionFloydWarshall = new CompetitionFloydWarshall("input-E.txt", a, b, c);
    //     assertEquals("Testing Floyd Warshall for input-E.txt",28, competitionFloydWarshall.timeRequiredforCompetition());

    //     competitionFloydWarshall = new CompetitionFloydWarshall("input-F.txt", a, b, c);
    //     assertEquals("Testing Floyd Warshall for input-F.txt",-1, competitionFloydWarshall.timeRequiredforCompetition());

    //     competitionFloydWarshall = new CompetitionFloydWarshall("inputAssignment2/input-G.txt", a, b, c);
    //     assertEquals("Testing Floyd Warshall for input-G.txt",-1, competitionFloydWarshall.timeRequiredforCompetition());

    //     competitionFloydWarshall = new CompetitionFloydWarshall("inputAssignment2/input-H.txt", a, b, c);
    //     assertEquals("Testing Floyd Warshall for input-H.txt",-1, competitionFloydWarshall.timeRequiredforCompetition());
    // }

    @Test
    public void testInputIJKL_FloydWarshall() {
        CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(null, a, b, c);

        competitionFloydWarshall = new CompetitionFloydWarshall("input-I.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-I.txt",240, competitionFloydWarshall.timeRequiredforCompetition());

        competitionFloydWarshall = new CompetitionFloydWarshall("input-J.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-J.txt",-1, competitionFloydWarshall.timeRequiredforCompetition());

        competitionFloydWarshall = new CompetitionFloydWarshall("input-K.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-K.txt",320, competitionFloydWarshall.timeRequiredforCompetition());

        competitionFloydWarshall = new CompetitionFloydWarshall("input-L.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-L.txt",160, competitionFloydWarshall.timeRequiredforCompetition());
    }

    @Test
    public void testInputMNT_FloydWarshall() {
        CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(null, a, b, c);

        competitionFloydWarshall = new CompetitionFloydWarshall("input-M.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-M.txt",300, competitionFloydWarshall.timeRequiredforCompetition());
        // assertEquals(-1, competitionFloydWarshall.timeRequiredforCompetition());
        competitionFloydWarshall = new CompetitionFloydWarshall("input-N.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for input-N.txt",160, competitionFloydWarshall.timeRequiredforCompetition());
        
        competitionFloydWarshall = new CompetitionFloydWarshall("tinyEWD.txt", a, b, c);
        assertEquals("Testing Floyd Warshall for tinyEWD.txt",38, competitionFloydWarshall.timeRequiredforCompetition());
    }

    @Test
    public void testExtremeSpeeds() {
        String filename = "tinyEWD.txt";
        int sA_large = 300, sB_large=120, sC_large=150;
        CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(filename, sA_large, sB_large, sC_large);
        assertEquals("Testing Dijkstra for high speeds",-1, competitionDijkstra.timeRequiredforCompetition());
        CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(filename, sA_large, sB_large, sC_large);
        assertEquals("Testing Floyd Warshall for high speeds",-1, competitionFloydWarshall.timeRequiredforCompetition());
    
        int sA_small=10, sB_small=20, sC_small=30;
        CompetitionDijkstra dj = new CompetitionDijkstra(filename, sA_small, sB_small, sC_small);
        assertEquals("Testing Dijkstra for low speeds",-1, dj.timeRequiredforCompetition());
        CompetitionFloydWarshall cfw = new CompetitionFloydWarshall(filename, sA_small, sB_small, sC_small);
        assertEquals("Testing Floyd Warshall for low speeds",-1, cfw.timeRequiredforCompetition());
    }

    // @Test
    // public void testInput1000EWD() {
    //     CompetitionDijkstra competitionDijkstra = new CompetitionDijkstra(null, a, b, c);

    //     competitionDijkstra = new CompetitionDijkstra("1000EWD.txt", a, b, c);
    //     assertEquals("Testing Dijkstra for 1000EWD.txt",28, competitionDijkstra.timeRequiredforCompetition());

    //     CompetitionFloydWarshall competitionFloydWarshall = new CompetitionFloydWarshall(null, a, b, c);

    //     competitionFloydWarshall = new CompetitionFloydWarshall("1000EWD.txt", a, b, c);
    //     assertEquals("Testing Floyd Warshall for 1000EWD.txt",28, competitionFloydWarshall.timeRequiredforCompetition());
    // }

}
