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

import request from '@/api/request.ts'
import type { PageVO, ListParams } from '@/api/types'
import type { ClusterVO, ServiceUserVO, UpdateClusterParam } from './types'

export const getCluster = (id: number): Promise<ClusterVO> => {
  return request({
    method: 'get',
    url: `/clusters/${id}`
  })
}

export const updateCluster = (id: number, data: UpdateClusterParam) => {
  return request({
    method: 'put',
    url: `/clusters/${id}`,
    data
  })
}

export const getClusterList = (): Promise<ClusterVO[]> => {
  return request({
    method: 'get',
    url: '/clusters'
  })
}

export const getUserListOfService = (id: number, params: ListParams): Promise<PageVO<ServiceUserVO[]>> => {
  return request({
    method: 'get',
    url: `/clusters/${id}/services/users`,
    params
  })
}
