package com.wipro.bank.service;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.BankDAO;
import com.wipro.bank.util.InsufficientFundsException;

public class BankService {

    BankDAO bankDao = new BankDAO();

    public String checkBalance(String accountNumber) {
        try {
            if (bankDao.validateAccount(accountNumber)) {
                return "BALANCE: " + bankDao.findBalance(accountNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Invalid Account";
    }

    public String transfer(TransferBean transferBean) {
        if (transferBean != null) {
            try {
                if (bankDao.validateAccount(transferBean.getFromAccountNumber()) &&
                        bankDao.validateAccount(transferBean.getToAccountNumber())) {

                    float senderBalance = bankDao.findBalance(transferBean.getFromAccountNumber());

                    if (senderBalance >= transferBean.getAmount()) {
                        
                        bankDao.updateBalance(
                                transferBean.getFromAccountNumber(),
                                senderBalance - transferBean.getAmount()
                        );

                       
                        float receiverBalance = bankDao.findBalance(transferBean.getToAccountNumber());
                        bankDao.updateBalance(
                                transferBean.getToAccountNumber(),
                                receiverBalance + transferBean.getAmount()
                        );

                        
                        bankDao.transferMoney(transferBean);
                        return "  SUCCESS";
                    }

                    throw new InsufficientFundsException();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "INVALID TRANSACTION";
    }
}
