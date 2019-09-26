import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Top } from 'app/shared/model/top.model';
import { TopService } from './top.service';
import { TopComponent } from './top.component';
import { TopDetailComponent } from './top-detail.component';
import { TopUpdateComponent } from './top-update.component';
import { TopDeletePopupComponent } from './top-delete-dialog.component';
import { ITop } from 'app/shared/model/top.model';

@Injectable({ providedIn: 'root' })
export class TopResolve implements Resolve<ITop> {
    constructor(private service: TopService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITop> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Top>) => response.ok),
                map((top: HttpResponse<Top>) => top.body)
            );
        }
        return of(new Top());
    }
}

export const topRoute: Routes = [
    {
        path: '',
        component: TopComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.top.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TopDetailComponent,
        resolve: {
            top: TopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.top.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TopUpdateComponent,
        resolve: {
            top: TopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.top.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TopUpdateComponent,
        resolve: {
            top: TopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.top.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const topPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TopDeletePopupComponent,
        resolve: {
            top: TopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.top.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
