import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtra } from 'app/shared/model/extra.model';

@Component({
    selector: 'jhi-extra-detail',
    templateUrl: './extra-detail.component.html'
})
export class ExtraDetailComponent implements OnInit {
    extra: IExtra;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ extra }) => {
            this.extra = extra;
        });
    }

    previousState() {
        window.history.back();
    }
}
