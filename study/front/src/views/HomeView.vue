<script setup lang="ts">
  import axios from "axios";

  const posts = [];

  axios.get("/api/posts?page=1&size=5").then((response) => {
    console.log(response);
  });

  
</script>

<template>
  <ul>
      <li v-for="post in posts" :key="post.id">
        <div>
           {{post.title}}
        </div>
        <div>
          {{post.content}}
        </div>
      </li>
  </ul>
  <main></main>
</template>

<style scoped>
  li {
    margin-bottom: 1rem;
  }

  li:last-child {
    margin-bottom: 0;
  }
</style>
