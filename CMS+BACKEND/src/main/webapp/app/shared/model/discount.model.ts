import { Moment } from 'moment';
import { IListOfValue } from 'app/shared/model/list-of-value.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IDiscount {
    id?: number;
    name?: string;
    percentage?: number;
    starDate?: Moment;
    endDate?: Moment;
    startHour?: Moment;
    endHour?: Moment;
    status?: boolean;
    days?: IListOfValue[];
    products?: IProduct[];
    clientId?: number;
}

export class Discount implements IDiscount {
    constructor(
        public id?: number,
        public name?: string,
        public percentage?: number,
        public starDate?: Moment,
        public endDate?: Moment,
        public startHour?: Moment,
        public endHour?: Moment,
        public status?: boolean,
        public days?: IListOfValue[],
        public products?: IProduct[],
        public clientId?: number
    ) {
        this.status = this.status || false;
    }
}
