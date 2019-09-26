import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderOk } from 'app/shared/model/order-ok.model';
import { OrderOkService } from './order-ok.service';
import { OrderOkComponent } from './order-ok.component';
import { OrderOkDetailComponent } from './order-ok-detail.component';
import { OrderOkUpdateComponent } from './order-ok-update.component';
import { OrderOkDeletePopupComponent } from './order-ok-delete-dialog.component';
import { IOrderOk } from 'app/shared/model/order-ok.model';

@Injectable({ providedIn: 'root' })
export class OrderOkResolve implements Resolve<IOrderOk> {
    constructor(private service: OrderOkService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderOk> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OrderOk>) => response.ok),
                map((orderOk: HttpResponse<OrderOk>) => orderOk.body)
            );
        }
        return of(new OrderOk());
    }
}

export const orderOkRoute: Routes = [
    {
        path: '',
        component: OrderOkComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.orderOk.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OrderOkDetailComponent,
        resolve: {
            orderOk: OrderOkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderOk.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OrderOkUpdateComponent,
        resolve: {
            orderOk: OrderOkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderOk.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OrderOkUpdateComponent,
        resolve: {
            orderOk: OrderOkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderOk.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderOkPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OrderOkDeletePopupComponent,
        resolve: {
            orderOk: OrderOkResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.orderOk.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
