import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduct } from 'app/shared/model/product.model';

@Component({
    selector: 'jhi-product-detail',
    templateUrl: './products-detail.component.html'
})
export class ProductsDetailComponent implements OnInit {
    product: IProduct;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
        });
    }

    previousState() {
        window.history.back();
    }
}
