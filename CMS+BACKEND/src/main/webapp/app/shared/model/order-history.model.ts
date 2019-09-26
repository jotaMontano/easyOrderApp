import { Moment } from 'moment';

export interface IOrderHistory {
    id?: number;
    total?: number;
    payDate?: Moment;
    clientId?: number;
    orderId?: number;
}

export class OrderHistory implements IOrderHistory {
    constructor(public id?: number, public total?: number, public payDate?: Moment, public clientId?: number, public orderId?: number) {}
}
