import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IExtra } from 'app/shared/model/extra.model';
import { ExtrasService } from './extras.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { AccountService } from 'app/core';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-extra-update',
    templateUrl: './extras-update.component.html',
    styleUrls: ['../easy-order.scss']
})
export class ExtrasUpdateComponent implements OnInit {
    extra: IExtra;
    isSaving: boolean;

    client: IClient;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected extraService: ExtrasService,
        protected accountService: AccountService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        console.log(this.extra);
        this.isSaving = false;
        this.accountService.identity().then(account => {
            this.getClient(account.id, client => {
                this.client = client;
            });
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.extra.clientId = this.client.id;
        if (this.extra.id !== undefined) {
            this.subscribeToSaveResponse(this.extraService.update(this.extra));
        } else {
            this.extra.status = true;
            this.subscribeToSaveResponse(this.extraService.create(this.extra));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtra>>) {
        result.subscribe((res: HttpResponse<IExtra>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.eventManager.broadcast({
            name: 'extraListModification',
            content: 'Update an extra'
        });
        this.activeModal.dismiss(true);
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    async getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }
}

@Component({
    selector: 'jhi-extras-update-popup',
    template: ''
})
export class ExtrasUpdatePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ extra }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExtrasUpdateComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.extra = extra;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/extras', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/extras', { outlets: { popup: null } }]);
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
