import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExtraInLine } from 'app/shared/model/extra-in-line.model';
import { ExtraInLineService } from './extra-in-line.service';

@Component({
    selector: 'jhi-extra-in-line-delete-dialog',
    templateUrl: './extra-in-line-delete-dialog.component.html'
})
export class ExtraInLineDeleteDialogComponent {
    extraInLine: IExtraInLine;

    constructor(
        protected extraInLineService: ExtraInLineService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.extraInLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'extraInLineListModification',
                content: 'Deleted an extraInLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-extra-in-line-delete-popup',
    template: ''
})
export class ExtraInLineDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ extraInLine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExtraInLineDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.extraInLine = extraInLine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/extra-in-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/extra-in-line', { outlets: { popup: null } }]);
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
