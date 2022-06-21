package com.nttdata.bc19.mstransactionfixedtermaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsTransactionFixedTermAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTransactionFixedTermAccountApplication.class, args);
	}

}
