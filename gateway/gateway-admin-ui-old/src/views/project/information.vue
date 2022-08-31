<template>
  <el-card :body-style="{padding: '5px'}">
    <el-descriptions
      class="margin-top"
      title="项目信息"
      :column="1"
      border
    >
      <el-descriptions-item>
        <template #label>
          项目名称
        </template>
        {{ packageInfo.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          项目版本
        </template>
        {{ packageInfo.version }}
      </el-descriptions-item>
    </el-descriptions>
    <el-descriptions
        class="margin-top"
        title="生产环境依赖"
        :column="4"
        border
    >
      <el-descriptions-item v-for="(item) in schema" :label="item.field" :key="item.field">{{item.label}}</el-descriptions-item>
    </el-descriptions>
    <el-descriptions
      class="margin-top"
      title="开发环境依赖"
      :column="4"
      border
    >
      <el-descriptions-item v-for="(item) in devSchema" :label="item.field" :key="item.field">{{item.label}}</el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script lang="ts" setup>
import packageInfo from '../../../package.json'

export interface schemaItem {
  field: string;
  label: string;
}
const schema: schemaItem[] = [];
const devSchema: schemaItem[] = [];

const { dependencies, devDependencies, name, version } = packageInfo;

Object.keys(dependencies).forEach((key) => {
  schema.push({ field: key, label: dependencies[key] });
});

Object.keys(devDependencies).forEach((key) => {
  devSchema.push({ field: key, label: devDependencies[key] });
});
</script>
