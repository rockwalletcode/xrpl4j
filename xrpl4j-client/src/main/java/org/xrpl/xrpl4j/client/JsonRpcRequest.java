package org.xrpl.xrpl4j.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;
import org.xrpl.xrpl4j.model.client.XrplRequestParams;

import java.util.List;

/**
 * Generic rippled JSON RPC request object.
 */
@Immutable
@JsonSerialize(as = ImmutableJsonRpcRequest.class)
@JsonDeserialize(as = ImmutableJsonRpcRequest.class)
public interface JsonRpcRequest {

  static ImmutableJsonRpcRequest.Builder builder() {
    return ImmutableJsonRpcRequest.builder();
  }

  /**
   * The name of the <a href="https://xrpl.org/public-rippled-methods.html">API method</a>.
   *
   * @return A {@link String} containing the method name.
   */
  String method();

  /**
   * A one-item {@link List} containing a {@link XrplRequestParams} with the parameters to this method.
   * You may omit this field if the method does not require any parameters.
   *
   * @return A {@link List} of {@link XrplRequestParams}.
   */
  List<XrplRequestParams> params();

}
