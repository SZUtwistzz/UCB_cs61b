package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        for(Map.Entry<Integer,Double> x:ts.entrySet()){
            int year = x.getKey();
            double value = x.getValue();

            if(year>=MIN_YEAR && year<=MAX_YEAR){
                this.put(year,value);
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        List<Integer> toReturn = new ArrayList<>();
        int Counter = 0;
        for(Map.Entry<Integer,Double> x:this.entrySet()){
            int year = x.getKey();
            toReturn.add(year);
        }

        return toReturn;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Double> toReturn = new ArrayList<>();
        for(Map.Entry<Integer,Double> x:this.entrySet()){
            double value = x.getValue();
            toReturn.add(value);
        }

        return toReturn;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        if(this.size()==0 && ts.size()==0){
            return new TimeSeries();
        }

        TimeSeries newItem = new TimeSeries();

        for(Map.Entry<Integer,Double> x:this.entrySet()){
            int year = x.getKey();
            double new_value = x.getValue();
            if(ts.containsKey(year)){
                new_value+=ts.get(year);
            }

            newItem.put(year,new_value);
        }

        for(Map.Entry<Integer,Double> x:ts.entrySet()){
            int year = x.getKey();
            double new_value = x.getValue();
            if(!this.containsKey(year)){
                newItem.put(year,new_value);
            }
        }

        return newItem;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        if(this.size() == 0 && ts.size()==0){
            return new TimeSeries();
        }

        TimeSeries newTs = new TimeSeries();

        for(Map.Entry<Integer,Double> x:this.entrySet()){
            int x_year = x.getKey();
            double x_value = x.getValue();
            if(ts.containsKey(x_year)) {
                double result = x_value / ts.get(x_year);
                newTs.put(x_year,result);
            }
        }
        return newTs;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
