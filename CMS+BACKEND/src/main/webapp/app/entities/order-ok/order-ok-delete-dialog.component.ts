import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderOk } from 'app/shared/model/order-ok.model';
import { OrderOkService } from './order-ok.service';

@Component({
    selector: 'jhi-order-ok-delete-dialog',
    templateUrl: './order-ok-delete-dialog.component.html'
})
export class OrderOkDeleteDialogComponent {
    orderOk: IOrderOk;

    constructor(protected orderOkService: OrderOkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderOkService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orderOkListModification',
                content: 'Deleted an orderOk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-ok-delete-popup',
    template: ''
})
export class OrderOkDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderOk }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrderOkDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.orderOk = orderOk;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/order-ok', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/order-ok', { outlets: { popup: null } }]);
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
