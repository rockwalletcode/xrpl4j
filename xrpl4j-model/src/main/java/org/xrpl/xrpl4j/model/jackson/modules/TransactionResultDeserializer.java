package org.xrpl.xrpl4j.model.jackson.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.transactions.Hash256;
import org.xrpl.xrpl4j.model.transactions.Transaction;
import org.xrpl.xrpl4j.model.transactions.TransactionMetadata;

import java.io.IOException;
import java.util.Optional;

/**
 * Custom deserializer for {@link TransactionResult}, which wraps the {@link Transaction} fields in the result JSON.
 * This is similar to what {@link com.fasterxml.jackson.annotation.JsonUnwrapped} provides, but is necessary because
 * deserializing polymorphic types to a {@link com.fasterxml.jackson.annotation.JsonUnwrapped} annotated field
 * does not function properly.
 *
 * @param <T> The type of {@link Transaction} in the {@link TransactionResult}.
 */
public class TransactionResultDeserializer<T extends Transaction> extends StdDeserializer<TransactionResult<T>> {

  /**
   * No-args constructor.
   */
  protected TransactionResultDeserializer() {
    super(TransactionResult.class);
  }

  @Override
  public TransactionResult<T> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
    ObjectNode objectNode = objectMapper.readTree(jsonParser);

    JavaType javaType = objectMapper.getTypeFactory().constructType(new TypeReference<T>() {
    });
    T transaction = objectMapper.convertValue(objectNode, javaType);

    LedgerIndex ledgerIndex = objectNode.has("ledger_index") ?
        LedgerIndex.of(UnsignedInteger.valueOf(objectNode.get("ledger_index").asInt())) :
        null;
    Hash256 hash = Hash256.of(objectNode.get("hash").asText());
    String status = objectNode.has("status") ? objectNode.get("status").asText() : null;
    boolean validated = objectNode.has("validated") && objectNode.get("validated").asBoolean();
    Optional<TransactionMetadata> metadata = getTransactionMetadata(objectMapper, objectNode);
    UnsignedLong closeDate = objectNode.has("date") ? UnsignedLong.valueOf(objectNode.get("date").asLong()) : null;

    return TransactionResult.<T>builder()
      .transaction(transaction)
      .ledgerIndex(Optional.ofNullable(ledgerIndex))
      .hash(hash)
      .status(Optional.ofNullable(status))
      .validated(validated)
      .metadata(metadata)
      .closeDate(Optional.ofNullable(closeDate))
      .build();
  }

  private Optional<TransactionMetadata> getTransactionMetadata(ObjectMapper objectMapper, ObjectNode objectNode) {
    if (objectNode.has("meta")) {
      return Optional.of(objectMapper.convertValue(objectNode.get("meta"), TransactionMetadata.class));
    } else if (objectNode.has("metaData")) {
      return Optional.of(objectMapper.convertValue(objectNode.get("metaData"), TransactionMetadata.class));
    }
    return Optional.empty();
  }

}
