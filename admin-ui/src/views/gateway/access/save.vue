<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
			<el-form-item label="请求来源key" prop="apiKey">
				<el-input v-model="form.apiKey" clearable/>
			</el-form-item>
			<el-form-item label="请求来源secret" prop="apiSecret">
				<el-input v-model="form.apiSecret" clearable/>
			</el-form-item>
			<el-form-item label="请求来源系统" prop="system">
				<el-input v-model="form.system" clearable/>
			</el-form-item>
			<el-form-item label="状态" prop="status">
				<el-switch v-model="form.status" active-value="0" inactive-value="1"/>
			</el-form-item>
			<el-form-item label="备注" prop="remark">
				<el-input v-model="form.remark" clearable/>
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
					add: '新增网关访问',
					edit: '编辑网关访问',
					show: '查看',
					copy: '新增路由(拷贝)',
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					id: "",
					apiKey: "",
					apiSecret: "",
					system: "",
					status: "",
					remark: "",
				},
				//验证规则
				rules: {
					apiKey:[
						{required: true, message: '请输入apiKey'}
					],
					apiSecret: [
						{required: true, message: '请输入apiSecret'}
					],
					system: [
						{required: true, message: '请输入system'}
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
						let res;
						if (this.form.id === '') {
							res = await this.$API.gateway.access_save.post(this.form);
						} else {
							res = await this.$API.gateway.access_update.put(this.form);
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
					this.form.apiKey = this.form.apiKey+'_copy';
					this.form.apiSecret = this.form.apiSecret+'_copy';
					this.form.system = this.form.system+'_copy';
				}
			}
		}
	}
</script>

<style>
</style>
