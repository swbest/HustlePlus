import { Skill } from './skill'; 
import { AccessRightEnum } from './access-right-enum.enum';

export class Student {

    userId: number;
    name: string;
    username: string;
    password: string;
    email: string;
    accessRightEnum: AccessRightEnum;
    description: string;
    avgRating: number;
    isVerified: boolean;
    isSuspended: boolean;
    bankAccountName: string;
    bankAccountNumber: number;
    skills: Skill[];
    resume: File;

    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, 
        accessRightEnum?: AccessRightEnum, description?: string, bankAccountName?: string, 
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
