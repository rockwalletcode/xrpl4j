package org.xrpl.xrpl4j.crypto;

import com.google.common.io.BaseEncoding;
import org.immutables.value.Value;
import org.immutables.value.Value.Derived;
import org.xrpl.xrpl4j.codec.addresses.Base58;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByte;
import org.xrpl.xrpl4j.codec.addresses.UnsignedByteArray;
import org.xrpl.xrpl4j.codec.addresses.VersionType;

import java.util.Objects;

/**
 * A typed instance of an XRPL private-key.
 */
public interface PrivateKey {

  /**
   * Keys generated from the secp256k1 curve have 33 bytes in XRP Ledger. However, keys derived from the ed25519 curve
   * have only 32 bytes, and so get prefixed with this HEX value so that all keys in the ledger are 33 bytes.
   */
  UnsignedByte PREFIX = UnsignedByte.of(0xED);

  /**
   * Instantiates a new builder.
   *
   * @return A {@link ImmutableDefaultPrivateKey.Builder}.
   */
  static ImmutableDefaultPrivateKey.Builder builder() {
    return ImmutableDefaultPrivateKey.builder();
  }

  /**
   * Construct a {@link PrivateKey} from a base58-encoded {@link String}.
   *
   * @param base58EncodedPrivateKey A base58-encoded {@link String}.
   *
   * @return A {@link PrivateKey}.
   */
  static PrivateKey fromBase58EncodedPrivateKey(final String base58EncodedPrivateKey) {
    return PrivateKey.builder()
      .value(UnsignedByteArray.of(Base58.decode(base58EncodedPrivateKey)))
      .build();
  }

  /**
   * Construct a {@link PrivateKey} from a base16-encoded (HEX) {@link String}.
   *
   * @param base16EncodedPrivateKey A base16-encoded (HEX) {@link String}.
   *
   * @return A {@link PrivateKey}.
   */
  static PrivateKey fromBase16EncodedPrivateKey(final String base16EncodedPrivateKey) {
    Objects.requireNonNull(base16EncodedPrivateKey);

    return PrivateKey.builder()
      .value(UnsignedByteArray.of(BaseEncoding.base16().decode(base16EncodedPrivateKey)))
      .build();
  }

  /**
   * The key, in binary (Note: will be 33 bytes).
   *
   * @return An instance of {@link UnsignedByteArray}.
   */
  UnsignedByteArray value();

  /**
   * The key, as a Base58-encoded string.
   *
   * @return A {@link String}.
   */
  String base58Encoded();

  /**
   * The key, as a Base16-encoded (i.e., HEX) string. Note that if this is an Ed25519 private-key, then this value
   * contains a leading prefix of `ED`, in hex.
   *
   * @return A {@link String}.
   */
  String base16Encoded();

  /**
   * The type of this key (either {@link VersionType#ED25519} or {@link VersionType#SECP256K1}).
   *
   * @return A {@link VersionType}.
   */
  VersionType versionType();

  /**
   * Abstract implementation for immutables.
   */
  @Value.Immutable
  abstract class DefaultPrivateKey implements PrivateKey {

    @Override
    @Derived
    public String base58Encoded() {
      return Base58.encode(value().toByteArray());
    }

    @Override
    @Derived
    public String base16Encoded() {
      return this.value().hexValue();
    }

    @Derived
    @Override
    public VersionType versionType() {
      return this.base16Encoded().startsWith("ED") ? VersionType.ED25519 : VersionType.SECP256K1;
    }

    @Override
    public String toString() {
      return base58Encoded();
    }
  }

}
