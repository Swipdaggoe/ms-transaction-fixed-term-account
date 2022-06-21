package com.nttdata.bc19.mstransactionfixedtermaccount.request;

import lombok.Data;

@Data
public class TransactionFixedTermAccountPersonRequest {
    private String idFixedTermAccount;
    private double amount;
    private String transactionTypeFixedTermAccount;
}
