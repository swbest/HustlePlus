import { Skill } from './skill'; 
import { AccessRightEnum } from './access-right-enum.enum';
import { Team } from './team';
import { CompanyReview } from './company-review';
import { StudentReview } from './student-review';
import { Payment } from './payment';
import { Application } from './application';
import { Project } from './project';

export class Student {

    userId: number;
    name: string;
    username: string;
    password: string;
    email: string;
    accessRightEnum: string;
    description: string;
    avgRating: number;
    isVerified: boolean;
    isSuspended: boolean;
    bankAccountName: string;
    bankAccountNumber: number;
    resume: File;
    skills: Skill[];
    teams: Team[];
    companyReviews: CompanyReview[]; // done by this student
    studentReviews: StudentReview[]; // done by this student
    payments: Payment[];
    applications: Application[];
    projects: Project[];

    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, 
        accessRightEnum?: string, description?: string, bankAccountName?: string, 
        bankAccountNumber?: number, resume?: File) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accessRightEnum = accessRightEnum;
        this.description = description;
        this.bankAccountName = bankAccountName;
        this.bankAccountNumber = bankAccountNumber;
        this.resume = resume;
    }
}
