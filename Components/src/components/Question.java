
package components;
import java.util.*;

public class Question extends CommandName {
    public int QuestionId;
    public String QuestionText;
    
    public ArrayList<Option> Options = new ArrayList<Option>();
    
    public int AttemptedOptionId;
    
}
