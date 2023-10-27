import java.util.*;
/**
 * Write a description of class MovieRunnerSimilarRatings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MovieRunnerSimilarRatings
{
    public void printAverageRatings() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
       
        
        ArrayList<Rating> averageRatingList = fr.getAverageRatings(3);
        Collections.sort(averageRatingList);
        System.out.println("found " + averageRatingList.size() + " movies");
        
        for(Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1990));
        filters.addFilter(new GenreFilter("Drama"));
        
        ArrayList<Rating> ratingList = fr.getAverageRatingsByFilter(8, filters);
        Collections.sort(ratingList);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printSimilarRatings() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        String raterID = "71";
        int similarRaters = 20;
        int minimalRaters = 5;
        
        ArrayList<Rating> ratingList = fr.getSimilarRatings(raterID, similarRaters, minimalRaters);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printSimilarRatingsByGenre() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        String raterID = "964";
        int similarRaters = 20;
        int minimalRaters = 5;
        GenreFilter genre = new GenreFilter("Mystery");
        
        ArrayList<Rating> ratingList = fr.getSimilarRatingsByFilter(raterID, similarRaters, minimalRaters, genre);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printSimilarRatingsByDirector() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        String raterID = "120";
        int similarRaters = 10;
        int minimalRaters = 2;
        DirectorsFilter director = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        
        ArrayList<Rating> ratingList = fr.getSimilarRatingsByFilter(raterID, similarRaters, minimalRaters, director);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        String raterID = "168";
        int similarRaters = 10;
        int minimalRaters = 3;
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new GenreFilter("Drama"));
        filters.addFilter(new MinutesFilter(80, 160));
        
        ArrayList<Rating> ratingList = fr.getSimilarRatingsByFilter(raterID, similarRaters, minimalRaters, filters);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getMinutes(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes() {
        FourthRatings fr = new FourthRatings();
        
        //RaterDatabase.initialize("ratings_short.csv");
        RaterDatabase.initialize("ratings.csv");
        
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //MovieDatabase.initialize("ratedmovies_short.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        System.out.println("Number of movies: " + MovieDatabase.size());
        
        String raterID = "314";
        int similarRaters = 10;
        int minimalRaters = 5;
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1975));
        filters.addFilter(new MinutesFilter(70, 200));
        
        ArrayList<Rating> ratingList = fr.getSimilarRatingsByFilter(raterID, similarRaters, minimalRaters, filters);
        System.out.println("found " + ratingList.size() + " movies");
        
        for(Rating rating : ratingList) {
            System.out.println(rating.getValue() + " " + MovieDatabase.getYear(rating.getItem()) + " " + MovieDatabase.getMinutes(rating.getItem()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }        
    }
}
