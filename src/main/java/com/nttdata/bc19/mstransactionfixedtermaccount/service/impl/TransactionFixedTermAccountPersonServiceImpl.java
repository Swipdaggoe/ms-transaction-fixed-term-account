package com.nttdata.bc19.mstransactionfixedtermaccount.service.impl;

import com.nttdata.bc19.mstransactionfixedtermaccount.exception.ModelNotFoundException;
import com.nttdata.bc19.mstransactionfixedtermaccount.model.TransactionFixedTermAccountPerson;
import com.nttdata.bc19.mstransactionfixedtermaccount.model.responseWC.FixedTermAccountPerson;
import com.nttdata.bc19.mstransactionfixedtermaccount.repository.ITransactionFixedTermAccountPersonRepository;
import com.nttdata.bc19.mstransactionfixedtermaccount.request.TransactionFixedTermAccountPersonRequest;
import com.nttdata.bc19.mstransactionfixedtermaccount.service.ITransactionFixedTermAccountPersonService;
import com.nttdata.bc19.mstransactionfixedtermaccount.util.TransactionTypePasPro;
import com.nttdata.bc19.mstransactionfixedtermaccount.webclient.impl.ServiceWCImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TransactionFixedTermAccountPersonServiceImpl implements ITransactionFixedTermAccountPersonService {

    @Autowired
    ITransactionFixedTermAccountPersonRepository transactionFixedTermAccountPersonRepository;

    @Autowired
    private ServiceWCImpl clientServiceWC;

    @Override
    public Mono<TransactionFixedTermAccountPerson> create(TransactionFixedTermAccountPersonRequest transactionFixedTermAccountPersonRequest) {
        return clientServiceWC.findFixedTermAccountPersonById(transactionFixedTermAccountPersonRequest.getIdFixedTermAccount())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(fixedTermAccountResponse -> this.businessLogicTransaction(transactionFixedTermAccountPersonRequest, fixedTermAccountResponse));
    }

    @Override
    public Mono<TransactionFixedTermAccountPerson> update(TransactionFixedTermAccountPerson transactionFixedTermAccountPerson) {
        transactionFixedTermAccountPerson.setUpdatedAt(LocalDateTime.now());
        return clientServiceWC.findFixedTermAccountPersonById(transactionFixedTermAccountPerson.getId())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(fixedTermAccountPerson -> this.updateTransaction(transactionFixedTermAccountPerson));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return transactionFixedTermAccountPersonRepository.deleteById(id);
    }

    @Override
    public Mono<TransactionFixedTermAccountPerson> findById(String id) {
        return transactionFixedTermAccountPersonRepository.findById(id);
    }

    @Override
    public Flux<TransactionFixedTermAccountPerson> findAll() {
        return transactionFixedTermAccountPersonRepository.findAll();
    }

    @Override
    public Flux<TransactionFixedTermAccountPerson> findByIdFixedTermAccountPerson(String idFixedTermAccountPerson) {
        return transactionFixedTermAccountPersonRepository.findByIdFixedTermAccountPerson(idFixedTermAccountPerson);
    }

    private Mono<TransactionFixedTermAccountPerson> businessLogicTransaction(TransactionFixedTermAccountPersonRequest transactionFixedTermAccountPersonRequest, FixedTermAccountPerson fixedTermAccountPerson){

        TransactionFixedTermAccountPerson transactionFixedTermAccountPerson = new TransactionFixedTermAccountPerson();
        transactionFixedTermAccountPerson.setId(new ObjectId().toString());
        transactionFixedTermAccountPerson.setIdFixedTermAccountPerson(transactionFixedTermAccountPersonRequest.getIdFixedTermAccount());
        transactionFixedTermAccountPerson.setTransactionTypeFixedTermAccount(transactionFixedTermAccountPersonRequest.getTransactionTypeFixedTermAccount());
        transactionFixedTermAccountPerson.setTransactionDate(LocalDateTime.now());
        transactionFixedTermAccountPerson.setCreatedAt(LocalDateTime.now());

        if(fixedTermAccountPerson.getLastTrasactionDate().getMonth().getValue() < LocalDateTime.now().getMonth().getValue()){
            fixedTermAccountPerson.setNumberMovements(0);
        }
        fixedTermAccountPerson.setLastTrasactionDate(LocalDateTime.now());

        double commission = 0;
        fixedTermAccountPerson.setNumberMovements(fixedTermAccountPerson.getNumberMovements() + 1);
        if(fixedTermAccountPerson.getPasiveProduct().getNumLimitMovements() < fixedTermAccountPerson.getNumberMovements()){
            commission = fixedTermAccountPerson.getPasiveProduct().getTransactionCommission();
        }

        if(transactionFixedTermAccountPersonRequest.getTransactionTypeFixedTermAccount().equals(TransactionTypePasPro.RETIRO.name())){
            if(fixedTermAccountPerson.getAmount() >= transactionFixedTermAccountPersonRequest.getAmount() + commission){
                fixedTermAccountPerson.setAmount(fixedTermAccountPerson.getAmount() - transactionFixedTermAccountPersonRequest.getAmount() - commission);
                return clientServiceWC.updateFixedTermAccountPerson(fixedTermAccountPerson)
                        .switchIfEmpty(Mono.error(new Exception()))
                        .flatMap(fixedTermAccountPersonUpdate -> this.registerTransaction(fixedTermAccountPersonUpdate, transactionFixedTermAccountPerson));
            }
            else{
                return Mono.error(new ModelNotFoundException("Insufficient balance."));
            }
        }
        else if(transactionFixedTermAccountPersonRequest.getTransactionTypeFixedTermAccount().equals(TransactionTypePasPro.DEPOSITO.name())){
            fixedTermAccountPerson.setAmount(fixedTermAccountPerson.getAmount() + transactionFixedTermAccountPersonRequest.getAmount() - commission);
            return clientServiceWC.updateFixedTermAccountPerson(fixedTermAccountPerson)
                    .switchIfEmpty(Mono.error(new Exception()))
                    .flatMap(fixedTermAccountPersonUpdate -> this.registerTransaction(fixedTermAccountPersonUpdate, transactionFixedTermAccountPerson));
        }

        return Mono.error(new ModelNotFoundException("Invalid option"));
    }

    private Mono<TransactionFixedTermAccountPerson> registerTransaction(FixedTermAccountPerson fixedTermAccountPerson, TransactionFixedTermAccountPerson transactionFixedTermAccountPerson){
        transactionFixedTermAccountPerson.setFixedTermAccountPerson(fixedTermAccountPerson);
        return transactionFixedTermAccountPersonRepository.save(transactionFixedTermAccountPerson);
    }

    private Mono<TransactionFixedTermAccountPerson> updateTransaction(TransactionFixedTermAccountPerson transactionFixedTermAccountPerson){
        transactionFixedTermAccountPerson.setUpdatedAt(LocalDateTime.now());
        return transactionFixedTermAccountPersonRepository.save(transactionFixedTermAccountPerson);
    }
}
