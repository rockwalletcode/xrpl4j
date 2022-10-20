package org.xrpl.xrpl4j.model.ledger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Market interface for XRP Ledger Objects.
 * TODO: pull common fields up.
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.EXISTING_PROPERTY,
  property = "LedgerEntryType"
)
@JsonSubTypes( {
  @JsonSubTypes.Type(value = ImmutableAccountRootObject.class, name = "AccountRoot"),
  //    @JsonSubTypes.Type(value = ImmutableAmendmentsObject.class, name = "Amendments"),
  @JsonSubTypes.Type(value = ImmutableCheckObject.class, name = "Check"),
  @JsonSubTypes.Type(value = ImmutableDepositPreAuthObject.class, name = "DepositPreauth"),
  //    @JsonSubTypes.Type(value = ImmutableDirectoryNodeObject.class, name = "DirectoryNode"),
  @JsonSubTypes.Type(value = ImmutableEscrowObject.class, name = "Escrow"),
  //    @JsonSubTypes.Type(value = ImmutableFeeSettingsObject.class, name = "FeeSettings"),
  //    @JsonSubTypes.Type(value = ImmutableLedgerHashesObject.class, name = "LedgerHashes"),
  //    @JsonSubTypes.Type(value = ImmutableNegativeUnlObject.class, name = "NegativeUNL"),
  @JsonSubTypes.Type(value = ImmutableOfferObject.class, name = "Offer"),
  @JsonSubTypes.Type(value = ImmutablePayChannelObject.class, name = "PayChannel"),
  @JsonSubTypes.Type(value = ImmutableRippleStateObject.class, name = "RippleState"),
  @JsonSubTypes.Type(value = ImmutableSignerListObject.class, name = "SignerList"),
  @JsonSubTypes.Type(value = ImmutableTicketObject.class, name = "Ticket"),
})
// TODO: Uncomment subtypes as we implement
public interface LedgerObject {

  /**
   * Enum for all types of ledger objects.
   */
  enum LedgerEntryType {
    /**
     * The {@link LedgerEntryType} for {@code AccountRoot} ledger objects.
     */
    ACCOUNT_ROOT("AccountRoot"),

    /**
     * The {@link LedgerEntryType} for {@code Amendments} ledger objects.
     */
    AMENDMENTS("Amendments"),

    /**
     * The {@link LedgerEntryType} for {@code Check} ledger objects.
     */
    CHECK("Check"),

    /**
     * The {@link LedgerEntryType} for {@code DepositPreauth} ledger objects.
     */
    DEPOSIT_PRE_AUTH("DepositPreauth"),

    /**
     * The {@link LedgerEntryType} for {@code DirectoryNode} ledger objects.
     */
    DIRECTORY_NODE("DirectoryNode"),

    /**
     * The {@link LedgerEntryType} for {@code Escrow} ledger objects.
     */
    ESCROW("Escrow"),

    /**
     * The {@link LedgerEntryType} for {@code FeeSettings} ledger objects.
     */
    FEE_SETTINGS("FeeSettings"),

    /**
     * The {@link LedgerEntryType} for {@code LedgerHashes} ledger objects.
     */
    LEDGER_HASHES("LedgerHashes"),

    /**
     * The {@link LedgerEntryType} for {@code NegativeUNL} ledger objects.
     */
    NEGATIVE_UNL("NegativeUNL"),

    /**
     * The {@link LedgerEntryType} for {@code Offer} ledger objects.
     */
    OFFER("Offer"),

    /**
     * The {@link LedgerEntryType} for {@code PayChannel} ledger objects.
     */
    PAY_CHANNEL("PayChannel"),

    /**
     * The {@link LedgerEntryType} for {@code RippleState} ledger objects.
     */
    RIPPLE_STATE("RippleState"),

    /**
     * The {@link LedgerEntryType} for {@code SignerList} ledger objects.
     */
    SIGNER_LIST("SignerList"),

    /**
     * The {@link LedgerEntryType} for {@code TicketObject} ledger objects.
     */
    TICKET("Ticket");

    private final String value;

    LedgerEntryType(String value) {
      this.value = value;
    }

    /**
     * Constructs the {@link LedgerEntryType} corresponding to the given value, or throws an
     * {@link IllegalArgumentException} if no corresponding {@link LedgerEntryType} exists.
     *
     * <p>Mostly used by Jackson for deserialization.
     *
     * @param value The {@link String} value of a {@link LedgerEntryType}.
     *
     * @return A {@link LedgerEntryType}.
     */
    @JsonCreator
    public static LedgerEntryType forValue(String value) {
      for (LedgerEntryType type : LedgerEntryType.values()) {
        if (type.value.equals(value)) {
          return type;
        }
      }

      throw new IllegalArgumentException("No matching LedgerEntryType enum value for String value " + value);
    }

    /**
     * Get the underlying value of this {@link LedgerEntryType}.
     *
     * @return The {@link String} value associated with this {@link LedgerEntryType}.
     */
    @JsonValue
    public String value() {
      return value;
    }
  }
}
