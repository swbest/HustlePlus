export class Review {

    reviewId: number;
    reviewText: string;
    rating: number;

    constructor(reviewId?: number, reviewText?: string, rating?: number) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

}
