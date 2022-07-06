<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";
</script>

<template>
  <Header></Header>
  <Content></Content>
  
</template>

<style>
  @font-face {
      font-family: 'OKDDUNG';
      src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-02@1.0/OKDDUNG.woff2') format('woff2');
      font-weight: normal;
      font-style: normal;
  }

  body {
    font-family: OKDDUNG;
    margin: 0;
    width: 100%;
  }

  

</style>
