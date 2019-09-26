import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductByOrder } from 'app/shared/model/product-by-order.model';
import { ProductByOrderService } from './product-by-order.service';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { OrderOkService } from 'app/entities/order-ok';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-product-by-order-update',
    templateUrl: './product-by-order-update.component.html'
})
export class ProductByOrderUpdateComponent implements OnInit {
    productByOrder: IProductByOrder;
    isSaving: boolean;

    orderoks: IOrderOk[];

    products: IProduct[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productByOrderService: ProductByOrderService,
        protected orderOkService: OrderOkService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productByOrder }) => {
            this.productByOrder = productByOrder;
        });
        this.orderOkService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrderOk[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrderOk[]>) => response.body)
            )
            .subscribe((res: IOrderOk[]) => (this.orderoks = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productByOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.productByOrderService.update(this.productByOrder));
        } else {
            this.subscribeToSaveResponse(this.productByOrderService.create(this.productByOrder));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductByOrder>>) {
        result.subscribe((res: HttpResponse<IProductByOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrderOkById(index: number, item: IOrderOk) {
        return item.id;
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
