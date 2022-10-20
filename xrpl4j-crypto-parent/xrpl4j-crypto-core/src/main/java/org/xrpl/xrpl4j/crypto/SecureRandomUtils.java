package org.xrpl.xrpl4j.crypto;

/*
 * Copyright 2021 XRPL Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

import java.security.SecureRandom;

/**
 * Utility class for working with SecureRandom implementation (Implementation from web3j).
 *
 * @see "https://github.com/web3j/web3j/blob/master/crypto/src/main/java/org/web3j/crypto/SecureRandomUtils.java"
 */
public final class SecureRandomUtils {

  private static final SecureRandom SECURE_RANDOM;
  // Taken from BitcoinJ implementation
  // https://github.com/bitcoinj/bitcoinj/blob/3cb1f6c6c589f84fe6e1fb56bf26d94cccc85429/core/
  // src/main/java/org/bitcoinj/core/Utils.java#L573
  private static int isAndroid = -1;

  static {
    if (isAndroidRuntime()) {
      new LinuxSecureRandom();
    }
    SECURE_RANDOM = new SecureRandom();
  }

  /**
   * No-args constructor, to prevent instantiation.
   */
  private SecureRandomUtils() {
  }

  /**
   * Accessor for the shared {@link SecureRandom}.
   *
   * @return A {@link SecureRandom}.
   */
  public static SecureRandom secureRandom() {
    return SECURE_RANDOM;
  }

  static boolean isAndroidRuntime() {
    if (isAndroid == -1) {
      final String runtime = System.getProperty("java.runtime.name");
      isAndroid = (runtime != null && runtime.equals("Android Runtime")) ? 1 : 0;
    }
    return isAndroid == 1;
  }
}
