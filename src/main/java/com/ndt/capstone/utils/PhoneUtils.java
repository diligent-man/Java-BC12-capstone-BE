package com.ndt.capstone.utils;


public class PhoneUtils {
    public static String formatPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();

        if  (phoneNumber.length() == 10) {
            phoneNumber = phoneNumber.substring(0, 4) + " " + phoneNumber.substring(4, 7) + " " + phoneNumber.substring(7);
        } else if  (phoneNumber.length() == 11) {
            phoneNumber = phoneNumber.substring(0, 3) + " " + phoneNumber.substring(3, 6) + " " + phoneNumber.substring(6);
        }

        return phoneNumber;
    }
}
