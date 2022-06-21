package com.nttdata.bc19.mstransactionfixedtermaccount.api;

import com.nttdata.bc19.mstransactionfixedtermaccount.model.TransactionFixedTermAccountPerson;
import com.nttdata.bc19.mstransactionfixedtermaccount.request.TransactionFixedTermAccountPersonRequest;
import com.nttdata.bc19.mstransactionfixedtermaccount.service.ITransactionFixedTermAccountPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transaction/fixed-term")
public class TrasactionFixedTermAccountPersonApi {

    @Autowired
    private ITransactionFixedTermAccountPersonService transactionFixedTermAccountPersonService;

    @PostMapping
    public Mono<TransactionFixedTermAccountPerson> create(@RequestBody TransactionFixedTermAccountPersonRequest transactionFixedTermAccountPersonRequest){
        return transactionFixedTermAccountPersonService.create(transactionFixedTermAccountPersonRequest);
    }

    @PutMapping
    public Mono<TransactionFixedTermAccountPerson> update(@RequestBody TransactionFixedTermAccountPerson transactionFixedTermAccountPerson){
        return transactionFixedTermAccountPersonService.update(transactionFixedTermAccountPerson);
    }

    @GetMapping
    public Flux<TransactionFixedTermAccountPerson> findAll(){
        return transactionFixedTermAccountPersonService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<TransactionFixedTermAccountPerson> findById(@PathVariable String id){ return transactionFixedTermAccountPersonService.findById(id); }

    @GetMapping("/find/{idFixedTermAcountPerson}")
    public Flux<TransactionFixedTermAccountPerson> findByIdPasProPerCli(@PathVariable String idFixedTermAcountPerson){
        return transactionFixedTermAccountPersonService.findByIdFixedTermAccountPerson(idFixedTermAcountPerson);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return transactionFixedTermAccountPersonService.deleteById(id);
    }
}
