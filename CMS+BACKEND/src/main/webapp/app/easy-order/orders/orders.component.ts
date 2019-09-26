import { Component, NgZone, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { IOrderOk } from 'app/shared/model/order-ok.model';
import { OrderOkService } from 'app/entities/order-ok';
import { IProductByOrder } from 'app/shared/model/product-by-order.model';
import { IProduct } from 'app/shared/model/product.model';
import { IExtraInLine } from 'app/shared/model/extra-in-line.model';
import { AccountService, JhiTrackerService } from 'app/core';
import { ClientService } from 'app/entities/client';
import { IClient } from 'app/shared/model/client.model';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
@Component({
    selector: 'jhi-orders',
    templateUrl: './orders.component.html',
    styles: []
})
export class OrdersComponent implements OnInit, OnDestroy {
    listOrders = [];
    orders: IOrderOk[];
    productsByOrder: IProductByOrder[];
    extrasInLine: IExtraInLine[];
    currentAccount: any;
    client: IClient;
    filterOrder = 'Pendiente';
    eventSubscriber: Subscription;

    constructor(
        protected service: OrderOkService,
        protected accountService: AccountService,
        protected clientService: ClientService,
        protected eventManager: JhiEventManager,
        private trackerService: JhiTrackerService,
        private zone: NgZone
    ) {
        this.orders = [];
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.getClient(account.id, client => {
                this.client = client;
                this.loadOrders(this.filterOrder);
            });
        });
        this.trackerService.subscribeOrder();
        this.trackerService.receiveOrder().subscribe(activity => {
            this.zone.run(() => {
                this.loadOrders('Pendiente');
                this.registerChangeInOrderOks();
            });
        });
        this.registerChangeInOrderOks();
    }
    async getClient(id, callback) {
        return this.clientService.findByUserId(id).subscribe(rest => {
            callback(rest.body);
        });
    }

    registerChangeInOrderOks() {
        this.eventSubscriber = this.eventManager.subscribe('orderOkListModification', response => this.loadOrders('Pendiente'));
    }

    loadOrders(filter: string) {
        this.listOrders = [];
        this.service.getOrders(this.client.id, filter).subscribe((res: HttpResponse<IOrderOk[]>) => {
            this.orders = res.body;
            for (let i = 0; i < this.orders.length; i++) {
                const products = [];
                const productByOrders: IProductByOrder[] = [];
                const prices = [];
                const comments = [];
                const extras = [];
                this.service.getOrderLine(this.orders[i].id).subscribe((resOrder: HttpResponse<IProductByOrder[]>) => {
                    this.productsByOrder = resOrder.body;
                    for (let j = 0; j < this.productsByOrder.length; j++) {
                        const quantity = this.productsByOrder[j].quantity;
                        const comment = this.productsByOrder[j].comment;
                        const productByOrder = this.productsByOrder[j];
                        this.service.getProductByOrder(this.productsByOrder[j].productsId).subscribe((resP: HttpResponse<IProduct>) => {
                            const product: IProduct = resP.body;
                            products.push(' (' + quantity + ') ' + product.name);
                            productByOrders.push(productByOrder);
                            prices.push('₡ ' + product.price.toLocaleString());
                            if (comment !== '') {
                                comments.push(comment);
                            }
                        });
                        this.service.getExtraLine(this.productsByOrder[j].id).subscribe(value => {
                            this.extrasInLine = value.body;
                            for (let k = 0; k < this.extrasInLine.length; k++) {
                                this.service
                                    .getExtraByOrder(this.extrasInLine[k].extraId)
                                    .subscribe(valueExtraByOrder => extras.push(valueExtraByOrder.body.name));
                            }
                        });
                        if (j === this.productsByOrder.length - 1) {
                            const order1 = {
                                id: this.orders[i].id,
                                order: products,
                                price: prices,
                                extra: extras,
                                comment: comments,
                                productByOrder: productByOrders,
                                total: '₡ ' + this.orders[i].total.toLocaleString(),
                                state: this.orders[i].status ? 'Entregado' : 'Pendiente'
                            };
                            this.listOrders.push(order1);
                        }
                    }
                });
            }
        });
    }
    //  For confirm action On Delete
    onDeleteConfirm(event) {
        if (window.confirm('Are you sure you want to delete?')) {
            event.confirm.resolve();
        } else {
            event.confirm.reject();
        }
    }
    trackId(index: number, item) {
        return item.id;
    }
    edit(order, filter) {
        const orderUpdate = this.orders.find(value => value.id === order.id);
        orderUpdate.status = true;
        this.service.update(orderUpdate).subscribe(() => {
            this.loadOrders(filter);
        });
    }
    filter(state) {
        this.loadOrders(state);
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.trackerService.unsubscribeOrder();
    }
}
