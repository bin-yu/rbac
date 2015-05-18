/********************************************************************
 * File Name:    EncryptionUtil.java
 *
 * Date Created: Mar 16, 2015
 *
 * ------------------------------------------------------------------
 * Copyright (C) 2010 Symantec Corporation. All Rights Reserved.
 *
 *******************************************************************/

// PACKAGE/IMPORTS --------------------------------------------------
package org.binyu.rbac.auth;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

/**
 * Util class for encryption purpose
 */
@Component
public class EncryptionUtil
{

  // CONSTANTS ------------------------------------------------------

  private static final String US_ASCII = "US-ASCII";
  private static final String HMAC_KEY = "{045216F7-84F1-4B87-A4BD-B7F00A65EB3C}";
  private static final byte[] SEED_128 = { -57, -50, 7, -72, 5, -56, -30, -119, 20, -54, -95, 108, -20, 15, -106, -53, 57, 76, 66,
    101, 126, 107, -67, -1, -69, 22, -106, 13, 89, 56, 33, -122, 2, 127, -1, 19, -93, 20, 34, 1, 25, -115, 75, -105, 111, -23,
    -115, 109, -98, 83, 64, -72, -117, -5, -12, -110, -67, 40, -28, 118, 93, 3, -57, 19, -67, 13, 60, 103, 57, -26, -49, -119, 35,
    -108, -93, 88, 96, 45, -19, -44, 29, 13, 34, 38, -114, 58, -45, 16, -61, 96, 23, 75, -128, -12, -43, 118, 72, -41, 22, 112,
    -35, -110, 51, 50, -7, -83, 14, -30, -120, 76, -43, 50, -54, -43, -116, 76, 107, -8, -45, -110, -42, 11, -38, -82, 51, 55, 103,
    66 };

  // CLASS VARIABLES ------------------------------------------------

  // INSTANCE VARIABLES ---------------------------------------------

  private SecureRandom sr = new SecureRandom();

  // CONSTRUCTORS ---------------------------------------------------
  public EncryptionUtil()
  {

    sr.setSeed(SEED_128);
  }

  // PUBLIC METHODS -------------------------------------------------
  /**
   * Call this method to check whether the origin string matches the encrypted one.
   * @param origin
   * @param encrypted
   * @return
   */
  public boolean matchesHMAC(String origin, String encrypted)
  {
    if (encrypted == null || encrypted.length() < 16)
    {
      return false;
    }
    String newEncrypted = encryptHMAC(extractSalt(encrypted), origin);
    return encrypted.equals(newEncrypted);
  }

  /**
   * Call this method to encrypt the origin data using HMAC with a random generated salt
   * @param origin
   * @return
   */
  public String encryptHMAC(String origin)
  {
    return encryptHMAC(generateSalt(), origin);
  }

  // PROTECTED METHODS ----------------------------------------------

  // PRIVATE METHODS ------------------------------------------------

  private String encryptHMAC(String salt, String origin)
  {
    try
    {
      final Charset asciiCs = Charset.forName(US_ASCII);
      final SecretKeySpec secret_key = new javax.crypto.spec.SecretKeySpec(asciiCs.encode(HMAC_KEY).array(), "HmacSHA256");
      final Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
      sha256_HMAC.init(secret_key);
      final byte[] mac_data = sha256_HMAC.doFinal(asciiCs.encode(salt + origin).array());
      StringBuilder sb = new StringBuilder();
      sb.append(salt);
      sb.append(Hex.encode(mac_data));
      return sb.toString();
    }
    catch (NoSuchAlgorithmException | InvalidKeyException e)
    {
      throw new RuntimeException(e);
    }
  }

  private String generateSalt()
  {
    long random = sr.nextLong();
    return String.format("%016X", random);
  }

  private String extractSalt(String encrypted)
  {
    return encrypted.substring(0, 16);
  }
  // ACCESSOR METHODS -----------------------------------------------

}
