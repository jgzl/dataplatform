<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
			<el-form-item label="应用" prop="apiKey">
				<el-input v-model="form.applicationName" clearable/>
			</el-form-item>
			<el-form-item label="编码" prop="apiSecret">
				<el-input v-model="form.code" clearable/>
			</el-form-item>
			<el-form-item label="信息" prop="environment">
				<el-input v-model="form.message" clearable/>
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
					add: '新增日志',
					edit: '编辑日志',
					show: '查看'
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					id: "",
					applicationName: "",
					code: "",
					message: "",
					version: 1,
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
							this.form.type = 2
							res = await this.$API.system.errorCode.save.post(this.form);
						} else {
							res = await this.$API.system.errorCode.update.put(this.form);
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
			}
		}
	}
</script>

<style>
</style>
