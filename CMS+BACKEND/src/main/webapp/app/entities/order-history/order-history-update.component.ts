import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrderHistory } from 'app/shared/model/order-history.model';
import { OrderHistoryService } from './order-history.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { OrderOkService } from 'app/entities/order-ok';

@Component({
    selector: 'jhi-order-history-update',
    templateUrl: './order-history-update.component.html'
})
export class OrderHistoryUpdateComponent implements OnInit {
    orderHistory: IOrderHistory;
    isSaving: boolean;

    clients: IClient[];

    orders: IOrderOk[];
    payDate: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected orderHistoryService: OrderHistoryService,
        protected clientService: ClientService,
        protected orderOkService: OrderOkService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderHistory }) => {
            this.orderHistory = orderHistory;
            this.payDate = this.orderHistory.payDate != null ? this.orderHistory.payDate.format(DATE_TIME_FORMAT) : null;
        });
        this.clientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClient[]>) => response.body)
            )
            .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.orderOkService
            .query({ filter: 'orderhistory-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IOrderOk[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrderOk[]>) => response.body)
            )
            .subscribe(
                (res: IOrderOk[]) => {
                    if (!this.orderHistory.orderId) {
                        this.orders = res;
                    } else {
                        this.orderOkService
                            .find(this.orderHistory.orderId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IOrderOk>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IOrderOk>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IOrderOk) => (this.orders = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.orderHistory.payDate = this.payDate != null ? moment(this.payDate, DATE_TIME_FORMAT) : null;
        if (this.orderHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.orderHistoryService.update(this.orderHistory));
        } else {
            this.subscribeToSaveResponse(this.orderHistoryService.create(this.orderHistory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderHistory>>) {
        result.subscribe((res: HttpResponse<IOrderHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackClientById(index: number, item: IClient) {
        return item.id;
    }

    trackOrderOkById(index: number, item: IOrderOk) {
        return item.id;
    }
}
