import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderHistory } from 'app/shared/model/order-history.model';
import { OrderHistoryService } from './order-history.service';
import { OrderHistoryComponent } from './order-history.component';
import { OrderHistoryDetailComponent } from './order-history-detail.component';
import { OrderHistoryUpdateComponent } from './order-history-update.component';
import { OrderHistoryDeletePopupComponent } from './order-history-delete-dialog.component';
import { IOrderHistory } from 'app/shared/model/order-history.model';

@Injectable({ providedIn: 'root' })
export class OrderHistoryResolve implements Resolve<IOrderHistory> {
    constructor(private service: OrderHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OrderHistory>) => response.ok),
                map((orderHistory: HttpResponse<OrderHistory>) => orderHistory.body)
            );
        }
        return of(new OrderHistory());
    }
}

export const orderHistoryRoute: Routes = [
    {
        path: '',
        component: OrderHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.orderHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OrderHistoryDetailComponent,
        resolve: {
            orderHistory: OrderHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OrderHistoryUpdateComponent,
        resolve: {
            orderHistory: OrderHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OrderHistoryUpdateComponent,
        resolve: {
            orderHistory: OrderHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderHistoryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OrderHistoryDeletePopupComponent,
        resolve: {
            orderHistory: OrderHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
