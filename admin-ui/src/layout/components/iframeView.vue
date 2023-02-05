<!--
 * @Descripttion: 处理iframe持久化，涉及store(VUEX)
 * @version: 1.0
 * @author sakuya
 * @date 2021年6月30日13:20:41
 * @LastEditors:
 * @LastEditTime:
-->

<template>
	<div v-show="$gatewayRouteDO.meta.type=='iframe'" class="iframe-pages">
		<iframe v-for="item in iframeList" :key="item.meta.url" v-show="$gatewayRouteDO.meta.url==item.meta.url" :src="item.meta.url" frameborder='0'></iframe>
	</div>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		watch: {
			$gatewayRouteDO(e) {
				this.push(e)
			},
		},
		created() {
			this.push(this.$gatewayRouteDO);
		},
		computed:{
			iframeList(){
				return this.$store.state.iframe.iframeList
			},
			ismobile(){
				return this.$store.state.global.ismobile
			},
			layoutTags(){
				return this.$store.state.global.layoutTags
			}
		},
		mounted() {

		},
		methods: {
			push(gatewayRouteDO){
				if(gatewayRouteDO.meta.type == 'iframe'){
					if(this.ismobile || !this.layoutTags){
						this.$store.commit("setIframeList", gatewayRouteDO)
					}else{
						this.$store.commit("pushIframeList", gatewayRouteDO)
					}
				}else{
					if(this.ismobile || !this.layoutTags){
						this.$store.commit("clearIframeList")
					}
				}
			}
		}
	}
</script>

<style scoped>
	.iframe-pages {width:100%;height:100%;background: #fff;}
	iframe {border:0;width:100%;height:100%;display: block;}
</style>
