package ngrams;

import edu.princeton.cs.algs4.In;

import java.awt.geom.QuadCurve2D;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    TreeMap<String,TimeSeries> root = new TreeMap<>();
    TimeSeries totalCountes = new TimeSeries();
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In wordfile = new In(wordsFilename);
        In countsfile = new In(countsFilename);

        while(wordfile.hasNextLine()){
            String nextline = wordfile.readLine();
            String[] splitLine = nextline.split("\t");
            String word = splitLine[0];
            int year = Integer.parseInt(splitLine[1]);
            Double count = Double.valueOf(splitLine[2]);
            if(!root.containsKey(word)){
                TimeSeries ts = new TimeSeries();
                ts.put(year,count);
                root.put(word,ts);
            }else{
                root.get(word).put(year,count);
            }
        }

        while(countsfile.hasNextLine()){
            String nextline = countsfile.readLine();
            String[] splitLine = nextline.split(",");
            int year = Integer.parseInt(splitLine[0]);
            Double count = Double.valueOf(splitLine[1]);
            totalCountes.put(year,count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(root.get(word)==null){
            return new TimeSeries();
        }
        TimeSeries toReturn = new TimeSeries();
        for(Map.Entry<Integer, Double> x:root.get(word).entrySet()){
            int year = x.getKey();
            if(year>=startYear && year<=endYear){
                toReturn.put(x.getKey(),x.getValue());
            }
        }
        return toReturn;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if(root.get(word)==null){
            return new TimeSeries();
        }
        TimeSeries toReturn = new TimeSeries();
        for(Map.Entry<Integer, Double> x:root.get(word).entrySet()){
            int year = x.getKey();
            toReturn.put(x.getKey(),x.getValue());
        }
        return toReturn;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries toReturn = new TimeSeries();
        for(Map.Entry<Integer,Double> x:totalCountes.entrySet()){
            toReturn.put(x.getKey(),x.getValue());
        }
        return toReturn;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(root.get(word)==null) return new TimeSeries();

        TimeSeries toReturn = new TimeSeries();
        for(Map.Entry<Integer,Double> x:root.get(word).entrySet()){
            int year = x.getKey();
            if(year>=startYear && year<=endYear) {
                double count = x.getValue();
                double totalCount = totalCountes.get(year);
                double weight = count / totalCount;
                toReturn.put(year, weight);
            }
        }
        return toReturn;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if(root.get(word)==null) return new TimeSeries();

        TimeSeries toReturn = new TimeSeries();
        for(Map.Entry<Integer,Double> x:root.get(word).entrySet()){
            int year = x.getKey();
            double count = x.getValue();
            double totalCount = totalCountes.get(year);
            double weight = count / totalCount;
            toReturn.put(year, weight);
        }
        return toReturn;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        // TODO: Fill in this method.

        TimeSeries toReturn = new TimeSeries();


        for(String word:words){
            TimeSeries wordWeight = weightHistory(word,startYear,endYear);
            toReturn = toReturn.plus(wordWeight);
        }
        return toReturn;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.

        TimeSeries toReturn = new TimeSeries();
        for(String word:words){
            TimeSeries wordWeight = weightHistory(word);
            toReturn = toReturn.plus(wordWeight);
        }
        return toReturn;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
