import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListOfValue } from 'app/shared/model/list-of-value.model';

@Component({
    selector: 'jhi-list-of-value-detail',
    templateUrl: './list-of-value-detail.component.html'
})
export class ListOfValueDetailComponent implements OnInit {
    listOfValue: IListOfValue;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ listOfValue }) => {
            this.listOfValue = listOfValue;
        });
    }

    previousState() {
        window.history.back();
    }
}
