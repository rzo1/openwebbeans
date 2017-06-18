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
package org.apache.webbeans.portable.events;

import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.configurator.ObserverMethodConfigurator;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.configurator.ObserverMethodConfiguratorImpl;
import org.apache.webbeans.portable.events.discovery.ExtensionAware;

/**
 * Implementation of  {@link ProcessObserverMethod}.
 * 
 * @version $Rev$ $Date$
 *
 * @param <X> declared bean class
 * @param <T> event type
 */
public class ProcessObserverMethodImpl<T,X> extends EventBase implements ProcessObserverMethod<T, X>, ExtensionAware
{
    private final WebBeansContext webBeansContext;

    /**Observer annotated method*/
    private final AnnotatedMethod<X> annotatedMethod;
    
    /**ObserverMethod instance*/
    private ObserverMethod<T> observerMethod;
    private boolean vetoed;
    private ObserverMethodConfiguratorImpl observerMethodConfigurator;

    private Extension extension;


    public ProcessObserverMethodImpl(WebBeansContext webBeansContext, AnnotatedMethod<X> annotatedMethod,ObserverMethod<T> observerMethod)
    {
        this.webBeansContext = webBeansContext;
        this.annotatedMethod = annotatedMethod;
        this.observerMethod = observerMethod;
    }

    @Override
    public void setExtension(Extension extension)
    {
        this.extension = extension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDefinitionError(Throwable t)
    {
        checkState();
        WebBeansContext.getInstance().getBeanManagerImpl().getErrorStack().pushError(t);
    }

    @Override
    public ObserverMethodConfigurator<T> configureObserverMethod()
    {
        if (observerMethodConfigurator == null)
        {
            this.observerMethodConfigurator = new ObserverMethodConfiguratorImpl(webBeansContext, extension, observerMethod);
        }
        return observerMethodConfigurator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotatedMethod<X> getAnnotatedMethod()
    {
        checkState();
        return annotatedMethod;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObserverMethod<T> getObserverMethod()
    {
        checkState();
        if (observerMethodConfigurator != null)
        {
            return observerMethodConfigurator.getObserverMethod();
        }
        return observerMethod;
    }

    @Override
    public void setObserverMethod(ObserverMethod<T> observerMethod)
    {
        checkState();
        this.observerMethod = observerMethod;
        this.observerMethodConfigurator = null;
    }

    @Override
    public void veto()
    {
        checkState();
        vetoed = true;
    }

    public boolean isVetoed()
    {
        return vetoed;
    }
}
