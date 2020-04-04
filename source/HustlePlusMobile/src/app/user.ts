export class User {

    userId: number;
    name: string;
    username: string;
    password: string;
    email: string;
    // accessRightEnum: AccessRightEnum do we need this for front end?

//    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string, accessRightEnum?: AccessRightEnum, description?: string) {
    constructor(userId?: number, name?: string, username?: string, password?: string, email?: string) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
