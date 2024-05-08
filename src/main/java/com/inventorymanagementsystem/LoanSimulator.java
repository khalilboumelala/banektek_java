/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventorymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Gurjit
 */
public class LoanSimulator implements Initializable {
    
    private Label label;
    @FXML
    private Button close;
    @FXML
    private TextField downpayment_id;
    @FXML
    private TextField interest_id;
    @FXML
    private TextField purchaseprice_id;
    @FXML
    private Slider slider_id;
    
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    @FXML
    private Button loanbtn;
    @FXML
    private TextField ten_id;
    @FXML
    private TextField twenty_id;
    @FXML
    private TextField thirty_id;
    @FXML
    private TextField monthlypayment_id;
    @FXML
    private TextField loan_id;
    @FXML
    private TextField customyear_id;
            @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
//    downpayment_id.a
//
//    @FXML
//    private void restrict_int(KeyEvent event) {
//    char c = event.getEventType();
//    if(!(Character.isDigit(c))) {
//        event.consume();
//    }
//    }
//
    @FXML
public void onExit(){
    close.getScene().getWindow().hide();
}

    @FXML
    private void calculate(ActionEvent event) {
    Double purchase = Double.parseDouble(purchaseprice_id.getText());
    Double downpay = Double.parseDouble(downpayment_id.getText());
    Double interest = Double.parseDouble(interest_id.getText());
    Double loan = loan(purchase, downpay);
    Double monthly_ten = monthly_payment(loan, interest, 10);
    Double monthly_twenty = monthly_payment(loan, interest, 20);
    Double monthly_thirty = monthly_payment(loan, interest, 30);
    Double monthy_custom  = monthly_payment(loan,interest,slider_id.getValue());

    loan_id.setText(loan.toString());
    ten_id.setText(monthly_ten.toString());
    twenty_id.setText(monthly_twenty.toString());
    thirty_id.setText(monthly_thirty.toString());
    monthlypayment_id.setText(monthy_custom.toString());
    customyear_id.setText(String.valueOf(slider_id.getValue()));

    }


    public double loan(double purchase, double down) {
        return purchase-down;
}
    
    
  public double monthly_payment(double loan, double interest, double months) {
        double interest_rate = interest/1200;
        double monthlyPayment = loan * interest_rate / (1 -
      (1 / Math.pow(1 + interest_rate, months*12)));
    return monthlyPayment;  
   }

    @FXML
    private void restrict_int(KeyEvent event) {
    }
}
