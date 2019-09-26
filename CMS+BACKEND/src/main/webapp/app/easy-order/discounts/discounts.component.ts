import { Component, OnInit, OnDestroy, NgZone } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import Swal from 'sweetalert2';

import { IDiscount } from 'app/shared/model/discount.model';
import { AccountService, JhiTrackerService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DiscountsService } from './discounts.service';
import { ClientService } from 'app/entities/client';
import { IClient } from 'app/shared/model/client.model';

@Component({
    selector: 'jhi-discounts',
    templateUrl: './discounts.component.html',
    styleUrls: ['../easy-order.scss']
})
export class DiscountsComponent implements OnInit, OnDestroy {
    currentAccount: any;
    discounts: IDiscount[];
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
        protected discountService: DiscountsService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected clientService: ClientService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        private trackerService: JhiTrackerService,
        private zone: NgZone
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.discounts = [];
    }

    loadAll() {
        this.discountService
            .findDiscountsByClient(this.client.id, {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IDiscount[]>) => this.paginateDiscounts(res.body, res.headers),
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
        this.router.navigate(['/discounts'], {
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
            '/discounts',
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
        this.trackerService.subscribeDiscount();
        this.trackerService.receiveDiscount().subscribe(activity => {
            this.zone.run(() => {
                this.loadAll();
            });
        });
        this.registerChangeInDiscounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.trackerService.unsubscribeDiscount();
    }

    trackId(index: number, item: IDiscount) {
        return item.id;
    }

    registerChangeInDiscounts() {
        this.eventSubscriber = this.eventManager.subscribe('discountListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateDiscounts(data: IDiscount[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.discounts = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    async getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }

    deleteDiscount(id: number) {
        Swal({
            title: 'Eliminar descuento',
            text: '¿Está seguro que quiere eliminar el descuento?',
            type: 'question',
            showCloseButton: true,
            showCancelButton: true,
            confirmButtonText: 'Eliminar',
            cancelButtonText: 'Cancelar',
            confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d',
            confirmButtonClass: 'confirmButton'
        }).then(result => {
            if (result.value) {
                this.confirmDelete(id);
                Swal({
                    text: 'El descuento ha sido eliminado.',
                    type: 'success',
                    confirmButtonColor: '#009688',
                    confirmButtonText: 'Continuar'
                });
            }
        });
    }

    confirmDelete(id: number) {
        this.discountService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'discountListModification',
                content: 'Deleted discount'
            });
        });
    }

    onSearchByState(status) {
        this.discountService
            .getDiscountsByStatus(this.client.id, status === '1', {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IDiscount[]>) => {
                    this.links = this.parseLinks.parse(res.headers.get('link'));
                    this.totalItems = res.headers.get('X-Total-Count');
                    this.discounts = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
