import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IProduct } from 'app/shared/model/product.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ProductsService } from './products.service';
import Swal from 'sweetalert2';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { IListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from 'app/entities/list-of-value';

@Component({
    selector: 'jhi-products',
    templateUrl: './products.component.html',
    styleUrls: ['../easy-order.scss']
})
export class ProductsComponent implements OnInit, OnDestroy {
    @Input() query: string;
    currentAccount: any;
    products: IProduct[];
    listTypesProduct: IListOfValue[];
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
        protected productService: ProductsService,
        protected listOfValuesService: ListOfValueService,
        protected clientService: ClientService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
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
        this.products = [];
    }

    loadAll() {
        this.productService
            .getProductsByClient(this.client.id, {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProduct[]>) => this.paginateProducts(res.body, res.headers),
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
        this.router.navigate(['/products'], {
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
            '/products',
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
        this.getLstProducts();
        this.registerChangeInProducts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProduct) {
        return item.id;
    }

    registerChangeInProducts() {
        this.eventSubscriber = this.eventManager.subscribe('productListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateProducts(data: IProduct[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.products = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }
    deleteProduct(product: IProduct) {
        Swal({
            title: 'Inactivar producto',
            text: '¿Está seguro que desea inactivar el producto?',
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
                this.confirmDelete(product);
                Swal({
                    text: 'El producto ha sido inactivado.',
                    type: 'success',
                    confirmButtonColor: '#009688',
                    confirmButtonText: 'Continuar'
                });
            }
        });
    }
    confirmDelete(product: IProduct) {
        product.status = false;
        this.productService.update(product).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productListModification',
                content: 'Deleted an product'
            });
        });
    }
    getLstProducts() {
        this.listOfValuesService.getLstProducts().subscribe((lst: HttpResponse<IListOfValue[]>) => {
            this.listTypesProduct = lst.body;
        });
    }
    getTypeProduct(product: IProduct) {
        return this.listTypesProduct.find(type => type.value === product.type).description;
    }
    onSearch() {
        if (this.query === '') {
            this.loadAll();
        } else {
            this.productService
                .getProductsByDescription(this.client.id, this.query, {
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IProduct[]>) => {
                        this.links = this.parseLinks.parse(res.headers.get('link'));
                        this.totalItems = res.headers.get('X-Total-Count');
                        this.products = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }
    onSearchByState(status) {
        this.productService
            .getProductsByStatus(this.client.id, status === '1', {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProduct[]>) => {
                    this.links = this.parseLinks.parse(res.headers.get('link'));
                    this.totalItems = res.headers.get('X-Total-Count');
                    this.products = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
}
