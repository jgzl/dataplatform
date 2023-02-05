<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
			<el-form-item label="路由id" prop="routeId">
				<el-input v-model="form.routeId" clearable/>
			</el-form-item>
			<el-form-item label="路由名称" prop="routeName">
				<el-input v-model="form.routeName" clearable/>
			</el-form-item>
			<el-form-item label="断言" prop="predicates">
				<el-input v-model="form.predicates" clearable/>
			</el-form-item>
			<el-form-item label="过滤器" prop="filters">
				<el-input v-model="form.filters" clearable/>
			</el-form-item>
			<el-form-item label="uri" prop="uri">
				<el-input v-model="form.uri" clearable/>
			</el-form-item>
			<el-form-item label="排序" prop="sort">
				<el-input v-model="form.sort" clearable/>
			</el-form-item>
			<el-form-item label="元数据" prop="metadata">
				<el-input v-model="form.metadata" clearable/>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button v-if="mode!=='show'" type="primary" :loading="isSaving" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>
	export default {
		emits: ['success', 'closed'],
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '新增路由',
					edit: '编辑路由',
					show: '查看',
					copy: '新增路由(拷贝)',
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					id: "",
					routeName: "",
					routeId: "",
					predicates: "",
					filters: "",
					uri: "",
					sort: "",
					metadata: ""
				},
				//验证规则
				rules: {

				},
			}
		},
		mounted() {

		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
				this.visible = true;
				return this
			},
			//表单提交方法
			submit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaving = true;
						let res;
						if (this.form.id === '') {
							res = await this.$API.gateway.gatewayRouteDO.save.post(this.form);
						} else {
							res = await this.$API.gateway.gatewayRouteDO.update.put(this.form);
						}
						this.isSaving = false;
						if(res.code === 200){
							this.$emit('success', this.form, this.mode)
							this.visible = false;
							this.$message.success("操作成功")
						}else{
							this.$alert(res.msg, "提示", {type: 'error'})
						}
					}else{
						return false;
					}
				})
			},
			//表单注入数据
			setData(data){
				Object.assign(this.form,data)
				if (this.mode === 'copy') {
					this.form.id = '';
					this.form.routeId = this.form.routeId+'_copy';
					this.form.routeName = this.form.routeName+'_copy';
				}
			}
		}
	}
</script>

<style>
</style>
