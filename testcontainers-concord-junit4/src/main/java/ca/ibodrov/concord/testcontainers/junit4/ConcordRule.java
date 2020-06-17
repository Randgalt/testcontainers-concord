package ca.ibodrov.concord.testcontainers.junit4;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2020 Ivan Bodrov
 * -----
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
 * =====
 */

import ca.ibodrov.concord.testcontainers.Concord;
import ca.ibodrov.concord.testcontainers.ConcordEnvironment;
import ca.ibodrov.concord.testcontainers.ProcessLogStreamers;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ConcordRule extends Concord<ConcordRule> implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try (ConcordEnvironment env = initEnvironment()) {
                    env.start();
                    try {
                        base.evaluate();
                    } finally {
                        ProcessLogStreamers.stop();
                    }
                }
            }
        };
    }
}