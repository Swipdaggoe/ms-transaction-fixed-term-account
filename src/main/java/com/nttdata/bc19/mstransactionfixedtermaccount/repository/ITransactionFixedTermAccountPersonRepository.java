package com.nttdata.bc19.mstransactionfixedtermaccount.repository;

import com.nttdata.bc19.mstransactionfixedtermaccount.model.TransactionFixedTermAccountPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ITransactionFixedTermAccountPersonRepository extends ReactiveMongoRepository<TransactionFixedTermAccountPerson, String> {

    Flux<TransactionFixedTermAccountPerson> findByIdFixedTermAccountPerson(String idFixedTermAccountPerson);
}
