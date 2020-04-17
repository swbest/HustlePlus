export class Student {

    userId: number;
    name: string;
    username: string;
    password: string;
    email: string;
    // accessRightEnum: AccessRightEnum do we need this for front end?
    // resume file?
    description: string;
    avgRating: number;
    isVerified: boolean;
    isSuspended: boolean;
    bankAccountName: string;
    bankAccountNumber: number;

//    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, accessRightEnum?: AccessRightEnum, description?: string) {
    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, 
        description?: string, bankAccountName?: string, bankAccountNumber?: number) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.bankAccountName = bankAccountName;
        this.bankAccountNumber = bankAccountNumber;
    }
}
