import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderHistory } from 'app/shared/model/order-history.model';
import { OrderHistoryService } from './order-history.service';

@Component({
    selector: 'jhi-order-history-delete-dialog',
    templateUrl: './order-history-delete-dialog.component.html'
})
export class OrderHistoryDeleteDialogComponent {
    orderHistory: IOrderHistory;

    constructor(
        protected orderHistoryService: OrderHistoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orderHistoryListModification',
                content: 'Deleted an orderHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-history-delete-popup',
    template: ''
})
export class OrderHistoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrderHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.orderHistory = orderHistory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/order-history', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/order-history', { outlets: { popup: null } }]);
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
