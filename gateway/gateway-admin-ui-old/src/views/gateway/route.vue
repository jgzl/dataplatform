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
                                v-permission="['gateway_route_add']"
                                @click="onAddItem"
                        >
                            添加
                        </el-button>
                        <el-button
                                type="primary"
                                size="mini"
                                icon="PlusIcon"
                                v-permission="['gateway_route_copy']"
                                :disabled="selectRows.length !== 1"
                                @click="onCopyItem(selectRows[0])"
                        >
                            复制
                        </el-button>
                        <el-button
                                type="danger"
                                size="mini"
                                icon="DeleteIcon"
                                v-permission="['gateway_route_del']"
                                :disabled="selectRows.length === 0"
                                @click="onDeleteItems"
                        >
                            删除
                        </el-button>
                    </template>
                </TableConfig>
            </template>
            <template #default>
                <el-table
                        ref="tableRef"
                        v-loading="tableLoading"
                        :data="dataList"
                        :header-cell-style="tableConfig.headerCellStyle"
                        :size="tableConfig.size"
                        :stripe="tableConfig.stripe"
                        :border="tableConfig.border"
                        :height="tableConfig.height"
                        @selection-change="handleSelectionChange"
                >
                    <el-table-column type="selection" width="45"/>
                    <el-table-column
                            align="center"
                            label="路由id"
                            prop="routeId"
                    />
                    <el-table-column
                            align="center"
                            label="路由名称"
                            prop="routeName"
                    />
                    <el-table-column
                            align="center"
                            label="断言"
                            prop="predicates"
                    />
                    <el-table-column
                            align="center"
                            label="过滤器"
                            prop="filters"
                    />
                    <el-table-column
                            align="center"
                            label="uri"
                            prop="uri"
                    />
                    <el-table-column
                            align="center"
                            label="排序"
                            prop="order"
                    />
                    <el-table-column
                            align="center"
                            label="路由元数据"
                            prop="metadata"
                    />
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
                                    v-permission="['gateway_route_edit']"
                                    @click="onUpdateItem(scope.row)"
                            >编辑
                            </el-button
                            >
                            <el-button
                                    type="danger"
                                    size="mini"
                                    plain
                                    v-permission="['gateway_route_del']"
                                    @click="onDeleteItem(scope.row)"
                            >删除
                            </el-button
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
                        :model="routeModel"
                        :inline="true"
                        label-width="240px"
                        label-position="right"
                >
                    <el-divider border-style="dashed" content-position="left">基本信息</el-divider>
                    <el-form-item label="路由id">
                        <el-input
                                v-model="routeModel.routeId"
                                size="small"
                                :disabled="routeIdDisabled"
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="路由名称">
                        <el-input
                                v-model="routeModel.routeName"
                                size="small"
                                placeholder="请输入路由名称"
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="断言">
                        <el-input
                                v-model="routeModel.predicates"
                                size="small"
                                placeholder="请输入断言"
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="过滤器">
                        <el-input
                                v-model="routeModel.filters"
                                size="small"
                                placeholder="请输入过滤器"
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="uri">
                        <el-input
                                v-model="routeModel.uri"
                                size="small"
                                placeholder="请输入uri"
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="排序">
                        <el-input
                                v-model="routeModel.order"
                                size="small"
                                placeholder="请输入排序"
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="路由元数据">
                        <el-input
                                v-model="routeModel.metadata"
                                size="small"
                                placeholder="请输入元数据"
                                clearable
                        />
                    </el-form-item>
                </el-form>
            </template>
        </Dialog>
    </div>
</template>

<script lang="ts" setup>
    import {useGet, usePost, usePut, useDelete, useLikeSearch, usePageDataTable} from "@/hooks";
    import {onMounted, reactive, ref} from "vue";
    import {ElMessage, ElMessageBox} from "element-plus";
    import {
        gatewayRoute,
        gatewayRoutePage,
    } from "@/api/url";
    import type { DialogType, TableFooter } from "@/components/types";

    const get = useGet();
    const post = usePost();
    const put = usePut();
    const httpDelete = useDelete();
    const dialogRef = ref<DialogType>();
    const tableFooter = ref<TableFooter>();
    const {
        dataList,
        tableLoading,
        tableConfig,
        handleSuccess,
        handleSelectionChange,
        selectRows,
        useHeight,
    } = usePageDataTable();

    const routeModel: RouteModel = reactive({
        id: "",
        routeName: "",
        routeId: "",
        predicates: "",
        filters: "",
        uri: "",
        order: "",
        metadata: ""
    });

    const routeIdDisabled = ref(true);
    const { likeSearchModel, getSearchParams, resetSearch } = useLikeSearch();
    likeSearchModel.extraParams = () => ({
        ...tableFooter.value?.withPageInfoData(),
    });
    likeSearchModel.conditionItems = reactive([
        {
            name: "routeName",
            label: "路由名称",
            value: "",
            type: "input",
            placeholder: "请输入路由名称",
            span: 8,
        }
    ]);
    const doSearch = () => {
        const params = getSearchParams();
        get({
            url: gatewayRoutePage,
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
            url: gatewayRoutePage,
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

    function onDeleteItems() {
        if (selectRows.value.length > 0) {
            ElMessageBox.confirm("确定要删除这些数据吗？", "提示")
                .then(() => {
                  const idArray = [];
                  selectRows.value.map((row: RouteModel)=>idArray.push(row.routeId));
                  httpDelete({ url:gatewayRoute+"/"+idArray.join(",") })
                      .then((res)=>{
                        console.log(JSON.stringify(res))
                        doRefresh();
                      })
                      .catch(console.log)
                })
                .catch(console.log);
            console.log("请求结束")
        }
    }

    function onAddItem() {
        routeIdDisabled.value = false;
        routeModel.routeName = "";
        routeModel.routeId = "";
        routeModel.predicates = "[]";
        routeModel.filters = "[]";
        routeModel.uri = "lb://";
        routeModel.order = "0";
        routeModel.metadata = "{}";
        dialogRef.value?.show(() => {
            dialogRef.value?.showLoading();
            post({url:gatewayRoute ,data: routeModel})
                .then((res)=>{
                    console.log(JSON.stringify(res))
                    doRefresh();
                })
                .catch(console.log)
            dialogRef.value?.close();
        });
    }

    function onCopyItem(item: RouteModel) {
        routeIdDisabled.value = false;
        routeModel.id = "";
        routeModel.routeName = item.routeName;
        routeModel.routeId = item.routeId;
        routeModel.predicates = item.predicates;
        routeModel.filters = item.filters;
        routeModel.uri = item.uri;
        routeModel.order = item.order;
        routeModel.metadata = item.metadata;
        dialogRef.value?.show(() => {
            dialogRef.value?.showLoading();
            post({url:gatewayRoute ,data: routeModel})
                .then((res)=>{
                    console.log(JSON.stringify(res))
                    doRefresh();
                })
                .catch(console.log)
            dialogRef.value?.close();
        });
    }

    function onUpdateItem(item: RouteModel) {
        routeIdDisabled.value = true;
        routeModel.id = item.id;
        routeModel.routeName = item.routeName;
        routeModel.routeId = item.routeId;
        routeModel.predicates = item.predicates;
        routeModel.filters = item.filters;
        routeModel.uri = item.uri;
        routeModel.order = item.order;
        routeModel.metadata = item.metadata;
        dialogRef.value?.show(() => {
            dialogRef.value?.showLoading();
            put({ url:gatewayRoute ,data: routeModel })
                .then((res)=>{
                    console.log(JSON.stringify(res))
                    doRefresh();
                })
                .catch(console.log)
            dialogRef.value?.close();
        });
    }

    function onDeleteItem(item: RouteModel) {
        ElMessageBox.confirm("确定要删除此数据吗？", "提示")
            .then(() => {
                httpDelete({ url:gatewayRoute+"/"+item.routeId })
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

</style>