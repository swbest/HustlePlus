import { Student } from './student';
import { Project } from './project';

export class Skill {
    skillId: number;
    title: string;
    students: Student[];
    projects: Project[];

    constructor(skillId?: number, title?: string) {
        this.skillId = skillId;
        this.title = title;
    }
}
