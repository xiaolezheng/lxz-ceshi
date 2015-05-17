package com.lxz.rribbit;

import org.rribbit.Listener;

public interface PaymentService {

	@Listener(hint = "createPayment")
    public Payment createPayment(Payment p);
}