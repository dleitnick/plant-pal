<template>
  <form class="post-to-thread" @submit.prevent="handleSubmit()">
    <button
      class="post-to-thread-buttons"
      @click.prevent="formToggle()"
      v-if="!showForm"
    >
      <div class="post-button-content">
        <span class="material-symbols-outlined"> reply </span> {{ buttonName }}
      </div>
    </button>
    <div v-if="showForm">
      <input
        type="text"
        class="ptt-textbox"
        v-if="isPost"
        v-model="titleForPost"
        placeholder="Write your title..."
        required
      />
      <vue-editor
        id="editor"
        v-model="htmlForEditor"
        placeholder="Write your reply..."
        :editor-toolbar="customToolbar"
      >
      </vue-editor>
      <button class="post-to-thread-buttons" type="submit">Submit</button>
      <button class="post-to-thread-buttons" @click.prevent="formToggle()">
        Cancel
      </button>
    </div>
  </form>
</template>

<script>
import { VueEditor } from "vue2-editor/dist/vue2-editor.core.js";
import forumService from "../services/ForumService.js";

export default {
  name: "post-to-thread",
  components: {
    VueEditor,
  },
  props: {
    isPost: {
      type: Boolean,
      default: false,
    },
    parentId: Number,
  },
  computed: {
    buttonName() {
      return this.isPost ? "Create New Post" : "Reply";
    },
  },
  data() {
    return {
      showForm: false,
      htmlForEditor: "",
      titleForPost: "",
      customToolbar: [
        ["bold", "italic", "underline"],
        [{ list: "ordered" }, { list: "bullet" }],
      ],
    };
  },
  methods: {
    formToggle() {
      this.showForm = !this.showForm;
    },
    handleSubmit() {
      if (this.isPost) {
        let post = {
          userId: this.$store.state.user.id,
          title: this.titleForPost,
          message: this.htmlForEditor,
        };
        forumService
          .createPost(post, this.$store.state.currentForum.id)
          .then((response) => {
            if (response.status == 201) {
              //emit to parent component it should refresh from db
              this.$emit("post-added");
              console.log("Post added.");
              this.clearForm();
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        let thread = {
          userId: this.$store.state.user.id,
          message: this.htmlForEditor,
        };
        forumService
          .createThread(thread, this.parentId)
          .then((response) => {
            if (response.status == 201) {
              //emit to parent component it should refresh from db
              this.$emit("thread-added");
              console.log("Thread added.");
              this.clearForm();
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    clearForm() {
      this.htmlForEditor = "";
      this.titleForPost = "";
      this.showForm = false;
    },
  },
};
</script>

<style>
@import "~vue2-editor/dist/vue2-editor.css";
@import "~quill/dist/quill.snow.css";

.ql-editor.ql-blank::before {
  color: gray;
  font-style: normal;
}

.post-to-thread {
  padding: 5px;
}

.post-to-thread-buttons {
  margin-top: 5px;
  background-color: rgb(200, 196, 189);
  color: #181a1b;
  border: none;
  border-radius: 50px;
  padding: 10px 15px;
  font-size: 1rem;
  cursor: pointer;
}

.post-button-content {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
}

.post-to-thread-buttons:hover {
  background-color: rgb(255, 208, 0);
}

.post-to-thread-buttons > .material-symbols-outlined {
  margin-top: -10px;
  margin-bottom: -10px;
}

.ptt-textbox {
  margin-top: 3px;
  height: 3.5rem;
  box-sizing: border-box;
  width: 100%;
  font-size: 1.5rem;
  font-family: roboto;
  border: 1px solid #ccc;
  border-bottom: 0;
  border-radius: 0;
  padding: 10px;
  resize: none;
  color: rgb(200, 196, 189);
  background-color: #181a1b;
}

.ptt-textbox:focus {
  outline: none;
}
</style>