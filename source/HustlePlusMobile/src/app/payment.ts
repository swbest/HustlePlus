export class Payment {

    paymentId: number;
    title: string;
    paymentDescription: string;
    isPaid: boolean;

    constructor(paymentId?: number, paymentDescription?:string) {
        this.paymentId = paymentId;
        this.paymentDescription = paymentDescription;
    }

}
