package challengeclient;

import components.Challenge;
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Test extends javax.swing.JDialog {

    Challenge challenge;

    public Test(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void startTest(Challenge challenge) {
        this.challenge = challenge;
        jLabel1.setText(challenge.Question.QuestionText);

        jRadioButton1.setText(challenge.Question.Options.get(0).OptionText);
        jRadioButton2.setText(challenge.Question.Options.get(1).OptionText);
        jRadioButton3.setText(challenge.Question.Options.get(2).OptionText);
        jRadioButton4.setText(challenge.Question.Options.get(3).OptionText);

        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("jLabel1");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("jRadioButton1");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("jRadioButton2");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("jRadioButton3");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("jRadioButton4");

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4)
                            .addComponent(jButton1))))
                .addContainerGap(533, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jRadioButton1)
                .addGap(38, 38, 38)
                .addComponent(jRadioButton2)
                .addGap(41, 41, 41)
                .addComponent(jRadioButton3)
                .addGap(42, 42, 42)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(validateData()==true)
        {
        if (jRadioButton1.isSelected()) {
            challenge.Question.AttemptedOptionId = challenge.Question.Options.get(0).OptionId;
        } else if (jRadioButton2.isSelected()) {
            challenge.Question.AttemptedOptionId = challenge.Question.Options.get(1).OptionId;
        } else if (jRadioButton3.isSelected()) {
            challenge.Question.AttemptedOptionId = challenge.Question.Options.get(2).OptionId;
        } else if (jRadioButton4.isSelected()) {
            challenge.Question.AttemptedOptionId = challenge.Question.Options.get(3).OptionId;
        }

        try {
            challenge.commandname = "SubmitQuestion";
            challenge.QuestionFor = ChallengeClient.LoggedUser.UserNo;
            Socket socket = new Socket(ChallengeClient.IpAdress, ChallengeClient.PortNo);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            os.writeObject(challenge);

            this.dispose();
        } catch (Exception e) {
            System.out.println(e);
        }
        }//validate data
        else
            JOptionPane.showMessageDialog(null,"select an option", ChallengeClient.Title, JOptionPane.WARNING_MESSAGE);

    }//GEN-LAST:event_jButton1ActionPerformed
     public boolean validateData() {
        if(jRadioButton1.isSelected())
        return true;
        else if(jRadioButton2.isSelected())
        return true;  
        else if(jRadioButton3.isSelected())
        return true;    
        else if(jRadioButton4.isSelected())
        return true;    
        
        else
            return false;
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    // End of variables declaration//GEN-END:variables
}
