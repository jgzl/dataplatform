<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="500" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="头像" prop="avatar">
				<sc-upload v-model="form.avatar" title="上传头像"></sc-upload>
			</el-form-item>
			<el-form-item label="登录账号" prop="userName">
				<el-input v-model="form.userName" placeholder="用于登录系统" clearable></el-input>
			</el-form-item>
			<el-form-item label="姓名" prop="nickName">
				<el-input v-model="form.nickName" placeholder="请输入完整的用户别名" clearable></el-input>
			</el-form-item>
			<el-form-item label="手机号" prop="mobile">
				<el-input v-model="form.mobile" placeholder="请输入手机号" clearable></el-input>
			</el-form-item>
			<el-form-item label="邮箱" prop="email">
				<el-input v-model="form.email" placeholder="请输入邮箱" clearable></el-input>
			</el-form-item>
			<template v-if="mode==='add'">
				<el-form-item label="登录密码" prop="password">
					<el-input type="password" v-model="form.password" clearable show-password></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="password2">
					<el-input type="password" v-model="form.password2" clearable show-password></el-input>
				</el-form-item>
			</template>
			<template v-if="mode==='add'">
				<el-form-item label="所属部门" prop="deptId">
					<el-tree-select
						style="width: 100%"
						v-model="form.deptId"
						:data="this.depts"
						node-key="id"
						check-strictly
						:render-after-expand="false"
						show-checkbox
					/>
				</el-form-item>
				<el-form-item label="所属角色" prop="role">
					<el-select v-model="form.role" multiple filterable style="width: 100%">
						<el-option v-for="item in roles" :key="item.roleId" :label="item.roleName" :value="item.roleId"></el-option>
					</el-select>
				</el-form-item>
			</template>

			<template v-if="mode!=='add'">
				<el-form-item label="所属部门" prop="deptId">
					<el-tree-select
						style="width: 100%"
						v-model="form.deptId"
						:data="this.depts"
						node-key="id"
						check-strictly
						:render-after-expand="false"
						show-checkbox
						:default-checked-keys="this.deptIdArray"
					/>
				</el-form-item>
				<el-form-item label="所属角色" prop="role">
					<el-select v-model="form.role" multiple filterable style="width: 100%">
						<el-option v-for="item in roles" :key="item.roleId" :label="item.roleName" :value="item.roleId"/>
					</el-select>
				</el-form-item>
			</template>
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
					add: '新增用户',
					edit: '编辑用户',
					show: '查看'
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					userId:"",
					userName: "",
					avatar: "",
					nickName: "",
					mobile: "",
					email: "",
					deptId: "",
					role: [],
					gender: "",
					lockFlag: "0",
					createTime: new Date(),
					updateTime: new Date(),
					delFlag: "0",
				},
				//验证规则
				rules: {
					avatar:[
						{required: false, message: '请上传头像'}
					],
					userName: [
						{required: true, message: '请输入登录账号'}
					],
					nickName: [
						{required: true, message: '请输入用户别名'}
					],
					mobile: [
						{required: true, message: '请输入手机号码'}
					],
					email: [
						{required: true, message: '请输入邮箱'}
					],
					password: [
						{required: true, message: '请输入登录密码'},
						{validator: (rule, value, callback) => {
							if (this.form.password2 !== '') {
								this.$refs.dialogForm.validateField('password2');
							}
							callback();
						}}
					],
					password2: [
						{required: true, message: '请再次输入密码'},
						{validator: (rule, value, callback) => {
							if (value !== this.form.password) {
								callback(new Error('两次输入密码不一致!'));
							}else{
								callback();
							}
						}}
					],
					deptId: [
						{required: true, message: '请选择所属部门', trigger: 'change'}
					],
					role: [
						{required: true, message: '请选择所属角色', trigger: 'change'}
					]
				},
				//所需数据选项
				roles: [],
				rolesProps: {
					value: "roleId",
					multiple: true,
					checkStrictly: true
				},
				roleIdArray: [],
				depts: [],
				deptIdArray: [],
			}
		},
		mounted() {
			this.getRole()
			this.getDept()
		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
				this.visible = true;
				return this
			},
			//加载树数据
			async getRole(){
				let res = await this.$API.system.role.list.get();
				this.roles = res.data;
			},
			async getDept(){
				let res = await this.$API.system.dept.tree.get();
				this.depts = res.data;
			},
			//表单提交方法
			submit(){
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaving = true;
						let res;
						if (this.form.userId === '') {
							res = await this.$API.system.user.save.post(this.form);
						} else {
							res = await this.$API.system.user.update.put(this.form);
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
				this.form.userId = data.userId
				this.form.avatar = data.avatar
				this.form.userName = data.userName
				this.form.nickName = data.nickName
				this.form.mobile = data.mobile
				this.form.email = data.email
				this.form.role = data.roleList.map(item => item.roleId)
				this.form.deptId = data.deptId
				this.deptIdArray.push(data.deptId)
			}
		}
	}
</script>

<style>
</style>
