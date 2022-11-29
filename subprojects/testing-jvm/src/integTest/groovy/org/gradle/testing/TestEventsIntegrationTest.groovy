/*
 * Copyright 2021 the original author or authors.
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

package org.gradle.testing


import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestOutputListener
import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.UnsupportedWithConfigurationCache

class TestEventsIntegrationTest extends AbstractIntegrationSpec {
    @UnsupportedWithConfigurationCache(because = "tests listener behaviour")
    def "fails when #type is registered via gradle.addListener() and feature preview is enabled"() {
        settingsFile """
            enableFeaturePreview 'STABLE_CONFIGURATION_CACHE'
        """
        buildFile """
            def testListener = new TestListener() {
                void beforeSuite(TestDescriptor suite) {}
                void afterSuite(TestDescriptor suite, TestResult result) {}
                void beforeTest(TestDescriptor testDescriptor) {}
                void afterTest(TestDescriptor testDescriptor, TestResult result) {}
            }
            def testOutputListener = new TestOutputListener() {
                void onOutput(TestDescriptor testDescriptor, TestOutputEvent outputEvent) {}
            }
            gradle.addListener($listener)
            task broken
        """

        when:
        fails("broken")

        then:
        failureHasCause("Listener registration using Gradle.addListener() is unsupported with the STABLE_CONFIGURATION_CACHE feature preview.")

        where:
        type               | listener
        TestListener       | "testListener"
        TestOutputListener | "testOutputListener"
    }
}
