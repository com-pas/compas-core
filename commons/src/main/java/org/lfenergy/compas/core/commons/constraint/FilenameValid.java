// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.constraint;

import org.lfenergy.compas.core.commons.constraint.impl.FilenameConstraintValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to mark a field that it should validate if the string can be used as filename on an OS.
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {FilenameConstraintValidator.class})
@Documented
public @interface FilenameValid {
    String message() default "{org.lfenergy.compas.FilenameValid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
