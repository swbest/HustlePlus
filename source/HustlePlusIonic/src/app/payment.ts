import { Student } from './student';
import { Milestone } from './milestone';

export class Payment {

    paymentId: number;
    title: string;
    paymentDescription: string;
    isPaid: boolean;
    student: Student;
    milestone: Milestone;

    constructor(paymentId?: number, paymentDescription?:string) {
        this.paymentId = paymentId;
        this.paymentDescription = paymentDescription;
    }

}
