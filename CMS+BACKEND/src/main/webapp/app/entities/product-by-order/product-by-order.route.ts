import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductByOrder } from 'app/shared/model/product-by-order.model';
import { ProductByOrderService } from './product-by-order.service';
import { ProductByOrderComponent } from './product-by-order.component';
import { ProductByOrderDetailComponent } from './product-by-order-detail.component';
import { ProductByOrderUpdateComponent } from './product-by-order-update.component';
import { ProductByOrderDeletePopupComponent } from './product-by-order-delete-dialog.component';
import { IProductByOrder } from 'app/shared/model/product-by-order.model';

@Injectable({ providedIn: 'root' })
export class ProductByOrderResolve implements Resolve<IProductByOrder> {
    constructor(private service: ProductByOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductByOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductByOrder>) => response.ok),
                map((productByOrder: HttpResponse<ProductByOrder>) => productByOrder.body)
            );
        }
        return of(new ProductByOrder());
    }
}

export const productByOrderRoute: Routes = [
    {
        path: '',
        component: ProductByOrderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.productByOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductByOrderDetailComponent,
        resolve: {
            productByOrder: ProductByOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.productByOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductByOrderUpdateComponent,
        resolve: {
            productByOrder: ProductByOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.productByOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductByOrderUpdateComponent,
        resolve: {
            productByOrder: ProductByOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.productByOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productByOrderPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductByOrderDeletePopupComponent,
        resolve: {
            productByOrder: ProductByOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.productByOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
