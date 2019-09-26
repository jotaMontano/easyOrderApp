import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderOk } from 'app/shared/model/order-ok.model';

@Component({
    selector: 'jhi-order-ok-detail',
    templateUrl: './order-ok-detail.component.html'
})
export class OrderOkDetailComponent implements OnInit {
    orderOk: IOrderOk;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderOk }) => {
            this.orderOk = orderOk;
        });
    }

    previousState() {
        window.history.back();
    }
}
