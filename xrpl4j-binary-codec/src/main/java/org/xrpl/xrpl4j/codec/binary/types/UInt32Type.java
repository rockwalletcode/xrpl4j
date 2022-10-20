package org.xrpl.xrpl4j.codec.binary.types;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.google.common.primitives.UnsignedLong;
import org.xrpl.xrpl4j.codec.binary.serdes.BinaryParser;

/**
 * Codec for XRPL UInt32 type.
 */
public class UInt32Type extends UIntType<UInt32Type> {

  public UInt32Type() {
    this(UnsignedLong.ZERO);
  }

  public UInt32Type(UnsignedLong value) {
    super(value, 32);
  }

  @Override
  public UInt32Type fromParser(BinaryParser parser) {
    return new UInt32Type(parser.readUInt32());
  }

  @Override
  public UInt32Type fromJson(JsonNode value) {
    return new UInt32Type(UnsignedLong.valueOf(value.asText()));
  }

  @Override
  public JsonNode toJson() {
    return new LongNode(UnsignedLong.valueOf(toHex(), 16).longValue());
  }
}
