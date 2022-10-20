package org.xrpl.xrpl4j.codec.addresses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray.of;

import org.junit.jupiter.api.Test;


public class UnsignedByteArrayTest {

  static byte MAX_BYTE = (byte) 255;

  @Test
  public void ofByteArray() {

    assertThat(UnsignedByteArray.of(new byte[] {0}).hexValue()).isEqualTo("00");
    assertThat(UnsignedByteArray.of(new byte[] {MAX_BYTE}).hexValue()).isEqualTo("FF");
    assertThat(UnsignedByteArray.of(new byte[] {0, MAX_BYTE}).hexValue()).isEqualTo("00FF");
    assertThat(UnsignedByteArray.of(new byte[] {MAX_BYTE, 0}).hexValue()).isEqualTo("FF00");
    assertThat(UnsignedByteArray.of(new byte[] {MAX_BYTE, MAX_BYTE}).hexValue()).isEqualTo("FFFF");
  }

  @Test
  public void ofUnsignedByteArray() {
    assertThat(of(UnsignedByte.of(0)).hexValue()).isEqualTo("00");
    assertThat(of(UnsignedByte.of(MAX_BYTE)).hexValue()).isEqualTo("FF");
    assertThat(of(UnsignedByte.of(0), UnsignedByte.of((MAX_BYTE))).hexValue()).isEqualTo("00FF");
    assertThat(of(UnsignedByte.of(MAX_BYTE), UnsignedByte.of((0))).hexValue()).isEqualTo("FF00");
    assertThat(of(UnsignedByte.of(MAX_BYTE), UnsignedByte.of((MAX_BYTE))).hexValue()).isEqualTo("FFFF");
  }

  @Test
  public void lowerCaseOrUpperCase() {
    assertThat(UnsignedByteArray.fromHex("Ff").hexValue()).isEqualTo("FF");
    assertThat(UnsignedByteArray.fromHex("00fF").hexValue()).isEqualTo("00FF");
    assertThat(UnsignedByteArray.fromHex("00ff").hexValue()).isEqualTo("00FF");
    assertThat(UnsignedByteArray.fromHex("00FF").hexValue()).isEqualTo("00FF");
    assertThat(UnsignedByteArray.fromHex("fF00").hexValue()).isEqualTo("FF00");
    assertThat(UnsignedByteArray.fromHex("ff00").hexValue()).isEqualTo("FF00");
    assertThat(UnsignedByteArray.fromHex("FF00").hexValue()).isEqualTo("FF00");
    assertThat(UnsignedByteArray.fromHex("abcdef0123").hexValue()).isEqualTo("ABCDEF0123");
  }

}
