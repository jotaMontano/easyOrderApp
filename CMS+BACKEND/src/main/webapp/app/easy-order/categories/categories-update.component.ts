import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ICategory } from 'app/shared/model/category.model';
import { CategoriesService } from './categories.service';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ClientService } from 'app/entities/client';

@Component({
    selector: 'jhi-categories-update',
    templateUrl: './categories-update.component.html',
    styleUrls: ['../easy-order.scss']
})
export class CategoriesUpdateComponent implements OnInit {
    category: ICategory;
    isSaving: boolean;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected categoryService: CategoriesService,
        protected accountService: AccountService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.accountService.identity().then(account => {
            this.clientService.findByUserId(account.id).subscribe(response => {
                this.category.clientId = response.body.id;
            });
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.category.id !== undefined) {
            this.subscribeToSaveResponse(this.categoryService.update(this.category));
        } else {
            this.category.status = true;
            this.subscribeToSaveResponse(this.categoryService.create(this.category));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>) {
        result.subscribe((res: HttpResponse<ICategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.eventManager.broadcast({
            name: 'categoryListModification',
            content: 'Update a category'
        });
        this.activeModal.dismiss(true);
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

@Component({
    selector: 'jhi-categories-update-popup',
    template: ''
})
export class CategoriesUpdatePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ category }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CategoriesUpdateComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.category = category;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/categories', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/categories', { outlets: { popup: null } }]);
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
