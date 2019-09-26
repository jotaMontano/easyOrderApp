import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Product } from 'app/shared/model/product.model';
import { ProductsService } from './products.service';
import { ProductsComponent } from './products.component';
import { ProductsDetailComponent } from './products-detail.component';
import { ProductsUpdateComponent } from './products-update.component';
import { ProductsDeletePopupComponent } from './products-delete-dialog.component';
import { IProduct } from 'app/shared/model/product.model';

@Injectable({ providedIn: 'root' })
export class ProductsResolve implements Resolve<IProduct> {
    constructor(private service: ProductsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProduct> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Product>) => response.ok),
                map((product: HttpResponse<Product>) => product.body)
            );
        }
        return of(new Product());
    }
}

export const productsRoute: Routes = [
    {
        path: '',
        component: ProductsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.product.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductsDetailComponent,
        resolve: {
            product: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.product.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductsUpdateComponent,
        resolve: {
            product: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.product.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductsUpdateComponent,
        resolve: {
            product: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.product.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductsDeletePopupComponent,
        resolve: {
            product: ProductsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.product.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
