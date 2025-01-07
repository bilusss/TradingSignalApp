package com.signalapp.tradingsignalapp;

import com.signalapp.tradingsignalapp.Transaction.Transaction;
import com.signalapp.tradingsignalapp.Transaction.TransactionRepository;
import com.signalapp.tradingsignalapp.User.User;
import com.signalapp.tradingsignalapp.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TestModels {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testUserCreation() {
        // Create a new user
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("securePassword");
        userRepository.create(user);

        // Retrieve user by username
        Optional<User> user2 = userRepository.getByUsername("testUser");

        // Validate user properties
        assertTrue(user2.isPresent());
        assertEquals("testUser", user2.get().getUsername());
    }

    @Test
    void testTransactionCreation() {
        // Create a new transaction
        Transaction transaction = new Transaction();
        transaction.setTitle("testTitle");
        transaction.setUserId(1);
        transaction.setCryptoIdBought(1);
        transaction.setCryptoIdSold(2);
        transaction.setAmountBought(3.0);
        transaction.setAmountSold(4.0);
        transaction.setDescription("testDescription");
        try{
            transactionRepository.create(transaction);
        }catch(Exception e){

        }

        // Retrieve transaction by ID
        Transaction transaction2 = transactionRepository.getById(1);

        // Validate transaction properties
        assertNotNull(transaction2);
        assertEquals("testTitle", transaction2.getTitle());
        assertEquals(1, transaction2.getUserId());
        assertEquals(1, transaction2.getCryptoIdBought());
        assertEquals(2, transaction2.getCryptoIdSold());
        assertEquals(3.0, transaction2.getAmountBought());
        assertEquals(4.0, transaction2.getAmountSold());
        assertEquals("testDescription", transaction2.getDescription());
    }
}
