<template>
	<el-container>
		<el-header>
			<div class="left-panel">
				<el-button type="primary" icon="el-icon-plus" @click="add"></el-button>
				<el-button type="danger" plain icon="el-icon-delete" :disabled="selection.length === 0" @click="batch_del"></el-button>
			</div>
			<div class="right-panel">
				<div class="right-panel-search">
					<el-button type="primary" icon="el-icon-search" @click="upsearch"></el-button>
				</div>
			</div>
		</el-header>
		<el-main class="nopadding">
			<scTable ref="table" :apiObj="apiObj" @selection-change="selectionChange" stripe remoteSort remoteFilter>
				<el-table-column type="selection" width="45"/>
				<el-table-column label="主键" prop="id" align="center" width="180"></el-table-column>
				<el-table-column label="应用编码" prop="applicationCode" align="center" width="180"></el-table-column>
				<el-table-column label="应用名称" prop="applicationName" align="center" width="180"></el-table-column>
				<el-table-column label="部署模式" prop="deploymentMode" align="center" width="180"></el-table-column>

				<el-table-column label="操作" fixed="right" align="center" width="240">
					<template #default="scope">
						<el-button-group>
							<el-button text type="primary" size="small" @click="table_show(scope.row)">查看</el-button>
							<el-button text type="primary" size="small" @click="table_edit(scope.row)">编辑</el-button>
							<el-popconfirm title="确定删除吗？" @confirm="table_del(scope.row)">
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
			apiObj: this.$API.gateway.application_page,
			selection: [],
			search: {
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
		async table_del(row){
			let res = await this.$API.gateway.application_delete.delete(row.id);
			if(res.code === 200){
				//这里选择刷新整个表格 OR 插入/编辑现有表格数据
				this.$refs.table.refresh()
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
		}
	}
}
</script>
<style>
</style>
