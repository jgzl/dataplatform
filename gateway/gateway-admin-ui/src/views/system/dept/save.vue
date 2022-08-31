<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px">
			<el-form-item label="上级部门" prop="parentId">
				<el-cascader v-model="form.parentId" :options="groups" :props="groupsProps" :show-all-levels="false" clearable style="width: 100%;"></el-cascader>
			</el-form-item>
			<el-form-item label="部门名称" prop="label">
				<el-input v-model="form.label" placeholder="请输入部门名称" clearable></el-input>
			</el-form-item>
			<el-form-item label="排序" prop="sort">
				<el-input-number v-model="form.sort" controls-position="right" :min="1" style="width: 100%;"></el-input-number>
			</el-form-item>
			<el-form-item label="是否有效" prop="status">
				<el-switch v-model="form.status" :active-value="1" :inactive-value="0"></el-switch>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button v-if="mode==='add'" type="primary" :loading="isSaving" @click="submit()">保 存</el-button>
			<el-button v-if="mode==='edit'" type="primary" :loading="isSaving" @click="submitEdit()">保 存</el-button>
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
					id:"",
					parentId: "",
					label: "",
					name: "",
					sort: 1,
					status: "1",
					createTime: new Date(),
					updateTime: new Date(),
					delFlag: "0",
				},
				//验证规则
				rules: {
					sort: [
						{required: true, message: '请输入排序', trigger: 'change'}
					],
					label: [
						{required: true, message: '请输入部门名称'}
					]
				},
				//所需数据选项
				groups: [],
				groupsProps: {
					value: "id",
					emitPath: false,
					checkStrictly: true
				}
			}
		},
		mounted() {
			this.getGroup()
		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
				this.visible = true;
				return this
			},
			//加载树数据
			async getGroup(){
				let res = await this.$API.system.dept.tree.get();
				this.groups = res.data;
			},
			//表单提交方法
			submit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaving = true;
						this.form.name = this.form.label;
						let res;
						if (this.form.id === '') {
							res = await this.$API.system.dept.save.post(this.form);
						} else {
							res = await this.$API.system.dept.update.put(this.form);
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
