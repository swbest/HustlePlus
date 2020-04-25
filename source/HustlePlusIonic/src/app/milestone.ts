import { Payment } from './payment';
import { Project } from './project';

export class Milestone {

    milestoneId: number;
    title: string;
    description: string;
    payments: Payment[];
    project: Project;

    constructor(milestoneId?: number, title?: string, description?:string) {
        this.milestoneId = milestoneId;
        this.title = title;
        this.description = description;
    }
}
