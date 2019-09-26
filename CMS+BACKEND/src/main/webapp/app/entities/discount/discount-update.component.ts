import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from './discount.service';
import { IListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from 'app/entities/list-of-value';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-discount-update',
    templateUrl: './discount-update.component.html'
})
export class DiscountUpdateComponent implements OnInit {
    discount: IDiscount;
    isSaving: boolean;

    listofvalues: IListOfValue[];

    products: IProduct[];

    clients: IClient[];
    starDate: string;
    endDate: string;
    startHour: string;
    endHour: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected discountService: DiscountService,
        protected listOfValueService: ListOfValueService,
        protected productService: ProductService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ discount }) => {
            this.discount = discount;
            this.starDate = this.discount.starDate != null ? this.discount.starDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.discount.endDate != null ? this.discount.endDate.format(DATE_TIME_FORMAT) : null;
            this.startHour = this.discount.startHour != null ? this.discount.startHour.format(DATE_TIME_FORMAT) : null;
            this.endHour = this.discount.endHour != null ? this.discount.endHour.format(DATE_TIME_FORMAT) : null;
        });
        this.listOfValueService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IListOfValue[]>) => mayBeOk.ok),
                map((response: HttpResponse<IListOfValue[]>) => response.body)
            )
            .subscribe((res: IListOfValue[]) => (this.listofvalues = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.clientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClient[]>) => response.body)
            )
            .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.discount.starDate = this.starDate != null ? moment(this.starDate, DATE_TIME_FORMAT) : null;
        this.discount.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        this.discount.startHour = this.startHour != null ? moment(this.startHour, DATE_TIME_FORMAT) : null;
        this.discount.endHour = this.endHour != null ? moment(this.endHour, DATE_TIME_FORMAT) : null;
        if (this.discount.id !== undefined) {
            this.subscribeToSaveResponse(this.discountService.update(this.discount));
        } else {
            this.subscribeToSaveResponse(this.discountService.create(this.discount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscount>>) {
        result.subscribe((res: HttpResponse<IDiscount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackListOfValueById(index: number, item: IListOfValue) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackClientById(index: number, item: IClient) {
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
