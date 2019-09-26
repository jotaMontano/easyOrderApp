import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITop } from 'app/shared/model/top.model';
import { TopService } from './top.service';

@Component({
    selector: 'jhi-top-delete-dialog',
    templateUrl: './top-delete-dialog.component.html'
})
export class TopDeleteDialogComponent {
    top: ITop;

    constructor(protected topService: TopService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.topService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'topListModification',
                content: 'Deleted an top'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-top-delete-popup',
    template: ''
})
export class TopDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ top }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TopDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.top = top;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/top', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/top', { outlets: { popup: null } }]);
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
