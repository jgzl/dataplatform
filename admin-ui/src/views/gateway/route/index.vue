<template>
		<el-container>
				<el-header>
					<div class="left-panel">
						<el-button type="primary" icon="el-icon-plus" @click="add"></el-button>
						<el-button type="danger" plain icon="el-icon-delete" :disabled="selection.length==0" @click="batch_del"></el-button>
					</div>
					<div class="right-panel">
						<div class="right-panel-search">
							<el-input v-model="search.routeId" placeholder="路由id" clearable></el-input>
							<el-input v-model="search.routeName" placeholder="路由名称" clearable></el-input>
							<el-button type="primary" icon="el-icon-search" @click="upsearch"></el-button>
						</div>
					</div>
				</el-header>
				<el-main class="nopadding">
					<scTable ref="table" :apiObj="apiObj" @selection-change="selectionChange" stripe remoteSort remoteFilter>
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
							prop="sort"
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
								<el-button-group>
									<el-button text type="primary" size="small" @click="table_show(scope.row, scope.$index)">查看</el-button>
									<el-button text type="primary" size="small" @click="table_edit(scope.row, scope.$index)">编辑</el-button>
									<el-popconfirm title="确定删除吗？" @confirm="table_del(scope.row, scope.$index)">
										<template #reference>
											<el-button text type="primary" size="small">删除</el-button>
										</template>
									</el-popconfirm>
									<el-button text type="primary" size="small" @click="table_copy(scope.row, scope.$index)">复制</el-button>
								</el-button-group>
							</template>
						</el-table-column>

					</scTable>
				</el-main>
		</el-container>

	<save-dialog v-if="dialog.save" ref="saveDialog" @success="handleSuccess" @closed="dialog.save=false"></save-dialog>

</template>

<script>
import saveDialog from './save'

export default {
		name: 'user',
		components: {
			saveDialog
		},
		data() {
			return {
				dialog: {
					save: false
				},
				group: [],
				apiObj: this.$API.gateway.route.page,
				selection: [],
				search: {
					routeId: "",
					routeName: "",
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
				let res = await this.$API.gateway.route.delete.delete(row.routeId);
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
			//拷贝
			table_copy(row){
				this.dialog.save = true
				this.$nextTick(() => {
					this.$refs.saveDialog.open('copy').setData(row)
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
			}
		}
	}
</script>

<style>
</style>
