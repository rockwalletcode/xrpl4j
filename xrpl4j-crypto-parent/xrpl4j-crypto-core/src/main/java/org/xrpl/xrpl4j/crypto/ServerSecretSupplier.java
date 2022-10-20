package org.xrpl.xrpl4j.crypto;

import java.util.function.Supplier;

/**
 * Supplies the Server secret in an implementation agnostic manner. For example, this value could be stored in an
 * encrypted JKS file, or it could be supplied via an environment variable (for lower-security deployments).
 */
public interface ServerSecretSupplier extends Supplier<byte[]> {

}
