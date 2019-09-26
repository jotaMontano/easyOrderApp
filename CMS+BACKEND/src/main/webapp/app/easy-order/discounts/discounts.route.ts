import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Discount } from 'app/shared/model/discount.model';
import { DiscountsService } from './discounts.service';
import { DiscountsComponent } from './discounts.component';
import { DiscountsUpdatePopupComponent } from './discounts-update.component';
import { IDiscount } from 'app/shared/model/discount.model';

@Injectable({ providedIn: 'root' })
export class DiscountResolve implements Resolve<IDiscount> {
    constructor(private service: DiscountsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDiscount> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Discount>) => response.ok),
                map((discount: HttpResponse<Discount>) => discount.body)
            );
        }
        return of(new Discount());
    }
}

export const discountsRoute: Routes = [
    {
        path: '',
        component: DiscountsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DiscountsUpdatePopupComponent,
        resolve: {
            discount: DiscountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: ':id/edit',
        component: DiscountsUpdatePopupComponent,
        resolve: {
            discount: DiscountResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.discount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
