import java.util.*;
/**
 * Prints ratings using ThirdRatingsClass
 *
 * @author Todd Cole
 * @version 8-21-23
 */
public class MovieRunnerWithFilters
{
    public void printAverageRatings() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
       
        
        ArrayList<Rating> averageRatingList = tr.getAverageRatings(35);
        Collections.sort(averageRatingList);
        System.out.println("found " + averageRatingList.size() + " movies");
        
        for(Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfter() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        YearAfterFilter year = new YearAfterFilter(2000);
        ArrayList<Rating> ratingList = tr.getAverageRatingsByFilter(20, year);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByGenre() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        GenreFilter genre = new GenreFilter("Comedy");
        ArrayList<Rating> ratingList = tr.getAverageRatingsByFilter(20, genre);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByMinutes() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        MinutesFilter minutes = new MinutesFilter(105, 135);
        ArrayList<Rating> ratingList = tr.getAverageRatingsByFilter(5, minutes);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByDirectors() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        DirectorsFilter directors = new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        ArrayList<Rating> ratingList = tr.getAverageRatingsByFilter(4, directors);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1990));
        filters.addFilter(new GenreFilter("Drama"));
        
        ArrayList<Rating> ratingList = tr.getAverageRatingsByFilter(8, filters);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByDirectorsAndMinutes() {
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));
        filters.addFilter(new MinutesFilter(90, 180));
        
        ArrayList<Rating> ratingList = tr.getAverageRatingsByFilter(3, filters);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
}
