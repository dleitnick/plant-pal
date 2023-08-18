<template>
    <div id="reviews-and-comments">
        <div id="plant-reviews">
            <h3>Reviews</h3>
            <review-comment-form :formType="'review'" @review-submitted="getReviews" />
            <reviews-list :reviews="reviews" />
        </div>
        <div id="plant-comments">
            <h3>Comments and Tips</h3>
            <review-comment-form :formType="'comment'" @comment-submitted="getComments" />
            <comments-list :comments="comments" />
        </div>
    </div>
</template>

<script>
import reviewService from "../../services/ReviewService.js";
import commentService from "../../services/CommentService.js";
import ReviewsList from "./ReviewsList.vue";
import CommentsList from "./CommentsList.vue";
import ReviewCommentForm from "./ReviewCommentForm.vue";

export default {
    components: { ReviewsList, CommentsList, ReviewCommentForm },
    name: "reviews-and-comments",
    data() {
        return {
            reviews: [],
            comments: [],
        };
    },
    created() {
        this.getReviews();
        this.getComments();
    },
    methods: {
        getReviews() {
            reviewService
                .getReviewsByPlant(this.$route.params.id)
                .then((response) => {
                    this.reviews = response.data;
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        getComments() {
            commentService
                .getCommentsByPlant(this.$route.params.id)
                .then((response) => {
                    this.comments = response.data;
                })
                .catch((err) => {
                    console.log(err);
                });
        },
    }
}
</script>

<style>
#reviews-and-comments>#plant-reviews>h3,
#reviews-and-comments>#plant-comments>h3 {
    text-align: center;
    font-size: 40px;
    font-weight: 450;
    margin: 10px 0 20px 0;
    
}

.review-comment-form {
    margin-bottom: 20px;
    text-align: center;
}

</style>