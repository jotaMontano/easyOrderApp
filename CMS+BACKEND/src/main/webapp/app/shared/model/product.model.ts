import { IProductByOrder } from 'app/shared/model/product-by-order.model';
import { ITop } from 'app/shared/model/top.model';
import { IExtra } from 'app/shared/model/extra.model';
import { IProduct } from 'app/shared/model/product.model';
import { IDiscount } from 'app/shared/model/discount.model';

export interface IProduct {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    waitAverage?: number;
    urlImage?: string;
    type?: string;
    status?: boolean;
    productByOrders?: IProductByOrder[];
    tops?: ITop[];
    extras?: IExtra[];
    combos?: IProduct[];
    discounts?: IDiscount[];
    categoriesName?: string;
    categoriesId?: number;
    products?: IProduct[];
    clientId?: number;
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public price?: number,
        public waitAverage?: number,
        public urlImage?: string,
        public type?: string,
        public status?: boolean,
        public productByOrders?: IProductByOrder[],
        public tops?: ITop[],
        public extras?: IExtra[],
        public combos?: IProduct[],
        public discounts?: IDiscount[],
        public categoriesName?: string,
        public categoriesId?: number,
        public products?: IProduct[],
        public clientId?: number
    ) {
        this.status = this.status || false;
    }
}
