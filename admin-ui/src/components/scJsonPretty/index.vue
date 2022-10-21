<template>
	<div>
		<vue-json-pretty :data="prettyData" />
	</div>
</template>

<script>
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';

export default {
	props: {
		prettyData: {
			type: Object,
			default: ()=>{},
		}
	},
	components: {
		VueJsonPretty,
	},
};
</script>
