import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import Swal from 'sweetalert2';

import { IExtra } from 'app/shared/model/extra.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ExtrasService } from './extras.service';
import { ClientService } from 'app/entities/client';
import { IClient } from 'app/shared/model/client.model';

@Component({
    selector: 'jhi-extra',
    templateUrl: './extras.component.html',
    styleUrls: ['../easy-order.scss']
})
export class ExtrasComponent implements OnInit, OnDestroy {
    currentAccount: any;
    extras: IExtra[];
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
        protected extraService: ExtrasService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected clientService: ClientService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.extras = [];
    }

    loadAll() {
        this.extraService
            .findExtrasByClient(this.client.id, {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IExtra[]>) => this.paginateExtras(res.body, res.headers),
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
        this.router.navigate(['/extras'], {
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
            '/extras',
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
        this.registerChangeInExtras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExtra) {
        return item.id;
    }

    registerChangeInExtras() {
        this.eventSubscriber = this.eventManager.subscribe('extraListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateExtras(data: IExtra[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.extras = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    async getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }

    deleteExtra(extra: IExtra) {
        Swal({
            title: 'Deshabilitar extra',
            text: '¿Está seguro que quiere deshabilitar el extra?',
            type: 'question',
            showCloseButton: true,
            showCancelButton: true,
            confirmButtonText: 'Deshabilitar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonClass: 'confirmButton'
        }).then(result => {
            if (result.value) {
                this.confirmDelete(extra);
                Swal({
                    text: 'El extra ha sido deshabilitado.',
                    type: 'success',
                    confirmButtonColor: '#009688',
                    confirmButtonText: 'Continuar'
                });
            }
        });
    }

    confirmDelete(extra: IExtra) {
        extra.status = false;
        this.extraService.update(extra).subscribe(response => {
            this.eventManager.broadcast({
                name: 'extraListModification',
                content: 'Deleted extra'
            });
        });
    }

    onSearchByState(status) {
        this.extraService
            .getExtrasByStatus(this.client.id, status === '1', {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IExtra[]>) => {
                    this.links = this.parseLinks.parse(res.headers.get('link'));
                    this.totalItems = res.headers.get('X-Total-Count');
                    this.extras = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
