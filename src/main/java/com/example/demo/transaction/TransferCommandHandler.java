package com.example.demo.transaction;

import com.example.demo.Command;
import com.example.demo.event.TransferEventPublisher;
import com.example.demo.exceptions.CustomBaseException;
import com.example.demo.exceptions.SimpleResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional
public class TransferCommandHandler implements Command<TransferDTO, ResponseEntity> {
    private final BankAccountRepository bankAccountRepository;
    private final TransferEventPublisher transferEventPublisher;

    public TransferCommandHandler(BankAccountRepository bankAccountRepository, TransferEventPublisher transferEventPublisher) {
        this.bankAccountRepository = bankAccountRepository;
        this.transferEventPublisher = transferEventPublisher;
    }

    @Override
    public ResponseEntity execute(TransferDTO transferDTO) {
        Optional<BankAccount> fromAccount = bankAccountRepository.findById(transferDTO.getFromUser());
        Optional<BankAccount> toAccount = bankAccountRepository.findById(transferDTO.getToUser());
        if (toAccount.isEmpty() || fromAccount.isEmpty()) {
            throw new CustomBaseException(HttpStatus.BAD_REQUEST, new SimpleResponse("Invalid users"));
        }

        BankAccount from = fromAccount.get();
        BankAccount to = toAccount.get();

        add(to, transferDTO.getAmount());
        deduct(from, transferDTO.getAmount());

        // No need to call method .save

        transferEventPublisher.publish(this, transferDTO);

        return ResponseEntity.ok("Successful");
    }

    private void deduct(BankAccount bankAccount, double amount) {
        if (bankAccount.getBalance() < amount) {
            throw new CustomBaseException(HttpStatus.INTERNAL_SERVER_ERROR, new SimpleResponse("Insufficient funds"));
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
    }

    private void add(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
    }
}
