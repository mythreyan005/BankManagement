package com.wipro.bank.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {

    private Connection con;

    public BankDAO() {
        
        this.con = DBUtil.getDBConnection();
    }

    public boolean validateAccount(String accountNumber) throws SQLException {
        String query = "SELECT Account_Number FROM account_tbl WHERE Account_Number = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, accountNumber);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public float findBalance(String accountNumber) throws SQLException {
        if (validateAccount(accountNumber)) {
            String query = "SELECT Balance FROM account_tbl WHERE Account_Number = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getFloat("Balance");
        } else {
            return -1;
        }
    }

    public boolean updateBalance(String accountNumber, float newBalance) {
        try {
            if (validateAccount(accountNumber)) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE account_tbl SET Balance = ? WHERE Account_Number = ?"
                );
                ps.setFloat(1, newBalance);
                ps.setString(2, accountNumber);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean transferMoney(TransferBean transferBean) {
        try {
            String query = "INSERT INTO transfer_tbl (Transaction_id, Account_Number, Beneficiary_acc_number, Transaction_Date, Transaction_Amount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, generateSequenceNumber());
            ps.setString(2, transferBean.getFromAccountNumber());
            ps.setString(3, transferBean.getToAccountNumber());
            ps.setDate(4, new Date(transferBean.getDateOfTransaction().getTime()));
            ps.setFloat(5, transferBean.getAmount());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int generateSequenceNumber() {
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery("SELECT MAX(Transaction_id) FROM transfer_tbl");
            rs.next();
            return rs.getInt(1) + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; 
    }
}
