package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNetReader wordnet;

    public HyponymsHandler(String synesetsFile,String hyponymsFile){
        if(synesetsFile == null || hyponymsFile ==null){
            throw new IllegalArgumentException("File paths cannot be null");
        }

        wordnet = new WordNetReader(hyponymsFile,synesetsFile);
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words =q.words();

        if(words.isEmpty() || words ==null){
            return "[]";
        }

        Set<String> hyponyms;

        if(words.size()==1){
            hyponyms = wordnet.gethyponyms(words.get(0));
        }else{
            hyponyms = wordnet.getCommonHyponyms(words);
        }

        List<String> sortResults = new ArrayList<>(hyponyms);
        Collections.sort(sortResults);

        StringBuilder result = new StringBuilder("[");

        for(int i=0;i<sortResults.size();i++){
            result.append(sortResults.get(i));

            if (i < sortResults.size() - 1) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }
}
