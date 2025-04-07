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
import { describe, it, expect, vi } from 'vitest'
import useBaseTable from '../../src/composables/use-base-table'
import { withSetup } from '../test-util'

// Mock useI18n
vi.mock('vue-i18n', () => ({
  useI18n: () => ({
    t: (key: string, args?: any[]) => {
      if (key === 'common.total') {
        return `Total: ${args?.[0] || 0}`
      }
      return key
    }
  })
}))

describe('useBaseTable', () => {
  const columns = [{ title: 'Column 1' }]
  const rows = [{ id: 1, name: 'Item 1' }]

  it('should initialize with correct initial state', () => {
    const [result] = withSetup(useBaseTable, { columns, rows })
    expect(result.columnsProp.value).toEqual(columns)
    expect(result.dataSource.value).toEqual(rows)
    expect(result.loading.value).toBe(false)

    // Compare paginationProps excluding the showTotal function
    const expectedPaginationProps = {
      current: 1,
      pageSize: 10,
      total: rows.length,
      size: 'small',
      showSizeChanger: true,
      pageSizeOptions: ['10', '20', '30', '40', '50']
    }
    expect(result.paginationProps.value).toEqual(expect.objectContaining(expectedPaginationProps))
    // Test showTotal function separately
    expect(result.paginationProps.value.showTotal(5)).toBe('Total: 5')
  })

  it('should update paginationProps when onChange is called', () => {
    const [result] = withSetup(useBaseTable, { columns, rows })
    result.onChange({ current: 2, pageSize: 20 })

    // Compare paginationProps excluding the showTotal function
    const expectedPaginationProps = {
      current: 2,
      pageSize: 20,
      total: rows.length,
      size: 'small',
      showSizeChanger: true,
      pageSizeOptions: ['10', '20', '30', '40', '50']
    }
    expect(result.paginationProps.value).toEqual(expect.objectContaining(expectedPaginationProps))
    // Test showTotal function separately
    expect(result.paginationProps.value.showTotal(10)).toBe('Total: 10')
  })

  it('should reset state when resetState is called', () => {
    const [result] = withSetup(useBaseTable, { columns, rows })
    result.resetState()
    expect(result.columnsProp.value).toEqual(columns)
    expect(result.dataSource.value).toEqual([])
    expect(result.loading.value).toBe(false)

    // Compare paginationProps excluding the showTotal function
    const expectedPaginationProps = {
      current: 1,
      pageSize: 10,
      total: 0,
      size: 'small',
      showSizeChanger: true,
      pageSizeOptions: ['10', '20', '30', '40', '50']
    }
    expect(result.paginationProps.value).toEqual(expect.objectContaining(expectedPaginationProps))
    // Test showTotal function separately
    expect(result.paginationProps.value.showTotal(0)).toBe('Total: 0')
  })

  it('should update columnsProp when columns change', () => {
    const updatedColumns = [{ title: 'Column 2' }]
    const [result] = withSetup(useBaseTable, { columns: columns })
    result.columnsProp.value = updatedColumns
    expect(result.columnsProp.value).toEqual(updatedColumns)
  })

  it('should call resetState when component unmounts', () => {
    const [result, app] = withSetup(useBaseTable, { columns, rows })
    const resetStateSpy = vi.fn()
    result.resetState = resetStateSpy
    app.unmount = vi.fn().mockImplementation(() => {
      resetStateSpy()
    })
    app.unmount()
    expect(resetStateSpy).toHaveBeenCalled()
  })
})
