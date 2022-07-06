<script setup lang="ts">
    import axios from "axios";
    import { ref } from "vue";
    import { useRouter } from "vue-router";
    import { defineProps, onMounted } from 'vue';

    const router = useRouter();

    const props = defineProps({
        postId: {
            type: [Number, String],
            require: true
        }
    });
    
    const post = ref({
        id: 0,
        title: "",
        content: ""
    });

    const edit = () => {
         axios
            .patch(`/api/posts/${props.postId}`, post.value)
            .then(() => {
                router.replace({name: "home"});
            });
    }

    onMounted( () => {
        axios.get(`/api/posts/${props.postId}`).then((response) => {
            post.value = response.data;
        });
    })
 



</script>

<template>
  <div class="mt-2">
    <el-input v-model="post.title" placeholder="제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15"></el-input>
  </div>

  <div class="mt-2">
    <el-button @click="edit()" type="warning">수정완료</el-button>
  </div>
</template>

<style></style>

