import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import Swal from 'sweetalert2';

import { ICategory } from 'app/shared/model/category.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CategoriesService } from './categories.service';
import { ClientService } from 'app/entities/client';
import { IClient } from 'app/shared/model/client.model';

@Component({
    selector: 'jhi-categories',
    templateUrl: './categories.component.html',
    styleUrls: ['../easy-order.scss']
})
export class CategoriesComponent implements OnInit, OnDestroy {
    currentAccount: any;
    categories: ICategory[];
    client: IClient;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        protected categoryService: CategoriesService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.categories = [];
    }

    loadAll() {
        this.categoryService
            .findCategoryByClient(this.client.id, {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICategory[]>) => this.paginateCategories(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/categories'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/categories',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.getClient(account.id, client => {
                this.client = client;
                this.loadAll();
            });
        });
        this.registerChangeInCategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICategory) {
        return item.id;
    }

    registerChangeInCategories() {
        this.eventSubscriber = this.eventManager.subscribe('categoryListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateCategories(data: ICategory[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.categories = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    async getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }

    deleteCategory(category: ICategory) {
        Swal({
            title: 'Inactivar categoría',
            text: '¿Está seguro que quiere inhabilitar la categoría?',
            type: 'question',
            showCloseButton: true,
            showCancelButton: true,
            confirmButtonText: 'Inhabilitar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonClass: 'confirmButton'
        }).then(result => {
            if (result.value) {
                this.confirmDelete(category);
                Swal({
                    text: 'La categoría ha sido deshabilitada.',
                    type: 'success',
                    confirmButtonColor: '#009688',
                    confirmButtonText: 'Continuar'
                });
            }
        });
    }

    confirmDelete(category: ICategory) {
        category.status = false;
        this.categoryService.update(category).subscribe(response => {
            this.eventManager.broadcast({
                name: 'categoryListModification',
                content: 'Deleted category'
            });
        });
    }

    onSearchByState(status) {
        this.categoryService
            .getCategoriesByStatus(this.client.id, status === '1', {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICategory[]>) => {
                    this.links = this.parseLinks.parse(res.headers.get('link'));
                    this.totalItems = res.headers.get('X-Total-Count');
                    this.categories = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
