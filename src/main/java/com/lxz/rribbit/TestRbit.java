package com.lxz.rribbit;

import org.rribbit.RequestResponseBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestRbit {
    private RequestResponseBus rrb;

    @Autowired
    PaymentServiceImpl ps;

    public static void main(String[] args) {
        new TestRbit().testRribbit();
    }

    private void testRribbit() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        rrb = (RequestResponseBus) context.getBean("rrb");
        Payment p = rrb.send("createPayment", new Payment());
        System.out.println(p);
    }
}
