import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
/**
 * Process movie and ratings data and answer questions about them
 *
 * @author Todd Cole
 * @version 9/18/23
 */
public class FirstRatings
{
    public ArrayList<Movie> loadMovies(String movieFile) {
        FileResource fr = new FileResource(movieFile);
        CSVParser movieParser = fr.getCSVParser();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        
        for(CSVRecord movieRecord : movieParser) {
            int minutes = Integer.parseInt(movieRecord.get("minutes"));
            Movie movie = new Movie(
                movieRecord.get("id"),
                movieRecord.get("title"),
                movieRecord.get("year"),
                movieRecord.get("genre"),
                movieRecord.get("director"),
                movieRecord.get("country"),
                movieRecord.get("poster"),
                minutes
            );
            
            movies.add(movie);
        }
        
        return movies;
    }
    
    public ArrayList<Rater> loadRaters(String raterFile) {
        FileResource fr = new FileResource(raterFile);
        CSVParser raterParser = fr.getCSVParser();
        ArrayList<Rater> raters = new ArrayList<Rater>();
        HashMap<String, Rater> raterMap = new HashMap<>();
        
        for(CSVRecord raterRecord : raterParser) {
            String raterID = raterRecord.get("rater_id");
            String movieID = raterRecord.get("movie_id");
            double rating = Double.parseDouble(raterRecord.get("rating"));
            
            if(!raterMap.containsKey(raterID)) {
                Rater rater = new EfficientRater(raterID);
                raters.add(rater);
                raterMap.put(raterID, rater);
            }
            raterMap.get(raterID).addRating(movieID, rating);       
        }
        
        return raters;
    }
    
    public void testLoadRaters() {  
        //ArrayList<Rater> loadedRaters = loadRaters("data/ratings.csv");
        ArrayList<Rater> loadedRaters = loadRaters("data/ratings_short.csv");
        System.out.println("There are " + loadedRaters.size() + " raters in this file!");
        
        /*for (Rater rater : loadedRaters) {
            System.out.println("Raters ID: " + rater.getID() + " and the number of ratings: " + rater.numRatings());
            ArrayList<String> ratedMovies = rater.getItemsRated();            
            for (String movie : ratedMovies) {
                System.out.println(movie + ": " + rater.getRating(movie));
            }
        }*/
      
        String targetRaterID = "193";
        int numberOfRatings = getNumberOfRatingsByID(loadedRaters, targetRaterID);
        System.out.println("Rater with ID: " + targetRaterID + " has " + numberOfRatings + " ratings");
        
        HashMap<String, Integer> raterCounts = getRaterCountsMap(loadedRaters);
        int maxRatingsByRater = Collections.max(raterCounts.values());        
        System.out.println("Maximum ratings by a rater: " + maxRatingsByRater);
        
        ArrayList<String> maxRaters = getRatersByCount(raterCounts, maxRatingsByRater);
        System.out.println("Number of raters who have the most ratings: " + maxRaters.size());
        System.out.println("Max raters IDs: " + maxRaters);
        
        String targetMovieID = "1798709";
        int ratingsForMovie = getNumberOfRatingsForMovie(loadedRaters, targetMovieID);
        System.out.println("Movie with ID: " + targetMovieID + " was rated by " + ratingsForMovie + " raters"); 
        
        int moviesRated = getNumberOfDifferentMoviesRated(loadedRaters);
        System.out.println("Number of movies rated: " + moviesRated);
    }
    
    private int getNumberOfRatingsByID(ArrayList<Rater> raters, String target) {
        int numberOfRatings = 0;
        for (Rater rater : raters) {
            if(rater.getID().equals(target)) {
                numberOfRatings = rater.numRatings();
            }
        }
        return numberOfRatings;
    }
    
    private HashMap<String, Integer> getRaterCountsMap(ArrayList<Rater> raters) {
        HashMap<String, Integer> map = new HashMap<>();
        for(Rater rater : raters) {
            String raterID = rater.getID();
            int raterNum = rater.numRatings();
            if(!map.containsKey(raterID)) {
               map.put(raterID, raterNum);
            }
        }
        return map;
    }
    
    private ArrayList<String> getRatersByCount(HashMap<String, Integer> raterCounts, int max) {
        ArrayList<String> maxRaters = new ArrayList<String>();
        
        for(String rater : raterCounts.keySet()) {
            if(raterCounts.get(rater) == max) {
                maxRaters.add(rater);
            }
        }
        
        return maxRaters;
    }
    
    private int getNumberOfRatingsForMovie(ArrayList<Rater> raters, String movieID) {
        int ratingsByMovie = 0;
        
        for(Rater rater : raters) {
            if(rater.hasRating(movieID)) {
                ratingsByMovie++;
            }
        }
        
        return ratingsByMovie;
    }
    
    private int getNumberOfDifferentMoviesRated(ArrayList<Rater> raters) {
        ArrayList<String> differentMovieCount = new ArrayList<String>();
        
        for(Rater rater : raters) {
            ArrayList<String> moviesByRater = rater.getItemsRated();
            for(String item : moviesByRater) {
                if(!differentMovieCount.contains(item)) {
                    differentMovieCount.add(item);
                }
            }
        }
        
        return differentMovieCount.size();
    }
    
    public void testLoadMovies() {
        //ArrayList<Movie> loadedMovies = loadMovies("data/ratedmovies_short.csv");
        ArrayList<Movie> loadedMovies = loadMovies("data/ratedmoviesfull.csv");
        System.out.println("There are " + loadedMovies.size() + " movies in this file!");
    
        System.out.println("Number of comedies: " + countMoviesByGenre(loadedMovies, "Comedy"));
        System.out.println("Number of movies greater than 150 minutes in length: " + countLongMovies(loadedMovies, 150));
    
        HashMap<String, Integer> directorMap = getDirectorMap(loadedMovies);
        int maxMoviesByDirector = Collections.max(directorMap.values());
    
        System.out.println("Maximum movies by a director: " + maxMoviesByDirector);
    
        ArrayList<String> multiMovieDirectors = getDirectorsWithMaxMovies(directorMap, maxMoviesByDirector);
        System.out.println(multiMovieDirectors);
    }
    
    private int countMoviesByGenre(ArrayList<Movie> movies, String genre) {
        int count = 0;
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                count++;
            }
        }
        return count;
    }
    
    private int countLongMovies(ArrayList<Movie> movies, int length) {
        int count = 0;
        for (Movie movie : movies) {
            if (movie.getMinutes() > length) {
                count++;
            }
        }
        return count;
    }
    
    private HashMap<String, Integer> getDirectorMap(ArrayList<Movie> movies) {
        HashMap<String, Integer> directorMap = new HashMap<>();
        for (Movie movie : movies) {
            String[] directors = movie.getDirector().split(",");
            for (String director : directors) {
                director = director.trim();
                directorMap.put(director, directorMap.getOrDefault(director, 0) + 1);
            }
        }
        return directorMap;
    }
    
    private ArrayList<String> getDirectorsWithMaxMovies(HashMap<String, Integer> directorMap, int maxMovies) {
        ArrayList<String> directors = new ArrayList<>();
        for (String director : directorMap.keySet()) {
            if (directorMap.get(director) == maxMovies) {
                directors.add(director);
            }
        }
        return directors;
    }

    
    /*public Movie (String anID, String aTitle, String aYear, String theGenres, String aDirector,
    String aCountry, String aPoster, int theMinutes)*/
}
