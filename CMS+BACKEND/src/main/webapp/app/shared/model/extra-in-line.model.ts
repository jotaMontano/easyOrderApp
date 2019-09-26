export interface IExtraInLine {
    id?: number;
    extraId?: number;
    productByOrderId?: number;
}

export class ExtraInLine implements IExtraInLine {
    constructor(public id?: number, public extraId?: number, public productByOrderId?: number) {}
}
