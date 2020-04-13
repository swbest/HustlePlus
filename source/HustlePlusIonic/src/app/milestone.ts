export class Milestone {

    milestoneId: number;
    title: string;
    description: string;

    constructor(milestoneId?: number, title?: string, description?:string) {
        this.milestoneId = milestoneId;
        this.title = title;
        this.description = description;
    }
}
