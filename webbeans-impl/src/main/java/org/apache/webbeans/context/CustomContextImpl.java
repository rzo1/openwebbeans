/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.webbeans.context;

import java.lang.annotation.Annotation;

import javax.context.Context;
import javax.context.Contextual;
import javax.context.CreationalContext;

class CustomContextImpl implements Context
{
    private Context context;
    
    CustomContextImpl(Context context)
    {
        this.context = context;
    }
    
    public Class<? extends Annotation> getScopeType()
    {
        return this.context.getScopeType();
    }

    public <T> T get(Contextual<T> component, CreationalContext<T> crreationalContext)
    {
        return this.context.get(component, crreationalContext);
    }

    public <T> T get(Contextual<T> component)
    {
        return this.context.get(component);
    }

    public boolean isActive()
    {
        return this.context.isActive();
    }
    
}
