
package components;

import java.util.ArrayList;


public class Challenge extends CommandName {
    public int ChallengeId;
    public String Date;
   // public String ChallengeDate;
    public int CategoryId;
    public int FromUserNo;
    public int ToUserNo;
    public int Status; 
    public String ToUserName;
    public String FromUserName;
    public String CategoryName;
    public ArrayList<Question> Questions = null;
    
    public Question Question = null;

    public int QuestionFor=0;
    
    public int MyNumber = 0;

    public int QuestionIndex = -1;
    public int Flag;
    
    
    public int UserNo;
}
