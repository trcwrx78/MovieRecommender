import java.util.*;
/**
 * Filter for directors
 *
 * @author Todd Cole
 * @version 9-21-23
 */
public class DirectorsFilter implements Filter
{
    private String myDirectors;

    public DirectorsFilter(String directors)
    {
        myDirectors = directors;
    }

    @Override
    public boolean satisfies(String id) {
        String[] directorList = myDirectors.split(",");
        
        for(String director : directorList) {
            if(MovieDatabase.getDirector(id).contains(director)) {
                return true;
            }
        }
        
        return false;
    }
}
