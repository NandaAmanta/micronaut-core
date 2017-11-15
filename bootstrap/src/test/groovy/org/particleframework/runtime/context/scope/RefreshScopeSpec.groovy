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
package org.particleframework.runtime.context.scope

import org.particleframework.context.ApplicationContext
import org.particleframework.context.annotation.ConfigurationProperties
import org.particleframework.context.annotation.Value
import org.particleframework.context.env.Environment
import org.particleframework.inject.qualifiers.Qualifiers
import org.particleframework.runtime.context.scope.refresh.RefreshEvent
import org.particleframework.runtime.executor.IOExecutorService
import spock.lang.Specification

import java.util.concurrent.Executor

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class RefreshScopeSpec extends Specification {

    void "test fire refresh event that refreshes all beans"() {
        given:
        System.setProperty("foo.bar", "test")
        ApplicationContext beanContext = ApplicationContext.build().start()

        // override IO executor with synchronous impl
        beanContext.registerSingleton(Executor.class, new Executor() {
            @Override
            void execute(Runnable command) {
                command.run()
            }
        }, Qualifiers.byName(IOExecutorService.NAME))

        when:
        RefreshBean bean = beanContext.getBean(RefreshBean)

        then:
        bean.testValue() == 'test'
        bean.testConfigProps() == 'test'

        when:
        System.setProperty("foo.bar", "bar")
        Environment environment = beanContext.getEnvironment()
        environment.refresh()
        beanContext.publishEvent(new RefreshEvent())

        then:
        bean.testValue() == 'bar'
        bean.testConfigProps() == 'bar'

        cleanup:
        beanContext.stop()

    }

    @Refreshable
    static class RefreshBean {

        final MyConfig config

        @Value('foo.bar')
        String foo

        RefreshBean(MyConfig config) {
            this.config = config
        }

        String testValue() {
            return foo
        }

        String testConfigProps() {
            return config.bar
        }
    }

    @ConfigurationProperties('foo')
    static class MyConfig {
        String bar
    }
}
