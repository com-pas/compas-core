// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * The default SCD model
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class ScdBom{

    /**
     * the raw XML content
     */
    @Column(name="RAW_XML")
    @Lob
    private byte[] rawXml;
}
