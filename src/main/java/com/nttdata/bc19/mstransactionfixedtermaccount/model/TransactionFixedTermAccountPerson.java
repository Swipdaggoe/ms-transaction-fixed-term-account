package com.nttdata.bc19.mstransactionfixedtermaccount.model;

import com.nttdata.bc19.mstransactionfixedtermaccount.model.responseWC.FixedTermAccountPerson;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class TransactionFixedTermAccountPerson extends BaseModel{
    //private  String code;
    private String idFixedTermAccountPerson;
    private FixedTermAccountPerson fixedTermAccountPerson;
    private LocalDateTime transactionDate;
    private String transactionTypeFixedTermAccount;
    private double amount;
}
