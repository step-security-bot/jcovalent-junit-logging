/*
 * Copyright (C) 2022-2023 JCovalent
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
package com.jcovalent.junit.logging.extensions;

import static com.jcovalent.junit.logging.utils.Utils.qualifiedMemberName;
import static org.assertj.core.api.Assertions.assertThat;

import com.jcovalent.junit.logging.JCovalentLoggingSupport;
import com.jcovalent.junit.logging.ManagedLogger;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.event.Level;

@JCovalentLoggingSupport
class Slf4jParameterResolverTest {
    @Test
    void testBasicParameterBinding(final Logger logger) {
        assertThat(logger)
                .isNotNull()
                .isInstanceOf(Logger.class)
                .extracting(Logger::getName)
                .isEqualTo(qualifiedMemberName(this.getClass(), "testBasicParameterBinding", "arg0"));
    }

    @Test
    void testNamedParameterBinding(
            @ManagedLogger(name = "test") final Logger logger,
            @ManagedLogger(logLevel = Level.INFO) final Logger leveledLogger) {
        assertThat(logger)
                .isNotNull()
                .isInstanceOf(Logger.class)
                .extracting(Logger::getName)
                .isEqualTo("test");

        assertThat(leveledLogger)
                .isNotNull()
                .isInstanceOf(Logger.class)
                .extracting(Logger::getName)
                .isEqualTo(qualifiedMemberName(this.getClass(), "testNamedParameterBinding", "arg1"));

        assertThat(leveledLogger).extracting(Logger::isTraceEnabled).isEqualTo(false);
        assertThat(leveledLogger).extracting(Logger::isInfoEnabled).isEqualTo(true);
    }
}
