package com.project.board.utils;

import java.security.SecureRandom;
import java.util.Random;

public class LoginIdGenerator {

    public static String generateLoginId(){
        String prefix = "Guest";
        String randomNumber = generateRandomNumber(9);
        return prefix + randomNumber;
    }

    // Method to generate a random number with a specified number of digits
    private static String generateRandomNumber(int length) {
        Random random = new SecureRandom();
        StringBuilder number = new StringBuilder();

        // Ensure the number is exactly the specified length
        for (int i = 0; i < length; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }

}
