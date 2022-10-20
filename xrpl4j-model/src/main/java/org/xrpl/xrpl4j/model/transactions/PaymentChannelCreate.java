package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.flags.Flags;

import java.util.Optional;

/**
 * Create a unidirectional channel and fund it with XRP. The address sending this transaction becomes the
 * "source address" of the payment channel.
 */
@Value.Immutable
@JsonSerialize(as = ImmutablePaymentChannelCreate.class)
@JsonDeserialize(as = ImmutablePaymentChannelCreate.class)
public interface PaymentChannelCreate extends Transaction {

  /**
   * Construct a builder for this class.
   *
   * @return An {@link ImmutablePaymentChannelCreate.Builder}.
   */
  static ImmutablePaymentChannelCreate.Builder builder() {
    return ImmutablePaymentChannelCreate.builder();
  }

  /**
   * Set of {@link Flags.TransactionFlags}s for this {@link PaymentChannelCreate}, which only allows the
   * {@code tfFullyCanonicalSig} flag.
   *
   * <p>The value of the flags cannot be set manually, but exists for JSON serialization/deserialization only and for
   * proper signature computation in rippled.
   *
   * @return Always {@link Flags.TransactionFlags} with {@code tfFullyCanonicalSig} set.
   */
  @JsonProperty("Flags")
  @Value.Default
  default Flags.TransactionFlags flags() {
    return new Flags.TransactionFlags.Builder().tfFullyCanonicalSig(true).build();
  }

  /**
   * Amount of XRP, in drops, to deduct from the {@link #account()}'s balance and set aside in this channel. While
   * the channel is open, the XRP can only go to the {@link #destination()} address. When the channel closes,
   * any unclaimed XRP is returned to the {@link #account()}'s balance.
   *
   * @return An {@link XrpCurrencyAmount} representing the amount of the payment channel.
   */
  @JsonProperty("Amount")
  XrpCurrencyAmount amount();

  /**
   * {@link Address} to receive XRP claims against this channel. Cannot be the same as {@link #account()};
   *
   * @return The {@link Address} of the destination account.
   */
  @JsonProperty("Destination")
  Address destination();

  /**
   * Amount of time, in seconds, the {@link #account()} must wait before closing the channel if it has unclaimed XRP.
   *
   * @return An {@link UnsignedInteger} representing the settlement delay.
   */
  @JsonProperty("SettleDelay")
  UnsignedInteger settleDelay();

  /**
   * The public key of the key pair the {@link #account()} will use to sign claims against this channel,
   * in hexadecimal. This can be any secp256k1 or Ed25519 public key.
   *
   * @return A {@link String} containing the public key for the channel.
   */
  @JsonProperty("PublicKey")
  String publicKey();

  /**
   * The time, in <a href="https://xrpl.org/basic-data-types.html#specifying-time">seconds since the Ripple Epoch</a>,
   * when this channel expires. Any transaction that would modify the channel after this time closes the channel
   * without otherwise affecting it. This value is immutable; the channel can be closed earlier than this time but
   * cannot remain open after this time.
   *
   * @return An {@link Optional} of type {@link UnsignedLong} representing the cancel after time.
   */
  @JsonProperty("CancelAfter")
  Optional<UnsignedLong> cancelAfter();

  /**
   * Arbitrary tag to further specify the destination for this payment channel, such as a hosted recipient at
   * the {@link #destination()} address.
   *
   * @return An {@link Optional} of type {@link UnsignedInteger} representing the tag of the destination account.
   */
  @JsonProperty("DestinationTag")
  Optional<UnsignedInteger> destinationTag();

}
