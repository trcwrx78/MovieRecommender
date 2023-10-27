
/**
 * Deals with averaging movie ratings and connect to DB class
 * 
 * @author Todd Cole 
 * @version 9/20/23
 */

import edu.duke.*;
import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
    
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        for(String movie : movies) {
            double averageRating = getAverageByID(movie, minimalRaters);
            if(averageRating > 0) {
                ratings.add(new Rating(movie, averageRating));
            }
        }
        
        return ratings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> result = new ArrayList<Rating>();
        
        ArrayList<String> filteredMovieIDs = MovieDatabase.filterBy(filterCriteria);
        for(String movieID : filteredMovieIDs) {
            double movieRatingAverage = getAverageByID(movieID, minimalRaters);
            if(movieRatingAverage != 0.0) {
                result.add(new Rating(movieID, movieRatingAverage));
            }
        }
        
        return result;
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        double averageMovieRating = 0.0;
        int count = 0;
        
        for(Rater rater : myRaters) {
            if(rater.hasRating(id)) {
                count++;
                averageMovieRating += rater.getRating(id);
            }
        }
        
        if(count >= minimalRaters) {
            return averageMovieRating / count;
        }
        
        return 0.0;
    }

}