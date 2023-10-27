import java.util.*;
/**
 * Recommendation engine connection to the course site
 *
 * @author Todd Cole
 * @version 9-23-23
 */
public class RecommendationRunner implements Recommender
{
    public ArrayList<String> getItemsToRate() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new GenreFilter("Comedy"));
        filters.addFilter(new MinutesFilter(70, 160));
        
        ArrayList<String> movies = MovieDatabase.filterBy(filters);
        
        Collections.shuffle(movies);
        
        if(movies.size() >= 16) {
            movies = new ArrayList<String>(movies.subList(0, 16));
        }
        
        return movies;
    }
    
    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        int similarRaters = 10;
        int minimalRaters = 3;
        
        AllFilters filters = new AllFilters();
        filters.addFilter(new GenreFilter("Comedy"));
        filters.addFilter(new MinutesFilter(70, 160));
        
        ArrayList<Rating> recommendedMovies = fr.getSimilarRatingsByFilter(webRaterID, similarRaters, minimalRaters, filters);
        
        // HTML structure
        System.out.println("<html>");
        System.out.println("<head>");
        System.out.println("<title>Recommended Comedy Movies</title>");

        System.out.println("<style>"
            + "* {"
            + "  margin: 0;"
            + "  padding: 0;"
            + "  box-sizing: inherit;"
            + "}"
            + "html {"
            + "  box-sizing: border-box;"
            + "  font-size: 62.5%;"
            + "}"
            + "body {"
            + "  margin: 5rem 0;"
            + "  font-family: sans-serif;"
            + "  background-color: #F9F8F9;"
            + "}"
            + "h1 {"
            + "  color: #4B4985;"
            + "  text-align: center;"
            + "}"
            + "table {"
            + "  border-collapse: collapse;"
            + "  width: 80%;"
            + "  margin: 20px auto;"
            + "}"
            + "th, td {"
            + "  border: 1px solid black;"
            + "  padding: 8px 12px;"
            + "  text-align: left;"
            + "}"
            + "th {"
            + "  background-color: #ABA7B4;"
            + "  color: #F9F8F9;"
            + "  font-size: 1.3rem;"
            + "}"
            + "</style>");

        System.out.println("</head>");
        System.out.println("<body>");
        System.out.println("<h1>🍿 Top 10 Recommended Movies 🍿</h1>");
        
        if (recommendedMovies.isEmpty()) {
            System.out.println("<p>No recommended movies found.</p>");
        } else {
            System.out.println("<table>");
            System.out.println("<tr><th>Movie</th><th>Runtime</th><th>Year</th><th>Genre</th></tr>");
            
            int movieCount = 0;
            for(Rating movie : recommendedMovies) {
                if (movieCount >= 10) break;
                
                String movieName = MovieDatabase.getTitle(movie.getItem());
                String runtime = MovieDatabase.getMinutes(movie.getItem()) + " mins";
                String year = Integer.toString(MovieDatabase.getYear(movie.getItem()));
                String genre = MovieDatabase.getGenres(movie.getItem());
                
                System.out.println("<tr><td>" + movieName + "</td><td>" + runtime + "</td><td>" + year + "</td><td>" + genre + "</td></tr>");
                
                movieCount++;
            }
            
            System.out.println("</table>");
        }
        
        System.out.println("</body>");
        System.out.println("</html>");
    }
}
