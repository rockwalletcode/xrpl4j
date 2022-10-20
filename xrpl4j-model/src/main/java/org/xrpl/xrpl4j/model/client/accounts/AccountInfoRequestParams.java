package org.xrpl.xrpl4j.model.client.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.client.LegacyLedgerSpecifierUtils;
import org.xrpl.xrpl4j.model.client.XrplRequestParams;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Hash256;

import java.util.Optional;
import javax.annotation.Nullable;

/**
 * Request parameters for the "account_info" API method.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableAccountInfoRequestParams.class)
@JsonDeserialize(as = ImmutableAccountInfoRequestParams.class)
public interface AccountInfoRequestParams extends XrplRequestParams {

  /**
   * Construct a builder for this class.
   *
   * @return An {@link ImmutableAccountInfoRequestParams.Builder}.
   */
  static ImmutableAccountInfoRequestParams.Builder builder() {
    return ImmutableAccountInfoRequestParams.builder();
  }

  /**
   * Constructs an {@link AccountInfoRequestParams} for a specified account, with all other fields set to their
   * defaults.
   *
   * @param account The {@link Address} of the account.
   *
   * @return An {@link AccountInfoRequestParams} for the specified account.
   */
  static AccountInfoRequestParams of(Address account) {
    return builder()
      .account(account)
      .build();
  }

  /**
   * A unique {@link Address} for the account.
   *
   * @return The {@link Address} of the account.
   */
  Address account();

  /**
   * A 20-byte hex string for the ledger version to use.
   *
   * @return An optionally-present {@link Hash256}.
   * @deprecated Ledger hash should be specified in {@link #ledgerSpecifier()}.
   */
  @JsonIgnore
  @Deprecated
  @Value.Auxiliary
  Optional<Hash256> ledgerHash();

  /**
   * The ledger index of the ledger to use, or a shortcut string to choose a ledger automatically.
   *
   * @return A {@link LedgerIndex}.  Defaults to {@link LedgerIndex#CURRENT}.
   * @deprecated Ledger index and any shortcut values should be specified in {@link #ledgerSpecifier()}.
   */
  @JsonIgnore
  @Deprecated
  @Nullable
  @Value.Auxiliary
  LedgerIndex ledgerIndex();

  /**
   * Specifies the ledger version to request. A ledger version can be specified by ledger hash,
   * numerical ledger index, or a shortcut value.
   *
   * @return A {@link LedgerSpecifier} specifying the ledger version to request.
   */
  @Value.Default
  @JsonUnwrapped
  default LedgerSpecifier ledgerSpecifier() {
    return LegacyLedgerSpecifierUtils.computeLedgerSpecifier(ledgerHash(), ledgerIndex());
  }

  /**
   * A boolean indicating if the {@link #account()} field only accepts a public key or XRP Ledger {@link Address}.
   * Always true, as {@link #account()} is always an {@link Address}.
   *
   * @return {@code true} if the account field only accepts a public key or XRP Ledger address, otherwise {@code false}.
   *   Defaults to {@code true}.
   */
  @Value.Derived
  default boolean strict() {
    return true;
  }

  /**
   * If true, and the <a href="https://xrpl.org/known-amendments.html#feeescalation">FeeEscalation</a> amendment is
   * enabled, also returns stats about queued transactions associated with this account. Can only be used when
   * querying for the data from the current open ledger.
   *
   * @return {@code true} if queue transactions should be returned in the response, {@code false} if not.
   *   Defaults to {@code false}.
   */
  @Value.Default
  default boolean queue() {
    return false;
  }

  /**
   * If true, and the <a href="https://xrpl.org/known-amendments.html#multisign">MultiSign amendment</a> is enabled,
   * also returns any {@link org.xrpl.xrpl4j.model.ledger.SignerListObject}s associated with this account.
   *
   * @return {@code true} if signer lists should be returns, {@code false} if not. Defaults to {@code true}.
   */
  @Value.Default
  @JsonProperty("signer_lists")
  default boolean signerLists() {
    return true;
  }

}
