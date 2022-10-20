package org.xrpl.xrpl4j.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;
import org.xrpl.xrpl4j.model.client.XrplResult;

/**
 * Generic JSON RPC response object.
 *
 * @param <ResultType> The type of {@link JsonRpcResponse#result()}, which varies based on API method.
 */
@Immutable
@JsonSerialize(as = ImmutableJsonRpcResponse.class)
@JsonDeserialize(as = ImmutableJsonRpcResponse.class)
public interface JsonRpcResponse<ResultType extends XrplResult> {

  /**
   * The result of a request to the rippled JSON RPC API. Contents vary depending on the API method.
   *
   * @return The result of a request, represented by a {@link ResultType}.
   */
  ResultType result();

}
