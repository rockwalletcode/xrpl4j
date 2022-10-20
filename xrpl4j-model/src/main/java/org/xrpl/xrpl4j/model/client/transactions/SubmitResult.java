package org.xrpl.xrpl4j.model.client.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.UnsignedInteger;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.xrpl.xrpl4j.model.client.XrplResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.transactions.Transaction;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.util.Optional;

/**
 * The result of a "submit" rippled API call.
 *
 * @param <TxnType> The type of {@link Transaction} that was submitted.
 */
@Immutable
@JsonSerialize(as = ImmutableSubmitResult.class)
@JsonDeserialize(as = ImmutableSubmitResult.class)
public interface SubmitResult<TxnType extends Transaction> extends XrplResult {

  /**
   * Construct a builder for this class.
   *
   * @param <T> The actual type of {@link Transaction} that was submitted.
   *
   * @return An {@link ImmutableSubmitResult.Builder}
   */
  static <T extends Transaction> ImmutableSubmitResult.Builder<T> builder() {
    return ImmutableSubmitResult.builder();
  }

  /**
   * Text result code indicating the preliminary result of the transaction, for example "tesSUCCESS".
   *
   * @return {@link String} containing the result of the submission.
   * @deprecated This field will be typed as a {@link String} in a future release. Until then, use
   *   {@link #result()}.
   */
  @Deprecated
  @Value.Auxiliary
  default Optional<String> engineResult() {
    return Optional.of(result());
  }

  /**
   * Text result code indicating the preliminary result of the transaction, for example "tesSUCCESS".
   *
   * @return {@link String} containing the result of the submission.
   * @deprecated This will be removed in a future version and replaced by a field of the same type called
   *   {@link #engineResult()}.
   */
  @Deprecated
  @JsonProperty("engine_result")
  String result();

  /**
   * Human-readable explanation of the transaction's preliminary result.
   *
   * @return An optionally-present {@link String} containing the result message of the submission.
   * @deprecated This field will be typed as a {@link String} in a future release. Until then, use
   *   {@link #resultMessage()}.
   */
  @Deprecated
  @Value.Auxiliary
  default Optional<String> engineResultMessage() {
    return Optional.of(resultMessage());
  }

  /**
   * Human-readable explanation of the transaction's preliminary result.
   *
   * @return A {@link String} containing the result message of the submission.
   * @deprecated This will be removed in a future version and replaced by a field of the same type called
   *   {@link #engineResultMessage()}.
   */
  @Deprecated
  @JsonProperty("engine_result_message")
  String resultMessage();

  /**
   * The complete transaction in hex {@link String} format.
   *
   * @return A hexadecimal encoded {@link String} containing the binary encoded transaction that was submitted.
   */
  @JsonProperty("tx_blob")
  String transactionBlob();

  /**
   * The complete {@link Transaction} that was submitted, as a {@link TransactionResult}.
   *
   * @return A {@link TransactionResult}.
   */
  @JsonProperty("tx_json")
  TransactionResult<TxnType> transactionResult();

  /**
   * The value {@code true} indicates that the transaction was applied, queued, broadcast, or kept for later.
   * The value {@code false} indicates that none of those happened, so the transaction cannot possibly succeed as long
   * as you do not submit it again and have not already submitted it another time.
   *
   * @return {@code true} if the transaction was applied, queued, broadcast, or kept for later, otherwise {@code false}.
   */
  boolean accepted();

  /**
   * The next Sequence number available for the sending account after all pending and queued transactions.
   *
   * @return An {@link UnsignedInteger} representing the sequence number.
   */
  @JsonProperty("account_sequence_available")
  UnsignedInteger accountSequenceAvailable();

  /**
   * The next Sequence number for the sending account after all transactions that have been provisionally applied,
   * but not transactions in the queue.
   *
   * @return An {@link UnsignedInteger} representing the sequence number.
   */
  @JsonProperty("account_sequence_next")
  UnsignedInteger accountSequenceNext();

  /**
   * The value {@code true} indicates that this transaction was applied to the open ledger.
   * In this case, the transaction is likely, but not guaranteed, to be validated in the next ledger version.
   *
   * @return {@code true} if this transaction was applied to the open ledger, otherwise {@code false}.
   */
  boolean applied();

  /**
   * {@code true} indicates this transaction was broadcast to peer servers in the peer-to-peer XRP Ledger network.
   * {@code false} indicates the transaction was not broadcast to any other servers.
   *
   * @return {@code true} if this transaction was broadcast to peer servers in the peer-to-peer XRP Ledger network,
   *   otherwise {@code false}.
   */
  boolean broadcast();

  /**
   * The value {@code true} indicates that the transaction was kept to be retried later.
   *
   * @return {@code true} if the transaction was kept to be retried later, {@code false} if not.
   */
  boolean kept();

  /**
   * The value {@code true} indicates the transaction was put in the Transaction Queue,
   * which means it is likely to be included in a future ledger version.
   *
   * @return {@code true} if the transaction was put in the Transaction Queue, otherwise {@code false}.
   */
  boolean queued();

  /**
   * The current open ledger cost before processing this transaction. Transactions with a lower cost are
   * likely to be queued.
   *
   * @return An {@link XrpCurrencyAmount} representing the current open ledger cost.
   */
  @JsonProperty("open_ledger_cost")
  XrpCurrencyAmount openLedgerCost();

  /**
   * The ledger index of the newest validated ledger at the time of submission.
   * This provides a lower bound on the ledger versions that the transaction can appear in as a result of this request.
   *
   * @return A {@link LedgerIndex} indicating the ledger index of the newest validated ledger.
   */
  @JsonProperty("validated_ledger_index")
  LedgerIndex validatedLedgerIndex();

}
