// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

/**
 * Simple abstract class to save some boilerplate code with common implementations.
 *
 * @param <T> The type to decode.
 */
public abstract class AbstractDecoder<T> implements Decoder.Text<T> {
    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
