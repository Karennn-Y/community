package com.project.community.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
   public static boolean equals(String plainText, String hashed) {
       if (plainText == null || plainText.length() < 1)  {
           return false;
       }

       if (hashed == null || hashed.length() < 1) {
           return false;
       }
       return BCrypt.checkpw(plainText, hashed);
   }

   public static String encPassword(String plainText) {
       if (plainText != null || plainText.length() < 1)  {
           return "";
       }
       return BCrypt.hashpw(plainText, BCrypt.gensalt());
   }
}
