import { IProductByOrder } from 'app/shared/model/product-by-order.model';

export interface IOrderOk {
    id?: number;
    total?: number;
    status?: boolean;
    clientId?: number;
    orders?: IProductByOrder[];
}

export class OrderOk implements IOrderOk {
    constructor(
        public id?: number,
        public total?: number,
        public status?: boolean,
        public clientId?: number,
        public orders?: IProductByOrder[]
    ) {
        this.status = this.status || false;
    }
}
