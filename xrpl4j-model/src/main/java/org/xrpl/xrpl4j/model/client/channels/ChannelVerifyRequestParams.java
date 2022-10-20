package org.xrpl.xrpl4j.model.client.channels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.client.XrplRequestParams;
import org.xrpl.xrpl4j.model.transactions.Hash256;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

/**
 * Request parameters for the "channel_verify" rippled API method.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableChannelVerifyRequestParams.class)
@JsonDeserialize(as = ImmutableChannelVerifyRequestParams.class)
public interface ChannelVerifyRequestParams extends XrplRequestParams {

  /**
   * Construct a builder for this class.
   *
   * @return An {@link ImmutableChannelVerifyRequestParams.Builder}.
   */
  static ImmutableChannelVerifyRequestParams.Builder builder() {
    return ImmutableChannelVerifyRequestParams.builder();
  }

  /**
   * The amount of XRP, in drops, the provided {@link #signature()} authorizes.
   *
   * @return the xrp currency amount
   */
  XrpCurrencyAmount amount();

  /**
   * The Channel ID of the channel that provides the XRP.
   *
   * @return the hash 256
   */
  @JsonProperty("channel_id")
  Hash256 channelId();

  /**
   * The public key of the channel and the key pair that was used to create the {@link #signature()}, in
   * hexadecimal or the XRP Ledger's base58 format.
   *
   * @return A {@link String} containing the public key.
   */
  @JsonProperty("public_key")
  String publicKey();

  /**
   * The signature to verify, in hexadecimal.
   *
   * @return A {@link String} containing the signature.
   */
  String signature();

}
