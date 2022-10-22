<template>
  <div>
    <n-grid :x-gap="10">
      <n-grid-item :span="5">
        <n-card
          class="h-full"
          :content-style="{ padding: '5px' }"
          :header-style="{ padding: '5px' }"
          :segmented="true"
        >
          <template #header>
            <div class="flex items-center">
              <n-input class="mr-2" v-model:value="pattern" placeholder="搜索" size="small" />
              <n-switch size="small" v-model:value="expandAllFlag" />
            </div>
          </template>
          <n-tree
            :expanded-keys="getExpandedKeys"
            block-line
            :pattern="pattern"
            :data="departmentData"
            key-field="id"
            selectable
            :on-update:expanded-keys="onUpdateExpandedKeys"
            :on-update:selected-keys="onCheckedKeys"
          />
        </n-card>
      </n-grid-item>
      <n-grid-item :span="19">
        <div>
          <TableBody>
            <template #header>
              <TableHeader ref="tableHeaderRef" :show-filter="false">
                <template #top-right>
                  <DeleteButton @delete="onDeleteItems" />
                </template>
              </TableHeader>
            </template>
            <template #default>
              <n-data-table
                :loading="tableLoading"
                :data="dataList"
                :row-key="rowKey"
                :columns="tableColumns"
                :scroll-x="1000"
                :style="{ height: `${tableHeight}px` }"
                :flex-height="true"
                @update:checked-row-keys="onRowCheck"
              />
            </template>
            <template #footer>
              <TableFooter ref="tableFooterRef" :pagination="pagination" />
            </template>
          </TableBody>
        </div>
      </n-grid-item>
    </n-grid>
  </div>
</template>

<script lang="ts">
import {get, Response} from '@/api/http'
import {systemDeptTree, systemUserPage} from '@/api/url'
import {renderTag} from '@/hooks/form'
import {
  TableActionModel,
  usePagination,
  useRenderAction,
  useRowKey,
  useTable,
  useTableColumn,
  useTableHeight,
} from '@/hooks/table'
import {DataTableColumn, NAvatar, useDialog, useMessage} from 'naive-ui'
import {defineComponent, h, onMounted, ref, shallowReactive, watch} from 'vue'

export default defineComponent({
    name: 'UserList',
    setup() {
      const table = useTable()
      const rowKey = useRowKey('id')
      const naiveDialog = useDialog()
      const message = useMessage()
      const pagination = usePagination(doRefresh)
      const checkedRowKeys = [] as Array<any>
      const departmentData = ref([])
      const tableColumns = useTableColumn(
        [
          table.selectionColumn,
          table.indexColumn,
          {
            title: '名称',
            key: 'nickName',
          },
          {
            title: '性别',
            key: 'gender',
            width: 80,
            render: (rowData) => {
              return h('div', rowData.gender === 0 ? '男' : '女')
            },
          },
          {
            title: '头像',
            key: 'avatar',
            render: (rowData: any) => {
              return h(
                NAvatar,
                {
                  circle: true,
                  size: 'small',
                },
                { default: () => rowData.nickName.substring(0, 1) }
              )
            },
          },
          {
            title: '地址',
            key: 'address',
          },
          {
            title: '上次登录时间',
            key: 'lastLoginTime',
          },
          {
            title: '上次登录IP',
            key: 'lastLoginIp',
          },
          {
            title: '状态',
            key: 'status',
            render: (rowData) =>
              renderTag(!!rowData.status ? '正常' : '禁用', {
                type: !!rowData.status ? 'success' : 'error',
                size: 'small',
              }),
          },
          {
            title: '操作',
            key: 'actions',
            fixed: 'right',
            width: 80,
            render: (rowData) => {
              return useRenderAction([
                {
                  label: '删除',
                  type: 'error',
                  onClick: onDeleteItem.bind(null, rowData),
                },
              ] as TableActionModel[])
            },
          },
        ],
        {
          align: 'center',
        } as DataTableColumn
      )
      const expandAllFlag = ref(true)
      function doRefresh(data: {}) {
        searchUserList(data)
        searchDeptTree(data)
      }
      function searchUserList(data: {}) {
        get({
          url: systemUserPage,
          data: data,
        })
          .then(({ data }: Response) => {
            pagination.setTotalSize(data.totalSize)
            return data.records
          })
          .then(table.handleSuccess)
          .catch(console.log)
      }
      function searchDeptTree() {
        get({
          url: systemDeptTree,
        })
          .then(({ data }: Response) => {
            departmentData.value = data
          })
          .catch(console.log)
      }
      function onDeleteItems() {
        naiveDialog.warning({
          title: '提示',
          content: '确定要删除此数据吗？',
          positiveText: '确定',
          onPositiveClick: () => {
            message.success('数据模拟删除成功，参数为：' + JSON.stringify(checkedRowKeys))
          },
        })
      }
      function onDeleteItem(item: any) {
        naiveDialog.warning({
          title: '提示',
          content: '确定要删除此数据吗？',
          positiveText: '确定',
          onPositiveClick: () => {
            table.dataList.value!.splice(table.dataList.value!.indexOf(item), 1)
          },
        })
      }
      function onRowCheck(rowKeys: Array<any>) {
        checkedRowKeys.length = 0
        checkedRowKeys.push(...rowKeys)
      }
      function onUpdateExpandedKeys(keys: any) {
        getExpandedKeys.length = 0
        getExpandedKeys.push(...keys)
      }
      function onCheckedKeys(keys: any) {
        message.success('选中的值为--->' + JSON.stringify(keys))
        searchUserList({ deptId: keys[0] })
      }
      const getExpandedKeys = shallowReactive([] as Array<number>)
      watch(
        () => expandAllFlag.value,
        (newVal) => {
          newVal
            ? getExpandedKeys.push(...departmentData.value.map((it) => it.id))
            : (getExpandedKeys.length = 0)
        }
      )
      onMounted(async () => {
        table.tableHeight.value = await useTableHeight()
        doRefresh({})
      })
      return {
        ...table,
        rowKey,
        pattern: ref(''),
        expandAllFlag,
        departmentData,
        tableColumns,
        pagination,
        onDeleteItem,
        onDeleteItems,
        onRowCheck,
        getExpandedKeys,
        onUpdateExpandedKeys,
        onCheckedKeys,
      }
    },
  })
</script>

<style lang="scss" scoped>
  .avatar-container {
    position: relative;
    width: 30px;
    margin: 0 auto;
    vertical-align: middle;
    .avatar {
      width: 100%;
      border-radius: 50%;
    }
    .avatar-vip {
      border: 2px solid #cece1e;
    }
    .vip {
      position: absolute;
      top: 0;
      right: -9px;
      width: 15px;
      transform: rotate(60deg);
    }
  }
  .gender-container {
    .gender-icon {
      width: 20px;
    }
  }
</style>
