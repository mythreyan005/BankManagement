package com.wipro.bank.main;

import java.util.Date;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.service.BankService;

public class MainApp {

    public static void main(String[] args) throws Exception {

        BankService service = new BankService();
        System.out.println(service.checkBalance("12345867892"));

       
        TransferBean transferBean = new TransferBean(1, "1234567892", "1234567893", new Date(), 1000f);
        System.out.println(service.transfer(transferBean));

    
        System.out.println(service.checkBalance("1234567892"));
        System.out.println(service.checkBalance("1234567893")); 
    }
}
