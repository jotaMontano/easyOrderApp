import { IExtraInLine } from 'app/shared/model/extra-in-line.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IExtra {
    id?: number;
    name?: string;
    price?: number;
    status?: boolean;
    extraInLines?: IExtraInLine[];
    products?: IProduct[];
    clientId?: number;
}

export class Extra implements IExtra {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public status?: boolean,
        public extraInLines?: IExtraInLine[],
        public products?: IProduct[],
        public clientId?: number
    ) {
        this.status = this.status || false;
    }
}
