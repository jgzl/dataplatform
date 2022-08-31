<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
			<el-form-item label="请求来源系统" prop="system">
				<el-input v-model="form.system" clearable/>
			</el-form-item>
			<el-form-item label="请求来源key" prop="apiKey">
				<el-input v-model="form.apiKey" clearable/>
			</el-form-item>
			<el-form-item label="请求来源secret" prop="apiSecret">
				<el-input v-model="form.apiSecret" clearable/>
			</el-form-item>
			<el-form-item label="备注" prop="remark">
				<el-input v-model="form.remark" clearable/>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button v-if="mode==='add'" type="primary" :loading="isSaving" @click="submit()">保 存</el-button>
			<el-button v-if="mode==='edit'" type="primary" :loading="isSaving" @click="submitEdit()">保 存(编辑)</el-button>
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
					add: '新增用户',
					edit: '编辑用户',
					show: '查看'
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					id: "",
					system: "",
					apiKey: "",
					apiSecret: "",
					remark: "",
				},
				//验证规则
				rules: {
					apiKey:[
						{required: true, message: '请上传头像'}
					],
					apiSecret: [
						{required: true, message: '请输入登录账号'}
					],
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
						let res = await this.$API.gateway.access.save.post(this.form);
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
			//表单提交方法
			submitEdit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaving = true;
						let res = await this.$API.gateway.access.update.put(this.form);
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
