package challengeclient;

import components.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JDialog;

public class StartChallenge extends javax.swing.JDialog {

    Challenge challenge = null;
    int Turn;
    ArrayList<Question> AttemptedQuestions = new ArrayList<Question>();
    JDialog Parent;

    int QuestionCounter = 0;
    int AskedQuestionsCounter = 0;

    Timer timer;

    class inner extends TimerTask {

        public void run() {

            try {
                challenge.MyNumber = ChallengeClient.LoggedUser.UserNo;
                challenge.commandname = "QuestionForMe";

                Socket ss = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
                ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
                os.writeObject(challenge);
                ObjectInputStream is = new ObjectInputStream(ss.getInputStream());
                Challenge feedback = (Challenge) is.readObject();

                if (feedback.ChallengeId > 0) {
                    challenge = feedback;

                    if (challenge.QuestionIndex == 0) {
                        jRadioButton1.setEnabled(false);
                    } else if (challenge.QuestionIndex == 1) {
                        jRadioButton2.setEnabled(false);
                    } else if (challenge.QuestionIndex == 2) {
                        jRadioButton3.setEnabled(false);
                    } else if (challenge.QuestionIndex == 3) {
                        jRadioButton4.setEnabled(false);
                    } else if (challenge.QuestionIndex == 4) {
                        jRadioButton5.setEnabled(false);
                    } else if (challenge.QuestionIndex == 5) {
                        jRadioButton6.setEnabled(false);
                    } else if (challenge.QuestionIndex == 6) {
                        jRadioButton7.setEnabled(false);
                    }

                    Test test = new Test(null, true);
                    test.startTest(challenge);
                    jButton1.setEnabled(true);

                    QuestionCounter++;

                    if (AskedQuestionsCounter == 3 && QuestionCounter == 3) {
                        try {

                            challenge.commandname = "IncreaseFlag";
                            Socket socket = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(challenge);

                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                        this.cancel();
                        Parent.dispose();

                        Result result = new Result(null, true);
                        result.startResult(challenge.ChallengeId);
                    }

                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }

    public StartChallenge(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Parent = this;
    }

    public void Start(Challenge challenge, int pTurn) {
        this.challenge = challenge;
        this.Turn = pTurn;

        jLabel3.setText(challenge.ToUserName);
        jLabel4.setText(challenge.FromUserName);

        jRadioButton1.setText(challenge.Questions.get(0).QuestionText);
        jRadioButton2.setText(challenge.Questions.get(1).QuestionText);
        jRadioButton3.setText(challenge.Questions.get(2).QuestionText);
        jRadioButton4.setText(challenge.Questions.get(3).QuestionText);
        jRadioButton5.setText(challenge.Questions.get(4).QuestionText);
        jRadioButton6.setText(challenge.Questions.get(5).QuestionText);
        jRadioButton7.setText(challenge.Questions.get(6).QuestionText);

        if (Turn == 0) {
            jButton1.setEnabled(false);
        }

        inner obj = new inner();
        timer = new Timer();
        timer.schedule(obj, 1000, 2000);

        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("To");

        jLabel2.setText("From");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("jRadioButton1");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("jRadioButton2");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("jRadioButton3");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("jRadioButton4");

        jButton1.setText("Send Questions");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setText("jRadioButton6");

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setText("jRadioButton5");

        buttonGroup1.add(jRadioButton7);
        jRadioButton7.setText("jRadioButton7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                                    .addComponent(jRadioButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 50, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRadioButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                            .addComponent(jRadioButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jRadioButton1, jRadioButton2, jRadioButton3, jRadioButton4});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton5)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton6)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton7)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public boolean validateData() {
        if(jRadioButton1.isSelected())
        return true;
        else if(jRadioButton2.isSelected())
        return true;  
        else if(jRadioButton3.isSelected())
        return true;    
        else if(jRadioButton4.isSelected())
        return true;    
        else if(jRadioButton5.isSelected())
        return true; 
        else if(jRadioButton6.isSelected())
        return true;    
        else if(jRadioButton7.isSelected())
        return true;    
        else
            return false;
        
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (validateData()==true) {
            int index = -1;

            if (jRadioButton1.isSelected()) {
                index = 0;
                jRadioButton1.setEnabled(false);
            } else if (jRadioButton2.isSelected()) {
                index = 1;
                jRadioButton2.setEnabled(false);

            } else if (jRadioButton3.isSelected()) {
                index = 2;
                jRadioButton3.setEnabled(false);

            } else if (jRadioButton4.isSelected()) {
                index = 3;
                jRadioButton4.setEnabled(false);

            } else if (jRadioButton5.isSelected()) {
                index = 4;
                jRadioButton5.setEnabled(false);
            } else if (jRadioButton6.isSelected()) {
                index = 5;
                jRadioButton6.setEnabled(false);
            } else if (jRadioButton7.isSelected()) {
                index = 6;
                jRadioButton7.setEnabled(false);
            }

            challenge.Question = challenge.Questions.get(index);
            challenge.commandname = "AnswerThisQuestion";
            challenge.QuestionIndex = index;

            if (challenge.FromUserNo == ChallengeClient.LoggedUser.UserNo) {
                challenge.QuestionFor = challenge.ToUserNo;
            } else {
                challenge.QuestionFor = challenge.FromUserNo;
            }

            jButton1.setEnabled(false);

            buttonGroup1.clearSelection();
            AskedQuestionsCounter++;

            try {
                Socket ss = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
                ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
                os.writeObject(challenge);
                os.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }

            if (AskedQuestionsCounter == 3 && QuestionCounter == 3) {
                try {

                    challenge.commandname = "IncreaseFlag";
                    Socket socket = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(challenge);

                } catch (Exception ex) {
                    System.out.println(ex);
                }

                timer.cancel();
                this.dispose();

                Result result = new Result(null, true);
                result.startResult(challenge.ChallengeId);

            }
        }//if validateData
        else
            JOptionPane.showMessageDialog(null, "Select any Question", ChallengeClient.Title, JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    // End of variables declaration//GEN-END:variables
}
