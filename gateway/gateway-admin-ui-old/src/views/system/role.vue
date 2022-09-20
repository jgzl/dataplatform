<template>
  <div class="main-container">
    <TableHeader
            :can-collapsed="
        likeSearchModel.conditionItems &&
        likeSearchModel.conditionItems.length !== 0
      "
            :search-model="likeSearchModel.conditionItems"
            :default-collapsed-state="true"
            title="数据筛选"
            @doSearch="doSearch"
            @resetSearch="resetSearch"
    />
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
              size="mini"
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
                :height="tableConfig.height"
                @selection-change="handleSelectionChange"
        >
          <el-table-column align="center" label="序号" fixed="left" width="80">
            <template #default="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="角色名称" prop="roleName" />
          <el-table-column align="center" label="角色编号" prop="roleCode" />
          <el-table-column align="center" label="角色描述" prop="roleDesc" />
          <el-table-column align="center" label="创建时间" prop="createTime" />
          <el-table-column align="center" label="操作" width="300px">
            <template #default="scope">
              <el-button
                      v-permission = "['sys_role_edit']"
                      plain
                      type="primary"
                      size="mini"
                      @click="onUpdateItem(scope.row)"
              >编辑</el-button
              >
              <el-button
                      v-role = "['ROLE_admin','ROLE_editor']"
                      plain
                      type="danger"
                      size="mini"
                      @click="onDeleteItem(scope.row)"
              >删除</el-button
              >
              <el-button
                      plain
                      type="warning"
                      size="mini"
                      @click="showMenu(scope.row)"
              >菜单权限</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </template>
      <template #footer>
        <TableFooter
            ref="tableFooter"
            @refresh="doRefresh"
            @pageChanged="doRefresh"
        />
      </template>
    </TableBody>
    <Dialog ref="dialogRef">
      <template #content>
        <BaseForm ref="baseFormRef" :form-items="formItems" />
      </template>
    </Dialog>
    <Dialog ref="menuDialogRef" title="菜单权限">
      <template #content>
        <el-tree
                ref="tree"
                :data="allMenuList"
                show-checkbox
                :check-strictly="true"
                node-key="id"
                :default-expanded-keys="defaultExpandedKeys"
                :props="defaultProps"
        />
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import {useDelete, useGet, useLikeSearch, usePageDataTable, usePost, usePut} from "@/hooks";
import type {DialogType, TableFooter} from "@/components/types";
import {nextTick, onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {systemMenuTree, systemMenuTreeByRole, systemRole, systemRoleMenu, systemRolePage} from "@/api/url";
import useUserStore from "@/store/modules/user";

const { likeSearchModel, getSearchParams, resetSearch } = useLikeSearch();
  const userStore = useUserStore();
  const ROLE_CODE_FLAG = "ROLE_";
  const MENU_LEFT = "0";
  const MENU_BUTTON = "1";
  const roleModel = reactive({
    id: 0,
    roleName: "",
    roleCode: "",
    description: "",
    createTime: "",
  });
  const defaultProps = {
    children: "children",
    label: "menuName",
  };
  const defaultCheckedKeys = ref<string[]>([]);
  const defaultExpandedKeys = ref<string[]>([]);
  const formItems = reactive([
    {
      label: "角色名称",
      type: "input",
      name: "roleName",
      value: "",
      maxLength: 50,
      inputType: "text",
      placeholder: "请输入角色名称",
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
    {
      label: "角色编号",
      type: "input",
      name: "roleCode",
      value: "",
      maxLength: 20,
      inputType: "text",
      placeholder: "请输入角色编号:ROLE_",
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
    {
      label: "角色描述",
      type: "input",
      name: "roleDesc",
      value: "",
      maxLength: 50,
      inputType: "text",
      placeholder: "请输入角色描述",
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
  const menuDialogRef = ref<DialogType>();
  const dialogRef = ref<DialogType>();
  const tableFooter = ref<TableFooter>();
  const baseFormRef = ref();
  const tree = ref()
  const get = useGet();
  const post = usePost();
  const put = usePut();
  const httpDelete = useDelete();
  const {
    dataList,
    tableLoading,
    tableConfig,
    handleSuccess,
    handleSelectionChange,
    selectRows,
    useHeight,
  } = usePageDataTable();
  const allMenuList = ref([]);

  likeSearchModel.extraParams = () => ({
    ...tableFooter.value?.withPageInfoData(),
  });
  likeSearchModel.conditionItems = reactive([
    {
      name: "roleName",
      label: "角色名称",
      value: "",
      type: "input",
      placeholder: "请输入角色名称",
      span: 8,
    },
    {
      name: "roleCode",
      label: "角色编码",
      value: "",
      type: "input",
      placeholder: "请输入角色编码",
      span: 8,
    },
    {
      name: "roleDesc",
      label: "角色描述",
      value: "",
      type: "input",
      placeholder: "请输入角色描述",
      span: 8,
    }
  ]);
  const doSearch = () => {
    const params = getSearchParams();
    get({
      url: systemRolePage,
      data: params,
    })
    .then(handleSuccess)
    .then((res: any) => {
      tableFooter.value?.setTotalSize(res.total);
    })
    .catch(console.log);
  };

  function doRefresh() {
    get({
      url: systemRolePage,
      data: {
        ...tableFooter.value?.withPageInfoData(),
        ...getSearchParams(),
      },
    })
    .then(handleSuccess)
    .then((res: any) => {
      tableFooter.value?.setTotalSize(res.total);
    })
    .catch(console.log);
  }

  function getAllMenuList() {
    get({
      url: systemMenuTree,
      data: {
        lazy: false
      }
    }).then((res) => {
      allMenuList.value = res.data;
    });
  }

  function showMenu(item: RoleModel) {
    defaultCheckedKeys.value = [];
    defaultExpandedKeys.value = [];
    get({
      url: systemMenuTreeByRole,
      data: {
        type: MENU_LEFT+","+MENU_BUTTON,
        id: item.id
      }
    }).then((res) => {
      if (res.data===null||res.data===undefined) {
        handleRoleMenusSelected([]);
      }else {
        handleRoleMenusSelected(res.data);
      }
      menuDialogRef.value?.show(() => {
        const menuIds = tree.value.getCheckedKeys().join(",")
        put({
          url:systemRoleMenu ,
          data: {
            id: item.id,
            menuIds: menuIds
          }
        })
        .then((res)=>{
          console.log(JSON.stringify(res))
          doRefresh();
        })
        .catch(console.log)
        menuDialogRef.value?.close();
      });
      nextTick(() => {
        tree.value.setCheckedKeys(defaultCheckedKeys.value);
      });
    })
    .catch(console.log);
  }
  function onAddItem() {
    formItems.forEach((it: FormItem) => it.reset && it.reset());
    dialogRef.value?.show(() => {
      post({url:systemRole ,data: baseFormRef.value?.generatorParams()})
              .then((res)=>{
                console.log(JSON.stringify(res))
                doRefresh();
              })
              .catch(console.log)
      dialogRef.value?.close();
    });
  }
  function onUpdateItem(item: RoleModel) {
    formItems.forEach((it: FormItem) => {
      const typeName = it.name;
      if (typeName) {
        const typeValue = item[typeName];
        if (typeValue) {
          it.value = item[typeName];
        }
      }
    });
    const currentRoles = userStore.roles
    console.log(currentRoles)
    dialogRef.value?.show(() => {
      const updateData: any = {};
      updateData['id'] = item.id;
      formItems.forEach((it: FormItem) => {
        updateData[it.name] = it.value
      });
      put({url:systemRole ,data: updateData})
              .then((res)=>{
                console.log(JSON.stringify(res))
                doRefresh();
              })
              .catch(console.log)
      dialogRef.value?.close();
    });
  }
  function onDeleteItem(item: RoleModel) {
    ElMessageBox.confirm("是否要删除此信息，删除后不可恢复？", "提示").then(
            () => {
              httpDelete({url:`${systemRole}/${item.id}`})
                      .then((res)=>{
                        console.log(JSON.stringify(res))
                        doRefresh();
                      })
                      .catch(console.log)
            }
    );
  }
  function handleRoleMenusSelected(menus: Array<any>) {
    menus.forEach((it: any) => {
        defaultCheckedKeys.value.push(it.id);
        if (it.children) {
          defaultExpandedKeys.value.push(it.id);
          handleRoleMenusSelected(it.children);
        }
      })
  }
  onMounted(async () => {
    await getAllMenuList();
    doRefresh();
    useHeight();
  });
</script>
