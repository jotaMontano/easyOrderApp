export interface ITop {
    id?: number;
    quantity?: number;
    type?: string;
    productsName?: string;
    productsId?: number;
}

export class Top implements ITop {
    constructor(
        public id?: number,
        public quantity?: number,
        public type?: string,
        public productsName?: string,
        public productsId?: number
    ) {}
}
