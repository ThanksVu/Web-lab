package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        String plainPassword = "password123";

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        boolean matches = BCrypt.checkpw(plainPassword, hashedPassword);
        System.out.println("Verification test: " + matches);
    }
}
