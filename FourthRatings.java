import java.util.*;
/**
 * FourthRatings class connecting rati
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FourthRatings
{
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
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> similarRatings = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> weightedRatings = new ArrayList<Rating>();
        
        if(similarRatings.size() > numSimilarRaters) {
            similarRatings = new ArrayList<>(similarRatings.subList(0, numSimilarRaters));
        }
        
        Rater currentRater = RaterDatabase.getRater(id);
        for(String movieID : movies) {
            if(currentRater.hasRating(movieID)) {
                continue; // Skip to the next iteration if the current rater has already rated the movie.
            }

            double totalWeightedRating = 0.0;
            int raterCount = 0;
            
            for(Rating raterSimilarity : similarRatings) {
                Rater rater = RaterDatabase.getRater(raterSimilarity.getItem());
                
                if(rater.hasRating(movieID)){
                    double rating = rater.getRating(movieID);
                    double weightedRating = rating * raterSimilarity.getValue();
                    
                    totalWeightedRating += weightedRating;
                    raterCount++;
                }
            }
            
            if(raterCount >= minimalRaters) {
                double averageWeightedRating = totalWeightedRating / raterCount;
                weightedRatings.add(new Rating(movieID, averageWeightedRating));
            }
        }
        
        Collections.sort(weightedRatings, Collections.reverseOrder());
        
        return weightedRatings;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> similarRatings = getSimilarities(id);
        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> weightedRatings = new ArrayList<Rating>();
        
        if(similarRatings.size() > numSimilarRaters) {
            similarRatings = new ArrayList<>(similarRatings.subList(0, numSimilarRaters));
        }
        
        Rater currentRater = RaterDatabase.getRater(id);
        for(String movieID : filteredMovies) {
            if(currentRater.hasRating(movieID)) {
                continue; // Skip to the next iteration if the current rater has already rated the movie.
            }

            double totalWeightedRating = 0.0;
            int raterCount = 0;
            
            for(Rating raterSimilarity : similarRatings) {
                Rater rater = RaterDatabase.getRater(raterSimilarity.getItem());
                
                if(rater.hasRating(movieID)){
                    double rating = rater.getRating(movieID);
                    double weightedRating = rating * raterSimilarity.getValue();
                    
                    totalWeightedRating += weightedRating;
                    raterCount++;
                }
            }
            
            if(raterCount >= minimalRaters) {
                double averageWeightedRating = totalWeightedRating / raterCount;
                weightedRatings.add(new Rating(movieID, averageWeightedRating));
            }
        }
        
        Collections.sort(weightedRatings, Collections.reverseOrder());
        
        return weightedRatings;
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        double averageMovieRating = 0.0;
        int count = 0;
        
        ArrayList<Rater> myRaters = RaterDatabase.getRaters(); 
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
    
    private double dotProduct(Rater me, Rater r) {
        double dotProduct = 0.0;
        ArrayList<String> myMovies = me.getItemsRated();
        
        for(String id: myMovies) {
            if(r.hasRating(id)) {
                double myRating = me.getRating(id) - 5;
                double rRating = r.getRating(id) - 5;
                dotProduct += myRating * rRating;
            }
        }
        
        return dotProduct;
    }
    
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarRatings = new ArrayList<Rating>();
        
        Rater currentRater = RaterDatabase.getRater(id);
        ArrayList<Rater> allRaters = RaterDatabase.getRaters();
        
        for(Rater rater : allRaters) {
            if(!rater.getID().equals(currentRater.getID())) {
                double dotProductValue = dotProduct(currentRater, rater);
                if(dotProductValue > 0) {
                    similarRatings.add(new Rating(rater.getID(), dotProductValue));
                }
            }
        }
        
        Collections.sort(similarRatings, Collections.reverseOrder());
        
        return similarRatings;
    }
}
