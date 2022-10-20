package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Represents a signer for a multi-signature XRPL Transaction.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableSigner.class)
@JsonDeserialize(as = ImmutableSigner.class)
public interface Signer {

  /**
   * Construct a builder for this class.
   *
   * @return An {@link ImmutableSigner.Builder}.
   */
  static ImmutableSigner.Builder builder() {
    return ImmutableSigner.builder();
  }

  /**
   * The {@link Address} associated with this signature, as it appears in the signer list.
   *
   * @return The {@link Address} of the signer account.
   */
  @JsonProperty("Account")
  Address account();

  /**
   * A signature for a transaction, verifiable using the {@link Signer#signingPublicKey()}.
   *
   * @return A {@link String} containing the transaction signature.
   */
  @JsonProperty("TxnSignature")
  String transactionSignature();

  /**
   * The public key used to create this signature.
   *
   * @return A {@link String} containing the public key used to sign the transaction.
   */
  @JsonProperty("SigningPubKey")
  String signingPublicKey();

}
