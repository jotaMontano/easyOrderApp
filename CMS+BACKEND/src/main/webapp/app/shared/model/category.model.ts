import { IProduct } from 'app/shared/model/product.model';

export interface ICategory {
    id?: number;
    description?: string;
    name?: string;
    status?: boolean;
    products?: IProduct[];
    clientId?: number;
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public description?: string,
        public name?: string,
        public status?: boolean,
        public products?: IProduct[],
        public clientId?: number
    ) {
        this.status = this.status || false;
    }
}
