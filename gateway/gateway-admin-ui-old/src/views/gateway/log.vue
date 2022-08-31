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
                                v-permission="['gateway_log_add']"
                                @click="onAddItem"
                        >
                            添加
                        </el-button>
                        <el-button
                                type="danger"
                                size="mini"
                                icon="DeleteIcon"
                                v-permission="['gateway_log_del']"
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
                            label="主键"
                            prop="id"
                            fixed="left"
                    />
                    <el-table-column
                            align="center"
                            label="请求来源系统"
                            prop="sourceService"
                            fixed="left"
                    />
                    <el-table-column
                            align="center"
                            label="请求来源key"
                            prop="apiKey"
                    />
                    <el-table-column
                            align="center"
                            label="请求来源secret"
                            prop="apiSecret"
                    />
                    <el-table-column
                            align="center"
                            label="请求来源环境"
                            prop="environment"
                    />
                    <el-table-column
                            align="center"
                            label="请求路径"
                            prop="requestPath"
                            show-overflow-tooltip
                    />
                    <el-table-column
                            align="center"
                            label="请求路径参数"
                            prop="requestPathAndQuery"
                            show-overflow-tooltip
                    />
                    <el-table-column
                            align="center"
                            label="请求方式"
                            prop="requestMethod"
                    />
                    <el-table-column
                            align="center"
                            label="请求头"
                            prop="requestHeader"
                            show-overflow-tooltip
                    />
                    <el-table-column
                            align="center"
                            label="请求源IP"
                            prop="requestSourceIp"
                    />
                    <el-table-column
                            align="center"
                            label="请求参数值"
                            prop="requestBody"
                            show-overflow-tooltip
                    />
                    <el-table-column
                            align="center"
                            label="返回参数值"
                            prop="responseBody"
                            show-overflow-tooltip
                    />
                    <el-table-column
                            align="center"
                            label="请求时长ms"
                            prop="executeTime"
                    />
                    <el-table-column
                            align="center"
                            label="请求返回HTTP状态码"
                            prop="httpStatus"
                            width="160px"
                    />
                    <el-table-column
                            align="center"
                            label="请求错误信息"
                            prop="errorMsg"
                            show-overflow-tooltip
                    />
                    <el-table-column
                            align="center"
                            label="创建时间"
                            prop="requestTime"
                    />
                    <el-table-column
                            align="center"
                            label="更新时间"
                            prop="responseTime"
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
                                    v-permission="['gateway_log_edit']"
                                    @click="onUpdateItem(scope.row)"
                            >编辑</el-button
                            >
                            <el-button
                                    type="danger"
                                    size="mini"
                                    plain
                                    v-permission="['gateway_log_del']"
                                    @click="onDeleteItem(scope.row)"
                            >删除</el-button
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
                        :model="logModel"
                        :inline="true"
                        label-width="80px"
                        label-position="right"
                >
                    <el-divider border-style="dashed" content-position="left">基本信息</el-divider>
                    <el-form-item label="请求路径">
                        <el-input
                                v-model="logModel.requestPath"
                                size="small"
                                placeholder="请输入请求路径"
                                disabled
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="请求路径参数">
                        <el-input
                                v-model="logModel.requestPathAndQuery"
                                size="small"
                                placeholder="请输入请求路径参数"
                                disabled
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="请求方法">
                        <el-input
                                v-model="logModel.requestMethod"
                                size="small"
                                placeholder="请输入请求方法"
                                disabled
                                clearable
                        />
                    </el-form-item>
                    <el-form-item label="请求头">
                        <el-input
                                v-model="logModel.requestHeader"
                                size="small"
                                placeholder="请输入请求头"
                                disabled
                                clearable
                        />
                    </el-form-item>
                </el-form>
            </template>
        </Dialog>
    </div>
</template>

<script lang="ts" setup>
import {useDelete, useGet, useLikeSearch, usePageDataTable, usePost, usePut} from "@/hooks";
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessageBox} from "element-plus";
import {gatewayLogs, gatewayLogSearch,} from "@/api/url";
import type {DialogType, TableFooter} from "@/components/types";

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

    const tableHeight = computed(() => {
        return tableConfig.height;
    });

    const logModel: LogModel = reactive({
        id: "",
        system: "",
        apiKey: "",
        apiSecret: "",
        environment: "",
        requestPath: "",
        requestPathAndQuery: "",
        requestMethod: "",
        requestHeader: "",
        requestSourceIp: "",
        requestBody: "",
        responseBody: "",
        executeTime: "",
        httpStatus: "",
    });

    const { likeSearchModel, getSearchParams, resetSearch } = useLikeSearch();
    likeSearchModel.extraParams = () => ({
        ...tableFooter.value?.withPageInfoData(),
    });
    likeSearchModel.conditionItems = reactive([
        {
            name: "apiKey",
            label: "网关访问key",
            value: "",
            type: "input",
            placeholder: "请输入网关访问key",
            span: 8,
        },
        {
            name: "sourceService",
            label: "访问系统",
            value: "",
            type: "input",
            placeholder: "请输入访问系统",
            span: 8,
        },
        {
            name: "requestPath",
            label: "请求路径",
            value: "",
            type: "input",
            placeholder: "请输入请求路径",
            span: 8,
        },
        {
            name: "environment",
            label: "请求来源环境",
            value: "",
            type: "input",
            placeholder: "请求来源环境",
            span: 8,
        },
        {
            name: "createTimeRange",
            label: "创建时间",
            value: [],
            type: "date-range",
            span: 8,
        },
        {
            name: "updateTimeRange",
            label: "更新时间",
            value: [],
            type: "date-range",
            span: 8,
        }
    ]);
    const doSearch = () => {
        const params = getSearchParams();
        get({
            url: gatewayLogSearch,
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
            url: gatewayLogSearch,
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
                  const idArray: string[] = [];
                  selectRows.value.map((row: LogModel)=>idArray.push(row.id));
                  httpDelete({url:`${gatewayLogs}/${idArray.join(",")}`})
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
        console.log("add item")
    }

    function onUpdateItem(item: LogModel) {
        logModel.requestPath = item.requestPath;
        logModel.requestPathAndQuery = item.requestPathAndQuery;
        logModel.requestMethod = item.requestMethod;
        logModel.requestHeader = item.requestHeader;
        dialogRef.value?.show();
        dialogRef.value?.showLoding();
    }

    function onDeleteItem(item: LogModel) {
        ElMessageBox.confirm("确定要删除此数据吗？", "提示")
            .then(() => {
                httpDelete({url:`${gatewayLogs}/${item.id}`})
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
