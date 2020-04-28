import { Project } from './project';
import { Student } from './student';

export class Application {
    
    applicationId: number;
    isApproved: boolean;
    isPending: boolean;
    project: Project;
    student: Student;

    constructor() {
        this.isApproved = false;
        this.isPending = true;
    }
}
