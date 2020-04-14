export class Project {

    projectId: number;
    projectName: string;
    jobValue: number;
    numStudentsRequired: number;
    projectDescription:string;
    startDate: Date;
    endDate: Date;
    isFinished: boolean;

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
