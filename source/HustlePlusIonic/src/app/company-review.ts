import { Project } from './project';
import { Student } from './student';
import { Company } from './company';

export class CompanyReview {

    companyReviewId: number;
    reviewText: string;
    rating: number;
    project: Project;
    student: Student;
    company: Company;

    constructor(companyReviewId?: number, reviewText?: string, rating?: number) {
        this.companyReviewId = companyReviewId;
        this.reviewText = reviewText;
        this.rating = rating;
    }
}
