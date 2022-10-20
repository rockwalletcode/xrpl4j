package org.xrpl.xrpl4j.model.client.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedInteger;
import org.immutables.value.Value;
import org.xrpl.xrpl4j.model.client.XrplResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerIndexBound;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Marker;
import org.xrpl.xrpl4j.model.transactions.Transaction;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * The result of an account_tx rippled call.
 */
@Value.Immutable
@JsonSerialize(as = ImmutableAccountTransactionsResult.class)
@JsonDeserialize(as = ImmutableAccountTransactionsResult.class)
public interface AccountTransactionsResult extends XrplResult {

  /**
   * Construct a builder for this class.
   *
   * @return An {@link ImmutableAccountTransactionsResult.Builder}.
   */
  static ImmutableAccountTransactionsResult.Builder builder() {
    return ImmutableAccountTransactionsResult.builder();
  }

  /**
   * Unique Address identifying the related account.
   *
   * @return The {@link Address} of the account.
   */
  Address account();

  /**
   * The ledger index of the earliest ledger actually searched for transactions.
   *
   * @return The {@link LedgerIndexBound} of the earliest ledger searched.
   * @deprecated This field will be removed in favor of {@link #ledgerIndexMinimum()}.
   */
  @Deprecated
  @Value.Auxiliary
  @JsonIgnore
  @Value.Default
  default LedgerIndex ledgerIndexMin() {
    return LedgerIndex.of(UnsignedInteger.valueOf(ledgerIndexMinimum().value()));
  }

  /**
   * The ledger index of the most recent ledger actually searched for transactions.
   *
   * @return The {@link LedgerIndexBound} of the latest ledger searched.
   * @deprecated This field will be removed in favor of {@link #ledgerIndexMaximum()}.
   */
  @Deprecated
  @Value.Auxiliary
  @JsonIgnore
  @Value.Default
  default LedgerIndex ledgerIndexMax() {
    return LedgerIndex.of(UnsignedInteger.valueOf(ledgerIndexMaximum().value()));
  }

  /**
   * The ledger index of the earliest ledger actually searched for transactions.
   *
   * @return The {@link LedgerIndexBound} of the earliest ledger searched.
   */
  @JsonProperty("ledger_index_min")
  LedgerIndexBound ledgerIndexMinimum();

  /**
   * The ledger index of the most recent ledger actually searched for transactions.
   *
   * @return The {@link LedgerIndexBound} of the latest ledger searched.
   */
  @JsonProperty("ledger_index_max")
  LedgerIndexBound ledgerIndexMaximum();

  /**
   * The limit value used in the request. (This may differ from the actual limit value enforced by the server.)
   *
   * @return An {@link UnsignedInteger} representing the requested limit.
   */
  UnsignedInteger limit();

  /**
   * Server-defined value indicating the response is paginated. Pass this to the next call to resume where this
   * call left off.
   *
   * @return A {@link String} containing the marker.
   */
  Optional<Marker> marker();

  /**
   * Array of transactions matching the request's criteria.
   *
   * @return A {@link List} of {@link AccountTransactionsTransactionResult}s.
   */
  List<AccountTransactionsTransactionResult<? extends Transaction>> transactions();

  /**
   * Whether or not the information in this response comes from a validated ledger version.
   *
   * @return {@code true} if the information is from a validated ledger, otherwise {@code false}.
   */
  @Value.Default
  default boolean validated() {
    return false;
  }
}
