package com.example.demo.event;

import com.example.demo.transaction.TransferDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class TransferEvent extends ApplicationEvent {
    private TransferDTO transferDTO;

    public TransferEvent(Object source, TransferDTO transferDTO) {
        super(source);
        this.transferDTO = transferDTO;
    }

}
