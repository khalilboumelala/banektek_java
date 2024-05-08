package com.inventorymanagementsystem.services;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    // Méthode pour générer un sel aléatoire
    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }


    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }


}
