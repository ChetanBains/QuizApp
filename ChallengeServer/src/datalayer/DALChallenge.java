package datalayer;

import components.*;

import java.sql.*;
import java.util.ArrayList;

public class DALChallenge {

    static Connection con = null;

    /// Keep This until Firebase Doesnt work

    public DALChallenge() {
        try {
           Class.forName(jdbc:oracle:thin:@localhost:1521:xe");
            con = DriverManager.getConnection("oracle.jdbc.OracleDriver\Chetan:49821;Database=projectDB;userName=sa;password=1100");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /// Keep This until Firebase Doesnt work

    public void sendChallenge(Challenge ob) {
        try {
            CallableStatement cs = con.prepareCall("{call AddChallenge(?,?,?,?,?)}");
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setInt(2, ob.CategoryId);
            cs.setInt(3, ob.FromUserNo);
            cs.setInt(4, ob.ToUserNo);
            cs.setInt(5, ob.Status);
            cs.execute();
            ob.ChallengeId = cs.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Challenge anyChallenge(Challenge ob) {
        Challenge challenge = new Challenge();
        try {
            PreparedStatement ps = con.prepareStatement("select C.ChallengeId, C.Date, C.FromUserNo, FU.Name as [FromUserName], C.ToUserNo, TU.Name as[ToUserName], C.Status, C.CategoryId, Cat.CategoryName\n" +
"            From Challenges as [C], Categories as [Cat], Users as [FU], Users as [TU]\n" +
"            where C.CategoryId = Cat.CategoriesId and C.FromUserNo = FU.UserNo and C.ToUserNo = TU.UserNo and C.ToUserNo = ? and Status = 0");
            ps.setInt(1, ob.ToUserNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                challenge.CategoryId = rs.getInt("CategoryId");
                challenge.Date = rs.getString("Date");
                challenge.ChallengeId = rs.getInt("ChallengeId");
                challenge.FromUserNo = rs.getInt("FromUserNo");
                challenge.ToUserNo = rs.getInt("ToUserNo");
                challenge.Status = rs.getInt("Status");
                challenge.ToUserName = rs.getString("ToUserName");
                challenge.FromUserName = rs.getString("FromUserName");
                challenge.CategoryName = rs.getString("CategoryName");

            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return challenge;
    }

    public ArrayList<Question> getQuestions(int CategoryId) {
        ArrayList<Question> AllQuestion = new ArrayList<Question>();
        try {

            PreparedStatement ps = con.prepareStatement("select top 7 * from Questions where CategoryId=? order by newID()");
            ps.setInt(1, CategoryId);
            ResultSet rs = ps.executeQuery();
            Question q1 = null;
            String IDS = "";
            while (rs.next()) {
                q1 = new Question();
                q1.QuestionId = rs.getInt("QuestionId");
                q1.QuestionText = rs.getString("QuestionText");

                AllQuestion.add(q1);
                IDS = IDS + q1.QuestionId + ",";

            }
            rs.close();
            IDS = IDS.substring(0, IDS.length() - 1);

            Statement st = con.createStatement();
            rs = st.executeQuery("select * from Options where QuestionId in(" + IDS + ")");
            Option opt = null;
            int counter = -1;
            int QuestionId = 0;

            while (rs.next()) {

                opt = new Option();
                opt.OptionId = rs.getInt("OptionId");
                opt.OptionText = rs.getString("OptionText");
                opt.QuestionId = rs.getInt("QuestionId");

                for (Question question : AllQuestion) {
                    if (question.QuestionId == opt.QuestionId) {
                        question.Options.add(opt);
                        break;
                    }
                }

            }

            rs.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return AllQuestion;
    }

    public void reject(Challenge obj) {
        System.out.println(obj.ChallengeId);

        try {
            PreparedStatement ps = con.prepareStatement("update Challenges set Status=2 where ChallengeId =?");
            ps.setInt(1, obj.ChallengeId);
            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void accept(Challenge obj) {
        try {
            PreparedStatement ps = con.prepareStatement("update Challenges set Status=1 where ChallengeId =?");
            ps.setInt(1, obj.ChallengeId);
            ps.executeUpdate();
        } catch (Exception e) {

            System.out.println(e);

        }

    }

    public void checkChallengeStatus(Challenge obj) {
        try {
            PreparedStatement ps = con.prepareStatement("Select * from Challenges Where ChallengeId=?");
            ps.setInt(1, obj.ChallengeId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            obj.Status = rs.getInt("Status");
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveAttemptedQuestion(int pChallengeId, int pQuestionId, int pOptionId, int pUserNo) {

        try {
            PreparedStatement ps = con.prepareStatement("insert into AttemptedQuestions values(?,?,?,?)");
            ps.setInt(1, pQuestionId);
            ps.setInt(2, pOptionId);
            ps.setInt(3, pChallengeId);
            ps.setInt(4, pUserNo);
            ps.execute();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void IncreaseFlag(Challenge ob) {
        try {
            PreparedStatement ps = con.prepareStatement("update challenges set flag= flag+1 where challengeid=?");
            ps.setInt(1, ob.ChallengeId);
            ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Challenge ChallengeCompleted(Challenge ob) {
        Challenge challenge = new Challenge();
        try {
            PreparedStatement ps = con.prepareStatement(" Select C.ChallengeId, C.Date, C.FromUserNo, FU.Name as [FromUserName] , C.ToUserNo, TU.Name as [ToUserName], C.Status,C.Flag, C.CategoryId, Cat.CategoryName\n" +
"                                        From Challenges as [C], Categories as [Cat], Users as [FU], Users as [TU]\n" +
"                                        Where C.CategoryId=Cat.categoriesId and C.FromUserNo=FU.UserNo and C.ToUserNo=TU.UserNo and C.ChallengeId=?");
            ps.setInt(1, ob.ChallengeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                challenge.ChallengeId = rs.getInt("ChallengeId");
                challenge.Date = rs.getString("Date");
                challenge.FromUserNo = rs.getInt("FromUserNo");
                challenge.ToUserNo = rs.getInt("ToUserNo");

                challenge.ToUserName = rs.getString("ToUserName");
                challenge.FromUserName = rs.getString("FromUserName");
                challenge.CategoryName = rs.getString("CategoryName");
                challenge.CategoryId = rs.getInt("CategoryId");

                challenge.Flag = rs.getInt("Flag");

            }
            rs.close();
        } catch (Exception e) {
        }
        return challenge;
    }

    public ArrayList<AttemptedQuestion> getResult(int UserNo, int ChallengeId) {

        ArrayList<AttemptedQuestion> attemptedQuestions = new ArrayList<AttemptedQuestion>();

        try {
            PreparedStatement ps = con.prepareStatement("select Q.QuestionText, O.OptionText,O.Answer, (Select OptionText From Options Where Answer=1 and QuestionId=AQ.QuestionId) as [CorrectAnswer] From Questions as [Q], Options as [O], AttemptedQuestions as [AQ]\n" +
"   Where Q.QuestionId=AQ.QuestionId and O.OptionId=AQ.OptionId and UserNo=? and ChallengeId=?");
            ps.setInt(1, UserNo);
            ps.setInt(2, ChallengeId);
            ResultSet rs = ps.executeQuery();
            AttemptedQuestion obj;

            while (rs.next() == true) {
                obj = new AttemptedQuestion();
                obj.QuestionText = rs.getString("QuestionText");
                obj.OptionText = rs.getString("OptionText");
                obj.IsAnswer = rs.getInt("Answer");
                obj.CorrectAnswer = rs.getString("CorrectAnswer");
                attemptedQuestions.add(obj);

            }

            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return attemptedQuestions;
    }

    public ArrayList<Challenge> getSentChallenges(User obj) {

        ArrayList<Challenge> SentChallenges = new ArrayList<Challenge>();
        Challenge ob;

        try {
            PreparedStatement ps = con.prepareStatement("Select C.ChallengeId, C.Date, U.Name, Cat.CategoryName From Challenges as [C], Users as [U], Categories as [Cat] where C.CategoryId=Cat.categoriesId and C.ToUserNo=U.UserNo and C.FromUserNo=?");
            ps.setInt(1, obj.UserNo);

            ResultSet rs = ps.executeQuery();

            while (rs.next() == true) {
                ob = new Challenge();
                ob.ChallengeId = rs.getInt("ChallengeId");
                ob.Date = rs.getString("Date");
                ob.FromUserName = rs.getString("Name");
                ob.CategoryName = rs.getString("CategoryName");

                SentChallenges.add(ob);
            }

            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return SentChallenges;
    }

    public ArrayList<Challenge> getRecievedChallenges(User obj) {

        ArrayList<Challenge> SentChallenges = new ArrayList<Challenge>();
        Challenge ob;

        try {
            PreparedStatement ps = con.prepareStatement("Select C.ChallengeId, C.Date, U.Name, Cat.CategoryName From Challenges as [C], Users as [U], Categories as [Cat] where C.CategoryId=Cat.categoriesId and C.FromUserNo=U.UserNo and C.ToUserNo=?");
            ps.setInt(1, obj.UserNo);

            ResultSet rs = ps.executeQuery();

            while (rs.next() == true) {
                ob = new Challenge();
                ob.ChallengeId = rs.getInt("ChallengeId");
                ob.Date = rs.getString("Date");
                ob.FromUserName = rs.getString("Name");
                ob.CategoryName = rs.getString("CategoryName");

                SentChallenges.add(ob);
            }

            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return SentChallenges;
    }
    
    
    public void closeConnection() {
        try {

            con.close();
            con = null;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
