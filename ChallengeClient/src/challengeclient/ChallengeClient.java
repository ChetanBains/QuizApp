package challengeclient;

import components.User;

public class ChallengeClient {

    static components.User LoggedUser = null;
    static String IpAdress = "127.0.0.1";
    static int PortNo = 5555;
    static String Title = "ChallengeMaster";

    public static void main(String[] args) {
        Gateway gateway = new Gateway();
        gateway.setVisible(true);

//        new LogIn(null, true).setVisible(true);
//        LoggedUser = new components.User();
//        LoggedUser.UserNo=2;
//        new Result(null, true).startResult(62);
    }

}
