import { Project } from './project';
import { Student } from './student';
import { Company } from './company';

export class Review {

    reviewId: number;
    reviewText: string;
    rating: number;
    project: Project;
    student: Student;
    company: Company;

    constructor(reviewId?: number, reviewText?: string, rating?: number) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.rating = rating;
    }
}
