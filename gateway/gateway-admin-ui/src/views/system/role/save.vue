<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="角色名称" prop="label">
				<el-input v-model="form.roleName" clearable></el-input>
			</el-form-item>
			<el-form-item label="角色编码" prop="alias">
				<el-input v-model="form.roleCode" clearable></el-input>
			</el-form-item>
			<el-form-item label="角色描述" prop="remark">
				<el-input v-model="form.roleDesc" clearable type="textarea"></el-input>
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
					add: '新增',
					edit: '编辑',
					show: '查看'
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					roleId:"",
					roleName: "",
					roleCode: "",
					roleDesc: "",
					createTime: new Date(),
					updateTime: new Date(),
					deleted: "0",
				},
				//验证规则
				rules: {
					roleName: [
						{required: true, message: '请输入角色名称'}
					],
					roleCode: [
						{required: true, message: '请输入角色编码'}
					]
				}
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
						if (this.form.roleId === '') {
							res = await this.$API.system.role.save.post(this.form);
						} else {
							res = await this.$API.system.role.update.put(this.form);
						}
						this.isSaving = false;
						if(res.code === 200){
							this.$emit('success', this.form, this.mode)
							this.visible = false;
							this.$message.success("操作成功")
						}else{
							this.$alert(res.msg, "提示", {type: 'error'})
						}
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
