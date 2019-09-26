import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { OrderOkService } from './order-ok.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-order-ok-update',
    templateUrl: './order-ok-update.component.html'
})
export class OrderOkUpdateComponent implements OnInit {
    orderOk: IOrderOk;
    isSaving: boolean;

    clients: IClient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected orderOkService: OrderOkService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderOk }) => {
            this.orderOk = orderOk;
        });
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
        if (this.orderOk.id !== undefined) {
            this.subscribeToSaveResponse(this.orderOkService.update(this.orderOk));
        } else {
            this.subscribeToSaveResponse(this.orderOkService.create(this.orderOk));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderOk>>) {
        result.subscribe((res: HttpResponse<IOrderOk>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
