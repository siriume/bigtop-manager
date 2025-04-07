/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.bigtop.manager.server.command.stage;

import org.apache.bigtop.manager.server.command.task.HostCheckTask;
import org.apache.bigtop.manager.server.command.task.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mockConstruction;

@ExtendWith(MockitoExtension.class)
public class HostCheckStageTest {

    @Mock
    private HostCheckStage stage;

    @Test
    public void testCreateTask() {
        StageContext stageContext = new StageContext();
        ReflectionTestUtils.setField(stage, "stageContext", stageContext);

        MockedConstruction<?> mocked = mockConstruction(HostCheckTask.class);

        doCallRealMethod().when(stage).createTask(any());
        Task task = stage.createTask("host1");

        assertEquals(1, mocked.constructed().size());
        assertInstanceOf(HostCheckTask.class, task);

        mocked.close();
    }

    @Test
    public void tesGetName() {
        doCallRealMethod().when(stage).getName();
        assertEquals("Check hosts", stage.getName());
    }
}
