package challengeserver;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import components.*;

public class ChallengeServer {

    static ArrayList<User> loggedUsers = new ArrayList<User>();

    public static void main(String[] args) {

        // Static ArrayList<User> loggedUsers = new ArrayList<User>();
        ArrayList<Challenge> AllChallenges = new ArrayList<Challenge>();

        CommandName cmd = null;
        System.out.println("Server started...");

        try {
            ServerSocket serversocket = new ServerSocket(5555);

            while (true) {

                Socket clientsocket = serversocket.accept();

                ObjectInputStream ois = new ObjectInputStream(clientsocket.getInputStream());

                cmd = (CommandName) ois.readObject();

                if (cmd.commandname.equals("Register")) {
                    User obj = (User) cmd;
                    datalayer.DALUser objDAL = new datalayer.DALUser();
                    objDAL.register(obj);
                    objDAL.closeConnection();
                }// if command is register
                else if (cmd.commandname.equals("logIn")) {
                    User obj = (User) cmd;
                    datalayer.DALUser objDal = new datalayer.DALUser();
                    User user = objDal.authentication(obj);

                    if (user.UserNo > 0) {
                        loggedUsers.add(user);
                    }

                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(user);

                    objDal.closeConnection();
                } // if command is log in
                else if (cmd.commandname.equals("ChangePassword")) {
                    User obj = (User) cmd;
                    datalayer.DALUser objDal = new datalayer.DALUser();
                    objDal.changePassword(obj);

                    objDal.closeConnection();
                } //if command is change password
                else if (cmd.commandname.equals("getUsers")) {
                    User obj = (User) cmd;
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(loggedUsers);
                }// if command is getUser
                else if (cmd.commandname.equals("SignOut")) {

                    User user = (User) cmd;
                    int pos = 0;
                    
                    for (User us : loggedUsers) {
                        if (us.UserNo == user.UserNo) {
                            break;
                        }
                        pos++;
                    }
                    
                    loggedUsers.remove(pos);
                    
                } // if command is SignOut
                else if (cmd.commandname.equals("sendChallenge")) {
                    Challenge obj = (Challenge) cmd;
                    
                    boolean found = false;
                    
                    for(User user : loggedUsers)
                    {
                        if(user.UserNo==obj.ToUserNo)
                        {
                            found=true;
                            break;
                        }
                    }
                    
                    if(found==true)
                    {
                        datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                        objDal.sendChallenge(obj);
                    }
                    else
                    {
                        obj.commandname="UserLoggedOut";
                    }
                    
                    ObjectOutputStream is = new ObjectOutputStream(clientsocket.getOutputStream());
                    is.writeObject(obj);
                } // if command is sendChallenge
                else if (cmd.commandname.equals("anyChallenge")) {
                    Challenge obj = (Challenge) cmd;

                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    Challenge challenge = objDal.anyChallenge(obj);

                    ObjectOutputStream is = new ObjectOutputStream(clientsocket.getOutputStream());
                    is.writeObject(challenge);
                } //if command is anyChallenge
                else if (cmd.commandname.equals("Reject")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    objDal.reject(obj);
                }// if command is any reject
                else if (cmd.commandname.equals("Accept")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    objDal.accept(obj);

                    obj.Questions = objDal.getQuestions(obj.CategoryId);
                    objDal.closeConnection();
                    AllChallenges.add(obj);
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(obj);

                }// if command is any reject
                else if (cmd.commandname.equals("CheckChallengeStatus")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    objDal.checkChallengeStatus(obj);

                    if (obj.Status == 1) {
                        for (Challenge challenge : AllChallenges) {
                            if (challenge.ChallengeId == obj.ChallengeId) {
                                obj = challenge;
                                break;
                            }
                        }
                    }
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(obj);

                }//Check Challenge Status
                else if (cmd.commandname.equals("AnswerThisQuestion")) {
                    Challenge obj = (Challenge) cmd;
                    int i = 0;
                    for (Challenge challenge : AllChallenges) {
                        if (challenge.ChallengeId == obj.ChallengeId) {
                            break;
                        }
                        i++;
                    }

                    AllChallenges.remove(i);
                    AllChallenges.add(obj);

                }//Answer This question
                else if (cmd.commandname.equals("QuestionForMe")) {
                    Challenge obj = (Challenge) cmd;
                    Challenge ref = null;
                    int i = 0;
                    for (Challenge challenge : AllChallenges) {
                        if (challenge.ChallengeId == obj.ChallengeId) {
                            ref = challenge;
                            break;
                        }
                        i++;
                    }

                    Challenge obj1 = new Challenge();

                    if (ref.QuestionFor == obj.MyNumber) {
                        obj1.ChallengeId = ref.ChallengeId;
                        obj1.Date = ref.Date;
                        obj1.CategoryName = ref.CategoryName;
                        obj1.CategoryId = ref.CategoryId;
                        obj1.FromUserName = ref.FromUserName;
                        obj1.FromUserNo = ref.FromUserNo;
                        obj1.ToUserName = ref.ToUserName;
                        obj1.ToUserNo = ref.ToUserNo;
                        obj1.MyNumber = ref.MyNumber;
                        obj1.Question = ref.Question;
                        obj1.Questions = ref.Questions;
                        obj1.QuestionFor = ref.QuestionFor;
                        obj1.Status = ref.Status;
                        obj1.commandname = ref.commandname;
                        obj1.QuestionIndex = ref.QuestionIndex;

                        AllChallenges.get(i).QuestionFor = 0;
                    }

                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(obj1);

                }//Question for me
                else if (cmd.commandname.equals("SubmitQuestion")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    objDal.saveAttemptedQuestion(obj.ChallengeId, obj.Question.QuestionId, obj.Question.AttemptedOptionId, obj.QuestionFor);

                } else if (cmd.commandname.equals("IncreaseFlag")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    objDal.IncreaseFlag(obj);
                    objDal.closeConnection();
                } else if (cmd.commandname.equals("ChallengeCompleted")) {
                    Challenge obj = (Challenge) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    Challenge ob = objDal.ChallengeCompleted(obj);
                    objDal.closeConnection();
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(ob);

                } else if (cmd.commandname.equals("GetResult")) {
                    Challenge obj = (Challenge) cmd;

                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    ArrayList<AttemptedQuestion> aq = objDal.getResult(obj.UserNo, obj.ChallengeId);
                    objDal.closeConnection();
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(aq);

                } else if (cmd.commandname.equals("GetSentChallenges")) {
                    User obj = (User) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    ArrayList<Challenge> challenges = objDal.getSentChallenges(obj);
                    objDal.closeConnection();
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(challenges);

                } else if (cmd.commandname.equals("GetRecievedChallenges")) {
                    User obj = (User) cmd;
                    datalayer.DALChallenge objDal = new datalayer.DALChallenge();
                    ArrayList<Challenge> challenges = objDal.getRecievedChallenges(obj);
                    objDal.closeConnection();
                    ObjectOutputStream os = new ObjectOutputStream(clientsocket.getOutputStream());
                    os.writeObject(challenges);
                }

            }//While Loop

        } catch (Exception e) {
            System.out.println(e);
        }//catch

    }

}
