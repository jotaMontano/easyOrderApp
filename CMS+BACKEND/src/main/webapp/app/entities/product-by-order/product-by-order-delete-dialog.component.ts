import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductByOrder } from 'app/shared/model/product-by-order.model';
import { ProductByOrderService } from './product-by-order.service';

@Component({
    selector: 'jhi-product-by-order-delete-dialog',
    templateUrl: './product-by-order-delete-dialog.component.html'
})
export class ProductByOrderDeleteDialogComponent {
    productByOrder: IProductByOrder;

    constructor(
        protected productByOrderService: ProductByOrderService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productByOrderService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productByOrderListModification',
                content: 'Deleted an productByOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-by-order-delete-popup',
    template: ''
})
export class ProductByOrderDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productByOrder }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductByOrderDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productByOrder = productByOrder;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/product-by-order', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/product-by-order', { outlets: { popup: null } }]);
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
