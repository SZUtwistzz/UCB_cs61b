import main.Graph;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import java.util.Set;

public class TestGraph {

    @Test
    public void testSimpleGraph(){
        Graph g = new Graph();
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,3);

        Set<Integer> reachable = g.getNeighbors(0);
        assertThat(reachable).containsExactly(1,2);
    }
}
