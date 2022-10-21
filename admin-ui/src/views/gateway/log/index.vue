<template>
		<el-container>
				<el-header>
					<div class="left-panel">
						<el-button type="primary" icon="el-icon-plus" @click="add"></el-button>
						<el-button type="danger" plain icon="el-icon-delete" :disabled="selection.length==0" @click="batch_del"></el-button>
						<el-button type="primary" :disabled="selection.length!==1" @click="prettyHeader(selection[0])">格式化展示Header</el-button>
					</div>
					<div class="right-panel">
						<div class="right-panel-search">
							<el-input v-model="search.sourceService" placeholder="请求来源系统" clearable></el-input>
							<el-input v-model="search.apiKey" placeholder="请求来源key" clearable></el-input>
							<el-input v-model="search.apiSecret" placeholder="请求来源secret" clearable></el-input>
							<el-input v-model="search.environment" placeholder="请求来源环境" clearable></el-input>
							<el-input v-model="search.targetService" placeholder="请求目标服务" clearable></el-input>
							<el-input v-model="search.requestPath" placeholder="请求路径" clearable></el-input>
							<el-input v-model="search.requestPathAndQuery" placeholder="请求路径参数" clearable></el-input>
							<el-input v-model="search.requestMethod" placeholder="请求方式" clearable></el-input>
							<el-input v-model="search.requestSourceIp" placeholder="请求源IP" clearable></el-input>
							<el-button type="primary" icon="el-icon-search" @click="upsearch"></el-button>
						</div>
					</div>
				</el-header>
				<el-main class="nopadding">
					<scTable ref="table" :apiObj="apiObj" @selection-change="selectionChange" stripe remoteSort remoteFilter>
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
							label="请求目标服务"
							prop="targetService"
						/>
						<el-table-column
							align="center"
							label="请求路径"
							prop="requestPath"
						/>
						<el-table-column
							align="center"
							label="请求路径参数"
							prop="requestPathAndQuery"
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
							label="请求时间"
							prop="@requestTime"
						/>
						<el-table-column
							align="center"
							label="响应时间"
							prop="@responseTime"
						/>
						<el-table-column
							align="center"
							label="操作"
							fixed="right"
							width="220"
						>
							<template #default="scope">
								<el-button-group>
									<el-button text type="primary" size="small" @click="table_show(scope.row, scope.$index)">查看</el-button>
									<el-button text type="primary" size="small" @click="table_edit(scope.row, scope.$index)">编辑</el-button>
									<el-popconfirm title="确定删除吗？" @confirm="table_del(scope.row, scope.$index)">
										<template #reference>
											<el-button text type="primary" size="small">删除</el-button>
										</template>
									</el-popconfirm>
								</el-button-group>
							</template>
						</el-table-column>

					</scTable>
				</el-main>
		</el-container>

	<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false"></save-dialog>
	<pretty-dialog v-if="dialog.pretty" ref="prettyDialog" @closed="dialog.pretty=false"></pretty-dialog>

</template>

<script>
import saveDialog from './save'
import prettyDialog from './pretty'

export default {
		name: 'user',
		components: {
			saveDialog,
			prettyDialog
		},
		data() {
			return {
				dialog: {
					save: false,
					pretty: false,
				},
				group: [],
				apiObj: this.$API.gateway.log.page,
				selection: [],
				search: {
					sourceService: "",
					apiKey: "",
					apiSecret: "",
					environment: "",
					targetService: "",
					requestPath: "",
					requestPathAndQuery: "",
					requestMethod: "",
					requestHeader: "",
					requestSourceIp: "",
					requestBody: "",
					responseBody: "",
					executeTime: "",
					httpStatus: "",
				}
			}
		},
		watch: {

		},
		mounted() {

		},
		methods: {
			//添加
			add(){
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open()
				})
			},
			//编辑
			table_edit(row){
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open('edit').setData(row)
				})
			},
			//查看
			table_show(row){
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open('show').setData(row)
				})
			},
			//删除
			async table_del(row, index){
				let res = await this.$API.gateway.log.delete.delete(row.id);
				if(res.code === 200){
					//这里选择刷新整个表格 OR 插入/编辑现有表格数据
					this.$refs.table.tableData.splice(index, 1);
					this.$message.success("删除成功")
				}else{
					this.$alert(res.msg, "提示", {type: 'error'})
				}
			},
			//批量删除
			async batch_del(){
				this.$confirm(`确定删除选中的 ${this.selection.length} 项吗？`, '提示', {
					type: 'warning'
				}).then(() => {
					const loading = this.$loading();
					this.selection.forEach(item => {
						this.$refs.table.tableData.forEach((itemI, indexI) => {
							if (item.id === itemI.id) {
								this.$refs.table.tableData.splice(indexI, 1)
							}
						})
					})
					loading.close();
					this.$message.success("操作成功")
				}).catch(() => {

				})
			},
			//表格选择后回调事件
			selectionChange(selection){
				this.selection = selection;
			},
			//搜索
			upsearch(){
				this.$refs.table.upData(this.search)
			},
			//本地更新数据
			handleSuccess() {
				this.$refs.table.refresh()
			},
			prettyHeader(row) {
				this.dialog.pretty = true
				this.$nextTick(() => {
					this.$refs.prettyDialog.open().setData(row);
				})
			}
		}
	}
</script>

<style>
</style>
