import { Company } from './company';
import { Student } from './student';
import { Skill } from './skill';
import { Milestone } from './milestone';
import { CompanyReview } from './company-review';
import { StudentReview } from './student-review';
import { Application } from './application';

export class Project {

    projectId: number;
    projectName: string;
    jobValue: number;
    numStudentsRequired: number;
    projectDescription:string;
    startDate: Date;
    endDate: Date;
    isFinished: boolean;
    company: Company;
    skills: Skill[];
    milestones: Milestone[];
    companyReviews: CompanyReview[];
    studentReviews: StudentReview[];
    applications: Application[];
    students: Student[];

    constructor(projectId?: number, projectName?:string, jobValue?:number, numStudentsRequired?:number, 
        projectDescription?:string, startDate?:Date, endDate?:Date) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.jobValue = jobValue;
        this.numStudentsRequired = numStudentsRequired;
        this.projectDescription = projectDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
