// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.testhelpers;

import org.lfenergy.compas.domain.IScdBom;
import org.lfenergy.compas.domain.ScdBom;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "SCD",
        uniqueConstraints = @UniqueConstraint(columnNames = {"FILE_NAME"},name = "UC_SCD_FILENAME")
)
public class SimpleSCD extends ScdBom implements IScdBom<UUID> {
    @Id
    @Column(name = "ID_SCD")
    private UUID id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleSCD)) return false;
        if (!super.equals(o)) return false;
        SimpleSCD simpleSCD = (SimpleSCD) o;
        return Objects.equals(id, simpleSCD.id) &&
                Objects.equals(fileName, simpleSCD.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, fileName);
    }
}
