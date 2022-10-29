<template>
	<el-form ref="loginForm" :model="form" :rules="rules" label-width="0" size="large">
		<el-form-item prop="user">
			<el-input v-model="form.userName" prefix-icon="el-icon-user" clearable :placeholder="$t('login.userPlaceholder')">
<!--				<template #append>-->
<!--					<el-select v-model="userType" style="width: 130px;">-->
<!--						<el-option :label="$t('login.admin')" value="admin"></el-option>-->
<!--						<el-option :label="$t('login.user')" value="user"></el-option>-->
<!--					</el-select>-->
<!--				</template>-->
			</el-input>
		</el-form-item>
		<el-form-item prop="password">
			<el-input v-model="form.password" prefix-icon="el-icon-lock" clearable show-password :placeholder="$t('login.PWPlaceholder')"></el-input>
		</el-form-item>
<!--		<el-form-item style="margin-bottom: 10px;">-->
<!--				<el-col :span="12">-->
<!--					<el-checkbox :label="$t('login.rememberMe')" v-model="form.autologin"></el-checkbox>-->
<!--				</el-col>-->
<!--				<el-col :span="12" class="login-forgot">-->
<!--					<router-link to="/reset_password">{{ $t('login.forgetPassword') }}？</router-link>-->
<!--				</el-col>-->
<!--		</el-form-item>-->
		<el-form-item>
			<el-button type="primary" style="width: 100%;" :loading="islogin" round @click="login">{{ $t('login.signIn') }}</el-button>
		</el-form-item>
<!--		<div class="login-reg">-->
<!--			{{$t('login.noAccount')}} <router-link to="/user_register">{{$t('login.createAccount')}}</router-link>-->
<!--		</div>-->
	</el-form>
</template>

<script>
	import {Base64} from 'js-base64'
	export default {
		data() {
			return {
				userType: 'admin',
				form: {
					userName: "admin",
					password: "123456",
					autologin: false
				},
				rules: {
					userName: [
						{required: true, message: this.$t('login.userError'), trigger: 'blur'}
					],
					password: [
						{required: true, message: this.$t('login.PWError'), trigger: 'blur'}
					]
				},
				islogin: false,
			}
		},
		watch:{
			userType(val){
				if(val == 'admin'){
					this.form.userName = 'admin'
					this.form.password = '123456'
				}else if(val == 'user'){
					this.form.userName = 'user'
					this.form.password = 'user'
				}
			}
		},
		mounted() {

		},
		methods: {
			async login(){

				var validate = await this.$refs.loginForm.validate().catch(()=>{})
				if(!validate){ return false }

				this.islogin = true
				let data = {
					username: this.form.userName,
					password: this.form.password,
					// password: this.$TOOL.crypto.MD5(this.form.password)
					grant_type: this.$CONFIG.MY_OAUTH2_GRANT_TYPE,
					scope: this.$CONFIG.MY_OAUTH2_SCOPE,
				}

				let header = {
					'Authorization': 'Basic '+ Base64.encode(this.$CONFIG.MY_OAUTH2_CLIENT_ID+":"+this.$CONFIG.MY_OAUTH2_CLIENT_SECRET),
					'Content-Type': 'application/x-www-form-urlencoded',
				}
				//获取token
				let user = await this.$API.auth.login.post(data,header)
				if(user.code == 200){
					this.$TOOL.cookie.set("TOKEN", user.data.token, {
						expires: this.form.autologin? 24*60*60 : 0
					})
					this.$TOOL.data.set("USER_INFO", user.data)
					this.$TOOL.data.set("PERMISSIONS", user.permissions)
				}else{
					this.islogin = false
					this.$message.warning(user.msg)
					return false
				}
				//获取菜单
				var menu = await this.$API.system.menu.myMenus.get()
				if(menu.code == 200){
					if(menu.data.length==0){
						this.islogin = false
						this.$alert("当前用户无任何菜单权限，请联系系统管理员", "无权限访问", {
							type: 'error',
							center: true
						})
						return false
					}
					this.$TOOL.data.set("MENU", menu.data)
				}else{
					this.islogin = false
					this.$message.warning(menu.message)
					return false
				}

				this.$router.replace({
					path: '/'
				})
				this.$message.success("Login Success 登录成功")
				this.islogin = false
			},
		}
	}
</script>

<style>
</style>
