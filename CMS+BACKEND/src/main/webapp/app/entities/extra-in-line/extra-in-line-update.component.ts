import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExtraInLine } from 'app/shared/model/extra-in-line.model';
import { ExtraInLineService } from './extra-in-line.service';
import { IExtra } from 'app/shared/model/extra.model';
import { ExtraService } from 'app/entities/extra';
import { IProductByOrder } from 'app/shared/model/product-by-order.model';
import { ProductByOrderService } from 'app/entities/product-by-order';

@Component({
    selector: 'jhi-extra-in-line-update',
    templateUrl: './extra-in-line-update.component.html'
})
export class ExtraInLineUpdateComponent implements OnInit {
    extraInLine: IExtraInLine;
    isSaving: boolean;

    extras: IExtra[];

    productbyorders: IProductByOrder[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected extraInLineService: ExtraInLineService,
        protected extraService: ExtraService,
        protected productByOrderService: ProductByOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ extraInLine }) => {
            this.extraInLine = extraInLine;
        });
        this.extraService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IExtra[]>) => mayBeOk.ok),
                map((response: HttpResponse<IExtra[]>) => response.body)
            )
            .subscribe((res: IExtra[]) => (this.extras = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productByOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductByOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductByOrder[]>) => response.body)
            )
            .subscribe((res: IProductByOrder[]) => (this.productbyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.extraInLine.id !== undefined) {
            this.subscribeToSaveResponse(this.extraInLineService.update(this.extraInLine));
        } else {
            this.subscribeToSaveResponse(this.extraInLineService.create(this.extraInLine));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtraInLine>>) {
        result.subscribe((res: HttpResponse<IExtraInLine>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackExtraById(index: number, item: IExtra) {
        return item.id;
    }

    trackProductByOrderById(index: number, item: IProductByOrder) {
        return item.id;
    }
}
