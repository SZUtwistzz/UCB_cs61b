package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<K> {
    private Map<K, Set<K>> adj;

    public Graph(){
        this.adj = new HashMap<>();
    }

    public void createNode(K v){
        if(!hasNode(v)){
            adj.put(v,new HashSet<>());
        }
    }
    public void addEdge(K v,K w){
        if(!hasNode(v)){
            createNode(v);
        }else{
            createNode(w);
        }
        adj.get(v).add(w);
    }

    public HashSet<K> getNeighbors(K v){
        if(!hasNode(v)) return new HashSet<>();
        return (HashSet<K>) adj.get(v);
    }

    public boolean hasNode(K v){
        return adj.containsKey(v);
    }

    public void dfs(K v,Set<K> result){
        result.add(v);

        for(K neighbor:getNeighbors(v)){
            if(!result.contains(neighbor)){
                dfs(neighbor,result);
            }
        }
    }

}
