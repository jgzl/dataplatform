<template>
  <div class="main-container">
    <TableBody>
      <template #tableConfig>
        <TableConfig
          v-model:border="tableConfig.border"
          v-model:stripe="tableConfig.stripe"
          @refresh="doRefresh"
        >
          <template #actions>
            <el-button
              type="primary"
              size="small"
              icon="PlusIcon"
              @click="onAddItem"
              >添加
            </el-button>
          </template>
        </TableConfig>
      </template>
      <template #default>
        <el-table
          v-loading="tableLoading"
          :data="dataList"
          :header-cell-style="tableConfig.headerCellStyle"
          :size="tableConfig.size"
          :stripe="tableConfig.stripe"
          :border="tableConfig.border"
          row-key="id"
          :tree-props="{ children: 'children' }"
        >
          <el-table-column align="center" label="序号" fixed="left" width="80">
            <template v-slot="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column
            v-for="item of tableColumns"
            :key="item.prop"
            :label="item.label"
            :prop="item.prop"
            align="center"
          >
            <template v-if="item.prop === 'actions'" #default="scope">
              <el-button
                plain
                type="primary"
                size="small"
                @click="onUpdateItem(scope.row)"
                >编辑</el-button
              >
              <el-button
                plain
                type="danger"
                size="small"
                @click="onDeleteItem(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </template>
    </TableBody>
    <Dialog ref="dialog" :title="dialogTitle">
      <template #content>
        <BaseForm
          class="padding-left padding-right"
          ref="baseForm"
          :form-items="formItems"
        >
          <template #prefix>
            <el-form-item :label="parentFormItem.label">
              <TreeSelector
                v-model:value="parentFormItem.value"
                placeholder="请选择上级部门"
                :data="dataList"
                :dataFields="{
                  label: 'name',
                  value: 'id',
                }"
              />
            </el-form-item>
          </template>
        </BaseForm>
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import {useDataTable, useDelete, useGet, usePost, usePut} from "@/hooks";
import {systemDept, systemDeptTree} from "@/api/url";
import type {DialogType} from "@/components/types";
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import _ from "lodash";

interface Department {
  parentId: number;
  id: number;
  name: string;
  depCode: string;
  sort: number;
  status: number;
  children: Array<Department>;
}

const tableColumns = reactive([
    {
      label: "部门名称",
      prop: "name",
    },
  {
    label: "排序",
    prop: "sort",
  },
    {
      label: "状态",
      prop: "status",
    },
    {
      label: "操作",
      prop: "actions",
    },
  ]);

  const get = useGet();
  const post = usePost();
  const put = usePut();
  const httpDelete = useDelete();

  const dialog = ref<DialogType>();
  const baseForm = ref();
  const dialogTitle = ref("添加部门");
  const { tableConfig, tableLoading, dataList, handleSuccess } = useDataTable();
  const parentFormItem = reactive({
    label: "上级部门",
    name: "parentId",
    value: "",
    placeholder: "请选择上级部门",
    selectOptions: {},
    reset() {
      this.value = "";
    },
  });
  const formItems = reactive([
    {
      label: "部门名称",
      type: "input",
      name: "name",
      value: "",
      maxLength: 50,
      inputType: "text",
      placeholder: "请输入部门名称",
      validator: (item: any) => {
        if (!item.value) {
          ElMessage.error(item.placeholder);
          return false;
        }
        return true;
      },
      reset() {
        this.value = "";
      },
    },
  ]);
  const onUpdateItem = (item: any) => {
    dialogTitle.value = "编辑部门";
    parentFormItem.value = item.parentId;

    dialog.value?.show(() => {
      if (!baseForm.value?.checkParams()) {
        return;
      }
      (dialog.value as any).loading = true;
      post({url: systemDept, data: baseForm.value?.generatorParams()})
          .then((res) => {
            console.log(JSON.stringify(res))
            doRefresh();
          })
          .catch(console.log)
      dialog.value?.close();
    });
  };
  const doRefresh = () => {
    get({
      url: systemDeptTree,
    }).then(handleSuccess);
  };
  function filterItems(srcArray: Array<Department>, filterItem: Department) {
    for (let index = 0; index < srcArray.length; index++) {
      const element = srcArray[index];
      if (element.id === filterItem.id) {
        if (!_.isEmpty(element.children)) {
          ElMessage.error("当前部门下有子部门，不能删除");
          return;
        }
        srcArray.splice(index, 1);
        return;
      } else {
        if (!_.isEmpty(element.children)) {
          filterItems(element.children, filterItem);
        }
      }
    }
  }
  const onDeleteItem = (item: any) => {
    ElMessageBox.confirm("确定要删除此信息，删除后不可恢复？", "提示")
        .then(() => {
            httpDelete({url:`${systemDept}/${item.id}`})
                .then((res)=>{
                  console.log(JSON.stringify(res))
                  doRefresh();
                })
        })
        .catch(console.log);
  };
  const onAddItem = () => {
    dialogTitle.value = "添加部门";
    formItems.forEach((it: any) => it.reset());
    parentFormItem.value = "";
    dialog.value?.show(() => {
      if (!baseForm.value?.checkParams()) {
        return;
      }
      (dialog.value as any).loading = true;
      const formParams = baseForm.value?.generatorParams();
      formParams.parentId = parentFormItem.value;
      post({url: systemDept, data: formParams})
          .then((res) => {
            console.log(JSON.stringify(res))
            doRefresh();
          })
          .catch(console.log)
      dialog.value?.close();
    });
  };
  parentFormItem.selectOptions = computed(() => {
    return dataList.map((it: any) => {
      return {
        label: it.name,
        value: it.id,
      };
    });
  });
  onMounted(doRefresh);
</script>
