<!--
 * @Descripttion: 系统计划任务配置
 * @version: 1.2
 * @author sakuya
 * @date 2021年7月7日09:28:32
 * @LastEditors: sakuya
 * @LastEditTime: 2021年7月10日20:56:47
-->

<template>
	<el-dialog :title="titleMap[mode]" v-model="visible" :width="400" destroy-on-close @closed="$emit('closed')">
		<el-form :model="form" :rules="rules" ref="dialogForm" label-width="100px" label-position="left">
			<el-form-item label="描述" prop="title">
				<el-input v-model="form.title" placeholder="计划任务标题" clearable></el-input>
			</el-form-item>
			<el-form-item label="执行类" prop="handler">
				<el-input v-model="form.handler" placeholder="计划任务执行类名称" clearable></el-input>
			</el-form-item>
			<el-form-item label="定时规则" prop="cron">
				<sc-cron v-model="form.cron" placeholder="请输入Cron定时规则" clearable :shortcuts="shortcuts"></sc-cron>
			</el-form-item>
			<el-form-item label="是否启用" prop="state">
				<el-switch v-model="form.state" active-value="1" inactive-value="-1"></el-switch>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
			<el-button type="primary" :loading="isSaving" @click="submit()">保 存</el-button>
		</template>
	</el-dialog>
</template>

<script>
import scCron from '@/components/scCron';

export default {
		components: {
			scCron
		},
		emits: ['success', 'closed'],
		data() {
			return {
				mode: "add",
				titleMap: {
					add: '新增计划任务',
					edit: '编辑计划任务'
				},
				form: {
					id:"",
					title: "",
					handler: "",
					cron: "",
					state: "1"
				},
				rules: {
					title:[
						{required: true, message: '请填写标题'}
					],
					handler:[
						{required: true, message: '请填写执行类'}
					],
					cron:[
						{required: true, message: '请填写定时规则'}
					]
				},
				visible: false,
				isSaving: false,
				shortcuts: [
					{
						text: "每天8点和12点 (自定义追加)",
						value: "0 0 8,12 * * ?"
					}
				]
			}
		},
		mounted() {

		},
		methods: {
			//显示
			open(mode='add'){
				this.mode = mode;
				this.visible = true;
				return this;
			},
			//表单提交方法
			submit(){
				this.$refs.dialogForm.validate((valid) => {
					if (valid) {
						this.isSaving = true;
						setTimeout(()=>{
							this.isSaving = false;
							this.visible = false;
							this.$message.success("操作成功")
							this.$emit('success', this.form, this.mode)
						},1000)
					}
				})
			},
			//表单注入数据
			setData(data){
				this.form.id = data.id
				this.form.title = data.title
				this.form.handler = data.handler
				this.form.cron = data.cron
				this.form.state = data.state
			}
		}
	}
</script>

<style>
</style>
