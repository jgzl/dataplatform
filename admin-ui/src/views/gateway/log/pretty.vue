<template>
	<el-dialog v-model="visible" :width="1024" destroy-on-close @closed="$emit('closed')">
		<sc-json-pretty :prettyData="formatData"/>
		<template #footer>
			<el-button @click="visible=false" >取 消</el-button>
		</template>
	</el-dialog>
</template>

<script>
	export default {
		emits: ['closed'],
		data() {
			return {
				visible: false,
				isSaving: false,
				formatData: {}
			}
		},
		mounted() {
			console.log(this.formatData)
		},
		methods: {
			//显示
			open(){
				this.visible = true;
				return this
			},
			setData(row) {
				this.formatData = JSON.parse(row.requestHeader)
			}
		}
	}
</script>

<style>
</style>
