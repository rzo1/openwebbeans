/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.webbeans.component.creation;

import org.apache.webbeans.component.ManagedBean;
import org.apache.webbeans.component.WebBeansType;
import org.apache.webbeans.config.WebBeansContext;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.UnproxyableResolutionException;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanAttributes;

class UnproxyableBean<T> extends ManagedBean<T>
{
    private final UnproxyableResolutionException exception;

    UnproxyableBean(final WebBeansContext webBeansContext, final WebBeansType webBeansType,
                           final BeanAttributes<T> beanAttributes, final AnnotatedType<T> at, final Class<T> beanClass,
                           final UnproxyableResolutionException error)
    {
        super(webBeansContext, webBeansType, at, beanAttributes, beanClass);
        this.exception = error;
    }

    @Override
    public boolean valid()
    {
        throw exception;
    }

    @Override
    public T create(final CreationalContext<T> creationalContext)
    {
        throw exception;
    }
}