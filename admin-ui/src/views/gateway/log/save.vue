<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
			<el-form-item label="请求来源系统" prop="sourceService">
				<el-input v-model="form.sourceService" clearable/>
			</el-form-item>
			<el-form-item label="请求来源key" prop="apiKey">
				<el-input v-model="form.apiKey" clearable/>
			</el-form-item>
			<el-form-item label="请求来源secret" prop="apiSecret">
				<el-input v-model="form.apiSecret" clearable/>
			</el-form-item>
			<el-form-item label="请求来源环境" prop="environment">
				<el-input v-model="form.environment" clearable/>
			</el-form-item>
			<el-form-item label="请求目标服务" prop="targetService">
				<el-input v-model="form.targetService" clearable/>
			</el-form-item>
			<el-form-item label="请求路径" prop="requestPath">
				<el-input v-model="form.requestPath" clearable/>
			</el-form-item>
			<el-form-item label="请求路径参数" prop="requestPathAndQuery">
				<el-input v-model="form.requestPathAndQuery" clearable/>
			</el-form-item>
			<el-form-item label="请求方式" prop="requestMethod">
				<el-input v-model="form.requestMethod" clearable/>
			</el-form-item>
			<el-form-item label="请求头" prop="requestHeader">
				<el-input v-model="form.requestHeader" clearable/>
			</el-form-item>
			<el-form-item label="请求源IP" prop="requestSourceIp">
				<el-input v-model="form.requestSourceIp" clearable/>
			</el-form-item>
			<el-form-item label="请求参数值" prop="requestBody">
				<el-input v-model="form.requestBody" clearable/>
			</el-form-item>
			<el-form-item label="返回参数值" prop="responseBody">
				<el-input v-model="form.responseBody" clearable/>
			</el-form-item>
			<el-form-item label="请求时长ms" prop="executeTime">
				<el-input v-model="form.executeTime" clearable/>
			</el-form-item>
			<el-form-item label="请求返回HTTP状态码" prop="httpStatus">
				<el-input v-model="form.httpStatus" clearable/>
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
							res = await this.$API.gateway.log.save.post(this.form);
						} else {
							res = await this.$API.gateway.log.update.put(this.form);
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
