import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtraInLine } from 'app/shared/model/extra-in-line.model';

@Component({
    selector: 'jhi-extra-in-line-detail',
    templateUrl: './extra-in-line-detail.component.html'
})
export class ExtraInLineDetailComponent implements OnInit {
    extraInLine: IExtraInLine;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ extraInLine }) => {
            this.extraInLine = extraInLine;
        });
    }

    previousState() {
        window.history.back();
    }
}
