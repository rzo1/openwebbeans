/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.webbeans.test.instance;

import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.enterprise.util.Nonbinding;
import jakarta.inject.Qualifier;
import org.apache.webbeans.test.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Set;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class CdiCurrentBatchEETest extends AbstractUnitTest
{

    @Test
    public void testCdiCurrentSelect()
    {
        startContainer(ProducerBean.class);

        {
            String s = CDI.current().select(String.class, new Qualifier1Literal("prop1")).get();
            Assert.assertNotNull(s);
            Assert.assertEquals("prop1", s);
        }

    }


    public static class ProducerBean
    {
        @Produces
        @Qualifier1
        public String produceProperty(final InjectionPoint injectionPoint) {
            if (injectionPoint != null) {
                Qualifier1 batchPropAnnotation = null;
                String batchPropName = null;
                Annotated annotated = injectionPoint.getAnnotated();
                if (annotated != null) {
                    batchPropAnnotation = annotated.getAnnotation(Qualifier1.class);

                    // If a name is not supplied the batch property name defaults to
                    // the field name
                    if (batchPropAnnotation.name().isEmpty()) {
                        batchPropName = injectionPoint.getMember().getName();
                    } else {
                        batchPropName = batchPropAnnotation.name();
                    }
                } else {
                    // No attempt to match by field name in this path.
                    Set<Annotation> qualifiers =  injectionPoint.getQualifiers();
                    for (Annotation a : qualifiers.toArray(new Annotation[0])) {
                        if (a instanceof Qualifier1) {
                            batchPropName = ((Qualifier1) a).name();
                            break;
                        }
                    }
                }

                return batchPropName;
            }

            return null;
        }
    }

    private class Qualifier1Literal extends AnnotationLiteral<Qualifier1> implements Qualifier1 {

        private String name;

        Qualifier1Literal(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }


    @Target(METHOD)
    @Retention(RUNTIME)
    @Qualifier
    public @interface Qualifier1
    {
        @Nonbinding
        String name() default "";
    }

}
