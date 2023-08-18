<template>
  <form @submit.prevent="handleSubmit" class="review-comment-form">
    <button id="add-review-comment" @click.prevent="formToggle" v-if="!showSubmitButton()">
      Add {{ formType }}
    </button>
    <div v-if="formType === 'review' && showReviewForm">
      <div class="stars">
        <span v-for="star in 5" :key="star" :class="{ filled: review.rating >= star }" @click="setRating(star)">
          <span class="material-symbols-outlined" v-if="review.rating >= star">
            star
          </span>
          <span class="material-symbols-outlined" v-else>
            star
          </span>
        </span>
      </div>
      <textarea class="rc-textbox" v-model="review.reviewText" placeholder="Write your review..." required></textarea>
      <p v-if="addRating">Please select a star rating.</p>
    </div>
    <div v-if="formType === 'comment' && showCommentForm">
      <div id="ghost-stars">
        <span class="material-symbols-outlined">
          star
        </span></div>
      <textarea class="rc-textbox" v-model="comment.commentText" placeholder="Write your comment..." required></textarea>
    </div>
    <button id="submit-review-comment" type="submit" v-if="showSubmitButton()">Submit</button>
  </form>
</template>

<script>
import reviewService from "../../services/ReviewService.js";
import commentService from "../../services/CommentService.js";

export default {
  props: {
    formType: {
      type: String,
      required: true,
      validator: value => ['review', 'comment'].includes(value),
    },
  },
  data() {
    return {
      review: {
        plantId: this.$route.params.id,
        reviewText: '',
        location: '',
        username: this.$store.state.user.username,
        rating: 0
      },
      comment: {
        plantId: this.$route.params.id,
        commentText: '',
        location: '',
        username: this.$store.state.user.username
      },
      showReviewForm: false,
      showCommentForm: false,
      addRating: false,
    };
  },
  methods: {
    handleSubmit() {
      this.addRating = false;
      if (this.formType === 'review') {
        if (this.review.rating !== 0) {
          reviewService.postReview(this.review)
            .then((response) => {
              if (response.data > 0) {
                this.$emit('review-submitted');
                console.log("Review added");
              }
            })
            .catch((err) => {
              console.log(err);
            });
        } else {
          this.addRating = true;
        }

      } else {
        commentService.postComment(this.comment)
          .then((response) => {
            if (response.data > 0) {
              this.$emit('comment-submitted');
              console.log("Comment added");
            }
          })
          .catch((err) => {
            console.log(err);
          });
      }
      if (!this.addRating) {
        this.clearForm();
      }

    },
    setRating(star) {
      this.review.rating = star;
    },
    clearForm() {
      this.review.reviewText = '';
      this.review.rating = 0;
      this.comment.commentText = '';
      this.showReviewForm = false;
      this.showCommentForm = false;
    },
    formToggle() {
      if (this.review.location === '') {
        this.setLocation();
      }
      if (this.formType === 'review') {
        this.showReviewForm = !this.showReviewForm;
      } else if (this.formType === 'comment') {
        this.showCommentForm = !this.showCommentForm;
      }
    },
    showSubmitButton() {
      return (this.formType === 'review' && this.showReviewForm) || (this.formType === 'comment' && this.showCommentForm);
    },
    setLocation() {
      console.log("setting location");
      this.review.location = this.$store.state.location;
      console.log(this.$store.state.location);
      this.comment.location = this.$store.state.location;
    }
  },
};
</script>

<style>


.stars,
#ghost-stars {
  margin-top: -16px;
}

.stars .material-symbols-outlined {
  cursor: pointer;
}

.filled .material-symbols-outlined {
  color: rgb(255, 208, 0);
  font-variation-settings:
    'FILL' 1,
    'wght' 400,
    'GRAD' 0,
    'opsz' 48
}

.material-symbols-outlined {
  font-variation-settings:
    'FILL' 0,
    'wght' 400,
    'GRAD' 0,
    'opsz' 48
}

#ghost-stars > .material-symbols-outlined {
  color: rgba(255, 255, 255, 0);
}

.rc-textbox {
  margin-top: 3px;
  height: 100px;
  width: 93.5%;
  font-size: 20px;
  font-family: roboto;
  border: 2px solid #ccc;
  border-radius: 10px;
  padding: 10px;
  resize: none;
  color: rgb(200, 196, 189);
  background-color: #181A1B;
  border-radius: 6px;
}

.rc-textbox:focus {
  outline: none
}

#submit-review-comment {
  border-radius: 10px;
  padding: 10px;
  width: 75px;
  font-size: 15px;
  background-color: #ffffff;
  color: black;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  transform: scale(1);
  transition: transform 0.3s ease-in-out;
}

#submit-review-comment:hover{
  background-color: rgb(255, 208, 0);
  transform: scale(1.05);
}

#add-review-comment{
 margin: 20px auto;
  padding: 10px 20px;
  height: 40px;
  background-color: #ffffff;
  color: black;
  border: none;
  border-radius: 5px;
  font-size: 20px;
  cursor: pointer;
  transition: transform 0.3s ease-in-out;
}

#add-review-comment:hover{
  background-color: rgb(255, 208, 0);
  transform: scale(1.05);
}
</style>