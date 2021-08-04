// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.constraint;

import org.lfenergy.compas.core.commons.constraint.impl.XmlAnyElementConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to check if a List of XML Elements (mostly annotated with XmlAnyElement) contains a specific number
 * of elements and also the expected Element (Name) with the correct namespace.
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {XmlAnyElementConstraintValidator.class})
@Documented
public @interface XmlAnyElementValid {
    String message() default "{org.lfenergy.compas.XmlAnyElementValid.unexpected.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String elementName();

    String elementNamespace();
}
