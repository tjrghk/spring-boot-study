<template>
    <el-header class="header">
      <el-menu mode="horizontal">
        <el-menu-item index="1"><RouterLink to="/">Home</RouterLink></el-menu-item>
        <el-menu-item index="2"><RouterLink to="/write">글 작성</RouterLink></el-menu-item>
      </el-menu>
  </el-header>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
    .header {
        padding: 0;
    }
</style>