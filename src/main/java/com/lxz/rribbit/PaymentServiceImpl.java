package com.lxz.rribbit;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
    public Payment createPayment(Payment p) {
		System.out.println("create payment");
		p.setAmount(90000);
		return p;
	}

}