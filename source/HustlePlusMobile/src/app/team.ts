export class Team {

    teamId: number;
    teamName: string;
    numStudents: number;

    constructor(teamId?: number, teamName?: string, numStudents?: number) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.numStudents = numStudents;
    }

}
