import { Student } from './student'
import { Project } from './project'

export class Team {

    teamId: number;
    teamName: string;
    numStudents: number;
    students: Student[];
    project: Project;

    constructor(teamId?: number, teamName?: string) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
