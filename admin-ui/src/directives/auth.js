import {permission} from '@/utils/permission'

export default {
	mounted(el, binding) {
		const { value } = binding
		if(Array.isArray(value)){
			let isHas = false;
			value.forEach(item => {
				if(permission(item)){
					isHas = true;
				}
			})
			if (!isHas){
				el.parentNode.removeChild(el)
			}
		}else{
			if(!permission(value)){
				el.parentNode.removeChild(el);
			}
		}
	}
};
