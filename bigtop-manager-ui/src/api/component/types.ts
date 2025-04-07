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

import type { PageVO } from '../types'

export type ComponentList = PageVO<ComponentVO>

export interface ComponentParams {
  clusterId?: number
  hostId?: number
  hostname?: string
  hostnames?: string[]
  name?: string
  orderBy?: string
  pageNum?: number
  pageSize?: number
  serviceId?: number
  serviceNames?: string[]
  sort?: string
  [property: string]: any
}

/**
 * ComponentVO
 */
export interface ComponentVO {
  cardinality?: string
  category?: string
  displayName?: string
  hostname?: string
  id?: number
  name?: string
  quickLink?: QuickLinkVO
  serviceDisplayName?: string
  serviceId?: number
  serviceName?: string
  stack?: string
  status?: number
  [property: string]: any
}

/**
 * QuickLinkVO
 */
export interface QuickLinkVO {
  displayName?: string
  url?: string
  [property: string]: any
}
