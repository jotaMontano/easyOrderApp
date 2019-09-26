import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITop } from 'app/shared/model/top.model';

@Component({
    selector: 'jhi-top-detail',
    templateUrl: './top-detail.component.html'
})
export class TopDetailComponent implements OnInit {
    top: ITop;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ top }) => {
            this.top = top;
        });
    }

    previousState() {
        window.history.back();
    }
}
