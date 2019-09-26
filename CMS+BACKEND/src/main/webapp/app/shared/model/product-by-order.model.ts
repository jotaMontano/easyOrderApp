import { IExtraInLine } from 'app/shared/model/extra-in-line.model';

export interface IProductByOrder {
    id?: number;
    quantity?: number;
    comment?: string;
    status?: boolean;
    orderOkId?: number;
    extraInLines?: IExtraInLine[];
    productsName?: string;
    productsId?: number;
}

export class ProductByOrder implements IProductByOrder {
    constructor(
        public id?: number,
        public quantity?: number,
        public comment?: string,
        public status?: boolean,
        public orderOkId?: number,
        public extraInLines?: IExtraInLine[],
        public productsName?: string,
        public productsId?: number
    ) {
        this.status = this.status || false;
    }
}
