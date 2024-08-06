package com.example.demo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TransferEventListener implements ApplicationListener<TransferEvent> {
    @Override
    public void onApplicationEvent(TransferEvent event) {
        System.out.println("Transfer Event Handled");
        System.out.println(event.getTransferDTO());
        System.out.println(event.getSource());
    }
}
