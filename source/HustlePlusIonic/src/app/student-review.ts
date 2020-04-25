import { Project } from './project';
import { Student } from './student';
import { Company } from './company';

export class StudentReview {

    studentReviewId: number;
    username: string; // username of Student or Company is reviewing
    reviewText: string;
    rating: number;
    project: Project;
    studentReviewed: Student; // student that left the review
    company: Company; // company that left the review

    constructor(studentReviewId?: number, username?: string, reviewText?: string, rating?: number) {
        this.studentReviewId = studentReviewId;
        this.username = username;
        this.reviewText = reviewText;
        this.rating = rating;
    }
}
