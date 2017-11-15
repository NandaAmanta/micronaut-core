/*
 * Copyright 2017 original authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.particleframework.runtime.context.scope.refresh;

import org.particleframework.context.event.ApplicationEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * <p>An {@link ApplicationEvent} for handling refreshes</p>
 *
 * @author Graeme Rocher
 * @since 1.0
 */
public class RefreshEvent extends ApplicationEvent {

    static final Collection<String> ALL_KEYS = Collections.singletonList("*");

    /**
     * Constructs a prototypical Event.
     *
     * @param source The configuration keys to be refreshed
     * @throws IllegalArgumentException if source is null.
     */
    public RefreshEvent(Set<String> source) {
        super(source);
    }

    /**
     * Constructs a refresh Event that refreshes all keys
     *
     */
    public RefreshEvent() {
        super(ALL_KEYS);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<String> getSource() {
        return (Collection<String>) super.getSource();
    }
}
