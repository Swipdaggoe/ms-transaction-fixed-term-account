package com.nttdata.bc19.mstransactionfixedtermaccount.service;

import com.nttdata.bc19.mstransactionfixedtermaccount.model.TransactionFixedTermAccountPerson;
import com.nttdata.bc19.mstransactionfixedtermaccount.request.TransactionFixedTermAccountPersonRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionFixedTermAccountPersonService {

    Mono<TransactionFixedTermAccountPerson> create(TransactionFixedTermAccountPersonRequest transactionFixedTermAccountPersonRequest);
    Mono<TransactionFixedTermAccountPerson> update(TransactionFixedTermAccountPerson transactionFixedTermAccountPerson);
    Mono<Void>deleteById(String id);
    Mono<TransactionFixedTermAccountPerson> findById(String id);
    Flux<TransactionFixedTermAccountPerson> findAll();

    Flux<TransactionFixedTermAccountPerson> findByIdFixedTermAccountPerson(String idFixedTermAccountPerson);
}
