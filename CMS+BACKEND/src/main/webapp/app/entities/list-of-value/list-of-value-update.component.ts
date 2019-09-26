import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from './list-of-value.service';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from 'app/entities/discount';

@Component({
    selector: 'jhi-list-of-value-update',
    templateUrl: './list-of-value-update.component.html'
})
export class ListOfValueUpdateComponent implements OnInit {
    listOfValue: IListOfValue;
    isSaving: boolean;

    discounts: IDiscount[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected listOfValueService: ListOfValueService,
        protected discountService: DiscountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ listOfValue }) => {
            this.listOfValue = listOfValue;
        });
        this.discountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDiscount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDiscount[]>) => response.body)
            )
            .subscribe((res: IDiscount[]) => (this.discounts = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.listOfValue.id !== undefined) {
            this.subscribeToSaveResponse(this.listOfValueService.update(this.listOfValue));
        } else {
            this.subscribeToSaveResponse(this.listOfValueService.create(this.listOfValue));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IListOfValue>>) {
        result.subscribe((res: HttpResponse<IListOfValue>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDiscountById(index: number, item: IDiscount) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
