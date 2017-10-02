/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmltest;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Ananya
 */
public class UpdateBalanceModel implements Initializable {

        public void updateInfo(String amount, String bankacc, String cvv, String bankname) throws SQLException
        {
            
            if(verifyBankDetails(bankacc, cvv, bankname) == 1 && checkBalance(amount) == 3)
            {
                try {
                    UserModel user = new UserModel();
                user.updateUserBalance(Integer.parseInt(amount),JavaFXMLTest.user_id);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                
                try {
                    BankModel bank = new BankModel();
                bank.updateBankBalance(Integer.parseInt(amount));
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                
                
                
            
        try {
            TransactionModel transaction = new TransactionModel();
                transaction.updateTransaction(Integer.parseInt(amount),'+', "updateBalance");
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("EasyPayzee");
        alert.setHeaderText("Successful!");
        alert.setContentText("Your EasyPayzee Account has been successfully updated");
        alert.showAndWait();
    }
            else if(verifyBankDetails(bankacc, cvv, bankname) == 1 && checkBalance(amount) == 4)
            {
                System.out.println("Insufficient balance");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("EasyPayzee");
                alert.setHeaderText("Insufficient balance!");
                alert.setContentText("You cannot withdraw money into your EasyPayzee account as the balance in your bank is insufficient");
                alert.showAndWait();
            }
            else if(verifyBankDetails(bankacc, cvv, bankname) == 2 && checkBalance(amount) == 3)
            {
                System.out.println("Incorrect bank details");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("EasyPayzee");
                alert.setHeaderText("Incorrect bank details!");
                alert.setContentText("Please check the bank details that you have entered");
                alert.showAndWait();
            }
            else
            {
                System.out.println("Incorrect details");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("EasyPayzee");
                alert.setHeaderText("Incorrect bank details!");
                alert.setContentText("Please check the bank details that you have entered");
                alert.showAndWait();
            }
        }
        
       
    public int verifyBankDetails(String bankacc, String cvv, String bankname) throws SQLException
    {
        
        try {
            DBConnection conn2 = new DBConnection();
            Statement stmt2 = conn2.connect().createStatement();
            
            ResultSet rs2 = stmt2.executeQuery("select * from bank where uid='" +JavaFXMLTest.user_id+ "' and acc_no='" +bankacc+ "' and cvv='" +cvv+ "' and bank_name='" +bankname+ "'");
            int count = 0;
            while (rs2.next()) {
              count++;
            }
            if (count > 0) {

                return 1;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 2;
    }
    
    public int checkBalance(String amount)
    {
        try {
            DBConnection conn3 = new DBConnection();
            Statement stmt3 = conn3.connect().createStatement();
            
            ResultSet rs3 = stmt3.executeQuery("select * from bank where uid='" +JavaFXMLTest.user_id+ "'");
            
            while (rs3.next()) {
              if(rs3.getInt("bank_balance") > Integer.parseInt(amount))
                  return 3;
            }
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return 4;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   
    }    
    
}
