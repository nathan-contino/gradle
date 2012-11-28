/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.internal.artifacts.ivyservice.modulecache;

import org.gradle.internal.TimeProvider;

import java.io.Serializable;
import java.math.BigInteger;

class ModuleDescriptorCacheEntry implements Serializable {
    public boolean isChanging;
    public boolean isMissing;
    public long createTimestamp;
    public BigInteger moduleDescriptorHash;

    ModuleDescriptorCacheEntry(boolean isChanging, boolean isMissing, TimeProvider timeProvider, BigInteger moduleDescriptorHash) {
        this.isChanging = isChanging;
        this.isMissing = isMissing;
        this.createTimestamp = timeProvider.getCurrentTime();
        this.moduleDescriptorHash = moduleDescriptorHash;
    }
}
