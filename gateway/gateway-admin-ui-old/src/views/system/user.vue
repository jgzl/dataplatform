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
            >
              添加
            </el-button>
            <el-button
                    type="danger"
                    size="mini"
                    icon="DeleteIcon"
                    :disabled="selectRows.length != 1"
                    @click="onDeleteItems"
            >
              删除
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
          <el-table-column type="selection" width="45" fixed="left" />
          <el-table-column
                  align="center"
                  label="用户id"
                  prop="id"
                  width="100"
          />
          <el-table-column
                  align="center"
                  label="用户名"
                  prop="userName"
                  width="100"
          />
          <el-table-column
                  align="center"
                  label="用户别名"
                  prop="nickName"
                  width="100"
          />
          <el-table-column
                  align="center"
                  label="手机号"
                  prop="mobile"
                  width="150"
          />
          <el-table-column
                  align="center"
                  label="邮箱"
                  prop="email"
                  width="150"
          />
          <el-table-column
                  align="center"
                  label="所属部门"
                  prop="deptName"
          />
          <el-table-column align="center" label="所属角色" >
            <template #default="scope">
              <el-tag
                      size="mini" v-for="(data,index) in scope.row.roleList" :key="index" type="success"
              >
                {{ data.roleName }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
                  align="center"
                  label="上次登录时间"
                  prop="lastLoginTime"
                  width="160px"
          />
          <el-table-column
                  align="center"
                  label="上次登录IP"
                  prop="lastLoginIp"
                  width="130px"
          />
          <el-table-column align="center" label="状态">
            <template #default="scope">
              <el-tag
                      size="mini"
                      :type="scope.row.lockFlag === '0' ? 'success' : 'danger'"
              >
                {{ scope.row.lockFlag === "0" ? "正常" : "禁用" }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
                  align="center"
                  label="操作"
                  fixed="right"
                  width="220"
          >
            <template #default="scope">
              <el-button
                      type="primary"
                      size="mini"
                      plain
                      @click="onUpdateItem(scope.row)"
              >编辑</el-button
              >
              <el-button
                      type="danger"
                      size="mini"
                      plain
                      @click="onDeleteItem(scope.row)"
              >删除</el-button
              >
              <el-button
                      :type="scope.row.lockFlag === '0' ? 'warning' : 'success'"
                      size="mini"
                      plain
                      @click="onEnableItem(scope.row)"
              >{{ scope.row.lockFlag === "0" ? "禁用" : "启用" }}</el-button
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
        <el-form
                class="base-form-container"
                :model="userModel"
                :inline="true"
                label-width="80px"
                label-position="right"
        >
          <el-divider border-style="dashed" content-position="left"
          >基本信息</el-divider
          >
          <el-form-item
                  class="form-item__require"
                  label="用户名"
                  prop="userName"
          >
            <el-input
                    v-model="userModel.userName"
                    size="small"
                    placeholder="请输入用户名"
                    clearable
            />
          </el-form-item>
          <el-form-item
                  class="form-item__require"
                  label="用户别名"
                  prop="nickName"
          >
            <el-input
                    v-model="userModel.nickName"
                    size="small"
                    placeholder="请输入用户别名"
                    clearable
            />
          </el-form-item>
          <el-form-item class="form-item__require" label="手机号码" prop="path">
            <el-input
                    v-model="userModel.mobile"
                    size="small"
                    placeholder="请输入手机号码"
                    clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item class="form-item__require" label="邮箱地址">
            <el-input
                    v-model="userModel.email"
                    size="small"
                    placeholder="请输入邮箱地址"
                    clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="userModel.gender">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-divider border-style="dashed" content-position="left">
            权限设置
          </el-divider>
          <el-form-item class="form-item__require" label="所属部门" prop="name">
            <TreeSelector
                    v-model:value="userModel.deptId"
                    placeholder="请选择所属部门"
                    :data="departmentList"
                    :dataFields="{
                label: 'name',
                value: 'id',
              }"
            />
          </el-form-item>
          <el-form-item class="form-item__require" label="所属角色" prop="path">
            <el-select
                    size="small"
                    placeholder="请选择角色"
                    v-model="userModel.role"
                    multiple
                    clearable
            >
              <el-option
                      v-for="roleItem of roleList"
                      :key="roleItem.id"
                      :value="roleItem.id"
                      :label="roleItem.roleName"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts" setup>
import {useDelete, useGet, useLikeSearch, usePageDataTable, usePost, usePut} from "@/hooks";
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {systemDeptTree, systemRoleList, systemUser, systemUserForLockFlag, systemUserPage,} from "@/api/url";
import type {DialogType, TableFooter} from "@/components/types";

const {likeSearchModel, getSearchParams, resetSearch} = useLikeSearch();
const get = useGet();
const post = usePost();
const put = usePut();
const httpDelete = useDelete();
const dialogRef = ref<DialogType>();
const tableFooter = ref<TableFooter>();
const tableRef = ref();
const {
    dataList,
    tableLoading,
    tableConfig,
    handleSuccess,
    handleSelectionChange,
    selectRows,
    useHeight,
  } = usePageDataTable();
  const departmentList = ref([]);
  const roleList = ref([]);

  const tableHeight = computed(() => {
    return tableConfig.height;
  });

  const userModel = reactive({
    id: "",
    userName: "",
    nickName: "",
    mobile: "",
    email: "",
    gender: 1,

    deptId: "",
    role: [],

    password: "",
    lockFlag: "0",
  });

  likeSearchModel.extraParams = () => ({
    ...tableFooter.value?.withPageInfoData(),
  });
  likeSearchModel.conditionItems = reactive([
    {
      name: "userName",
      label: "用户姓名",
      value: "",
      type: "input",
      placeholder: "请输入用户姓名",
      span: 8,
    }
  ]);
  const doSearch = () => {
    const params = getSearchParams();
    get({
      url: systemUserPage,
      data: params,
    })
    .then(handleSuccess)
    .then((res: any) => {
      tableFooter.value?.setTotalSize(res.total);
      get({
        url: systemDeptTree,
      }).then((depRes) => {
        departmentList.value = depRes.data;
      });
      get({
        url: systemRoleList,
      }).then((res) => {
        roleList.value = res.data;
      });
    })
    .catch(console.log);
  };

  function doRefresh() {
    get({
      url: systemUserPage,
      data: {
        ...tableFooter.value?.withPageInfoData(),
        ...getSearchParams(),
      },
    })
    .then(handleSuccess)
    .then((res: any) => {
      tableFooter.value?.setTotalSize(res.total);
      get({
        url: systemDeptTree,
      }).then((depRes) => {
        departmentList.value = depRes.data;
      });
      get({
        url: systemRoleList,
      }).then((roleRes) => {
        roleList.value = roleRes.data;
      });
    })
    .catch(console.log);
  }
  function onDeleteItems() {
    if (selectRows.value.length > 0) {
      ElMessageBox.confirm("确定要删除这些用户吗？", "提示")
              .then(() => {
                httpDelete({url:`${systemUser}/${selectRows.value[0].id}`})
                        .then((res)=>{
                          console.log(JSON.stringify(res))
                          doRefresh();
                        })
                        .catch(console.log)
              })
              .catch(console.log);
    }
  }
  function onAddItem() {
    userModel.id = "";
    userModel.userName = "";
    userModel.nickName = "";
    userModel.mobile = "";
    userModel.email = "";
    userModel.gender = 1;
    userModel.deptId = "";
    userModel.role = [];
    userModel.password = "";
    userModel.lockFlag = "0";
    dialogRef.value?.show(() => {
      if (!userModel.userName) {
        ElMessage.error("请输入用户名");
        return;
      }
      if (!userModel.nickName) {
        ElMessage.error("请输入用户别名");
        return;
      }
      if (!userModel.mobile) {
        ElMessage.error("请输入手机号");
        return;
      }
      if (!userModel.email) {
        ElMessage.error("请输入邮箱地址");
        return;
      }
      if (userModel.deptId === "") {
        ElMessage.error("请选择某个部门");
        return;
      }
      if (userModel.role === null || userModel.role.length === 0) {
        ElMessage.error("请选择某个角色");
        return;
      }
      dialogRef.value?.showLoading();
      post({url:systemUser ,data: userModel})
              .then((res)=>{
                console.log(JSON.stringify(res))
                doRefresh();
              })
              .catch(console.log)
      dialogRef.value?.close();
    });
  }
  function onUpdateItem(item: any) {
    userModel.id = item.id;
    userModel.userName = item.userName;
    userModel.nickName = item.nickName;
    userModel.mobile = item.mobile;
    userModel.email = item.email;
    userModel.gender = item.gender;
    userModel.deptId = item.deptId;
    userModel.role = [];
    if (item.roleList != null) {
      item.roleList.forEach(({id})=>{
        userModel.role.push(id)
      })
    }
    userModel.password = "";
    userModel.lockFlag = item.lockFlag;
    dialogRef.value?.show(() => {
      if (!userModel.nickName) {
        ElMessage.error("请输入用户名");
        return;
      }
      if (!userModel.mobile) {
        ElMessage.error("请输入手机号");
        return;
      }
      if (!userModel.email) {
        ElMessage.error("请输入邮箱地址");
        return;
      }
      if (userModel.deptId === "") {
        ElMessage.error("请选择某个部门");
        return;
      }
      if (userModel.role === null || userModel.role.length === 0) {
        ElMessage.error("请选择某个角色");
        return;
      }
      dialogRef.value?.showLoading();
      put({url:systemUser ,data: userModel})
              .then((res)=>{
                console.log(JSON.stringify(res))
                doRefresh();
              })
              .catch(console.log)
      dialogRef.value?.close();
    });
  }
  function onDeleteItem(item: any) {
    ElMessageBox.confirm("确定要删除此用户吗？", "提示")
            .then(() => {
              dataList.splice(dataList.indexOf(item), 1);
            })
            .catch(console.log);
  }
  function onEnableItem(item: any) {
    ElMessageBox.confirm(
            "确定要" + (item.lockFlag === "0" ? "禁用" : "启用") + "此用户吗？",
            "提示"
    )
            .then(() => {
              switch (item.lockFlag) {
                case "0":
                  item.lockFlag = "9"
                  break;
                case "9":
                  item.lockFlag = "0"
                  break;
                default:
                  item.lockFlag = "0"
              }
              console.log(item.lockFlag)
              put({url:systemUserForLockFlag , data:item})
                      .then((res)=>{
                        console.log(JSON.stringify(res))
                        doRefresh();
                      })
                      .catch(console.log)
            })
            .catch(console.log);
  }

  onMounted(() => {
    doRefresh();
    useHeight();
  });
</script>

<style lang="scss" scoped>
  .gender-container {
    .gender-icon {
      width: 20px;
    }
  }
</style>
