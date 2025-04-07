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
package org.apache.bigtop.manager.stack.bigtop.v3_3_0.spark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SparkParamsTest {

    private SparkParams sparkParams;

    @BeforeEach
    public void setUp() {
        sparkParams = mock(SparkParams.class);
        when(sparkParams.stackHome()).thenReturn("/stack");
        when(sparkParams.getServiceName()).thenCallRealMethod();
        when(sparkParams.serviceHome()).thenCallRealMethod();
        when(sparkParams.hadoopConfDir()).thenCallRealMethod();
        when(sparkParams.hadoopHome()).thenCallRealMethod();
        when(sparkParams.hiveConfDir()).thenCallRealMethod();
        when(sparkParams.hiveHome()).thenCallRealMethod();
        when(sparkParams.confDir()).thenCallRealMethod();
    }

    @Test
    public void testServiceHome() {
        assertEquals("/stack/spark", sparkParams.serviceHome());
    }

    @Test
    public void testHadoopConfDir() {
        assertEquals("/stack/hadoop/etc/hadoop", sparkParams.hadoopConfDir());
    }

    @Test
    public void testHadoopHome() {
        assertEquals("/stack/hadoop", sparkParams.hadoopHome());
    }

    @Test
    public void testHiveConfDir() {
        assertEquals("/stack/hive/conf", sparkParams.hiveConfDir());
    }

    @Test
    public void testHiveHome() {
        assertEquals("/stack/hive", sparkParams.hiveHome());
    }

    @Test
    public void testConfDir() {
        assertEquals("/stack/spark/conf", sparkParams.confDir());
    }

    @Test
    public void testGetServiceName() {
        assertEquals("spark", sparkParams.getServiceName());
    }
}
