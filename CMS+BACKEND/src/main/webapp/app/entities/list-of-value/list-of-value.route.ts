import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ListOfValue } from 'app/shared/model/list-of-value.model';
import { ListOfValueService } from './list-of-value.service';
import { ListOfValueComponent } from './list-of-value.component';
import { ListOfValueDetailComponent } from './list-of-value-detail.component';
import { ListOfValueUpdateComponent } from './list-of-value-update.component';
import { ListOfValueDeletePopupComponent } from './list-of-value-delete-dialog.component';
import { IListOfValue } from 'app/shared/model/list-of-value.model';

@Injectable({ providedIn: 'root' })
export class ListOfValueResolve implements Resolve<IListOfValue> {
    constructor(private service: ListOfValueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IListOfValue> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ListOfValue>) => response.ok),
                map((listOfValue: HttpResponse<ListOfValue>) => listOfValue.body)
            );
        }
        return of(new ListOfValue());
    }
}

export const listOfValueRoute: Routes = [
    {
        path: '',
        component: ListOfValueComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'easyOrderApp.listOfValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ListOfValueDetailComponent,
        resolve: {
            listOfValue: ListOfValueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.listOfValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ListOfValueUpdateComponent,
        resolve: {
            listOfValue: ListOfValueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.listOfValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ListOfValueUpdateComponent,
        resolve: {
            listOfValue: ListOfValueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.listOfValue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const listOfValuePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ListOfValueDeletePopupComponent,
        resolve: {
            listOfValue: ListOfValueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'easyOrderApp.listOfValue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
