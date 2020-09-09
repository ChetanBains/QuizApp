package challengeclient;

import components.Challenge;
import java.net.*;
import java.io.*;
import java.util.TimerTask;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ResponseWaiting extends javax.swing.JDialog {

    Challenge sentChallenge = null;
    java.util.Timer tt = new java.util.Timer();

    JDialog Parent;

    public ResponseWaiting(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Parent = this;
    }

    class inner extends TimerTask {

        public void run() {

            try {
                sentChallenge.commandname = "CheckChallengeStatus";
                Socket ss = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
                ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
                os.writeObject(sentChallenge);

                ObjectInputStream is = new ObjectInputStream(ss.getInputStream());
                sentChallenge = (Challenge) is.readObject();

                if (sentChallenge.Status == 1) {

                    Parent.dispose();
                    this.cancel();
                    StartChallenge CurrentChallenge = new StartChallenge(null, true);
                    CurrentChallenge.Start(sentChallenge, 0);

//                ObjectInputStream input = new ObjectInputStream(ss.getInputStream());
//                Challenge feedback = (Challenge) input.readObject();
                } else if (sentChallenge.Status == 2) {
                    JOptionPane.showMessageDialog(null, "Challenge Rejected!", ChallengeClient.Title, JOptionPane.INFORMATION_MESSAGE);
                    this.cancel();
                    Parent.dispose();
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    public int start(Challenge challenge) {
        int ret = 0;
        try {
            Socket ss = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
            ObjectOutputStream os = new ObjectOutputStream(ss.getOutputStream());
            os.writeObject(challenge);
            ObjectInputStream is = new ObjectInputStream(ss.getInputStream());
            sentChallenge = (Challenge) is.readObject();

            if (sentChallenge.commandname.equals("UserLoggedOut")) {
                JOptionPane.showMessageDialog(this, "Logged has logged out, you cannot send challenge!", ChallengeClient.Title, JOptionPane.WARNING_MESSAGE);
                this.dispose();
                ret = -1;
            } else {
                inner ob = new inner();

                tt.schedule(ob, 2000, 2000);

                this.setVisible(true);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Waiting for the response of the opponent");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
