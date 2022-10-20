package org.xrpl.xrpl4j.model.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.flags.Flags;

import java.util.Optional;

/**
 * Add additional XRP to an open payment channel, update the expiration time of the channel, or both.
 * Only the source address of the channel can use this transaction.
 *
 * @see "https://xrpl.org/paymentchannelfund.html"
 */
@Value.Immutable
@JsonSerialize(as = ImmutablePaymentChannelFund.class)
@JsonDeserialize(as = ImmutablePaymentChannelFund.class)
public interface PaymentChannelFund extends Transaction {

  /**
   * Construct a builder for this class.
   *
   * @return An {@link ImmutablePaymentChannelFund.Builder}.
   */
  static ImmutablePaymentChannelFund.Builder builder() {
    return ImmutablePaymentChannelFund.builder();
  }

  /**
   * Set of {@link Flags.TransactionFlags}s for this {@link PaymentChannelFund}, which only allows
   * {@code tfFullyCanonicalSig} flag.
   *
   * <p>The value of the flags cannot be set manually, but exists for JSON serialization/deserialization only and for
   * proper signature computation in rippled.
   *
   * @return Always {@link Flags.TransactionFlags} with {@code tfFullyCanonicalSig} set.
   */
  @JsonProperty("Flags")
  @Value.Derived
  default Flags.TransactionFlags flags() {
    return new Flags.TransactionFlags.Builder().tfFullyCanonicalSig(true).build();
  }

  /**
   * The unique ID of the channel to fund.
   *
   * @return A {@link Hash256} containing the channel ID.
   */
  @JsonProperty("Channel")
  Hash256 channel();

  /**
   * Amount of XRP, in drops to add to the channel. This field is required, therefore it is not possible to
   * set the {@link #expiration()} without adding value to the channel.  However, you can change the expiration
   * and add a negligible amount of XRP (like 1 drop) to the channel.
   *
   * @return An {@link XrpCurrencyAmount} representing the amount of the payment channel.
   */
  @JsonProperty("Amount")
  XrpCurrencyAmount amount();

  /**
   * New Expiration time to set for the channel, in seconds since the Ripple Epoch. This must be later than
   * either the current time plus the SettleDelay of the channel, or the existing Expiration of the channel.
   * After the Expiration time, any transaction that would access the channel closes the channel without
   * taking its normal action. Any unspent XRP is returned to the source address when the channel closes.
   * (Expiration is separate from the channel's immutable CancelAfter time.)
   *
   * @return An {@link Optional} of type {@link UnsignedLong}.
   */
  @JsonProperty("Expiration")
  Optional<UnsignedLong> expiration();
}
