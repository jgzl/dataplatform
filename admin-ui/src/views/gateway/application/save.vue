<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
            <el-form-item label="应用编码" prop="applicationCode">
                <el-input v-model="form.applicationCode" clearable></el-input>
            </el-form-item>
            <el-form-item label="应用名称" prop="applicationName">
                <el-input v-model="form.applicationName" clearable></el-input>
            </el-form-item>
            <el-form-item label="部署模式" prop="deploymentMode">
                <el-input v-model="form.deploymentMode" clearable></el-input>
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
					add: '新增网关应用',
					edit: '编辑网关应用',
					show: '查看网关应用',
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					id: "",
					applicationCode: "",
					applicationName: "",
					deploymentMode: "",
					version: "",
				},
				//验证规则
				rules: {
					id: [
						{required: true, message: '请输入主键'}
					],
					applicationCode: [
						{required: true, message: '请输入应用编码'}
					],
					applicationName: [
						{required: true, message: '请输入应用名称'}
					],
					deploymentMode: [
						{required: true, message: '请输入部署模式'}
					],
				},
			}
		},
		mounted(){
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
							res = await this.$API.gateway.application_save.post(this.form);
						} else {
							res = await this.$API.gateway.application_update.put(this.form);
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
				Object.assign(this.form, data)
			}
		}
	}
</script>
<style>
</style>
