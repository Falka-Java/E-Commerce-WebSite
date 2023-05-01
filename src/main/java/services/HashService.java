package services;

import java.security.MessageDigest;

public class HashService {
    /**
     * Method that hashes inputted string using MD5 algorithm
     * @param input string to hash
     * @return hashed string or null if an error occurred
     */
    public static String getMD5Hash(String input){

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for(byte b : messageDigest){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e){
            System.out.println("Exception -> " + e.getMessage());
            return null;
        }
    }
}
