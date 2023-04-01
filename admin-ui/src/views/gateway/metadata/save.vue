<template>
	<el-dialog ref="dialog" :title="titleMap[mode]" v-model="visible" :width="600" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" :disabled="mode==='show'" ref="dialogForm" label-width="150px" label-position="left">
			<el-form-item label="应用名称" prop="applicationCode">
				<el-select v-model="form.applicationCode" clearable size="default" placeholder="请选择应用">
					<el-option v-for="item in applications" :key="item.id" :label="item.applicationName" :value="item.applicationCode"/>
				</el-select>
				<el-button type="primary" icon="el-icon-plus" @click="addApplication"></el-button>
			</el-form-item>
			<el-form-item label="请求类型" prop="method">
				<el-select v-model="form.method" clearable size="default">
					<el-option key="GET" label="HTTP Method: GET" value="GET"/>
					<el-option key="POST" label="HTTP Method: POST" value="POST"/>
					<el-option key="PUT" label="HTTP Method: PUT" value="PUT"/>
					<el-option key="DELETE" label="HTTP Method: DELETE" value="DELETE"/>
					<el-option key="OPTIONS" label="HTTP Method: OPTIONS" value="OPTIONS"/>
				</el-select>
			</el-form-item>
			<el-form-item label="请求路径" prop="path">
				<el-input v-model="form.path" clearable></el-input>
			</el-form-item>
			<el-form-item label="接口描述" prop="description">
				<el-input v-model="form.description" clearable type="textarea"></el-input>
			</el-form-item>
			<el-form-item label="RPC类型" prop="rpcType">
				<el-select v-model="form.rpcType" clearable size="default">
					<el-option key="http" label="http" value="http"/>
					<el-option key="spring cloud" label="spring cloud" value="spring cloud"/>
					<el-option key="dubbo" label="dubbo" value="dubbo"/>
					<el-option key="grpc" label="grpc" value="grpc"/>
				</el-select>
			</el-form-item>
			<el-form-item label="RPC扩展参数" prop="rpcExtra">
				<el-input v-model="form.rpcExtra" clearable></el-input>
			</el-form-item>
			<el-form-item label="标签" prop="tag">
				<el-select v-model="form.tagArray" multiple-limit="5" multiple allow-create filterable default-first-option placeholder="最多填写五个标签" clearable>
					<el-option v-for="item in tags" :key="item.key" :label="item.value" :value="item.key"></el-option>
				</el-select>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button v-if="mode!=='show'" type="primary" :loading="isSaving" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
	<application-save-dialog v-if="applicationDialog.save" ref="applicationDialog"  @success="handleSuccess" @closed="applicationDialog.save=false"></application-save-dialog>
</template>
<script>
import applicationSaveDialog from '@/views/gateway/application/save'
	export default {
		emits: ['success', 'closed'],
		components: {
			applicationSaveDialog
		},
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '新增网关元数据',
					edit: '编辑网关元数据',
					show: '查看网关元数据',
				},
				visible: false,
				isSaving: false,
				//表单数据
				form: {
					id: "",
					applicationCode: "thingworx",
					method: "POST",
					path: "/api/",
					description: "",
					rpcType: "http",
					rpcExtra: "{}",
					tagArray: [],
					version: "",
				},
				//验证规则
				rules: {
					applicationCode: [
						{required: true, message: '请输入应用名称-提供服务方'}
					],
					method: [
						{required: true, message: '请输入请求类型(GET/POST/PUT/DELETE/OPTIONS)'}
					],
					path: [
						{required: true, message: '请输入请求路径'}
					],
					description: [
						{required: true, message: '请输入接口描述'}
					],
					rpcType: [
						{required: true, message: '请输入RPC接口类型(http)'}
					],
					rpcExtra: [
						{required: true, message: '请输入RPC扩展参数'}
					],
					tagArray: [
						{required: true, message: '请输入接口标签/请求标签'}
					],
				},
				applications: [],
				tags: [],
				applicationDialog: {
					save: false
				}
			}
		},
		mounted(){
			this.queryApplications();
			this.queryTags();
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
				if (this.form.tagArray) {
					this.form.tag = this.form.tagArray.join()
				}
				this.$refs.dialogForm.validate(async (valid) => {
					if (valid) {
						this.isSaving = true;
						let res;
						if (this.form.id === '') {
							res = await this.$API.gateway.metadata_save.post(this.form);
						} else {
							res = await this.$API.gateway.metadata_update.put(this.form);
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
				if (data.tag) {
					this.form.tagArray = data.tag.split(',')
				}
			},
			async queryApplications() {
				let res = await this.$API.gateway.application_page.get();
				this.applications = res.data.records;
			},
			async queryTags() {
				this.tags = [
					{
						"key": "乘用车GBOM",
						"value": "乘用车GBOM",
					},
					{
						"key": "智研数创",
						"value": "智研数创",
					},
					{
						"key": "泰国GBOM",
						"value": "泰国GBOM",
					}
				];
			},
			addApplication() {
				this.applicationDialog.save = true
				this.$nextTick(() => {
					this.$refs.applicationDialog.open()
				})
			},
			async handleSuccess() {
				await this.queryApplications()
			}
		}
	}
</script>
<style>
</style>
