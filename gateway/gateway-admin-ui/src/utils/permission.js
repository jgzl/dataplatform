import tool from '@/utils/tool';

export function permission(data) {
	let permissions = tool.data.get("PERMISSIONS");
	if(!permissions){
		return false;
	}
	let isHave = permissions.includes(data);
	return isHave;
}

export function rolePermission(data) {
	let roles = tool.data.get("ROLES");
	if(!roles){
		return false;
	}
	let isHave = roles.includes(data);
	return isHave;
}
