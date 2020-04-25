import { Project } from './project';
import { CompanyReview } from './company-review';
import { StudentReview } from './student-review';

export class Company {

    userId: number;
    name: string;
    username: string;
    password: string;
    email: string;
    // accessRightEnum: AccessRightEnum do we need this for front end?
    description: string;
    avgRating: number;
    isVerified: boolean;
    isSuspended: boolean;
    projects: Project[];
    companyReviews: CompanyReview[];
    studentReviews: StudentReview[];

//    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, accessRightEnum?: AccessRightEnum, description?: string) {
    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, description?: string) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
    }
}
