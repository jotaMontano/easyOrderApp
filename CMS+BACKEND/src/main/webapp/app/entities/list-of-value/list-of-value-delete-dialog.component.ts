import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from './list-of-value.service';

@Component({
    selector: 'jhi-list-of-value-delete-dialog',
    templateUrl: './list-of-value-delete-dialog.component.html'
})
export class ListOfValueDeleteDialogComponent {
    listOfValue: IListOfValue;

    constructor(
        protected listOfValueService: ListOfValueService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.listOfValueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'listOfValueListModification',
                content: 'Deleted an listOfValue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-list-of-value-delete-popup',
    template: ''
})
export class ListOfValueDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ listOfValue }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ListOfValueDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.listOfValue = listOfValue;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/list-of-value', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/list-of-value', { outlets: { popup: null } }]);
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
