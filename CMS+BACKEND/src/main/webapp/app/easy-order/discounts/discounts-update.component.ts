import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT, TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountsService } from './discounts.service';
import { IListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from 'app/entities/list-of-value';
import { IProduct } from 'app/shared/model/product.model';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientService } from 'app/entities/client';
import { IClient } from 'app/shared/model/client.model';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-discounts-update',
    templateUrl: './discounts-update.component.html',
    styleUrls: ['../easy-order.scss']
})
export class DiscountsUpdateComponent implements OnInit {
    currentAccount: any;
    discount: IDiscount;
    isSaving: boolean;

    listofvalues: IListOfValue[];
    clients: IClient[];

    products: IProduct[];
    starDate: string;
    endDate: string;
    startHour: string;
    endHour: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected discountService: DiscountsService,
        protected listOfValueService: ListOfValueService,
        protected accountService: AccountService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.clientService.findByUserId(this.currentAccount.id).subscribe(response => {
                this.discount.clientId = response.body.id;
            });
        });
        console.log(this.discount);

        this.starDate = this.discount.starDate != null ? this.discount.starDate.format(DATE_FORMAT) : null;
        this.endDate = this.discount.endDate != null ? this.discount.endDate.format(DATE_FORMAT) : null;
        this.startHour = this.discount.startHour != null ? this.discount.startHour.format(TIME_FORMAT) : null;
        this.endHour = this.discount.endHour != null ? this.discount.endHour.format(TIME_FORMAT) : null;
        // });
        this.listOfValueService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IListOfValue[]>) => mayBeOk.ok),
                map((response: HttpResponse<IListOfValue[]>) => response.body)
            )
            .subscribe(
                (res: IListOfValue[]) => (this.listofvalues = res.filter(value => value.type === 'LST_DAY')),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.clientService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClient[]>) => response.body)
            )
            .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.discount.starDate = this.starDate != null ? moment(this.starDate, DATE_FORMAT) : null;
        this.discount.endDate = this.endDate != null ? moment(this.endDate, DATE_FORMAT) : null;
        this.discount.startHour = this.startHour != null ? moment(this.startHour, TIME_FORMAT) : null;
        this.discount.endHour = this.endHour != null ? moment(this.endHour, TIME_FORMAT) : null;
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
        this.eventManager.broadcast({
            name: 'discountListModification',
            content: 'Deleted an discount'
        });
        this.activeModal.dismiss(true);
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

@Component({
    selector: 'jhi-discount-update-popup',
    template: ''
})
export class DiscountsUpdatePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ discount }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DiscountsUpdateComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.discount = discount;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/discounts', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/discounts', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
