/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.webbeans.test.component.dependent.circular;

import javax.context.RequestScoped;
import javax.inject.Current;

@RequestScoped
public class DependentA
{
    private @Current
    DependentB dependentB;

    public DependentA()
    {

    }

    /**
     * @return the dependentB
     */
    public DependentB getDependentB()
    {
        return dependentB;
    }

    /**
     * @param dependentB the dependentB to set
     */
    public void setDependentB(DependentB dependentB)
    {
        this.dependentB = dependentB;
    }

}
