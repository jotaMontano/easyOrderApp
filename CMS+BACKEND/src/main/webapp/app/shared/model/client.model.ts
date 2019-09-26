import { IProduct } from 'app/shared/model/product.model';
import { ICategory } from 'app/shared/model/category.model';
import { IExtra } from 'app/shared/model/extra.model';
import { IDiscount } from 'app/shared/model/discount.model';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { IOrderHistory } from 'app/shared/model/order-history.model';

export interface IClient {
    id?: number;
    email?: string;
    name?: string;
    userId?: number;
    products?: IProduct[];
    categories?: ICategory[];
    extras?: IExtra[];
    discounts?: IDiscount[];
    orders?: IOrderOk[];
    orderHistories?: IOrderHistory[];
}

export class Client implements IClient {
    constructor(
        public id?: number,
        public email?: string,
        public name?: string,
        public userId?: number,
        public products?: IProduct[],
        public categories?: ICategory[],
        public extras?: IExtra[],
        public discounts?: IDiscount[],
        public orders?: IOrderOk[],
        public orderHistories?: IOrderHistory[]
    ) {}
}
