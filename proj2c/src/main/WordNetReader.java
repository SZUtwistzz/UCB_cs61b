package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNetReader {
    private Graph g;
    private Map<Integer,Set<String>> idTOWord;
    private Map<String,Set<Integer>> wordToId;

    public WordNetReader(String HyponymsFile, String SynsetsFile){
        g = new Graph<Integer>();
        idTOWord = new HashMap<>();
        wordToId = new HashMap<>();
        this.ReadHyponyms(HyponymsFile);
        this.ReadSynsets(SynsetsFile);
    }

    Set<Integer> returnHyponyms(int v){
        Set<Integer> result = new HashSet<>();
        g.dfs(v,result);

        return result;
    }

    public void ReadHyponyms(String filename){

        In in = new In(filename);

        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int start = Integer.parseInt(splitLine[0]);

            for(int i =1;i< splitLine.length;i++){
                int end = Integer.parseInt(splitLine[i].trim());
                g.addEdge(start,end);
            }
        }
    }

    public void ReadSynsets(String filename){
        In in = new In(filename);

        if (in == null) {
            throw new IllegalArgumentException("Could not read file: " + filename);
        }

        while(!in.isEmpty()){
            String nextline = in.readLine();
            String[] splitLine = nextline.split(",");

            int start = Integer.parseInt(splitLine[0]);

            String[] words = splitLine[1].split(" ");

            Set<String> synonymSet = new HashSet<>();

            for(String word:words){
                synonymSet.add(word);

                if(!wordToId.containsKey(word)){
                    wordToId.put(word,new HashSet<>());
                }

                wordToId.get(word).add(start);
            }
            idTOWord.put(start,synonymSet);
            g.createNode(start);
        }
    }

    Set<String> gethyponyms(String str){
        if(str == null ||!wordToId.containsKey(str)){
            throw new IllegalArgumentException("Word does not exist in WordNet");
        }

        Set<Integer> startIds = wordToId.get(str);

        Set<Integer> result = new HashSet<>();
        for(int startid:startIds){
            Set<Integer> res = new HashSet<>();
            g.dfs(startid,res);
            for(int id:res){
                result.add(id);
            }
        }

        Set<String> hyponyms = new HashSet<>();
        for(int id:result){
            hyponyms.addAll(idTOWord.get(id));
        }

        return hyponyms;
    }

    public Set<String> getCommonHyponyms(List<String> words){
        if (words == null || words.isEmpty()) {
            throw new IllegalArgumentException("Words list cannot be null");
        }
        for (String word : words) {
            if (!wordToId.containsKey(word)) {
                throw new IllegalArgumentException("Word '" + "' dose not exist in WordNet");
            }
        }

        Set<String> result = new HashSet<>(gethyponyms(words.get(0)));

        for(int i=1;i<words.size();i++){
            Set<String> currentHyponyms = gethyponyms(words.get(i));
            result.retainAll(currentHyponyms);
        }

        return result;
    }
}
