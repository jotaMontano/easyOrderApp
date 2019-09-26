import { IDiscount } from 'app/shared/model/discount.model';

export interface IListOfValue {
    id?: number;
    value?: string;
    description?: string;
    type?: string;
    discounts?: IDiscount[];
}

export class ListOfValue implements IListOfValue {
    constructor(
        public id?: number,
        public value?: string,
        public description?: string,
        public type?: string,
        public discounts?: IDiscount[]
    ) {}
}
