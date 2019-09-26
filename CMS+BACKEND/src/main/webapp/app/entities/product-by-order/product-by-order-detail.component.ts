import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductByOrder } from 'app/shared/model/product-by-order.model';

@Component({
    selector: 'jhi-product-by-order-detail',
    templateUrl: './product-by-order-detail.component.html'
})
export class ProductByOrderDetailComponent implements OnInit {
    productByOrder: IProductByOrder;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productByOrder }) => {
            this.productByOrder = productByOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
